package dama;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Stack;
import java.util.Vector;

import dama.fazioni.Bianco;
import dama.fazioni.Fazione;
import dama.fazioni.Nero;
import dama.giocatori.Giocatore;
import dama.giocatori.Intelligenza;
import dama.giocatori.Mossa;
import dama.giocatori.Utente;

/**
 * Gestore del gioco.
 * La griglia di gioco si basa su quelle personali dei diversi giocatori.
 * 
 * 
 * TODO:
 * Aggiornamento modulo Log
 * Registrazione partite
 * Ripresa partite
 * 
 * @author Thomas Zilio
 */
public class Gioco {
	private final int LARGHEZZA = 8;
	private final boolean REGISTRAZIONE;
	private int turno = 0, tipoGioco;
	private Log log;
	private Fazione bianco, nero;
	private Vector<Giocatore> giocatori;
	private Giocatore vincitore;

	/**
	 * Costruttore di base.
	 * 
	 * @param tipoGioco
	 * @param log
	 * @param registrazione
	 */
	public Gioco(int tipoGioco, Log log, boolean registrazione) {
		bianco = new Bianco(this);
		nero = new Nero(this);
		giocatori = new Vector<Giocatore>(2, 1);
		resetGioco(tipoGioco);
		setLog(log);
		this.REGISTRAZIONE = registrazione;
//		if (registrazione && getLog().last() != null
//				&& !getLog().last().split(";")[0].equalsIgnoreCase(
//						"fine")) riprendi();
//		else
		scriviLog("inizio;" + tipoGioco + ";"
				+ DateFormat.getDateTimeInstance(DateFormat.SHORT,
						DateFormat.SHORT, new Locale("it", "IT")).format(
								new Date()));
	}

	/**
	 * 
	 */
	public void riprendi() {
		if (!getLog().last().split(";")[0].equalsIgnoreCase("fine")) {
			Stack<String> prova = new Stack<String>();
			while (!getLog().last().split(";")[0].equalsIgnoreCase("inizio")) {
				if (!getLog().last().split(";")[0].equalsIgnoreCase(
						"dama")) prova.push(getLog().last());
				getLog().removeLast();
			}
			if (getLog().last().split(";")[0].equalsIgnoreCase(
					"inizio")) setTipoGioco(
							Integer.parseInt(getLog().last().split(";")[1]));
			while (!prova.isEmpty()) {
				esegui(prova.pop());
			}
		}
	}

	/**
	 * 
	 * @param mossa
	 */
	private void esegui(String mossa) {
		if (mossa != null && mossa.split(";")[0].equalsIgnoreCase("muovi")) {
			String[] el, first, second;
			int differenzaOrizzontale, differenzaVerticale,
					coordinataOrizzontale, coordinataVerticale;
			if (!log.isGuardo()) scriviLog(mossa);
			el = mossa.split(";");
			first = el[1].split(",");
			second = el[2].split(",");
			differenzaOrizzontale = Integer.parseInt(first[0]);
			differenzaVerticale = Integer.parseInt(first[1]);
			coordinataOrizzontale = Integer.parseInt(second[0]);
			coordinataVerticale = Integer.parseInt(second[1]);
			if (isBianco(coordinataOrizzontale,
					coordinataVerticale)) setTurno(1);
			else setTurno(2);
			if (el.length == 4) {
				getPedina(
						coordinataOrizzontale + ((differenzaOrizzontale
								- coordinataOrizzontale) / 2),
						coordinataVerticale
								+ ((differenzaVerticale - coordinataVerticale)
										/ 2)).elimina();
				if (Integer.parseInt(el[3]) == 2) rendiDama(
						coordinataOrizzontale + ((differenzaOrizzontale
								- coordinataOrizzontale) / 2),
						coordinataVerticale
								+ ((differenzaVerticale - coordinataVerticale)
										/ 2));
			}
			getPedina(coordinataOrizzontale, coordinataVerticale).modifica(
					differenzaOrizzontale, differenzaVerticale);
			if (!log.isGuardo()) {
				if (isNero(differenzaOrizzontale, differenzaVerticale)
						&& el[0].equalsIgnoreCase("muovi")
						&& isNero(differenzaOrizzontale,
								differenzaVerticale)) setTurno(1);
				else if (isBianco(differenzaOrizzontale, differenzaVerticale)
						&& el[0].equalsIgnoreCase("muovi")
						&& isBianco(differenzaOrizzontale,
								differenzaVerticale)) setTurno(2);
				else if (getTurno() == 1) setTurno(2);
				else setTurno(1);
			}
			trovaDama();
		}
	}

