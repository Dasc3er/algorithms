package clientFX;

import java.io.IOException;
import java.util.Optional;
import java.util.Vector;

import client.Client;
import interfacce.IGestoreClient;
import interfacce.IPannello;
import interfacce.ITraduzione;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ClientFX extends Application
		implements IGestoreClient, ITraduzione, EventHandler<ActionEvent> {
	private Vector<IPannello> interfacce;
	private Client client;
	private Button aggiungi;
	private GridPane pannello;
	private int cont;

	@Override
	public void start(Stage primaryStage) throws IOException {
		setClient(crea());
		primaryStage.setTitle("Chat: " + getClient().getUsername());

		BorderPane root = new BorderPane();

		setAggiungi(new Button(traduzione("newClient")));
		getAggiungi().setOnAction(this);
		getAggiungi().setMaxWidth(Double.MAX_VALUE);

		setPannello(new GridPane());

		root.setCenter(getPannello());
		root.setTop(getAggiungi());

		primaryStage.setScene(new Scene(root, 300, 250));
		primaryStage.show();
	}

	public void aggiungi() throws IOException {
		getPannello().add(new PannelloFX(this, getClient()), (getCont() % 5),
				(getCont() / 5));
		setCont(getCont() + 1);
	}

	@Override
	public void elimina(IPannello pannello) {
		getPannello().getChildren().remove(pannello);
	}

	@Override
	public String inserisciUsername() {
		String user = null;
		do {
			TextInputDialog dialog = new TextInputDialog();
			dialog.setTitle("Username");
			dialog.setHeaderText(traduzione("insertUsername"));

			Optional<String> result = dialog.showAndWait();
			if (result.isPresent()) user = result.get();
			if (user == null) System.exit(0);
		}while (user.length() < 3 || user.length() > 15);
		return user;
	}

	@Override
	public void handle(ActionEvent arg0) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try {
					aggiungi();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public void errore(String nome, String testo) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(nome);
		alert.setHeaderText(null);
		alert.setContentText(testo);
		alert.showAndWait();
	}

	public IPannello getInterfaccia(int index) {
		return getInterfacce().elementAt(index);
	}

	@Override
	public void stop() {
		getClient().disconnettiTutto();
		System.exit(0);
	}

	public Vector<IPannello> getInterfacce() {
		return interfacce;
	}

	public void setInterfacce(Vector<IPannello> interfacce) {
		this.interfacce = interfacce;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Button getAggiungi() {
		return aggiungi;
	}

	public void setAggiungi(Button aggiungi) {
		this.aggiungi = aggiungi;
	}

	public GridPane getPannello() {
		return pannello;
	}

	public void setPannello(GridPane pannello) {
		this.pannello = pannello;
	}

	public int getCont() {
		return cont;
	}

	public void setCont(int cont) {
		this.cont = cont;
	}
}
