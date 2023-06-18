package serverSwing;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import interfacce.IGestore;
import interfacce.IPannello;
import interfacce.ITraduzione;
import server.GestoreServer;

/**
 * @author Thomas Zilio
 * @version 2.0 - PC1 4ai 5 maggio 2016
 */
public class ServerSwing
		implements IGestore, ITraduzione, WindowListener, ActionListener {
	private Vector<IPannello> interfacce;
	private GestoreServer gestoreServer;
	private JButton aggiungi;
	private JPanel pannello;

	public ServerSwing() {
		setGestoreServer(new GestoreServer(this));

		setPannello(new JPanel());
		getPannello().setLayout(new GridLayout(5, 1));
		setAggiungi(new JButton(traduzione("newClient")));
		getAggiungi().addActionListener(this);

		JFrame frame = new JFrame("Server");
		frame.addWindowListener(this);
		Container container = frame.getContentPane();
		container.setLayout(new BorderLayout());
		container.add(getAggiungi(), BorderLayout.PAGE_START);
		container.add(getPannello(), BorderLayout.CENTER);

		frame.setVisible(true);
		frame.setSize(400, 400);
	}

	public void aggiungi() {
		getPannello().add(new PannelloSwing(this, getGestoreServer()));
		getPannello().repaint();
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
	public void errore(String nome, String testo) {
		javax.swing.JOptionPane.showMessageDialog(null, testo, nome,
				JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public void windowClosing(WindowEvent e) {
		getGestoreServer().disconnetti();
		System.exit(0);
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

	public Vector<IPannello> getInterfacce() {
		return this.interfacce;
	}

	public void setInterfacce(Vector<IPannello> interfacce) {
		this.interfacce = interfacce;
	}

	public GestoreServer getGestoreServer() {
		return this.gestoreServer;
	}

	public void setGestoreServer(GestoreServer graficaGestore) {
		this.gestoreServer = graficaGestore;
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