	/**
	 * 
	 * @return
	 */
	public boolean annulla() {
		if (getLog().last() != null) {
			cancellaTemporanei();
			String[] el, first;
			while (log.last().split(";")[0].equalsIgnoreCase("dama")) {
				el = log.last().split(";");
				first = el[1].split(",");
				getPedina(Integer.parseInt(first[0]),
						Integer.parseInt(first[1])).setDama(false);
				log.removeLast();
			}
			indietro(log.last());
			return true;
		}
		return false;

	}

	/**
	 * 
	 */
	public void avanti() {
		String mossa = log.mossa();
		if (mossa != null) {
			if (!mossa.split(";")[0].equalsIgnoreCase("dama")) esegui(mossa);
			else esegui(log.mossa());
			controllaPartita();
		}
	}

	/**
	 * 
	 */
	public void indietroUltimo() {
		String[] el, first;
		int differenzaOrizzontale, differenzaVerticale;
		String mossa = log.torna();
		if (mossa != null && mossa.split(";")[0].equalsIgnoreCase("dama")) {
			el = mossa.split(";");
			first = el[1].split(",");
			differenzaOrizzontale = Integer.parseInt(first[0]);
			differenzaVerticale = Integer.parseInt(first[1]);
			if (!isVuoto(differenzaOrizzontale, differenzaVerticale)) getPedina(
					differenzaOrizzontale, differenzaVerticale).setDama(false);
			mossa = log.torna();
		}
		indietro(mossa);
		controllaPartita();
	}

	/**
	 * 
	 * @param mossa
	 */
	private void indietro(String mossa) {
		if (mossa != null && mossa.split(";")[0].equalsIgnoreCase("muovi")) {
			String[] el, first, second;
			int differenzaOrizzontale, differenzaVerticale,
					coordinataOrizzontale, coordinataVerticale;
			el = mossa.split(";");
			first = el[1].split(",");
			second = el[2].split(",");
			differenzaOrizzontale = Integer.parseInt(first[0]);
			differenzaVerticale = Integer.parseInt(first[1]);
			coordinataOrizzontale = Integer.parseInt(second[0]);
			coordinataVerticale = Integer.parseInt(second[1]);
			if (el.length == 4) {
				if (isNero(differenzaOrizzontale,
						differenzaVerticale)) new Pedina(getGiocatore(
								0), coordinataOrizzontale
										+ ((differenzaOrizzontale
												- coordinataOrizzontale)
												/ 2), coordinataVerticale
														+ ((differenzaVerticale
																- coordinataVerticale)
																/ 2));
				else if (isBianco(differenzaOrizzontale,
						differenzaVerticale)) new Pedina(getGiocatore(
								1), coordinataOrizzontale
										+ ((differenzaOrizzontale
												- coordinataOrizzontale)
												/ 2), coordinataVerticale
														+ ((differenzaVerticale
																- coordinataVerticale)
																/ 2));
				if (Integer.parseInt(el[3]) == 2) rendiDama(
						coordinataOrizzontale + ((differenzaOrizzontale
								- coordinataOrizzontale) / 2),
						coordinataVerticale
								+ ((differenzaVerticale - coordinataVerticale)
										/ 2));
			}
			getPedina(differenzaOrizzontale, differenzaVerticale).modifica(
					coordinataOrizzontale, coordinataVerticale);
			if (!log.isGuardo()) {
				log.removeLast();
				el = log.last().split(";");
				if (isNero(coordinataOrizzontale, coordinataVerticale)
						&& el[0].equalsIgnoreCase("muovi")
						&& isNero(differenzaOrizzontale,
								differenzaVerticale)) setTurno(1);
				else if (isBianco(coordinataOrizzontale, coordinataVerticale)
						&& el[0].equalsIgnoreCase("muovi")
						&& isBianco(differenzaOrizzontale,
								differenzaVerticale)) setTurno(2);
				else if (getTurno() == 1) setTurno(2);
				else setTurno(1);
			}
			trovaDama();
		}
	}

