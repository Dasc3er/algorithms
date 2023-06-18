package dama;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import dama.colori.Colore;
import dama.colori.Colori;
import layout.ElementoString;
import layout.GestoreLayout;
import layout.Layout;

/**
 * Grafica del gioco.
 * 
 * @author Thomas Zilio
 */
public class Pannello extends JPanel implements MouseListener {
	private int stato;
	private Gioco gioco;
	private Log log;
	private Colori colori;
	private GestoreLayout layout;

	/**
	 * Costruttore di base.
	 */
	public Pannello() {
		super();
		super.setBackground(Color.WHITE);
		addMouseListener(this);
		setLog(new Log());
		setStato(-1);
		setColori(new Colori());
		setLayout(new GestoreLayout());
		layout();
		if (!getLog().isEmpty()
				&& !getLog().last().split(";")[0].equalsIgnoreCase("fine")
				&& !getLog().last().split(";")[0].equalsIgnoreCase("inizio")) {
			setGioco(new Gioco(-1, log, true));
			if (getGioco().getTipoGioco() == -1) setGioco(null);
		}

	}

	/**
	 * 
	 * @return
	 */
	public void cronologia() {
		boolean fine = false;
		String x = "";
		Vector<String> vect = new Vector<String>(10, 10);
		for (int i = 0; i < getLog().getMosse().size(); i++) {
			if (getLog().getMosse().elementAt(i).split(";")[0].equalsIgnoreCase(
					"inizio")) {
				if (!fine && i != 0) vect.add("NC, " + x);
				x = getLog().getMosse().elementAt(i).split(";")[2];
			}
			if (getLog().getMosse().elementAt(i).split(";")[0].equalsIgnoreCase(
					"fine")) {
				vect.add("C, " + x);
			}
		}
		Layout lol =
				new Layout(getWidth(), getHeight(), 3, (vect.size() + 1) / 3);
		lol.aggiungi(
				new ElementoString(Color.red, "<-- Torna indietro", Color.black));
		for (int i = 2; i < vect.size() + 2; i++) {
			lol.aggiungi(new ElementoString(Color.white, vect.elementAt(
					i - 2), Color.black));
		}
		getGestoreLayout().aggiungiLayout("cronologia", lol);
	}

