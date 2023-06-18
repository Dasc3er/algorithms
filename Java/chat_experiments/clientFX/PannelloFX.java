package clientFX;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;

import client.Client;
import interfacce.IGestoreClient;
import interfacce.IPannello;
import interfacce.ITraduzione;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

/**
 * @author Thomas Zilio
 */
public class PannelloFX extends VBox
		implements IPannello, ITraduzione, EventHandler<ActionEvent> {
	private IGestoreClient gestore;
	private Client client;
	private int porta = 7001;
	private String IP = "192.168.2.101";

	private TextArea chat, messaggio;
	private Label stato, infoUtenti;
	private Button invia, pulisciCronologia, impostazioni, cambiaStato, elimina;

	public PannelloFX(IGestoreClient gestore, Client client)
			throws IOException {
		super();
		setGestore(gestore);
		setClient(client);

		HBox hbox = new HBox();
		hbox.setPadding(new Insets(15, 12, 15, 12));
		hbox.setSpacing(10);
		hbox.setStyle("-fx-background-color: #336699;");

		setPulisciCronologia(new Button(traduzione("clearLogs")));
		getPulisciCronologia().setOnAction(this);

		setImpostazioni(new Button(traduzione("settings")));
		getImpostazioni().setOnAction(this);

		setCambiaStato(new Button(traduzione("connect")));
		getCambiaStato().setOnAction(this);

		setElimina(new Button(traduzione("delete")));
		getElimina().setOnAction(this);

		hbox.getChildren().addAll(getPulisciCronologia(), getImpostazioni(),
				getCambiaStato(), getElimina());

		VBox vbox = new VBox();

		setStato(new Label(traduzione("stateDisconnected")));

		setInfoUtenti(new Label(traduzione("userConnected") + ": -"));

		setChat(new TextArea());
		getChat().setEditable(false);

		setMessaggio(new TextArea(traduzione("connectBeforeWriting")));
		getMessaggio().setEditable(true);
		getMessaggio().setDisable(true);
		getMessaggio().setPromptText(traduzione("okWriting"));

		setInvia(new Button("Invia"));
		getInvia().setOnAction(this);
		getInvia().setDisable(true);
		getInvia().setMaxWidth(Double.MAX_VALUE);

		vbox.getChildren().addAll(getStato(), getInfoUtenti(), getChat(),
				getMessaggio(), getInvia());

		getChildren().addAll(hbox, vbox);
	}

	@Override
	public void impostazioni() {
		getClient().disconnetti(this);

		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.setTitle(traduzione("settings"));
		dialog.setHeaderText(null);

		ButtonType salva = new ButtonType("Salva", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(salva,
				ButtonType.CANCEL);

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField IPText = new TextField();
		IPText.setPromptText("Indirizzo IP");
		IPText.setText(getIP());
		TextField portText = new TextField();
		portText.setPromptText("Porta");
		portText.setText("" + getPorta());

		grid.add(new Label(traduzione("IP") + ": "), 0, 0);
		grid.add(IPText, 1, 0);
		grid.add(new Label(traduzione("port") + ": "), 0, 1);
		grid.add(portText, 1, 1);

		dialog.getDialogPane().setContent(grid);

		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == salva) return new Pair<>(IPText.getText(), portText.getText());
			return null;
		});

		Optional<Pair<String, String>> result = dialog.showAndWait();
		result.ifPresent(dati -> {
			int errore = 0;
			String IP = dati.getKey();
			int porta = 0;
			if (IP.equalsIgnoreCase("localhost")) errore = 0;
			else {
				try {
					InetAddress inet = InetAddress.getByName(getIP());
					boolean isIPv4 = inet.getHostAddress().equals(getIP())
							&& inet instanceof Inet4Address;
					if (!isIPv4) errore = 1;
				}
				catch (UnknownHostException e1) {
					errore = 1;
				}
			}
			try {
				String temp = dati.getValue();
				if (temp.length() < 1 && temp.length() > 6) {
					if (errore == 0) errore = 2;
					else errore = 3;
				}
				else porta = Integer.parseInt(temp);
			}
			catch (NumberFormatException e1) {
				if (errore == 0) errore = 2;
				else errore = 3;
			}
			if (errore == 1) errore(traduzione("IPError"),
					traduzione("IPErrorDescription"));
			else if (errore == 2) errore(traduzione("portError"),
					traduzione("portErrorDescription"));
			else if (errore == 3) errore(traduzione("IPPortError"),
					traduzione("IPPortErrorDescription"));
			else {
				scrivi(traduzione("IPChanged") + IP + ": " + porta);
				setIP(IP);
				setPorta(porta);
			}
		});
	}

	@Override
	public void scrivi(String testo) {
		getChat().appendText("\n" + testo);
	}

	@Override
	public void avviso(String testo) {
		scrivi("---------------------------- " + testo
				+ " ----------------------------");
	}

	@Override
	public void errore(String nome, String testo) {
		getGestore().errore(nome, testo);
	}

	@Override
	public void UIConnessa() {
		getCambiaStato().setText(traduzione("disconnect"));
		getStato().setText(
				traduzione("stateConnected") + getIP() + ":" + getPorta());
		getInvia().setDisable(false);
		getMessaggio().setDisable(false);
		getMessaggio().setText("");
	}

	@Override
	public void UIDisconnessa() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				getCambiaStato().setText(traduzione("connect"));
				getInfoUtenti().setText(traduzione("userConnected") + ": -");
				getStato().setText(traduzione("stateDisconnected"));
				getInvia().setDisable(true);
				getMessaggio().setDisable(true);
				getMessaggio().setText(traduzione("connectBeforeWriting"));
			}
		});
	}

	@Override
	public void handle(ActionEvent e) {
		if (e.getSource() == getPulisciCronologia()) {
			if (!getChat().getText().equals("")) {
				getChat().setText("---------------------------- "
						+ traduzione("clearLogsDone")
						+ " ----------------------------");
			}
		}
		else if (e.getSource() == getImpostazioni()) {
			impostazioni();
		}
		else if (e.getSource() == getCambiaStato()) {
			if (getClient().getConnessione(this) == null
					|| !getClient().getConnessione(
							this).isConnesso()) getClient().connetti(this,
									getIP(), getPorta());
			else getClient().disconnetti(this);
		}
		else if (e.getSource() == getElimina()) {
			if (getClient().getConnessione(
					this) != null) getClient().disconnetti(this);
			getGestore().elimina(this);
		}
		else if (e.getSource() == getInvia()) {
			String testo = getMessaggio().getText().trim();
			if (!testo.equals("")) getClient().invia(this, testo);
			getMessaggio().setText("");
		}
	}

	public TextArea getChat() {
		return chat;
	}

	public void setChat(TextArea chat) {
		this.chat = chat;
	}

	public TextArea getMessaggio() {
		return messaggio;
	}

	public void setMessaggio(TextArea messaggio) {
		this.messaggio = messaggio;
	}

	public Label getStato() {
		return stato;
	}

	public void setStato(Label stato) {
		this.stato = stato;
	}

	public Label getInfoUtenti() {
		return infoUtenti;
	}

	public void setInfoUtenti(Label infoUtenti) {
		this.infoUtenti = infoUtenti;
	}

	public Button getInvia() {
		return invia;
	}

	public void setInvia(Button invia) {
		this.invia = invia;
	}

	public Button getPulisciCronologia() {
		return pulisciCronologia;
	}

	public void setPulisciCronologia(Button pulisciCronologia) {
		this.pulisciCronologia = pulisciCronologia;
	}

	public Button getImpostazioni() {
		return impostazioni;
	}

	public void setImpostazioni(Button impostazioni) {
		this.impostazioni = impostazioni;
	}

	public Button getCambiaStato() {
		return cambiaStato;
	}

	public void setCambiaStato(Button cambiaStato) {
		this.cambiaStato = cambiaStato;
	}

	public int getPorta() {
		return porta;
	}

	public void setPorta(int porta) {
		this.porta = porta;
	}

	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		IP = iP;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public IGestoreClient getGestore() {
		return gestore;
	}

	public void setGestore(IGestoreClient gestore) {
		this.gestore = gestore;
	}

	public Button getElimina() {
		return elimina;
	}

	public void setElimina(Button elimina) {
		this.elimina = elimina;
	}

}