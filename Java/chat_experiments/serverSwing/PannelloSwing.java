package serverSwing;

import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import interfacce.IGestore;
import interfacce.IPannello;
import interfacce.ITraduzione;
import server.GestoreServer;

/**
 * @author Thomas Zilio
 * @version 2.0 - PC1 4ai 5 maggio 2016
 */
public class PannelloSwing extends JPanel
		implements IPannello, ITraduzione, ActionListener {
	private int porta;
	private IGestore gestore;
	private GestoreServer gestoreServer;
	private JTextArea area;
	private JButton kickAll, cambiaStato, impostazioni, elimina;
	private JLabel utentiConnessi, info;

	public PannelloSwing(IGestore gestore, GestoreServer server) {
		super();
		try {
			setGestore(gestore);
			setGestoreServer(server);
			impostazioni();
			setLayout(new GridLayout(3, 1));

			JPanel pannello = new JPanel();
			pannello.setLayout(new GridLayout(2, 2));

			setUtentiConnessi(new JLabel(traduzione("userConnected") + ": -"));
			pannello.add(getUtentiConnessi());

			setInfo(new JLabel("Disconnesso"));
			pannello.add(getInfo());

			setCambiaStato(new JButton(traduzione("connect")));
			getCambiaStato().addActionListener(this);
			pannello.add(getCambiaStato());

			setElimina(new JButton(traduzione("delete")));
			getElimina().addActionListener(this);
			pannello.add(getElimina());

			setKickAll(new JButton(traduzione("kickAll")));
			getKickAll().addActionListener(this);
			pannello.add(getKickAll());

			add(pannello);

			setImpostazioni(new JButton(traduzione("settings")));
			getImpostazioni().addActionListener(this);
			add(getImpostazioni());

			setArea(new JTextArea());
			getArea().setLineWrap(true);
			getArea().setEditable(false);
			JScrollPane scroll = new JScrollPane(getArea());
			add(scroll);
		}
		catch (HeadlessException e) {
			System.err.println("Errore");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == getCambiaStato()) {
			if (getGestoreServer().getServer(this) == null
					|| !getGestoreServer().getServer(
							this).isOnline()) getGestoreServer().connetti(this,
									getPorta());
			else getGestoreServer().disconnetti(this);
		}
		else if (e.getSource() == getImpostazioni()) {
			impostazioni();
		}
		else if (e.getSource() == getElimina()) {
			getGestoreServer().disconnetti(this);
			getGestore().elimina(this);
		}
		else {
			if (getGestoreServer().getServer(this) != null
					&& getGestoreServer().getServer(
							this).numeroConnessi() != 0) {
				getGestoreServer().kickAll(this);
				avviso(traduzione("okKick"));
			}
			else avviso(traduzione("noKick"));
		}
	}

	@Override
	public void scrivi(String testo) {
		getArea().append("\n" + testo);
		getArea().setCaretPosition(area.getDocument().getLength());
	}

	@Override
	public void avviso(String testo) {
		scrivi("---------------------------- " + testo
				+ " ----------------------------");
	}

	@Override
	public void errore(String nome, String testo) {
		getGestore().errore(nome, testo);
	}

	@Override
	public void UIConnessa() {
		try {
			getInfo().setText(
					"Server " + InetAddress.getLocalHost().getHostAddress()
							+ ":" + getPorta());
		}
		catch (UnknownHostException e) {
			e.printStackTrace();
		}
		getCambiaStato().setText(traduzione("disconnect"));
	}

	@Override
	public void UIDisconnessa() {
		getInfo().setText(traduzione("stateDisconnected"));
		getCambiaStato().setText(traduzione("connect"));
	}

	@Override
	public void impostazioni() {
		getGestoreServer().disconnetti(this);
		String porta = null;
		porta = JOptionPane.showInputDialog(null,
				traduzione("insertPort") + ":", getPorta());
		int result;
		try {
			result = Integer.parseInt(porta);
		}
		catch (NumberFormatException e) {
			result = 0;
		}
		if (getPorta() == 0 && result == 0) System.exit(0);
		else if (porta == null || porta.length() < 2 || porta.length() > 6) {
			if (getPorta() == 0) {
				setPorta(7001);
				getGestore().errore(traduzione("defaultPort"),
						traduzione("defaultPortDescription"));
			}
			else getGestore().errore(traduzione("notEditedPort"),
					traduzione("notEditedPortDescription"));
		}
		else setPorta(result);
	}

	public IGestore getGestore() {
		return this.gestore;
	}

	public void setGestore(IGestore gestore) {
		this.gestore = gestore;
	}

	public GestoreServer getGestoreServer() {
		return this.gestoreServer;
	}

	public void setGestoreServer(GestoreServer server) {
		this.gestoreServer = server;
	}

	public JTextArea getArea() {
		return this.area;
	}

	public void setArea(JTextArea area) {
		this.area = area;
	}

	public JButton getKickAll() {
		return this.kickAll;
	}

	public void setKickAll(JButton kickAll) {
		this.kickAll = kickAll;
	}

	public JLabel getUtentiConnessi() {
		return this.utentiConnessi;
	}

	public void setUtentiConnessi(JLabel utentiConnessi) {
		this.utentiConnessi = utentiConnessi;
	}

	public int getPorta() {
		return this.porta;
	}

	public void setPorta(int porta) {
		this.porta = porta;
	}

	public JButton getCambiaStato() {
		return this.cambiaStato;
	}

	public void setCambiaStato(JButton cambiaStato) {
		this.cambiaStato = cambiaStato;
	}

	public JLabel getInfo() {
		return this.info;
	}

	public void setInfo(JLabel info) {
		this.info = info;
	}

	public JButton getImpostazioni() {
		return impostazioni;
	}

	public JButton getElimina() {
		return elimina;
	}

	public void setElimina(JButton elimina) {
		this.elimina = elimina;
	}

	public void setImpostazioni(JButton impostazioni) {
		this.impostazioni = impostazioni;
	}
}