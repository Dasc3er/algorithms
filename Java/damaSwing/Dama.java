package damaSwing;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Stack;

import javax.swing.JButton;

/**
 * @author Thomas Zilio
 */
public class Dama {
	private int x, y, gioco = -1, who = 1;
	private Pedina[][] pedine = new Pedina[8][8];// griglia in base a schermo, 0
													// in alto a sinistra, 7 in
													// bassso na destra
	private Log log;
	private Client client;
	private Color bianco, nero;

	public Dama() {
		int x, y = 0;
		for (int i = 0; i < 3; i++) {
			if (i % 2 == 0) x = 0;
			else x = 1;
			while (x < getSubLength()) {
				setPedina(x, y, new Pedina(2));
				x += 2;
			}
			y += 1;
		}
		x = 0;
		y = getSubLength() - 3;
		for (int i = 0; i < 3; i++) {
			if (i % 2 == 0) x = 1;
			else x = 0;
			while (x < getSubLength()) {
				setPedina(x, y, new Pedina(1));
				x += 2;
			}
			y += 1;
		}
		for (int i = 0; i < getLength(); i++) {
			for (int j = 0; j < getSubLength(); j++) {
				if (getPedina(i, j) == null) setPedina(i, j, new Pedina(-1));
			}
		}
		defaultColor();
		draw();
		reset();
		setGioco(0);
	}

	public void draw() {
		int cont;
		for (int i = 0; i < getLength(); i++) {
			if (i % 2 == 0) cont = 1;
			else cont = 0;
			for (int j = 0; j < getSubLength(); j++) {
				if ((j + 1) % 2 == cont) getPedina(i, j).setBackground(
						getNero());
				else getPedina(i, j).setBackground(getBianco());
			}
		}
	}

	public Dama(int gioco) {
		this();
		setGioco(gioco);
	}

	public Dama(int gioco, Log log) {
		this(gioco);
		setLog(log);
		if (gioco == 1) nemico();
	}

	public Dama(Log log) {
		this();
		setLog(log);
		if (getLog().last() != null
				&& !getLog().last().split(";")[0].equalsIgnoreCase("fine")) riprendi();
	}

	public void riprendi() {
		if (!getLog().last().split(";")[0].equalsIgnoreCase("fine")) {
			Stack<String> prova = new Stack<String>();
			while (!getLog().last().split(";")[0].equalsIgnoreCase("inizio")) {
				if (!getLog().last().split(";")[0].equalsIgnoreCase("dama")) prova.push(getLog().last());
				getLog().removeLast();
			}
			if (getLog().last().split(";")[0].equalsIgnoreCase("inizio")) setGioco(Integer.parseInt(getLog().last().split(
					";")[1]));
			while (!prova.isEmpty()) {
				avanza(prova.pop());
			}
		}
	}

	public boolean undo() {
		if (getLog().last() != null) {
			cancellaSelezione();
			String[] el, first;
			while (log.last().split(";")[0].equalsIgnoreCase("dama")) {
				el = log.last().split(";");
				first = el[1].split(",");
				getPedina(Integer.parseInt(first[0]),
						Integer.parseInt(first[1])).setDb(false);
				log.removeLast();
			}
			indietro(log.last());
			draw();
			return true;
		}
		return false;
	}

	public void next() {
		String x = log.mossa();
		if (x != null) {
			if (!x.split(";")[0].equalsIgnoreCase("dama")) avanza(x);
			else avanza(log.mossa());
			check();
		}
		draw();
	}

	public void pre() {
		String[] el, first;
		int xx, yy;
		String x = log.torna();
		if (x != null && x.split(";")[0].equalsIgnoreCase("dama")) {
			el = x.split(";");
			first = el[1].split(",");
			xx = Integer.parseInt(first[0]);
			yy = Integer.parseInt(first[1]);
			if (!isNull(xx, yy)) getPedina(xx, yy).setDb(false);
			x = log.torna();
		}
		indietro(x);
		check();
		draw();
	}