	/**
	 * 
	 */
	@Override
	public void layout() {
		// Schermata iniziale
		Vector<Layout> l = new Vector<Layout>(1, 1);
		l.add(new Layout(getWidth(), getHeight() + 1, 2, 3));
		l.get(0).aggiungi(
				new ElementoString(Color.white, "Bianco", Color.black));
		l.get(0).aggiungi(new ElementoString(Color.black, "Nero", Color.white));
		l.get(0).aggiungi(
				new ElementoString(Color.white, "SPERIMENTALE", Color.black));
		l.add(l.get(0).dividi(3, 2, 1));
		l.get(1).aggiungi(
				new ElementoString(Color.white, "Bianco", Color.black));
		l.get(1).aggiungi(new ElementoString(Color.black, "Nero", Color.white));

		l.get(0).aggiungi(
				new ElementoString(Color.blue, "Multiplayer", Color.white));
		l.get(0).aggiungi(
				new ElementoString(Color.yellow, "Bot vs bot", Color.blue));
		l.get(0).aggiungi(
				new ElementoString(Color.red, "Cronologia", Color.white));
		getGestoreLayout().aggiungiLayout("home", l);
		l = new Vector<Layout>(1, 1);

		// Schermata di gioco
		Layout lol = new Layout(getWidth(), getHeight() + 1, 10, 10);
		l.add(lol.dividi(1, 8, 8, 8, 10));
		l.add(lol.dividi(9, 1, 3, 2, 10));
		l.get(1).aggiungi(
				new ElementoString(Color.orange, "Nuovo", Color.white));
		l.get(1).aggiungi(
				new ElementoString(Color.green, "Colori", Color.white));
		l.get(1).aggiungi(
				new ElementoString(Color.blue, "Annulla", Color.white));
		getGestoreLayout().aggiungiLayout("gioco", l);
		getGestoreLayout().aggiungiLayout("registrazione", l.get(0));
		lol = lol.dividi(9, 1, 3, 2, 10);
		getGestoreLayout().aggiungiLayout("registrazione", lol);
		lol.aggiungi(new ElementoString(Color.orange, "Nuovo", Color.white));
		lol.aggiungi(new ElementoString(Color.green, "Colori", Color.white));
		lol.aggiungi(new ElementoString(Color.blue, "Annulla", Color.white));
		// Colori
		getGestoreLayout().aggiungiLayout("colori",
				new Layout(getWidth(), getHeight() + 1, 1, 1));
		// Vittoria
		getGestoreLayout().aggiungiLayout("vittoria",
				new Layout(getWidth(), getHeight() + 1, 1, 2));
		getGestoreLayout().getLayout("vittoria").get(0).aggiungi(
				new ElementoString(Color.white, "Ha vinto il giocatore", Color.black));
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		getGestoreLayout().disegna(g);
		String sezione = getGestoreLayout().getSelezionato();
		if (sezione.equalsIgnoreCase("gioco")
				|| sezione.equalsIgnoreCase("registrazione")) {
			Layout gioco = getGestoreLayout().getLayoutSelezionato().get(0);
			int numero, x, y, width, height;
			for (int i = 0; i < getGioco().getLarghezza(); i++) {
				for (int j = 0; j < getGioco().getLarghezza(); j++) {
					numero = i + j * getGioco().getLarghezza() + 1;
					x = gioco.coordinataOrizzontale(numero)
							+ (gioco.ultimaCoordinataOrizzontale(numero)
									- gioco.coordinataOrizzontale(numero)) / 5;
					y = gioco.coordinataVerticale(numero)
							+ (gioco.ultimaCoordinataVerticale(numero)
									- gioco.coordinataVerticale(numero)) / 5;
					width = (gioco.ultimaCoordinataOrizzontale(numero)
							- gioco.coordinataOrizzontale(numero)) / 5 * 3;
					height = (gioco.ultimaCoordinataVerticale(numero)
							- gioco.coordinataVerticale(numero)) / 5 * 3;
					if (getGioco().isBianco(i, j)) {
						if (getGioco().isDama(i, j)) {
							g.setColor(
									getColori().getColoreSelezionato().getPedinaDamaBianca());
							g.fillOval(x, y, width, height);
						}
						else {
							g.setColor(
									getColori().getColoreSelezionato().getPedinaBianca());
							g.fillOval(x, y, width, height);
						}
					}
					else if (getGioco().isNero(i, j)) {
						if (getGioco().isDama(i, j)) {
							g.setColor(
									getColori().getColoreSelezionato().getPedinaDamaNera());
							g.fillOval(x, y, width, height);
						}
						else {
							g.setColor(
									getColori().getColoreSelezionato().getPedinaNera());
							g.fillOval(x, y, width, height);
						}
					}
					else if (getGioco().isTemporanea(i, j)) {
						g.setColor(
								getColori().getColoreSelezionato().getPedinaTemporanea());
						g.fillOval(x, y, width, height);
					}
				}
			}
		}
		else if (sezione.equalsIgnoreCase("vittoria")) {
			Layout gioco = getGestoreLayout().getLayoutSelezionato().get(0);
			if (getStato() == 1) gioco.sostituisci(2,
					new ElementoString(Color.white, "della fazione bianca!!!", Color.black));
			else gioco.sostituisci(2,
					new ElementoString(Color.black, "della fazione nera!!!", Color.white));
		}
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		int x = arg0.getX();
		int y = arg0.getY();
		String sezione = getGestoreLayout().getSelezionato();
		Vector<Layout> selezionati = getGestoreLayout().getLayoutSelezionato();
		int posizione = selezionati.get(0).numeroCoordinate(x, y);
		System.out.println(sezione + " " + posizione);
		if (sezione.equalsIgnoreCase("colori")) {
			getColori().setSelezionato(posizione - 1);
			getGestoreLayout().setSelezionato("gioco");
		}
		else if (sezione.equalsIgnoreCase("cronologia")) {
			if (posizione == 1) getGestoreLayout().setSelezionato("home");
			else {
				log.mostra(posizione - 1);
				setStato(-4);
				setGioco(new Gioco(-1, log, true));
				log.setGuardo(true);
			}
		}
		else if (sezione.equalsIgnoreCase("home")) {
			if (posizione >= 1 && posizione <= 5) {
				if (posizione == 1) nuovoGioco(0);
				else if (posizione == 2) nuovoGioco(1);
				else if (posizione == 3) {
					if (selezionati.get(1).numeroCoordinate(x,
							y) == 1) nuovoGioco(4);
					else if (selezionati.get(1).numeroCoordinate(x,
							y) == 2) nuovoGioco(5);
				}
				else if (posizione == 4) nuovoGioco(2);
				else if (posizione == 5) nuovoGioco(3);
				getGestoreLayout().setSelezionato("gioco");
			}
			else if (posizione == 6) {
				cronologia();
				getGestoreLayout().setSelezionato("cronologia");
			}
		}
		else {
			if (posizione != 0) {
				if (getStato() == -1) getGioco().muovi(
						selezionati.get(0).indiceOrizzontale(
								selezionati.get(0).numeroCoordinate(x, y)),
						selezionati.get(0).indiceVerticale(
								selezionati.get(0).numeroCoordinate(x, y)));
			}
			else {
				posizione = selezionati.get(1).numeroCoordinate(x, y);
				if (posizione == 1) getGestoreLayout().setSelezionato("home");
				if (sezione.equalsIgnoreCase("gioco")) {
					if (posizione == 2) {
						getGestoreLayout().getLayout("colori").get(0).reset();
						getGestoreLayout().getLayout("colori").get(
								0).modificaSezioni(3,
										(getColori().getColori().size() / 3)
												+ 1);
						for (Colore c : getColori().getColori())
							getGestoreLayout().getLayout("colori").get(
									0).aggiungi(
											new ElementoString(c.getBackground(), c.getNome(), c.getColoreNome()));
						getGestoreLayout().setSelezionato("colori");
					}
					else if (posizione == 3) getGioco().annulla();
				}
				else {
					if (posizione == 1) log.setGuardo(false);
					if (posizione == 2) getGioco().avanti();
					else if (posizione == 3) getGioco().indietroUltimo();
				}
			}
		}
		repaint();
	}

