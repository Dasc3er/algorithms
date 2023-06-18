package dama.fazioni;

import dama.Gioco;
import dama.Pedina;

/**
 * Gestore di base delle fazioni, con funzioni relative al controllo di
 * possibili movimenti e di posizione della pedina quale dama.
 * I nomi dei relativi metodi sono creati in base alla visuale utente.
 * 
 * @author Thomas Zilio
 */
public abstract class Fazione {
	private Gioco gioco;

	/**
	 * Costruttore di base.
	 * 
	 * @param gioco
	 */
	public Fazione(Gioco gioco) {
		super();
		this.gioco = gioco;
	}

	/**
	 * Controlla se una pedina può mangiare direttamente in alto a sinistra
	 * dalla propria posizione.
	 * 
	 * @param pedina
	 *        Pedina da considerare
	 * @return
	 */
	public boolean mangiareSinistra(Pedina pedina) {
		return mangiareSinistra(pedina, pedina.getPosizioneOrizzontale(),
				pedina.getPosizioneVerticale());
	}

	/**
	 * Controlla se una pedina può mangiare direttamente in alto a destra dalla
	 * propria posizione.
	 * 
	 * @param pedina
	 *        Pedina da considerare
	 * @return
	 */
	public boolean mangiareDestra(Pedina pedina) {
		return mangiareDestra(pedina, pedina.getPosizioneOrizzontale(),
				pedina.getPosizioneVerticale());
	}

	/**
	 * Controlla se una pedina può mangiare direttamente in basso a sinistra
	 * dalla propria posizione.
	 * 
	 * @param pedina
	 *        Pedina da considerare
	 * @return
	 */
	public boolean mangiareISinistra(Pedina pedina) {
		return mangiareISinistra(pedina, pedina.getPosizioneOrizzontale(),
				pedina.getPosizioneVerticale());
	}

	/**
	 * Controlla se una pedina può mangiare direttamente in basso a destra dalla
	 * propria posizione.
	 * 
	 * @param pedina
	 *        Pedina da considerare
	 * @return
	 */
	public boolean mangiareIDestra(Pedina pedina) {
		return mangiareIDestra(pedina, pedina.getPosizioneOrizzontale(),
				pedina.getPosizioneVerticale());
	}

	/**
	 * Controlla se una pedina può muoversi direttamente in alto a sinistra
	 * dalla propria posizione.
	 * 
	 * @param pedina
	 *        Pedina da considerare
	 * 
	 * @return
	 */
	public boolean muoviSinistra(Pedina pedina) {
		return muoviSinistra(pedina, pedina.getPosizioneOrizzontale(),
				pedina.getPosizioneVerticale());
	}

	/**
	 * Controlla se una pedina può muoversi direttamente in alto a destra dalla
	 * propria posizione.
	 * 
	 * @param pedina
	 *        Pedina da considerare
	 * @return
	 */
	public boolean muoviDestra(Pedina pedina) {
		return muoviDestra(pedina, pedina.getPosizioneOrizzontale(),
				pedina.getPosizioneVerticale());
	}

	/**
	 * Controlla se una pedina può muoversi direttamente in basso a sinistra
	 * dalla propria posizione.
	 * 
	 * @param pedina
	 *        Pedina da considerare
	 * @return
	 */
	public boolean muoviISinistra(Pedina pedina) {
		return muoviISinistra(pedina, pedina.getPosizioneOrizzontale(),
				pedina.getPosizioneVerticale());
	}

	/**
	 * Controlla se una pedina può muoversi direttamente in basso a destra dalla
	 * propria posizione.
	 * 
	 * @param pedina
	 *        Pedina da considerare
	 * @return
	 */
	public boolean muoviIDestra(Pedina pedina) {
		return muoviIDestra(pedina, pedina.getPosizioneOrizzontale(),
				pedina.getPosizioneVerticale());
	}