	private void avanza(String xp) {
		if (xp != null && xp.split(";")[0].equalsIgnoreCase("muovi")) {
			String[] el, first, second;
			int xx, yy, x, y;
			if (!log.isGuardo()) write(xp);
			el = xp.split(";");
			first = el[1].split(",");
			second = el[2].split(",");
			xx = Integer.parseInt(first[0]);
			yy = Integer.parseInt(first[1]);
			x = Integer.parseInt(second[0]);
			y = Integer.parseInt(second[1]);
			if (bianco(x, y)) setWho(1);
			else setWho(2);
			if (el.length == 4) {
				setPedina(x + ((xx - x) / 2), y + ((yy - y) / 2),
						new Pedina(-1));
				if (Integer.parseInt(el[3]) == 2) rendiDama(x + ((xx - x) / 2),
						y + ((yy - y) / 2));
			}
			setPedina(xx, yy, getPedina(x, y));
			setPedina(x, y, new Pedina(-1));
			if (!log.isGuardo()) {
				if (nero(xx, yy) && el[0].equalsIgnoreCase("muovi")
						&& nero(xx, yy)) setWho(1);
				else if (bianco(xx, yy) && el[0].equalsIgnoreCase("muovi")
						&& bianco(xx, yy)) setWho(2);
				else if (getWho() == 1) setWho(2);
				else setWho(1);
			}
			trovaDama();
		}
	}

	private void indietro(String xp) {
		if (xp != null && xp.split(";")[0].equalsIgnoreCase("muovi")) {
			String[] el, first, second;
			int xx, yy, x, y;
			el = xp.split(";");
			first = el[1].split(",");
			second = el[2].split(",");
			xx = Integer.parseInt(first[0]);
			yy = Integer.parseInt(first[1]);
			x = Integer.parseInt(second[0]);
			y = Integer.parseInt(second[1]);
			if (el.length == 4) {
				if (nero(xx, yy)) setPedina(x + ((xx - x) / 2), y
						+ ((yy - y) / 2), new Pedina(1));
				else if (bianco(xx, yy)) setPedina(x + ((xx - x) / 2), y
						+ ((yy - y) / 2), new Pedina(2));
				if (Integer.parseInt(el[3]) == 2) rendiDama(x + ((xx - x) / 2),
						y + ((yy - y) / 2));
			}
			setPedina(x, y, getPedina(xx, yy));
			setPedina(xx, yy, new Pedina(-1));
			if (!log.isGuardo()) {
				log.removeLast();
				el = log.last().split(";");
				if (nero(x, y) && el[0].equalsIgnoreCase("muovi")
						&& nero(xx, yy)) setWho(1);
				else if (bianco(x, y) && el[0].equalsIgnoreCase("muovi")
						&& bianco(xx, yy)) setWho(2);
				else if (getWho() == 1) setWho(2);
				else setWho(1);
			}
			trovaDama();
		}
	}

	public int getLength() {
		return pedine.length;
	}

	public int getSubLength() {
		return pedine[0].length;
	}

