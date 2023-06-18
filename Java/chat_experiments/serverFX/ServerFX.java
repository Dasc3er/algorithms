package serverFX;

import java.io.IOException;
import java.util.Vector;

import interfacce.IGestore;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import server.GestoreServer;

/**
 * @author Thomas Zilio
 * @version 2.0 - PC1 4ai 5 maggio 2016
 */
public class ServerFX extends Application
		implements IGestore, ITraduzione, EventHandler<ActionEvent> {
	private Vector<IPannello> interfacce;
	private GestoreServer gestoreServer;
	private Button aggiungi;
	private GridPane pannello;
	private int cont;

	@Override
	public void start(Stage primaryStage) throws IOException {
		setGestoreServer(new GestoreServer(this));
		primaryStage.setTitle("Server");

		BorderPane root = new BorderPane();

		setAggiungi(new Button(traduzione("newServer")));
		getAggiungi().setOnAction(this);
		getAggiungi().setMaxWidth(Double.MAX_VALUE);

		setPannello(new GridPane());

		root.setCenter(getPannello());
		root.setTop(getAggiungi());
		primaryStage.show();
		primaryStage.setScene(new Scene(root, 300, 250));
	}

	public void aggiungi() {
		getPannello().add(new PannelloFX(this, getGestoreServer()),
				(getCont() % 5), (getCont() / 5));
		setCont(getCont() + 1);
	}

	@Override
	public void elimina(IPannello pannello) {
		getPannello().getChildren().remove(pannello);
	}

	@Override
	public void handle(ActionEvent arg0) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				aggiungi();
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

	@Override
	public void stop() {
		getGestoreServer().disconnetti();
		System.exit(0);
	}

	public Vector<IPannello> getInterfacce() {
		return this.interfacce;
	}

	public void setInterfacce(Vector<IPannello> interfacce) {
		this.interfacce = interfacce;
	}

	public GestoreServer getGestoreServer() {
		return this.gestoreServer;
	}

	public void setGestoreServer(GestoreServer graficaGestore) {
		this.gestoreServer = graficaGestore;
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