	/**
	 * Crea il campo di gioco e i relativi giocatori da zero.
	 * 
	 * @param tipoGioco
	 *        Tipo di partita
	 */
	private void resetGioco(int tipoGioco) {
		setVincitore(null);
		setTurno(0);
		setTipoGioco(tipoGioco);
		giocatori.clear();
		switch (tipoGioco) {
		case 1:
			giocatori.add(new Intelligenza(this, "Avversario", bianco));
			giocatori.add(new Utente(this, "Utente", nero));
			break;
		case 2:
			giocatori.add(new Utente(this, "Utente1", bianco));
			giocatori.add(new Utente(this, "Utente2", nero));
			break;
		case 3:
			giocatori.add(new Intelligenza(this, "Bianco", bianco));
			giocatori.add(new Intelligenza(this, "Nero", nero));
			break;
		default:
			giocatori.add(new Utente(this, "Utente", bianco));
			giocatori.add(new Intelligenza(this, "Avversario", nero));
			break;
		}
		int coordinataOrizzontale, coordinataVerticale = 0;
		for (int i = 0; i < 3; i++) {
			if (i % 2 == 0) coordinataOrizzontale = 0;
			else coordinataOrizzontale = 1;
			while (coordinataOrizzontale < getLarghezza()) {
				new Pedina(getGiocatori().elementAt(
						1), coordinataOrizzontale, coordinataVerticale);
				coordinataOrizzontale += 2;
			}
			coordinataVerticale += 1;
		}
		coordinataOrizzontale = 0;
		coordinataVerticale = getLarghezza() - 3;
		for (int i = 0; i < 3; i++) {
			if (i % 2 == 0) coordinataOrizzontale = 1;
			else coordinataOrizzontale = 0;
			while (coordinataOrizzontale < getLarghezza()) {
				new Pedina(getGiocatori().elementAt(
						0), coordinataOrizzontale, coordinataVerticale);
				coordinataOrizzontale += 2;
			}
			coordinataVerticale += 1;
		}
	}

	/**
	 * Scrive una riga nel file di log.
	 * 
	 * @param line
	 */
	public void scriviLog(String line) {
		log.add(line);
	}

	/**
	 * Cancella le pedine temporanee dei diversi giocatori.
	 */
	public void cancellaTemporanei() {
		for (Giocatore p : giocatori)
			p.cancellaTemporanei();
	}

	/**
	 * Restituisce la pedina nella posizione indicata.
	 * 
	 * @param coordinataOrizzontale
	 * @param coordinataVerticale
	 * @return
	 */
	public Pedina getPedina(int coordinataOrizzontale,
			int coordinataVerticale) {
		if (isInterno(coordinataOrizzontale, coordinataVerticale)) {
			for (Giocatore g : getGiocatori())
				if (g.getPedina(coordinataOrizzontale,
						coordinataVerticale) != null) return g.getPedina(
								coordinataOrizzontale, coordinataVerticale);
		}
		return null;
	}

	/**
	 * Costrolla se le coordinate passate sono interne alla griglia.
	 * 
	 * @param coordinataOrizzontale
	 * @param coordinataVerticale
	 * @return
	 */
	public boolean isInterno(int coordinataOrizzontale,
			int coordinataVerticale) {
		return coordinataOrizzontale >= 0
				&& coordinataOrizzontale < getLarghezza()
				&& coordinataVerticale >= 0
				&& coordinataVerticale < getLarghezza();
	}

