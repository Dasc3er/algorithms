package damaSwing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author Thomas Zilio
 */
public class DamaFrame implements ActionListener {
	JFrame frame;
	public Container c;
	private Dama game;
	private Log log;
	private Font font = new Font("Calibri", Font.PLAIN, 30);

	public DamaFrame() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		c = frame.getContentPane();
		frame.setTitle("Gioco");
		frame.setLocation(200, 200);
		frame.setMinimumSize(new Dimension(600 + frame.getInsets().left
				+ frame.getInsets().right, 500 + frame.getInsets().top
				+ frame.getInsets().bottom));
		visibile();
		iniziale();
		setLog(new Log());
		// defaultColor();
		if (!getLog().isEmpty()
				&& !getLog().last().split(";")[0].equalsIgnoreCase("fine")
				&& !getLog().last().split(";")[0].equalsIgnoreCase("inizio")) {
			setGame(new Dama(log));
			if (getGame().getGioco() == -1) setGame(null);
		}
	}

	public void visibile() {
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		new DamaFrame();
	}

	public Log getLog() {
		return this.log;
	}

	public void setLog(Log log) {
		this.log = log;
	}

	public Dama getGame() {
		return this.game;
	}

	public void setGame(Dama game) {
		this.game = game;
	}

	private void write(String line) {
		log.add(line);
	}

	public void defaultColor() {
		getGame().defaultColor();
	}

	public void halloweenColor() {
		getGame().halloweenColor();
	}

	public Vector<String> cronologia() {
		int cont = 1;
		boolean inizio = false;
		String x = "";
		Vector<String> vect = new Vector<String>(10, 10);
		for (int i = 0; i < getLog().getMosse().size(); i++) {
			if (getLog().getMosse().elementAt(i).split(";")[0].equalsIgnoreCase("inizio")) {
				if (inizio) {
					vect.add(cont + " - Partita non conclusa del " + x);
					cont++;
					inizio = false;
				}
				x = getLog().getMosse().elementAt(i).split(";")[2];
				inizio = true;
			}
			else if (getLog().getMosse().elementAt(i).split(";")[0].equalsIgnoreCase("fine")
					&& inizio) {
				vect.add(cont + " - Partita conclusa del " + x);
				cont++;
				inizio = false;
			}
		}
		return vect;
	}

	public JButton button(String testo, Color back, Color text) {
		JButton button = new JButton(testo);
		button.setFont(font);
		button.setBackground(back);
		button.setForeground(text);
		button.setBorder(null);
		button.addActionListener(this);
		return button;
	}

	public void iniziale() {
		c.removeAll();
		c.setLayout(new GridLayout(3, 2));
		c.add(button("Bianco", Color.white, Color.black));
		c.add(button("Nero", Color.black, Color.white));
		/* c.add(button("SPERIMENTALE", Color.yellow, Color.black)); */

		JPanel x = new JPanel();
		x.setLayout(new GridLayout(1, 2));
		x.add(button("Bianco [M]", Color.white, Color.black));
		x.add(button("Nero [M]", Color.black, Color.white));
		c.add(x);

		c.add(button("Multiplayer", Color.blue, Color.white));
		c.add(button("Bot vs bot", Color.orange, Color.black));
		c.add(button("Cronologia", Color.red, Color.black));
		c.doLayout();
		visibile();
	}

	public void gioco() {
		c.removeAll();
		c.setLayout(new BorderLayout());
		JPanel p =
				new JPanel(new GridLayout(getGame().getLength(), getGame().getSubLength()));
		for (int i = 0; i < getGame().getLength(); i++) {
			for (int j = 0; j < getGame().getSubLength(); j++) {
				p.add(getGame().getPedina(i, j));
				if (getGame().getPedina(i, j).getAction() == null) getGame().getPedina(
						i, j).addActionListener(this);
			}
		}
		c.add(p);
		JPanel x = new JPanel(new GridLayout(3, 1));
		x.add(button("Nuovo", Color.orange, Color.white));
		if (log.isGuardo()) {
			x.add(button("-->", Color.green, Color.black));
			x.add(button("<--", Color.blue, Color.black));
		}
		else {
			x.add(button("Colori", Color.green, Color.black));
			x.add(button("Annulla", Color.blue, Color.black));
		}
		c.add(x, BorderLayout.EAST);
		c.doLayout();
		visibile();
	}

	public void vinto(int stato) {
		c.removeAll();
		c.setLayout(new GridLayout(2, 1));
		c.add(button("Ha vinto il giocatore", Color.white, Color.black));
		if (stato == 1) c.add(button("della fazione bianca!!!", Color.white,
				Color.black));
		else c.add(button("della fazione nera!!!", Color.black, Color.white));
		c.doLayout();
		visibile();
	}

	public void colori() {
		c.removeAll();
		c.setLayout(new GridLayout(3, 2));
		c.add(button("Default", Color.white, Color.black));
		c.add(button("Halloween", Color.orange, Color.white));
		c.doLayout();
	}

	public void crono() {
		c.removeAll();
		c.setLayout(new GridLayout(10, 3));
		c.add(button("<-- Torna indietro", Color.red, Color.black));
		Vector<String> vect = cronologia();
		for (int i = 2; i < vect.size() + 2; i++) {
			c.add(button(vect.elementAt(i - 2), Color.white, Color.black));
		}
		c.doLayout();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equalsIgnoreCase("Nuovo")
				|| e.getActionCommand().equalsIgnoreCase("<-- Torna indietro")
				|| e.getActionCommand().equalsIgnoreCase(
						"Ha vinto il giocatore")
				|| e.getActionCommand().equalsIgnoreCase(
						"della fazione bianca!!!")
				|| e.getActionCommand().equalsIgnoreCase(
						"della fazione nera!!!")) {
			if (log.isGuardo()) log.setGuardo(false);
			iniziale();
		}
		if (e.getActionCommand().equalsIgnoreCase("Colori")) {
			colori();
		}
		else if (e.getActionCommand().equalsIgnoreCase("Default")) {
			defaultColor();
			gioco();
		}
		else if (e.getActionCommand().equalsIgnoreCase("Halloween")) {
			halloweenColor();
			gioco();
		}
		else if (e.getActionCommand().equalsIgnoreCase("Bianco")) {
			inizio(0);
			setGame(new Dama(0, log));
			gioco();
		}
		else if (e.getActionCommand().equalsIgnoreCase("Nero")) {
			inizio(1);
			setGame(new Dama(1, log));
			gioco();
		}
		else if (e.getActionCommand().equalsIgnoreCase("Bianco [M]")) {
			inizio(4);
			setGame(new Dama(4, log));
			gioco();
		}
		else if (e.getActionCommand().equalsIgnoreCase("Nero [M]")) {
			inizio(5);
			setGame(new Dama(5, log));
			gioco();
		}
		else if (e.getActionCommand().equalsIgnoreCase("Multiplayer")) {
			inizio(2);
			setGame(new Dama(2, log));
			gioco();
		}
		else if (e.getActionCommand().equalsIgnoreCase("Bot vs Bot")) {
			inizio(3);
			setGame(new Dama(3, log));
			gioco();
		}
		else if (e.getActionCommand().equalsIgnoreCase("Cronologia")) {
			crono();
		}
		else if (e.getActionCommand().indexOf(" - ") != -1) {
			// System.out.println(Integer.parseInt(e.getActionCommand().split(" - ")[0]));
			log.mostra(Integer.parseInt(e.getActionCommand().split(" - ")[0]));
			setGame(new Dama(-1, log));
			log.setGuardo(true);
			gioco();
		}
		else if (e.getActionCommand().equalsIgnoreCase("-->")) {
			getGame().next();
			gioco();
		}
		else if (e.getActionCommand().equalsIgnoreCase("<--")) {
			getGame().pre();
			gioco();
		}
		else if (e.getActionCommand().equalsIgnoreCase("Annulla")) {
			getGame().undo();
			gioco();
		}
		else {
			for (int i = 0; i < getGame().getLength(); i++) {
				for (int j = 0; j < getGame().getSubLength(); j++) {
					if (e.getSource() == getGame().getPedina(i, j)) getGame().opera(
							i, j);
				}
			}
			gioco();
			if (getGame().getGioco() == 11) {
				if (!log.isGuardo()) write("fine;1");
				else log.setGuardo(false);
				vinto(1);
			}
			else if (getGame().getGioco() == 22) {
				if (!log.isGuardo()) write("fine;2");
				else log.setGuardo(false);
				vinto(2);
			}
		}
		// System.out.println(e.getActionCommand());
	}

	public void inizio(int i) {
		Date now = new Date();
		write("inizio;" + i + ";" + now.toString());
	}
}