	/**
	 * Controlla se una determinata pedina, in una posizione ipotetica e
	 * indicata,
	 * può mangiare in alto a sinistra.
	 * (secondo la visione utente).
	 * 
	 * @param pedina
	 *        Pedina da considerare
	 * @param coordinataOrizzontale
	 *        Posizione orizzontale ipotetica nella griglia
	 * @param coordinataVerticale
	 *        Posizione verticale ipotetica nella griglia
	 * @return
	 */
	public boolean mangiareSinistra(Pedina pedina, int coordinataOrizzontale,
			int coordinataVerticale) {
		return (getGioco().isInterno(coordinataOrizzontale - 1,
				coordinataVerticale - 1)
				&& getGioco().isInterno(coordinataOrizzontale - 2,
						coordinataVerticale - 2)
				&& getGioco().isNemico(pedina, coordinataOrizzontale - 1,
						coordinataVerticale - 1)
				&& (!getGioco().isDama(coordinataOrizzontale - 1,
						coordinataVerticale - 1)
						|| getGioco().isDama(coordinataOrizzontale - 1,
								coordinataVerticale - 1) && pedina.isDama())
				&& getGioco().isLibero(coordinataOrizzontale - 2,
						coordinataVerticale - 2));
	}

	/**
	 * Controlla se una determinata pedina, in una posizione ipotetica e
	 * indicata,
	 * può mangiare in alto a destra.
	 * 
	 * @param pedina
	 *        Pedina da considerare
	 * @param coordinataOrizzontale
	 *        Posizione orizzontale ipotetica nella griglia
	 * @param coordinataVerticale
	 *        Posizione verticale ipotetica nella griglia
	 * @return
	 */
	public boolean mangiareDestra(Pedina pedina, int coordinataOrizzontale,
			int coordinataVerticale) {
		return (getGioco().isInterno(coordinataOrizzontale + 1,
				coordinataVerticale - 1)
				&& getGioco().isInterno(coordinataOrizzontale + 2,
						coordinataVerticale - 2)
				&& getGioco().isNemico(pedina, coordinataOrizzontale + 1,
						coordinataVerticale - 1)
				&& (!getGioco().isDama(coordinataOrizzontale + 1,
						coordinataVerticale - 1)
						|| getGioco().isDama(coordinataOrizzontale + 1,
								coordinataVerticale - 1) && pedina.isDama())
				&& getGioco().isLibero(coordinataOrizzontale + 2,
						coordinataVerticale - 2));
	}

	/**
	 * Controlla se una determinata pedina, in una posizione ipotetica e
	 * indicata,
	 * può mangiare in basso a sinistra.
	 * 
	 * @param pedina
	 *        Pedina da considerare
	 * @param coordinataOrizzontale
	 *        Posizione orizzontale ipotetica nella griglia
	 * @param coordinataVerticale
	 *        Posizione verticale ipotetica nella griglia
	 * @return
	 */
	public boolean mangiareISinistra(Pedina pedina, int coordinataOrizzontale,
			int coordinataVerticale) {
		return (getGioco().isInterno(coordinataOrizzontale - 1,
				coordinataVerticale + 1)
				&& getGioco().isInterno(coordinataOrizzontale - 2,
						coordinataVerticale + 2)
				&& getGioco().isNemico(pedina, coordinataOrizzontale - 1,
						coordinataVerticale + 1)
				&& (!getGioco().isDama(coordinataOrizzontale - 1,
						coordinataVerticale + 1)
						|| getGioco().isDama(coordinataOrizzontale - 1,
								coordinataVerticale + 1) && pedina.isDama())
				&& getGioco().isLibero(coordinataOrizzontale - 2,
						coordinataVerticale + 2));
	}

