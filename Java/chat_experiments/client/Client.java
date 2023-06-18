package client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import interfacce.IGestoreClient;
import interfacce.IPannello;
import interfacce.ITraduzione;

/**
 * Gestore delle connessioni.
 * 
 * @author Thomas Zilio
 * @version 2.0 - 1 maggio 2016
 *
 */
public class Client implements ITraduzione {
	private final int id = (int) (Math.random() * 100000);
	private String username;
	private IGestoreClient gestore;
	private HashMap<IPannello, ConnessioneClient> relazioni;

	/**
	 * Costruttore.
	 * 
	 * @param clientGraficaMultiplo
	 * @param username
	 * @throws IOException
	 */
	public Client(IGestoreClient clientGraficaMultiplo, String username)
			throws IOException {
		setRelazioni(new HashMap<IPannello, ConnessioneClient>());
		setGestore(clientGraficaMultiplo);
		setUsername(username);
	}

	/**
	 * 
	 * @param interfaccia
	 * @param IP
	 * @param porta
	 */
	public void connetti(IPannello interfaccia, String IP, int porta) {
		try {
			if (getConnessione(interfaccia) != null
					&& getConnessione(interfaccia).isConnesso()) getConnessione(
							interfaccia).disconnetti();
			getRelazioni().put(interfaccia,
					new ConnessioneClient(this, IP, porta));
			interfaccia.UIConnessa();
		}
		catch (IOException e) {
			getGestore().errore(traduzione("connectionError"),
					traduzione("connectionErrorDescription"));
		}
	}

	/**
	 * 
	 * @param interfaccia
	 */
	public void disconnetti(IPannello interfaccia) {
		disconnetti(getConnessione(interfaccia));
	}

	/**
	 * 
	 * @param connessione
	 */
	public void disconnetti(ConnessioneClient connessione) {
		try {
			if (connessione != null && connessione.isConnesso()) {
				connessione.disconnetti();
				getInterfaccia(connessione).UIDisconnessa();
				getRelazioni().put(getInterfaccia(connessione), null);
			}
		}
		catch (IOException e) {
			getGestore().errore(traduzione("disconnectionError"),
					traduzione("disconnectionErrorDescription"));
		}
	}

	/**
	 * 
	 */
	public void disconnettiTutto() {
		Set<IPannello> lista = getRelazioni().keySet();
		for (IPannello g : lista)
			disconnetti(g);
	}

	/**
	 * 
	 * @param connessione
	 * @param testo
	 */
	public void scrivi(ConnessioneClient connessione, String testo) {
		getInterfaccia(connessione).scrivi(testo);
	}

	/**
	 * 
	 * @param connessione
	 * @param testo
	 */
	public void avviso(ConnessioneClient connessione, String testo) {
		getInterfaccia(connessione).avviso(testo);
	}

	/**
	 * 
	 * @param interfaccia
	 * @param testo
	 */
	public void invia(IPannello interfaccia, String testo) {
		if (getConnessione(interfaccia).isConnesso()) getConnessione(
				interfaccia).invia(testo);
		else getGestore().errore(traduzione("sendError"),
				traduzione("sendErrorDescription"));
	}

	/**
	 * 
	 * @param interfaccia
	 * @return
	 */
	public ConnessioneClient getConnessione(IPannello interfaccia) {
		if (!getRelazioni().containsKey(interfaccia)) getRelazioni().put(
				interfaccia, null);
		return getRelazioni().get(interfaccia);
	}

	/**
	 * 
	 * @param connesione
	 * @return
	 */
	public IPannello getInterfaccia(ConnessioneClient connesione) {
		for (Entry<IPannello, ConnessioneClient> entry : getRelazioni().entrySet()) {
			if (entry.getValue() == connesione) { return entry.getKey(); }
		}
		return null;
	}

	/**
	 * 
	 * @param numero
	 * @return
	 */
	public static String formalizza(int numero) {
		return String.format("%05d%n", numero);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public IGestoreClient getGestore() {
		return gestore;
	}

	public void setGestore(IGestoreClient gestore) {
		this.gestore = gestore;
	}

	public HashMap<IPannello, ConnessioneClient> getRelazioni() {
		return relazioni;
	}

	public void setRelazioni(HashMap<IPannello, ConnessioneClient> relazioni) {
		this.relazioni = relazioni;
	}

	public int getId() {
		return id;
	}
}