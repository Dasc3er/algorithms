package server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

import interfacce.ITraduzione;

/**
 * @author Thomas Zilio
 * @version 2.0 - PC1 4ai 5 maggio 2016
 */
class ConnessioneServer extends Thread implements ITraduzione {
	private Server server;
	private Socket client;
	private Scanner input;
	private PrintStream output;
	private boolean connesso = true;
	private String IP;

	public ConnessioneServer(Server server, Socket client) throws IOException {
		setServer(server);
		setClient(client);
		setInput(new Scanner(getClient().getInputStream()));
		setOutput(new PrintStream(getClient().getOutputStream()));
		this.start();
	}

	@Override
	public void run() {
		setIP(getClient().getInetAddress().toString());
		getServer().scrivi(traduzione("newClientConnected") + " --> " + IP);
		String testo = null;
		while (isConnesso()) {
			try {
				testo = input.nextLine();
				testo = cercaEseguiComando(testo);
				if (testo != null) {
					getServer().inviaATutti(testo);
					getServer().scrivi(testo);
				}
			}
			catch (NoSuchElementException e) {
				setConnesso(false);
			}
		}

		disconnetti();

		if (getServer().getConnessi().size() > 1) {
			getServer().avviso(getServer().getConnessi().size() + " "
					+ traduzione("userOnline"));
		}
		else if (getServer().getConnessi().size() == 1) {
			getServer().inviaATutti(traduzione("oneConnected"));
			getServer().avviso(traduzione("allButOneDisconnected"));
		}
		else getServer().avviso(traduzione("allDisconnected"));
	}

	public void disconnetti() {
		getInput().close();
		getOutput().close();
		try {
			getClient().close();
		}
		catch (IOException e) {
			getServer().errore(traduzione("disconnectionError"),
					traduzione("disconnectionErrorDescription"));
		}
		getServer().avviso(traduzione("connectionEnded") + IP);
		getServer().getConnessi().remove(this);
	}

	public String cercaEseguiComando(String comando) {
		if (comando.indexOf("clearserver") != -1) {
			invia(traduzione("clearLogsDone"));
			getServer().inviaATutti(
					getClient().getInetAddress().getHostAddress() + " "
							+ traduzione("hasClearedLogs"));
			getServer().avviso(getClient().getInetAddress().getHostAddress()
					+ " " + traduzione("hasClearedLogs"));
			return null;
		}
		else if (comando.indexOf("endclientserverconnection123456789") != -1) {
			setConnesso(false);
			return null;
		}
		return comando;
	}

	public void invia(String testo) {
		output.println(testo);
	}

	public void kick() {
		invia(traduzione("kicked"));
		invia("endclientserverconnection123456789");
	}

	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	public Scanner getInput() {
		return input;
	}

	public void setInput(Scanner input) {
		this.input = input;
	}

	public PrintStream getOutput() {
		return output;
	}

	public void setOutput(PrintStream output) {
		this.output = output;
	}

	public boolean isConnesso() {
		return connesso;
	}

	public void setConnesso(boolean connesso) {
		this.connesso = connesso;
	}

	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		IP = iP;
	}

	public Socket getClient() {
		return client;
	}

	public void setClient(Socket client) {
		this.client = client;
	}

}