	/**
	 * Controlla se una determinata pedina, in una posizione ipotetica e
	 * indicata,
	 * può mangiare in basso a destra.
	 * 
	 * @param pedina
	 *        Pedina da considerare
	 * @param coordinataOrizzontale
	 *        Posizione orizzontale ipotetica nella griglia
	 * @param coordinataVerticale
	 *        Posizione verticale ipotetica nella griglia
	 * @return
	 */
	public boolean mangiareIDestra(Pedina pedina, int coordinataOrizzontale,
			int coordinataVerticale) {
		return (getGioco().isInterno(coordinataOrizzontale + 1,
				coordinataVerticale + 1)
				&& getGioco().isInterno(coordinataOrizzontale + 2,
						coordinataVerticale + 2)
				&& getGioco().isNemico(pedina, coordinataOrizzontale + 1,
						coordinataVerticale + 1)
				&& (!getGioco().isDama(coordinataOrizzontale + 1,
						coordinataVerticale + 1)
						|| getGioco().isDama(coordinataOrizzontale + 1,
								coordinataVerticale + 1) && pedina.isDama())
				&& getGioco().isLibero(coordinataOrizzontale + 2,
						coordinataVerticale + 2));
	}

	/**
	 * Controlla se una determinata pedina, in una posizione ipotetica e
	 * indicata,
	 * può muoversi in alto a sinistra.
	 * 
	 * @param pedina
	 *        Pedina da considerare
	 * @param coordinataOrizzontale
	 *        Posizione orizzontale ipotetica nella griglia
	 * @param coordinataVerticale
	 *        Posizione verticale ipotetica nella griglia
	 * @return
	 */
	public boolean muoviSinistra(Pedina pedina, int coordinataOrizzontale,
			int coordinataVerticale) {
		return getGioco().isLibero(coordinataOrizzontale - 1,
				coordinataVerticale - 1);
	}

	/**
	 * Controlla se una determinata pedina, in una posizione ipotetica e
	 * indicata,
	 * può muoversi in alto a destra.
	 * 
	 * @param pedina
	 *        Pedina da considerare
	 * @param coordinataOrizzontale
	 *        Posizione orizzontale ipotetica nella griglia
	 * @param coordinataVerticale
	 *        Posizione verticale ipotetica nella griglia
	 * @return
	 */
	public boolean muoviDestra(Pedina pedina, int coordinataOrizzontale,
			int coordinataVerticale) {
		return getGioco().isLibero(coordinataOrizzontale + 1,
				coordinataVerticale - 1);
	}

	/**
	 * Controlla se una determinata pedina, in una posizione ipotetica e
	 * indicata,
	 * può muoversi in basso a sinistra.
	 * 
	 * @param pedina
	 *        Pedina da considerare
	 * @param coordinataOrizzontale
	 *        Posizione orizzontale ipotetica nella griglia
	 * @param coordinataVerticale
	 *        Posizione verticale ipotetica nella griglia
	 * @return
	 */
	public boolean muoviISinistra(Pedina pedina, int coordinataOrizzontale,
			int coordinataVerticale) {
		return getGioco().isLibero(coordinataOrizzontale - 1,
				coordinataVerticale + 1);
	}

	/**
	 * Controlla se una determinata pedina, in una posizione ipotetica e
	 * indicata,
	 * può muoversi in basso a destra.
	 * 
	 * @param pedina
	 *        Pedina da considerare
	 * @param coordinataOrizzontale
	 *        Posizione orizzontale ipotetica nella griglia
	 * @param coordinataVerticale
	 *        Posizione verticale ipotetica nella griglia
	 * @return
	 */
	public boolean muoviIDestra(Pedina pedina, int coordinataOrizzontale,
			int coordinataVerticale) {
		return getGioco().isLibero(coordinataOrizzontale + 1,
				coordinataVerticale + 1);
	}

	/**
	 * Controlla la condizione, in base alla fazione, per cui una determinata
	 * pedina può diventare dama.
	 * 
	 * @param pedina
	 *        Pedina da considerare
	 * @return
	 */
	public abstract boolean rendiDama(Pedina pedina);

	protected Gioco getGioco() {
		return this.gioco;
	}
}