	public void nuovoGioco(int tipoGioco) {
		setGioco(new Gioco(tipoGioco, log, false));
		Layout l = getGestoreLayout().getLayout("gioco").get(0);
		l.modificaSezioni(getGioco().getLarghezza(), getGioco().getLarghezza());
		for (int i = 1; i <= l.getElementi().length; i++) {
			if (i % 2 == ((i - 1) / l.getSezioniOrizzontali() + 1)
					% 2) l.ottieniElemento(i).setBackground(
							getColori().getColoreSelezionato().getQuadranteNero());
			else l.ottieniElemento(i).setBackground(
					getColori().getColoreSelezionato().getQuadranteBianca());
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	public Colori getColori() {
		return colori;
	}

	public void setColori(Colori colori) {
		this.colori = colori;
	}

	public int getStato() {
		return stato;
	}

	public void setStato(int stato) {
		this.stato = stato;
	}

	public Log getLog() {
		return this.log;
	}

	public void setLog(Log log) {
		this.log = log;
	}

	public GestoreLayout getGestoreLayout() {
		return layout;
	}

	public void setLayout(GestoreLayout layout) {
		this.layout = layout;
	}

	public Gioco getGioco() {
		return this.gioco;
	}

	public void setGioco(Gioco gioco) {
		this.gioco = gioco;
	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame frame = new JFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.getContentPane().add(new Pannello());
				frame.setTitle("Gioco");
				frame.setLocation(200, 200);
				frame.setMinimumSize(new Dimension(600 + frame.getInsets().left
						+ frame.getInsets().right, 500 + frame.getInsets().top
								+ frame.getInsets().bottom));
				frame.setVisible(true);
			}
		});
	}
}
