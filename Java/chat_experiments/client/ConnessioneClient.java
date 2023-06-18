package client;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

import interfacce.ITraduzione;

/**
 * Singola connessione.
 * 
 * @author Thomas Zilio
 * @version 2.0 - 1 maggio 2016
 */
public class ConnessioneClient extends Thread implements ITraduzione {
	private int porta, numeroUtentiConnessi = 1;
	private boolean connesso = false, serverOffline = false;
	private Socket socket;
	private PrintStream output;
	private Scanner input;
	private String IP;
	private Client client;

	/**
	 * 
	 * @param client
	 * @throws IOException
	 */
	public ConnessioneClient(Client client) throws IOException {
		this(client, "172.16.1.234", 7001);
	}

	/**
	 * 
	 * @param client
	 * @param IP
	 * @param porta
	 * @throws IOException
	 */
	public ConnessioneClient(Client client, String IP, int porta)
			throws IOException {
		setClient(client);
		setIP(IP);
		setPorta(porta);
		setServerOffline(false);
		setSocket(new Socket(getIP(), getPorta()));
		setOutput(new PrintStream(getSocket().getOutputStream()));
		setInput(new Scanner(getSocket().getInputStream()));
		inviaLog(getClient().getUsername() + " " + traduzione("joined"));
		setConnesso(true);
		this.start();
	}

	/**
	 * 
	 */
	@Override
	public void run() {
		String testoRicevuto = null;
		try {
			while (isConnesso()) {
				testoRicevuto = input.nextLine();
				String testo = cercaEseguiComando(testoRicevuto);
				// eventuali controlli della stringa prima di proseguire
				if (testo != null) scrivi(testo);
			}
		}
		catch (IOException e) {
		}
		catch (NoSuchElementException e) {
		}
	}

	/**
	 * 
	 * @throws IOException
	 */
	public void disconnetti() throws IOException {
		setConnesso(false);
		getSocket().close();
		setSocket(null);
		getInput().close();
		setInput(null);
		avviso(getClient().getUsername() + " " + traduzione("left"));
		avviso(traduzione("sessionEnd"));
		if (!isServerOffline()) {
			inviaLog("endclientserverconnection123456789");
			inviaLog(getClient().getUsername() + " " + traduzione("left"));
		}
		getOutput().close();
		setOutput(null);
	}

	/**
	 * 
	 * @param comando
	 * @return
	 * @throws IOException
	 */
	public String cercaEseguiComando(String comando) throws IOException {
		if (comando.indexOf("endclientserverconnection123456789") != -1) {
			getClient().disconnetti(this);
			return null;
		}
		else if (comando.indexOf(traduzione("joined")) != -1) {
			setNumeroUtentiConnessi(getNumeroUtentiConnessi() + 1);
		}
		else if (comando.indexOf(traduzione("left")) != -1) {
			setNumeroUtentiConnessi(getNumeroUtentiConnessi() - 1);
		}
		return comando;
	}

	/**
	 * 
	 * @param testo
	 */
	public void invia(String testo) {
		inviaLog(getClient().getUsername() + ": " + testo);
	}

	/**
	 * 
	 * @param testo
	 */
	public void inviaLog(String testo) {
		getOutput().println(testo);
	}

	/**
	 * 
	 * @param testo
	 */
	public void scrivi(String testo) {
		getClient().scrivi(this, testo);
	}

	/**
	 * 
	 * @param testo
	 */
	public void avviso(String testo) {
		getClient().avviso(this, testo);
	}

	public int getPorta() {
		return porta;
	}

	public void setPorta(int porta) {
		this.porta = porta;
	}

	public int getNumeroUtentiConnessi() {
		return numeroUtentiConnessi;
	}

	public void setNumeroUtentiConnessi(int numeroUtentiConnessi) {
		this.numeroUtentiConnessi = numeroUtentiConnessi;
	}

	public boolean isConnesso() {
		return connesso;
	}

	public void setConnesso(boolean connesso) {
		this.connesso = connesso;
	}

	public boolean isServerOffline() {
		return serverOffline;
	}

	public void setServerOffline(boolean serverOffline) {
		this.serverOffline = serverOffline;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public PrintStream getOutput() {
		return output;
	}

	public void setOutput(PrintStream output) {
		this.output = output;
	}

	public Scanner getInput() {
		return input;
	}

	public void setInput(Scanner input) {
		this.input = input;
	}

	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		IP = iP;
	}

	public Client getClient() {
		return this.client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
}