package server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import interfacce.IGestore;
import interfacce.IPannello;
import interfacce.ITraduzione;

/**
 * @author Thomas Zilio
 * @version 2.0 - PC1 4ai 5 maggio 2016
 */
public class GestoreServer implements ITraduzione {
	private IGestore gestore;
	private HashMap<IPannello, Server> relazioni;

	public GestoreServer(IGestore gestore) {
		setGestore(gestore);
		setRelazioni(new HashMap<IPannello, Server>());
	}

	public void connetti(IPannello interfaccia, int porta) {
		try {
			if (getServer(interfaccia) != null
					&& getServer(interfaccia).isOnline()) getServer(
							interfaccia).disconnetti();
			getRelazioni().put(interfaccia, new Server(this, porta));
			interfaccia.UIConnessa();
		}
		catch (IOException e) {
			getGestore().errore(traduzione("connectionError"),
					traduzione("connectionServerErrorDescription"));
		}
	}

	public void kickAll(IPannello interfaccia) {
		getServer(interfaccia).kickAll();;
	}

	public void disconnetti(IPannello interfaccia) {
		disconnetti(getServer(interfaccia));
	}

	public void disconnetti(Server server) {
		try {
			if (server != null && server.isOnline()) {
				server.disconnetti();
				getInterfaccia(server).UIDisconnessa();
				getRelazioni().put(getInterfaccia(server), null);
			}
		}
		catch (IOException e) {
			getGestore().errore(traduzione("disconnectionError"),
					traduzione("disconnectionErrorDescription"));
		}
	}

	public void disconnetti() {
		Set<IPannello> lista = getRelazioni().keySet();
		for (IPannello g : lista)
			disconnetti(g);
	}

	public void scrivi(Server server, String testo) {
		getInterfaccia(server).scrivi(testo);
	}

	public void avviso(Server server, String testo) {
		getInterfaccia(server).avviso(testo);
	}

	public void errore(Server server, String nome, String testo) {
		getInterfaccia(server).errore(nome, testo);
	}

	public Server getServer(IPannello interfaccia) {
		if (!getRelazioni().containsKey(interfaccia)) getRelazioni().put(
				interfaccia, null);
		return getRelazioni().get(interfaccia);
	}

	public IPannello getInterfaccia(Server server) {
		for (Entry<IPannello, Server> entry : getRelazioni().entrySet()) {
			if (entry.getValue() == server) { return entry.getKey(); }
		}
		return null;
	}

	public IGestore getGestore() {
		return this.gestore;
	}

	public void setGestore(IGestore gestore) {
		this.gestore = gestore;
	}

	public HashMap<IPannello, Server> getRelazioni() {
		return this.relazioni;
	}

	public void setRelazioni(HashMap<IPannello, Server> relazioni) {
		this.relazioni = relazioni;
	}

}