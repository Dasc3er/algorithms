package clientSwing;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import client.Client;
import interfacce.IGestoreClient;
import interfacce.IPannello;
import interfacce.ITraduzione;

/**
 * @author Thomas Zilio
 * @version 2.0 - 1 maggio 2016
 */
public class ClientSwing
		implements IGestoreClient, ITraduzione, WindowListener, ActionListener {
	private Vector<IPannello> interfacce;
	private Client client;
	private JButton aggiungi;
	private JPanel pannello;

	public ClientSwing() throws IOException {
		setClient(crea());

		setPannello(new JPanel());
		getPannello().setLayout(new GridLayout(5, 1));
		setAggiungi(new JButton(traduzione("newClient")));
		getAggiungi().addActionListener(this);

		JFrame frame = new JFrame();
		frame.addWindowListener(this);
		Container container = frame.getContentPane();
		container.setLayout(new BorderLayout());
		container.add(getAggiungi(), BorderLayout.PAGE_START);
		container.add(getPannello(), BorderLayout.CENTER);

		frame.setTitle("Chat: " + getClient().getUsername());
		frame.setVisible(true);
		frame.setSize(400, 400);
	}

	public void aggiungi() {
		try {
			getPannello().add(new PannelloSwing(this, getClient()));
			getPannello().repaint();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void elimina(IPannello pannello) {
		getPannello().remove((PannelloSwing) pannello);
		getPannello().repaint();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		aggiungi();
	}

	@Override
	public String inserisciUsername() {
		String user = null;
		while (user == null || user.length() < 3 || user.length() > 15) {
			user = JOptionPane.showInputDialog(null,
					traduzione("insertUsername"));
			if (user == null) System.exit(0);
		}
		return user;
	}

	@Override
	public void errore(String nome, String testo) {
		JOptionPane.showMessageDialog(null, testo, nome,
				JOptionPane.ERROR_MESSAGE);
	}

	public void chiudi() {
		getClient().disconnettiTutto();
		System.exit(0);
	}

	public IPannello getInterfaccia(int index) {
		return getInterfacce().elementAt(index);
	}

	@Override
	public void windowClosing(WindowEvent e) {
		chiudi();
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Vector<IPannello> getInterfacce() {
		return this.interfacce;
	}

	public void setInterfacce(Vector<IPannello> interfacce) {
		this.interfacce = interfacce;
	}

	public JButton getAggiungi() {
		return aggiungi;
	}

	public void setAggiungi(JButton aggiungi) {
		this.aggiungi = aggiungi;
	}

	public JPanel getPannello() {
		return pannello;
	}

	public void setPannello(JPanel pannello) {
		this.pannello = pannello;
	}
}