	/**
	 * Controlla se la posizione indicata è vuota o meno.
	 * 
	 * @param coordinataOrizzontale
	 * @param coordinataVerticale
	 * @return
	 */
	public boolean isVuoto(int coordinataOrizzontale, int coordinataVerticale) {
		return isInterno(coordinataOrizzontale, coordinataVerticale)
				&& (getPedina(coordinataOrizzontale,
						coordinataVerticale) == null);
	}

	/**
	 * Controlla se la posizione indicata è liberamente occupabile (vuota o con
	 * pedina temporanea)
	 * 
	 * @param coordinataOrizzontale
	 * @param coordinataVerticale
	 * @return
	 */
	public boolean isLibero(int coordinataOrizzontale,
			int coordinataVerticale) {
		return isInterno(coordinataOrizzontale, coordinataVerticale)
				&& (getPedina(coordinataOrizzontale,
						coordinataVerticale) == null
						|| getPedina(coordinataOrizzontale,
								coordinataVerticale).isTemp());
	}

	/**
	 * Controlla se la pedina nella posizione indicata è avversaria di quella
	 * inserita.
	 * 
	 * @param pedina
	 * @param coordinataOrizzontale
	 * @param coordinataVerticale
	 * @return
	 */
	public boolean isNemico(Pedina pedina, int coordinataOrizzontale,
			int coordinataVerticale) {
		Pedina p = getPedina(coordinataOrizzontale, coordinataVerticale);
		if (p != null) return p.getGiocatore() != pedina.getGiocatore();
		return false;
	}

	/**
	 * Controlla se la pedina nella posizione indicata è di fazione bianca.
	 * 
	 * @param coordinataOrizzontale
	 * @param coordinataVerticale
	 * @return
	 */
	public boolean isBianco(int coordinataOrizzontale,
			int coordinataVerticale) {
		Pedina p = getPedina(coordinataOrizzontale, coordinataVerticale);
		if (p != null) return p.getGiocatore().getFazione() == bianco
				&& !p.isTemp();
		return false;
	}

	/**
	 * Controlla se la pedina nella posizione indicata è di fazione nera.
	 * 
	 * @param coordinataOrizzontale
	 * @param coordinataVerticale
	 * @return
	 */
	public boolean isNero(int coordinataOrizzontale, int coordinataVerticale) {
		Pedina p = getPedina(coordinataOrizzontale, coordinataVerticale);
		if (p != null) return p.getGiocatore().getFazione() == nero
				&& !p.isTemp();
		return false;
	}

	/**
	 * Controlla se la pedina nella posizione indicata è temporaneo.
	 * 
	 * @param coordinataOrizzontale
	 * @param coordinataVerticale
	 * @return
	 */
	public boolean isTemporanea(int coordinataOrizzontale,
			int coordinataVerticale) {
		Pedina p = getPedina(coordinataOrizzontale, coordinataVerticale);
		if (p != null) return p.isTemp();
		return false;
	}

	/**
	 * Controlla se la pedina nella posizione indicata è una dama.
	 * 
	 * @param coordinataOrizzontale
	 * @param coordinataVerticale
	 * @return
	 */
	public boolean isDama(int coordinataOrizzontale, int coordinataVerticale) {
		return getPedina(coordinataOrizzontale, coordinataVerticale).isDama();
	}

	/**
	 * Rende la pedina indicata una dama.
	 * 
	 * @param coordinataOrizzontale
	 * @param coordinataVerticaleSelezionata
	 */
	private void rendiDama(int coordinataOrizzontale, int coordinataVerticale) {
		getPedina(coordinataOrizzontale, coordinataVerticale).setDama(true);
		scriviLog("dama;" + coordinataOrizzontale + "," + coordinataVerticale);
	}

	/**
	 * Cerca le dame in base alla fazione.
	 */
	private void trovaDama() {
		for (int i = 0; i < getLarghezza(); i++) {
			for (int j = 0; j < getLarghezza(); j++) {
				Pedina p = getPedina(i, j);
				if (p != null && (bianco.rendiDama(p) || nero.rendiDama(p))) {
					rendiDama(i, j);
				}
			}
		}
	}

