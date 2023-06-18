package serverFX;

import java.awt.HeadlessException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;

import interfacce.IGestore;
import interfacce.IPannello;
import interfacce.ITraduzione;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import server.GestoreServer;

/**
 * @author Thomas Zilio
 * @version 2.0 - PC1 4ai 5 maggio 2016
 */
public class PannelloFX extends VBox
		implements IPannello, ITraduzione, EventHandler<ActionEvent> {
	private int porta;
	private IGestore gestore;
	private GestoreServer gestoreServer;
	private TextArea area;
	private Button kickAll, cambiaStato, impostazioni, elimina;
	private Label utentiConnessi, info;

	public PannelloFX(IGestore gestore, GestoreServer server) {
		super();
		try {
			setGestore(gestore);
			setGestoreServer(server);
			impostazioni();

			HBox hbox1 = new HBox();
			hbox1.setPadding(new Insets(15, 12, 15, 12));
			hbox1.setSpacing(10);
			hbox1.setStyle("-fx-background-color: #336699;");

			setUtentiConnessi(new Label(traduzione("userConnected") + ": -"));
			setInfo(new Label(traduzione("stateDisconnected")));

			hbox1.getChildren().addAll(getUtentiConnessi(), getInfo());

			HBox hbox2 = new HBox();
			hbox2.setPadding(new Insets(15, 12, 15, 12));
			hbox2.setSpacing(10);

			setImpostazioni(new Button(traduzione("settings")));
			getImpostazioni().setOnAction(this);

			setCambiaStato(new Button(traduzione("connect")));
			getCambiaStato().setOnAction(this);

			setElimina(new Button(traduzione("delete")));
			getElimina().setOnAction(this);

			setKickAll(new Button(traduzione("kickAll")));
			getKickAll().setOnAction(this);

			hbox2.getChildren().addAll(getCambiaStato(), getImpostazioni(),
					getKickAll(), getElimina());

			setArea(new TextArea());
			getArea().setEditable(false);

			getChildren().addAll(hbox1, hbox2, getArea());
		}
		catch (HeadlessException e) {
			System.err.println("Errore");
		}
	}

	@Override
	public void handle(ActionEvent e) {
		if (e.getSource() == getCambiaStato()) {
			if (getGestoreServer().getServer(this) == null
					|| !getGestoreServer().getServer(
							this).isOnline()) getGestoreServer().connetti(this,
									getPorta());
			else getGestoreServer().disconnetti(this);
		}
		else if (e.getSource() == getImpostazioni()) {
			impostazioni();
		}
		else if (e.getSource() == getElimina()) {
			getGestoreServer().disconnetti(this);
			getGestore().elimina(this);
		}
		else {
			if (getGestoreServer().getServer(this) != null
					&& getGestoreServer().getServer(
							this).numeroConnessi() != 0) {
				getGestoreServer().kickAll(this);
				avviso(traduzione("okKick"));
			}
			else avviso(traduzione("noKick"));
		}
	}

	@Override
	public void scrivi(String testo) {
		getArea().appendText("\n" + testo);
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
		try {
			getInfo().setText(
					"Server " + InetAddress.getLocalHost().getHostAddress()
							+ ":" + getPorta());
		}
		catch (UnknownHostException e) {
			e.printStackTrace();
		}
		getCambiaStato().setText(traduzione("disconnect"));
	}

	@Override
	public void UIDisconnessa() {
		getInfo().setText(traduzione("stateDisconnected"));
		getCambiaStato().setText(traduzione("connect"));
	}

	@Override
	public void impostazioni() {
		getGestoreServer().disconnetti(this);

		String porta = null;
		TextInputDialog dialog;
		if (getPorta() == 0) dialog = new TextInputDialog();
		else dialog = new TextInputDialog("" + getPorta());
		dialog.setTitle("Porta");
		dialog.setHeaderText(traduzione("insertPort"));

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) porta = result.get();

		int numero;
		try {
			numero = Integer.parseInt(porta);
		}
		catch (NumberFormatException e) {
			numero = 0;
		}

		if (getPorta() == 0 && numero == 0) System.exit(0);
		else if (porta != null) {
			if ((porta.length() < 2 || porta.length() > 6) && getPorta() == 0) {
				setPorta(7001);
				getGestore().errore(traduzione("defaultPort"),
						traduzione("defaultPortDescription"));
			}
			else setPorta(numero);
		}
	}

	public IGestore getGestore() {
		return this.gestore;
	}

	public void setGestore(IGestore gestore) {
		this.gestore = gestore;
	}

	public GestoreServer getGestoreServer() {
		return this.gestoreServer;
	}

	public void setGestoreServer(GestoreServer server) {
		this.gestoreServer = server;
	}

	public TextArea getArea() {
		return this.area;
	}

	public void setArea(TextArea area) {
		this.area = area;
	}

	public Button getKickAll() {
		return this.kickAll;
	}

	public void setKickAll(Button kickAll) {
		this.kickAll = kickAll;
	}

	public Label getUtentiConnessi() {
		return this.utentiConnessi;
	}

	public void setUtentiConnessi(Label utentiConnessi) {
		this.utentiConnessi = utentiConnessi;
	}

	public int getPorta() {
		return this.porta;
	}

	public void setPorta(int porta) {
		this.porta = porta;
	}

	public Button getCambiaStato() {
		return this.cambiaStato;
	}

	public void setCambiaStato(Button cambiaStato) {
		this.cambiaStato = cambiaStato;
	}

	public Label getInfo() {
		return this.info;
	}

	public void setInfo(Label info) {
		this.info = info;
	}

	public Button getImpostazioni() {
		return impostazioni;
	}

	public void setImpostazioni(Button impostazioni) {
		this.impostazioni = impostazioni;
	}

	public Button getElimina() {
		return elimina;
	}

	public void setElimina(Button elimina) {
		this.elimina = elimina;
	}

}