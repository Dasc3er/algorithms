
import javax.swing.SwingUtilities;

import javafx.application.Application;
import serverFX.ServerFX;
import serverSwing.ServerSwing;

public class ServerMain {
	public static void main(String[] args) {
		Double version = Double.parseDouble(
				System.getProperty("java.specification.version"));
		if (version >= 1.8) Application.launch(ServerFX.class, args);
		else {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					new ServerSwing();
				}
			});
		}
	}
}