	/**
	 * Controlla se la partita è finita.
	 */
	private void controllaPartita() {
		if (getGiocatore(0).getGriglia().size() == 0) setVincitore(
				getGiocatore(1));
		else if (getGiocatore(1).getGriglia().size() == 0) setVincitore(
				getGiocatore(0));
	}

	/**
	 * Esegue la mossa indicata.
	 * 
	 * @param mossa
	 */
	public void eseguiMossa(Mossa mossa) {
		if (mossa.getPedina().getPosizioneOrizzontale() == mossa.getPosizioneOrizzontaleIniziale()
				&& mossa.getPedina().getPosizioneVerticale() == mossa.getPosizioneVerticaleIniziale()) {
			mossa.getPedina().modifica(mossa.getPosizioneOrizzontaleFinale(),
					mossa.getPosizioneVerticaleFinale());
			String log = "muovi;" + mossa.getPosizioneOrizzontaleIniziale()
					+ "," + mossa.getPosizioneVerticaleIniziale() + ";"
					+ mossa.getPosizioneOrizzontaleFinale() + ","
					+ mossa.getPosizioneVerticaleFinale();
			if (Math.abs(mossa.getPosizioneOrizzontaleIniziale()
					- mossa.getPosizioneOrizzontaleFinale()) > 1) {
				getPedina(
						mossa.getPosizioneOrizzontaleIniziale()
								- (mossa.getPosizioneOrizzontaleIniziale()
										- mossa.getPosizioneOrizzontaleFinale())
										/ 2,
						mossa.getPosizioneVerticaleIniziale()
								- (mossa.getPosizioneVerticaleIniziale()
										- mossa.getPosizioneVerticaleFinale())
										/ 2).elimina();
				log += ";1";
			}
			scriviLog(log);
		}
		getGiocatore().cancellaTemporanei();
	}

	/**
	 * Gestore delle mosse.
	 * 
	 * @param coordinataOrizzontale
	 * @param coordinataVerticale
	 */
	public void muovi(int coordinataOrizzontale, int coordinataVerticale) {
		getGiocatore().muovi(coordinataOrizzontale, coordinataVerticale);
		getGiocatore().muovi();
		trovaDama();
		controllaPartita();
	}

	/**
	 * 
	 */
	public void cambiaTurno() {
		setTurno((getTurno() + 1) % getGiocatori().size());
		cancellaTemporanei();
	}

	public Giocatore getGiocatore() {
		return getGiocatore(getTurno());
	}

	public Giocatore getGiocatore(int index) {
		return getGiocatori().elementAt(index);
	}

	public Giocatore getAvversario() {
		return getGiocatore(1 - getTurno());
	}

	public int getLarghezza() {
		return LARGHEZZA;
	}

	public boolean isRegistrazione() {
		return REGISTRAZIONE;
	}

	public int getTurno() {
		return this.turno;
	}

	public void setTurno(int turno) {
		this.turno = turno;
	}

	public int getTipoGioco() {
		return this.tipoGioco;
	}

	public void setTipoGioco(int tipoGioco) {
		this.tipoGioco = tipoGioco;
	}

	public Log getLog() {
		return this.log;
	}

	public void setLog(Log log) {
		this.log = log;
	}

	public Fazione getBianco() {
		return this.bianco;
	}

	public void setBianco(Fazione bianco) {
		this.bianco = bianco;
	}

	public Fazione getNero() {
		return this.nero;
	}

	public void setNero(Fazione nero) {
		this.nero = nero;
	}

	public Vector<Giocatore> getGiocatori() {
		return this.giocatori;
	}

	public void setGiocatori(Vector<Giocatore> giocatori) {
		this.giocatori = giocatori;
	}

	public Giocatore getVincitore() {
		return this.vincitore;
	}

	public void setVincitore(Giocatore vincitore) {
		this.vincitore = vincitore;
	}
}