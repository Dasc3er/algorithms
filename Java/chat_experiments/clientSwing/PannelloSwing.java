package clientSwing;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import client.Client;
import interfacce.IGestoreClient;
import interfacce.IPannello;
import interfacce.ITraduzione;

/**
 * @author Thomas Zilio
 */
public class PannelloSwing extends JPanel
		implements IPannello, ITraduzione, ActionListener, KeyListener {
	private IGestoreClient gestore;
	private Client client;
	private int porta = 7001;
	private String IP = "192.168.2.101";

	private JTextArea chat, messaggio;
	private JLabel stato, infoUtenti;
	private JButton invia, pulisciCronologia, impostazioni, cambiaStato,
			elimina;

	public PannelloSwing(IGestoreClient gestore, Client client)
			throws IOException {
		super();
		setGestore(gestore);
		setClient(client);
		setLayout(new GridLayout(5, 1));

		JPanel pannello = new JPanel();
		pannello.setLayout(new GridLayout(1, 1));

		setPulisciCronologia(new JButton(traduzione("clearLogs")));
		getPulisciCronologia().addActionListener(this);
		pannello.add(getPulisciCronologia());

		setImpostazioni(new JButton(traduzione("settings")));
		getImpostazioni().addActionListener(this);
		pannello.add(getImpostazioni());

		setCambiaStato(new JButton(traduzione("connect")));
		getCambiaStato().addActionListener(this);
		pannello.add(getCambiaStato());

		setElimina(new JButton(traduzione("delete")));
		getElimina().addActionListener(this);
		pannello.add(getElimina());

		setInfoUtenti(new JLabel(traduzione("userConnected") + ": -"));
		pannello.add(getInfoUtenti());

		add(pannello);

		setStato(new JLabel(traduzione("stateDisconnected")));
		getStato().setForeground(Color.red);
		add(getStato());

		setChat(new JTextArea());
		getChat().setLineWrap(true);
		getChat().setEditable(false);
		JScrollPane scroll = new JScrollPane(getChat());
		add(scroll);

		setMessaggio(new JTextArea(traduzione("connectBeforeWriting")));
		getMessaggio().setForeground(Color.lightGray);
		getMessaggio().setLineWrap(true);
		getMessaggio().setEditable(true);
		getMessaggio().addKeyListener(this);
		getMessaggio().setEnabled(false);
		add(getMessaggio());

		setInvia(new JButton("Invia"));
		getInvia().addActionListener(this);
		getInvia().setBorderPainted(false);
		getInvia().setForeground(Color.white);
		getInvia().setBackground(Color.green);
		getInvia().setFocusable(false);
		getInvia().setFont(new Font("Arial", Font.BOLD, 12));
		getInvia().setEnabled(false);
		add(getInvia());
	}

	@Override
	public void impostazioni() {
		getClient().disconnetti(this);

		JFrame frameImpostazioni = new JFrame(traduzione("settings"));
		frameImpostazioni.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		Container container = frameImpostazioni.getContentPane();
		container.setLayout(new GridLayout(5, 1));

		JButton salva = new JButton(traduzione("save"));
		KeyListener k = new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					e.consume();
					salva.doClick();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {

			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}
		};

		JLabel labelIP = new JLabel(traduzione("IP") + ": ");
		JTextField textIP = new JTextField(getIP());
		JLabel labelPorta = new JLabel(traduzione("port") + ": ");
		JTextField textPorta = new JTextField(getPorta() + "");
		textIP.addKeyListener(k);
		textPorta.addKeyListener(k);

		salva.addKeyListener(k);
		salva.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int errore = 0;
				String IP = textIP.getText();
				int porta = 0;
				if (IP.equalsIgnoreCase("localhost")) errore = 0;
				else {
					try {
						InetAddress inet = InetAddress.getByName(getIP());
						boolean isIPv4 = inet.getHostAddress().equals(getIP())
								&& inet instanceof Inet4Address;
						if (!isIPv4) errore = 1;
					}
					catch (UnknownHostException e1) {
						errore = 1;
					}
				}
				try {
					String temp = textPorta.getText();
					if (temp.length() < 1 && temp.length() > 6) {
						if (errore == 0) errore = 2;
						else errore = 3;
					}
					else porta = Integer.parseInt(temp);
				}
				catch (NumberFormatException e1) {
					if (errore == 0) errore = 2;
					else errore = 3;
				}
				if (errore == 1) errore(traduzione("IPError"),
						traduzione("IPErrorDescription"));
				else if (errore == 2) errore(traduzione("portError"),
						traduzione("portErrorDescription"));
				else if (errore == 3) errore(traduzione("IPPortError"),
						traduzione("IPPortErrorDescription"));
				else {
					scrivi(traduzione("IPChanged") + IP + ": " + porta);
					frameImpostazioni.setVisible(false);

					setIP(IP);
					setPorta(porta);
				}
			}
		});

		container.add(labelIP);
		container.add(textIP);
		container.add(labelPorta);
		container.add(textPorta);
		container.add(salva);

		frameImpostazioni.setResizable(false);
		frameImpostazioni.setLocation(100, 100);
		frameImpostazioni.pack();
		frameImpostazioni.setVisible(true);
	}

	@Override
	public void scrivi(String testo) {
		getChat().append("\n" + testo);
		getChat().setCaretPosition(chat.getDocument().getLength());
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
		getCambiaStato().setText(traduzione("disconnect"));
		getStato().setText(
				traduzione("stateConnected") + getIP() + ":" + getPorta());
		getStato().setForeground(Color.green);
		getInvia().setEnabled(true);
		getMessaggio().setEnabled(true);
		getMessaggio().setText(traduzione("okWriting"));
	}

	@Override
	public void UIDisconnessa() {
		getCambiaStato().setText(traduzione("connect"));
		getInfoUtenti().setText(traduzione("userConnected") + ": -");
		getStato().setText(traduzione("stateDisconnected"));
		getStato().setForeground(Color.red);
		getInvia().setEnabled(false);
		getMessaggio().setEnabled(false);
		getMessaggio().setText(traduzione("connectBeforeWriting"));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == getPulisciCronologia()) {
			if (!getChat().getText().equals("")) {
				getChat().setText("---------------------------- "
						+ traduzione("clearLogsDone")
						+ " ----------------------------");
			}
		}
		else if (e.getSource() == getImpostazioni()) {
			impostazioni();
		}
		else if (e.getSource() == getCambiaStato()) {
			if (getClient().getConnessione(this) == null
					|| !getClient().getConnessione(
							this).isConnesso()) getClient().connetti(this,
									getIP(), getPorta());
			else getClient().disconnetti(this);
		}
		else if (e.getSource() == getElimina()) {
			if (getClient().getConnessione(
					this) != null) getClient().disconnetti(this);
			getGestore().elimina(this);
		}
		else if (e.getSource() == getInvia()) {
			String testo = getMessaggio().getText().trim();
			if (!testo.equals("")) getClient().invia(this, testo);
			getMessaggio().setText(traduzione("okWriting"));
			getMessaggio().setForeground(Color.lightGray);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (getMessaggio().getText().equalsIgnoreCase(
				traduzione("okWriting"))) {
			getMessaggio().setText("");
			getMessaggio().setForeground(Color.black);
		}
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			e.consume();
			getInvia().doClick();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}

	public JLabel getInfoUtenti() {
		return infoUtenti;
	}

	public void setInfoUtenti(JLabel infoUtenti) {
		this.infoUtenti = infoUtenti;
	}

	public JButton getPulisciCronologia() {
		return pulisciCronologia;
	}

	public void setPulisciCronologia(JButton pulisciCronologia) {
		this.pulisciCronologia = pulisciCronologia;
	}

	public JButton getImpostazioni() {
		return impostazioni;
	}

	public void setImpostazioni(JButton impostazioni) {
		this.impostazioni = impostazioni;
	}

	public JButton getCambiaStato() {
		return cambiaStato;
	}

	public void setCambiaStato(JButton cambiaStato) {
		this.cambiaStato = cambiaStato;
	}

	public JTextArea getChat() {
		return chat;
	}

	public void setChat(JTextArea chat) {
		this.chat = chat;
	}

	public JTextArea getMessaggio() {
		return messaggio;
	}

	public void setMessaggio(JTextArea messaggio) {
		this.messaggio = messaggio;
	}

	public JLabel getStato() {
		return stato;
	}

	public void setStato(JLabel stato) {
		this.stato = stato;
	}

	public JButton getInvia() {
		return invia;
	}

	public void setInvia(JButton invia) {
		this.invia = invia;
	}

	public int getPorta() {
		return porta;
	}

	public void setPorta(int porta) {
		this.porta = porta;
	}

	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		IP = iP;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public IGestoreClient getGestore() {
		return gestore;
	}

	public void setGestore(IGestoreClient gestore) {
		this.gestore = gestore;
	}

	public JButton getElimina() {
		return elimina;
	}

	public void setElimina(JButton elimina) {
		this.elimina = elimina;
	}

}