	public Client getClient() {
		return this.client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	private int getX() {
		return x;
	}

	private void setX(int x) {
		if (x >= 0 && x < getLength()) this.x = x;
	}

	private int getY() {
		return y;
	}

	private void setY(int y) {
		if (y >= 0 && y < getSubLength()) this.y = y;
	}

	public Color getBianco() {
		return this.bianco;
	}

	public void setBianco(Color bianco) {
		this.bianco = bianco;
	}

	public Color getNero() {
		return this.nero;
	}

	public void setNero(Color nero) {
		this.nero = nero;
	}

	public int getGioco() {
		return gioco;
	}

	private void setGioco(int gioco) {
		this.gioco = gioco;
	}

	public Log getLog() {
		return this.log;
	}

	public void setLog(Log log) {
		this.log = log;
	}

	public int getWho() {
		return who;
	}

	private void setWho(int who) {
		this.who = who;
	}

	public void defaultColor() {
		setBianco(Color.white);
		setNero(Color.DARK_GRAY);
		getPedina(1, 1).defaultColor();
	}

	public void halloweenColor() {
		setBianco(Color.white);
		setNero(Color.DARK_GRAY);
		getPedina(1, 1).halloweenColor();
	}

	public Pedina getPedina(int x, int y) {
		if (inArray(x, y)) return pedine[x][y];
		else return null;
	}

	public void setPedina(int x, int y, Pedina p) {
		if (inArray(x, y)) pedine[x][y] = p;
	}

	private boolean selezionato() {
		return inArray(getX(), getY());
	}

	private void seleziona(int x, int y) {
		setX(x);
		setY(y);
	}

	private void reset() {
		setX(-1);
		setY(-1);
	}

	protected void write(String line) {
		log.add(line);
	}

	private boolean inArray(int x, int y) {
		return x >= 0 && x < getLength() && y >= 0 && y < getSubLength();
	}

	public boolean isNull(int x, int y) {
		return inArray(x, y) && fazione(x, y) == -1;
	}

	public boolean vuoto(int x, int y) {
		return inArray(x, y) && (isNull(x, y) || grigio(x, y));
	}

	private int fazione(int x, int y) {
		return getPedina(x, y).getWho();
	}

	public boolean bianco(int x, int y) {
		return inArray(x, y) && !isNull(x, y) && fazione(x, y) == 1;
	}

	public boolean nero(int x, int y) {
		return inArray(x, y) && !isNull(x, y) && fazione(x, y) == 2;
	}

	public boolean grigio(int x, int y) {
		return inArray(x, y) && !isNull(x, y) && fazione(x, y) == 3;
	}

	public boolean dama(int x, int y) {
		return getPedina(x, y).getDb();
	}

	// controlla se possibile mangiare a sinistra (da basso ad alto, visione
	// utente)
	private boolean mangiareSinistra(int x, int y, int who, boolean db) {
		return (who == 1 && inArray(x - 1, y - 1) && inArray(x - 2, y - 2)
				&& nero(x - 1, y - 1)
				&& (!dama(x - 1, y - 1) || dama(x - 1, y - 1) && db) && vuoto(
					x - 2, y - 2))
				|| (who == 2 && db && inArray(x - 1, y - 1)
						&& inArray(x - 2, y - 2) && bianco(x - 1, y - 1)
						&& (!dama(x - 1, y - 1) || dama(x - 1, y - 1) && db) && vuoto(
							x - 2, y - 2));
	}

	// controlla se possibile mangiare a destra (da basso ad alto, visione
	// utente)
	private boolean mangiareDestra(int x, int y, int who, boolean db) {
		return (who == 1 && inArray(x + 1, y - 1) && inArray(x + 2, y - 2)
				&& nero(x + 1, y - 1)
				&& (!dama(x + 1, y - 1) || dama(x + 1, y - 1) && db) && vuoto(
					x + 2, y - 2))
				|| (who == 2 && db && inArray(x + 1, y - 1)
						&& inArray(x + 2, y - 2) && bianco(x + 1, y - 1)
						&& (!dama(x + 1, y - 1) || dama(x + 1, y - 1) && db) && vuoto(
							x + 2, y - 2));
	}

	// controlla se possibile mangiare indietro a sinistra (da alto a basso,
	// visione utente)
	private boolean mangiareISinistra(int x, int y, int who, boolean db) {
		return (who == 1 && db && inArray(x - 1, y + 1)
				&& inArray(x - 2, y + 2) && nero(x - 1, y + 1) && vuoto(x - 2,
					y + 2))
				|| (who == 2 && inArray(x - 1, y + 1) && inArray(x - 2, y + 2)
						&& bianco(x - 1, y + 1)
						&& (!dama(x - 1, y + 1) || dama(x - 1, y + 1) && db) && vuoto(
							x - 2, y + 2));
	}

	// controlla se possibile mangiare indietro a destra (da alto a basso,
	// visione utente)
	private boolean mangiareIDestra(int x, int y, int who, boolean db) {
		return (who == 1 && db && inArray(x + 1, y + 1)
				&& inArray(x + 2, y + 2) && nero(x + 1, y + 1)
				&& (!dama(x + 1, y + 1) || dama(x + 1, y + 1) && db) && vuoto(
					x + 2, y + 2))
				|| (who == 2 && inArray(x + 1, y + 1) && inArray(x + 2, y + 2)
						&& bianco(x + 1, y + 1)
						&& (!dama(x + 1, y + 1) || dama(x + 1, y + 1) && db) && vuoto(
							x + 2, y + 2));
	}

	// controlla se per pedina (o dama) possible mangiare e segnala
	// possibilità
	private void mangiare(int x, int y, int who, boolean db) {
		if (mangiareSinistra(x, y, who, db)) setPedina(x - 2, y - 2,
				new Pedina(3));
		if (mangiareDestra(x, y, who, db)) setPedina(x + 2, y - 2,
				new Pedina(3));
		if (mangiareISinistra(x, y, who, db)) setPedina(x - 2, y + 2,
				new Pedina(3));
		if (mangiareIDestra(x, y, who, db)) setPedina(x + 2, y + 2,
				new Pedina(3));
	}

	// controlla se per pedina (o dama) possible muoversi sempilcemente e
	// segnala possibilità
	private void semplice(int x, int y, int who, boolean db) {
		if (vuoto(x - 1, y - 1) && (who == 1 || who == 2 && db)) setPedina(
				x - 1, y - 1, new Pedina(3));
		if (vuoto(x + 1, y - 1) && (who == 1 || who == 2 && db)) setPedina(
				x + 1, y - 1, new Pedina(3));
		if (vuoto(x - 1, y + 1) && (who == 2 || who == 1 && db)) setPedina(
				x - 1, y + 1, new Pedina(3));
		if (vuoto(x + 1, y + 1) && (who == 2 || who == 1 && db)) setPedina(
				x + 1, y + 1, new Pedina(3));
	}

	// individua i possibli movimenti della pedina
	private void trovaGrigio(int x, int y, int who, boolean db) {
		mangiare(x, y, who, db);
		semplice(x, y, who, db);
	}

	// elimina la possiblilà di movimento
	private void cancellaSelezione() {
		for (int i = 0; i < getLength(); i++) {
			for (int j = 0; j < getSubLength(); j++) {
				if (grigio(i, j)) {
					setPedina(i, j, new Pedina(-1));
				}
			}
		}
	}

	// seleziona il movimento da eseguire e mangia l'avversario se possibile
	private int scegliGrigio(int x, int y) {
		if (grigio(x, y)) {
			cancellaSelezione();
			setPedina(x, y, getPedina(getX(), getY()));
			setPedina(getX(), getY(), new Pedina(-1));
			int xx = (x - getX()) / 2;
			int yy = (y - getY()) / 2;
			if (xx != 0) {
				if (dama(getX() + xx, getY() + yy)) write("muovi;" + x + ","
						+ y + ";" + getX() + "," + getY() + ";2");
				else write("muovi;" + x + "," + y + ";" + getX() + "," + getY()
						+ ";1");
				setPedina(getX() + xx, getY() + yy, new Pedina(-1));
				reset();
				return 2;
			}
			write("muovi;" + x + "," + y + ";" + getX() + "," + getY());
			reset();
			return 1;
		}
		else {
			cancellaSelezione();
			reset();
			return 0;
		}
	}

	// rende la pedina selazionata una dama
	private void rendiDama(int x, int y) {
		getPedina(x, y).setDb(true);
		write("dama;" + x + "," + y);
	}

	// carca le dame, in base a fazione
	private void trovaDama() {
		for (int i = 0; i < getLength(); i++) {
			for (int j = 0; j < getSubLength(); j++) {
				if (!vuoto(i, j)) {
					if (j == 0 && bianco(i, j)) rendiDama(i, j);
					else if (j == getSubLength() - 1 && nero(i, j)) rendiDama(
							i, j);
				}
			}
		}
	}

	// intelligenza artificiale
	private void nemico() {
		int max, cont = 0, x = 0, y = 0;
		if (getWho() == 2) cont = trovaNero();
		else if (getWho() == 1) cont = trovaBianco();
		if (cont != 0) {
			int[][] array = new int[cont][2];
			int[] counter = new int[cont];
			cont = 0;
			for (int i = 0; i < getLength(); i++) {
				for (int j = 0; j < getSubLength(); j++) {
					if (((getWho() == 1 && bianco(j, i)) || (getWho() == 2 && nero(
							j, i))) && !intrappolato(j, i)) {
						array[cont][0] = j;
						array[cont][1] = i;
						cont++;
					}
				}
			}
			for (int i = 0; i < counter.length; i++) {
				counter[i] = maxvalore(voto(array[i][0], array[i][1]));
			}
			int where = maxneg(counter);
			boolean moved = false;
			do {
				max = puoiMangiare(array[where][0], array[where][1]);
				moved = valuta(array[where][0], array[where][1]);
				if (moved && max != -1) {
					if (max == 0) {
						x = array[where][0] - 2;
						y = array[where][1] - 2;
					}
					if (max == 1) {
						x = array[where][0] + 2;
						y = array[where][1] - 2;
					}
					if (max == 2) {
						x = array[where][0] - 2;
						y = array[where][1] + 2;
					}
					if (max == 3) {
						x = array[where][0] + 2;
						y = array[where][1] + 2;
					}
					mangiare(x, y, fazione(x, y), dama(x, y));
					muovi(x, y, max, 3);
				}
			}while (!moved);
			if (getWho() == 2) setWho(1);
			else if (getWho() == 1) setWho(2);
			cancellaSelezione();
		}
	}

	// cerca se disponibili pedine della fazione nera
	private int trovaNero() {
		int cont = 0;
		for (int i = 0; i < getLength(); i++) {
			for (int j = 0; j < getSubLength(); j++) {
				if (nero(i, j) && !intrappolato(i, j)) cont++;
			}
		}
		if (cont == 0) {
			setGioco(11);
			return 0;
		}
		else return cont;
	}

	// cerca se disponibili pedine della fazione bianca
	private int trovaBianco() {
		int cont = 0;
		for (int i = 0; i < getLength(); i++) {
			for (int j = 0; j < getSubLength(); j++) {
				if (bianco(i, j) && !intrappolato(i, j)) cont++;
			}
		}
		if (cont == 0) {
			setGioco(22);
			return 0;
		}
		else return cont;
	}

	// trova il valore massimo all'interno di un array
	private int max(int[] array) {
		int max = 0;
		for (int i = 0; i < array.length; i++) {
			if (array[i] > array[max]) max = i;
		}
		return max;
	}

	private int maxneg(int[] array) {
		int max = array.length - 1;
		for (int i = array.length - 1; i >= 0; i--) {
			if (array[i] > array[max]) max = i;
		}
		return max;
	}

	private int maxvalore(int[] array) {
		int max = -1;
		for (int i = 0; i < array.length; i++) {
			if (array[i] > max) max = array[i];
		}
		return max;
	}

	// muove a sinistra una pedina
	private void muoviSinistra(int x, int y, int where) {
		seleziona(x, y);
		if (where == 3) scegliGrigio(x - 2, y - 2);
		else if (where == 1) scegliGrigio(x - 1, y - 1);
	}

	// muove a destra una pedina
	private void muoviDestra(int x, int y, int where) {
		seleziona(x, y);
		if (where == 3) scegliGrigio(x + 2, y - 2);
		else if (where == 1) scegliGrigio(x + 1, y - 1);
	}

	// muove indietro a sinistra una pedina
	private void muoviISinistra(int x, int y, int where) {
		seleziona(x, y);
		if (where == 3) scegliGrigio(x - 2, y + 2);
		else if (where == 1) scegliGrigio(x - 1, y + 1);
	}

	// muove indietro a destra una pedina
	private void muoviIDestra(int x, int y, int where) {
		seleziona(x, y);
		if (where == 3) scegliGrigio(x + 2, y + 2);
		else if (where == 1) scegliGrigio(x + 1, y + 1);
	}

	// controlla se una pedina può mangiare
	private int puoiMangiare(int x, int y) {
		int[] array = new int[4];
		if (mangiareSinistra(x, y, fazione(x, y), dama(x, y))) array[0] = 3;
		if (mangiareDestra(x, y, fazione(x, y), dama(x, y))) array[1] = 3;
		if (mangiareISinistra(x, y, fazione(x, y), dama(x, y))) array[2] = 3;
		if (mangiareIDestra(x, y, fazione(x, y), dama(x, y))) array[3] = 3;
		int max = max(array);
		if (array[max] != 0) {
			return max;
		}
		else return -1;
	}

	// fa muovere una pedina
	private void muovi(int x, int y, int where, int value) {
		switch (where) {
		default:
			muoviSinistra(x, y, value);
			break;
		case 1:
			muoviDestra(x, y, value);
			break;
		case 2:
			muoviISinistra(x, y, value);
			break;
		case 3:
			muoviIDestra(x, y, value);
			break;
		}
	}

	public int[] voto(int x, int y) {
		int[] array = new int[4];
		if (mangiareSinistra(x, y, fazione(x, y), dama(x, y))) array[0] = 3;
		else if (vuoto(x - 1, y - 1)
				&& (bianco(x, y) || nero(x, y) && dama(x, y))) array[0] = 1;
		if (mangiareDestra(x, y, fazione(x, y), dama(x, y))) array[1] = 3;
		else if (vuoto(x + 1, y - 1)
				&& (bianco(x, y) || nero(x, y) && dama(x, y))) array[1] = 1;
		if (mangiareISinistra(x, y, fazione(x, y), dama(x, y))) array[2] = 3;
		else if (vuoto(x - 1, y + 1)
				&& (nero(x, y) || bianco(x, y) && dama(x, y))) array[2] = 1;
		if (mangiareIDestra(x, y, fazione(x, y), dama(x, y))) array[3] = 3;
		else if (vuoto(x + 1, y + 1)
				&& (nero(x, y) || bianco(x, y) && dama(x, y))) array[3] = 1;
		return array;
	}

	// controlla se una pedina è intrappolata o no
	private boolean intrappolato(int x, int y) {
		int[] array = new int[4];
		array = voto(x, y);
		int max = max(array);
		if (array[max] != 0) return false;
		else return true;
	}

	// valuta il movimento migliore per una pedina e fa muovere
	private boolean valuta(int x, int y) {
		int[] array = new int[4];
		array = voto(x, y);
		int max = max(array);
		if (array[max] != 0) {
			mangiare(x, y, fazione(x, y), dama(x, y));
			semplice(x, y, fazione(x, y), dama(x, y));
			muovi(x, y, max, array[max]);
			return true;
		}
		else return false;
	}

	private void move(int x, int y) {
		if (getWho() == 1 || getWho() == 3) {
			if (getWho() == 3 || (selezionato() && grigio(x, y))) {
				if (scegliGrigio(x, y) == 2 && puoiMangiare(x, y) != -1) {
					mangiare(x, y, fazione(x, y), dama(x, y));
					seleziona(x, y);
					setWho(3);
				}
				else {
					setWho(2);
				}
			}
			else {
				cancellaSelezione();
				if (bianco(x, y)) {
					trovaGrigio(x, y, fazione(x, y), dama(x, y));
					seleziona(x, y);

				}
			}
		}
		else {
			if (getWho() == 4 || (selezionato() && grigio(x, y))) {
				if (scegliGrigio(x, y) == 2 && puoiMangiare(x, y) != -1) {
					mangiare(x, y, fazione(x, y), dama(x, y));
					seleziona(x, y);
					setWho(4);
				}
				else {
					setWho(1);
				}
			}
			else {
				cancellaSelezione();
				if (nero(x, y)) {
					trovaGrigio(x, y, fazione(x, y), dama(x, y));
					seleziona(x, y);
				}
			}
		}
	}

	private void check() {
		trovaNero();
		trovaBianco();
	}

	// gestore delle mosse
	public void opera(int x, int y) {
		switch (getGioco()) {
		case 0:
			move(x, y);
			if (getWho() == 2) nemico();
			break;
		case 1:
			move(x, y);
			if (getWho() == 1) nemico();
			break;
		case 2:
			move(x, y);
			break;
		case 3:
			nemico();
			nemico();
			break;
		case 4:
			if (getClient() == null) client();
			if (getWho() == 2) move(x, y);
			break;
		case 5:
			if (getClient() == null) client();
			else move(x, y);
			break;
		default:
			break;
		}
		trovaDama();
		check();
		draw();
	}

	public void client() {
		setClient(new Client(log));
	}

	public static class Pedina extends JButton {
		private static final long serialVersionUID = 1L;
		private static Color bianco, nero, grigio, damaBianca, damaNera;
		private boolean db;
		private final int who;

		public Pedina(int w) {
			super("");
			this.who = w;
			setBorder(null);
			if (bianco == null) defaultColor();
		}

		public int getWho() {
			return this.who;
		}

		public void setDb(boolean n) {
			this.db = n;
		}

		public boolean getDb() {
			return this.db;
		}

		public static Color getBianco() {
			return bianco;
		}

		public static void setBianco(Color bianco) {
			Pedina.bianco = bianco;
		}

		public static Color getNero() {
			return nero;
		}

		public static void setNero(Color nero) {
			Pedina.nero = nero;
		}

		public static Color getGrigio() {
			return grigio;
		}

		public static void setGrigio(Color grigio) {
			Pedina.grigio = grigio;
		}

		public static Color getDamaBianca() {
			return damaBianca;
		}

		public static void setDamaBianca(Color damaBianca) {
			Pedina.damaBianca = damaBianca;
		}

		public static Color getDamaNera() {
			return damaNera;
		}

		public static void setDamaNera(Color damaNera) {
			Pedina.damaNera = damaNera;
		}

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (who != -1) {
				if (who == 1) {
					if (db) g.setColor(getDamaBianca());
					else g.setColor(getBianco());
				}
				else if (who == 2) {
					if (db) g.setColor(getDamaNera());
					else g.setColor(getNero());
				}
				else if (who == 3) {
					g.setColor(getGrigio());
				}
				g.fillOval(getWidth() / 2 - getWidth() / 6, getHeight() / 2
						- getHeight() / 6, getWidth() / 3, getHeight() / 3);
			}
		}

		@Override
		public String toString() {
			return "Pawn [db=" + db + ", who=" + who + "]";
		}

		public void defaultColor() {
			setBianco(Color.yellow);
			setNero(Color.black);
			setGrigio(Color.gray);
			setDamaBianca(Color.blue);
			setDamaNera(Color.red);
		}

		public void halloweenColor() {
			setBianco(Color.orange);
			setNero(Color.green);
			setGrigio(Color.gray);
			setDamaBianca(Color.magenta);
			setDamaNera(Color.PINK);
		}
	}
}