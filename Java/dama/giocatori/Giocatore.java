package dama.giocatori;

import java.util.HashMap;
import java.util.Vector;

import dama.Gioco;
import dama.Pedina;
import dama.fazioni.Fazione;

/**
 * Struttura base del giocatore.
 * 
 * @author Thomas Zilio
 */
public abstract class Giocatore {
	private Gioco gioco;
	private String name;
	private HashMap<String, Pedina> griglia;
	private Vector<Pedina> temporanee;
	private Fazione fazione;

	/**
	 * Costruttore di base.
	 * 
	 * @param gioco
	 * @param name
	 * @param fazione
	 */
	public Giocatore(Gioco gioco, String name, Fazione fazione) {
		griglia = new HashMap<String, Pedina>();
		temporanee = new Vector<Pedina>(2, 0);
		this.gioco = gioco;
		this.name = name;
		this.fazione = fazione;
	}

	/**
	 * Aggiunge una determinata pedina alla griglia personale del giocatore.
	 * 
	 * @param pedina
	 *        Pedina da considerare
	 */
	public void aggiungiPedina(Pedina pedina) {
		if (getGriglia().containsValue(pedina)) rimuovi(pedina);
		getGriglia().put(pedina.getPosizioneOrizzontale() + ","
				+ pedina.getPosizioneVerticale(), pedina);
		if (pedina.isTemp()) temporanee.add(pedina);
	}

	/**
	 * Restituisce la pedina nella posizione indicata.
	 * 
	 * @param posizioneOrizzontale
	 * @param posizioneVerticale
	 * @return
	 */
	public Pedina getPedina(int posizioneOrizzontale, int posizioneVerticale) {
		return getGriglia().get(
				posizioneOrizzontale + "," + posizioneVerticale);
	}

	public void rimuovi(Pedina pedina) {
		if (getGriglia().get(pedina.getPosizioneOrizzontale() + ","
				+ pedina.getPosizioneVerticale()) == pedina) getGriglia().remove(
						pedina.getPosizioneOrizzontale() + ","
								+ pedina.getPosizioneVerticale());
	}

	/**
	 * Elimina le possibilità di movimento indicate nel campo tramite pedine
	 * temporanee.
	 */
	public void cancellaTemporanei() {
		for (Pedina p : temporanee)
			p.elimina();
		temporanee.clear();
	}

	/**
	 * Cambia il turno del gioco, passandolo all'avversario.
	 */
	public void passaTurno() {
		getGioco().cambiaTurno();
	}

	/**
	 * Individua tutti i possibili movimenti di una determinata pedina.
	 * 
	 * @param pedina
	 *        Pedina da considerare
	 * @return Insieme di mosse possibili
	 */
	public Vector<Mossa> trovaMovimentiPossibili(Pedina pedina) {
		Vector<Mossa> mosse = new Vector<Mossa>(4, 2);
		mosse.addAll(trovaMovimentiMangiare(pedina));
		mosse.addAll(trovaMovimentiSemplici(pedina));
		return mosse;
	}

	/**
	 * Controlla se per pedina (o dama) è possible mangiare, costruendo
	 * l'insieme di mosse possibili.
	 * 
	 * @param pedina
	 *        Pedina da considerare
	 * @return Insieme di mosse possibili (solo per mangiare altre pedine)
	 */
	public Vector<Mossa> trovaMovimentiMangiare(Pedina pedina) {
		Vector<Mossa> mosse = new Vector<Mossa>(4, 2);
		if (getFazione().mangiareSinistra(
				pedina)) mosse.add(new Mossa(pedina, -2, -2));
		if (getFazione().mangiareDestra(
				pedina)) mosse.add(new Mossa(pedina, +2, -2));
		if (getFazione().mangiareISinistra(
				pedina)) mosse.add(new Mossa(pedina, -2, +2));
		if (getFazione().mangiareIDestra(
				pedina)) mosse.add(new Mossa(pedina, +2, +2));
		return mosse;
	}

	/**
	 * Controlla se per pedina (o dama) è possible muoversi in modo semplice,
	 * costruendo l'insieme di mosse possibili.
	 * 
	 * @param pedina
	 *        Pedina da considerare
	 * @return Insieme di mosse possibili (solo per il moviemnto senza mangiare)
	 */
	public Vector<Mossa> trovaMovimentiSemplici(Pedina pedina) {
		Vector<Mossa> mosse = new Vector<Mossa>(4, 2);
		if (getFazione().muoviSinistra(
				pedina)) mosse.add(new Mossa(pedina, -1, -1));
		if (getFazione().muoviDestra(
				pedina)) mosse.add(new Mossa(pedina, +1, -1));

		if (getFazione().muoviISinistra(
				pedina)) mosse.add(new Mossa(pedina, -1, +1));

		if (getFazione().muoviIDestra(
				pedina)) mosse.add(new Mossa(pedina, +1, +1));
		return mosse;
	}

	/**
	 * Controlla se una pedina può mangiare.
	 * 
	 * @param pedina
	 *        Pedina da considerare
	 * @return
	 */
	public boolean possibileMangiare(Pedina pedina) {
		return trovaMovimentiMangiare(pedina).size() != 0;
	}

	/**
	 * Controlla se una pedina può muoversi.
	 * 
	 * @param pedina
	 *        Pedina da considerare
	 * @return
	 */
	public boolean possibileMuoversi(Pedina pedina) {
		return trovaMovimentiPossibili(pedina).size() != 0;
	}

	/**
	 * Moviemento di base del sistema.
	 */
	public abstract void muovi();

	/**
	 * Moveimento richiedente la posizione del click effettuato dal mouse
	 * all'interno della griglia.
	 * 
	 * @param coordinataOrizzontale
	 * @param coordinataVerticale
	 */
	public abstract void muovi(int coordinataOrizzontale,
			int coordinataVerticale);

	public HashMap<String, Pedina> getGriglia() {
		return griglia;
	}

	private void setGriglia(HashMap<String, Pedina> griglia) {
		this.griglia = griglia;
	}

	public Gioco getGioco() {
		return gioco;
	}

	private void setGioco(Gioco gioco) {
		this.gioco = gioco;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Vector<Pedina> getTemporanee() {
		return temporanee;
	}

	public void setTemporanee(Vector<Pedina> temporanee) {
		this.temporanee = temporanee;
	}

	public Fazione getFazione() {
		return fazione;
	}

	private void setFazione(Fazione fazione) {
		this.fazione = fazione;
	}
}
