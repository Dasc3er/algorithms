package interfacce;

import java.io.IOException;

import client.Client;

/**
 * @author Thomas Zilio
 */
public interface IGestoreClient extends IGestore {
	default public Client crea() throws IOException {
		return new Client(this, inserisciUsername());
	}

	public String inserisciUsername();
}