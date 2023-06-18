package dama;

import dama.giocatori.Giocatore;

/**
 * Struttura interna delle pedina.
 * 
 * @author Thomas Zilio
 */
public class Pedina {
	private final Giocatore giocatore;
	private int posizioneOrizzontale, posizioneVerticale;
	private boolean dama, temp;

	/**
	 * Costruttore di base, inserisce la pedina nella griglia del rispettivo
	 * giocatore.
	 * 
	 * @param giocatore
	 * @param posizioneOrizzontale
	 * @param posizioneVerticale
	 */
	public Pedina(Giocatore giocatore, int posizioneOrizzontale,
			int posizioneVerticale) {
		this(giocatore, posizioneOrizzontale, posizioneVerticale, false);
	}

	/**
	 * Costrutore che imposta la condizione di temporaneità della pedina.
	 * 
	 * @see #Constructor(Giocatore giocatore, int posizioneOrizzontale, int
	 *      posizioneVerticale)
	 * @param giocatore
	 * @param posizioneOrizzontale
	 * @param posizioneVerticale
	 * @param temp
	 */
	public Pedina(Giocatore giocatore, int posizioneOrizzontale,
			int posizioneVerticale, boolean temp) {
		this.giocatore = giocatore;
		this.posizioneOrizzontale = posizioneOrizzontale;
		this.posizioneVerticale = posizioneVerticale;
		this.temp = temp;
		getGiocatore().aggiungiPedina(this);
	}

	/**
	 * Elimina la pedina dalla griglia.
	 */
	public void elimina() {
		getGiocatore().rimuovi(this);
	}

	/**
	 * Modifica la posizione della pedina sia internamente che nella girlgia.
	 * 
	 * @param posizioneOrizzontale
	 * @param posizioneVerticale
	 */
	public void modifica(int posizioneOrizzontale, int posizioneVerticale) {
		elimina();
		this.posizioneOrizzontale = posizioneOrizzontale;
		this.posizioneVerticale = posizioneVerticale;
		getGiocatore().aggiungiPedina(this);
	}

	public Giocatore getGiocatore() {
		return giocatore;
	}

	public int getPosizioneOrizzontale() {
		return posizioneOrizzontale;
	}

	public void setPosizioneOrizzontale(int posizioneOrizzontale) {
		this.posizioneOrizzontale = posizioneOrizzontale;
	}

	public int getPosizioneVerticale() {
		return posizioneVerticale;
	}

	public void setPosizioneVerticale(int posizioneVerticale) {
		this.posizioneVerticale = posizioneVerticale;
	}

	public boolean isTemp() {
		return temp;
	}

	public void setTemp(boolean temp) {
		this.temp = temp;
	}

	public boolean isDama() {
		return dama;
	}

	public void setDama(boolean dama) {
		this.dama = dama;
	}
}
