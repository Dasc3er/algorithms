package damaSwing;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author Thomas Zilio
 */
public class Client {
	private Log log;

	public Client() {

	}

	public Client(Log log) {
		setLog(log);
		invia();
	}

	public Log getLog() {
		return this.log;
	}

	public void setLog(Log log) {
		this.log = log;
	}

	public void invia() {
		try {
			Socket socket = new Socket("127.0.0.1", 7777);
			System.out.println("Socket al Client:" + socket);

			ObjectOutputStream oos =
					new ObjectOutputStream(socket.getOutputStream());

			ObjectInputStream ois =
					new ObjectInputStream(socket.getInputStream());

			oos.writeObject(getLog().last());
			String message = (String) ois.readObject();
			System.out.println("CLIENT - Message: " + message);

			ois.close();
			oos.close();
			socket.close();
		}
		catch (UnknownHostException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}