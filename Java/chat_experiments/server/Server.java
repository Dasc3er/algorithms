package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

/**
 * @author Thomas Zilio
 * @version 2.0 - PC1 4ai 5 maggio 2016
 */
public class Server extends Thread {
	private GestoreServer gestore;
	private String nome;
	private ServerSocket server;
	private Vector<ConnessioneServer> connessi;
	private boolean online = true;

	public Server(GestoreServer gestore, int porta) throws IOException {
		setNome("Prova");
		setGestore(gestore);
		setConnessi(new Vector<ConnessioneServer>(10, 10));
		setServer(new ServerSocket(porta));
		this.start();
	}

	@Override
	public void run() {
		while (online) {
			try {
				Socket client = server.accept();
				getConnessi().add(new ConnessioneServer(this, client));
			}
			catch (IOException e) {
			}
		}
	}

	public void scrivi(String testo) {
		getGestore().scrivi(this, testo);
	}

	public void avviso(String testo) {
		getGestore().avviso(this, testo);
	}

	public void errore(String nome, String testo) {
		getGestore().errore(this, nome, testo);
	}

	public void kickAll() {
		for (int i = 0; i < getConnessi().size(); i++)
			getConnessi().get(i--).kick();
	}

	public void inviaATutti(String testo) {
		for (int i = 0; i < getConnessi().size(); i++)
			getConnessi().get(i).invia(testo);
	}

	public void disconnetti() throws IOException {
		kickAll();
		setOnline(false);
		getServer().close();
	}

	public int numeroConnessi() {
		return getConnessi().size();
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public GestoreServer getGestore() {
		return gestore;
	}

	public void setGestore(GestoreServer gestore) {
		this.gestore = gestore;
	}

	public ServerSocket getServer() {
		return server;
	}

	public void setServer(ServerSocket server) {
		this.server = server;
	}

	public Vector<ConnessioneServer> getConnessi() {
		return connessi;
	}

	public void setConnessi(Vector<ConnessioneServer> connessi) {
		this.connessi = connessi;
	}

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}
}