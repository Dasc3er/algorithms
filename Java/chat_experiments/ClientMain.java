import java.io.IOException;

import javax.swing.SwingUtilities;

import clientFX.ClientFX;
import clientSwing.ClientSwing;
import javafx.application.Application;

public class ClientMain {
	public static void main(String[] args) {
		Double version = Double.parseDouble(
				System.getProperty("java.specification.version"));
		if (version >= 1.8) Application.launch(ClientFX.class, args);
		else {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					try {
						new ClientSwing();
					}
					catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
		}
	}
}
