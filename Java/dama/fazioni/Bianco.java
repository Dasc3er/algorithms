package dama.fazioni;

import dama.Gioco;
import dama.Pedina;

/**
 * Implementazione della fazione bianca.
 * 
 * @see Fazione
 * @author Thomas Zilio
 */
public class Bianco extends Fazione {
	/**
	 * Costruttore di base.
	 * 
	 * @param gioco
	 */
	public Bianco(Gioco gioco) {
		super(gioco);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean mangiareSinistra(Pedina pedina, int coordinataOrizzontale,
			int coordinataVerticale) {
		return super.mangiareSinistra(pedina, coordinataOrizzontale,
				coordinataVerticale);
	}

	@Override
	public boolean mangiareDestra(Pedina pedina, int coordinataOrizzontale,
			int coordinataVerticale) {
		return super.mangiareDestra(pedina, coordinataOrizzontale,
				coordinataVerticale);
	}

	@Override
	public boolean mangiareISinistra(Pedina pedina, int coordinataOrizzontale,
			int coordinataVerticale) {
		if (pedina.isDama()) return super.mangiareISinistra(pedina,
				coordinataOrizzontale, coordinataVerticale);
		return false;
	}

	@Override
	public boolean mangiareIDestra(Pedina pedina, int coordinataOrizzontale,
			int coordinataVerticale) {
		if (pedina.isDama()) return super.mangiareIDestra(pedina,
				coordinataOrizzontale, coordinataVerticale);
		return false;
	}

	@Override
	public boolean muoviSinistra(Pedina pedina, int coordinataOrizzontale,
			int coordinataVerticale) {
		return super.muoviSinistra(pedina, coordinataOrizzontale,
				coordinataVerticale);
	}

	@Override
	public boolean muoviDestra(Pedina pedina, int coordinataOrizzontale,
			int coordinataVerticale) {
		return super.muoviDestra(pedina, coordinataOrizzontale,
				coordinataVerticale);
	}

	@Override
	public boolean muoviISinistra(Pedina pedina, int coordinataOrizzontale,
			int coordinataVerticale) {
		if (pedina.isDama()) return super.muoviISinistra(pedina,
				coordinataOrizzontale, coordinataVerticale);
		return false;
	}

	@Override
	public boolean muoviIDestra(Pedina pedina, int coordinataOrizzontale,
			int coordinataVerticale) {
		if (pedina.isDama()) return super.muoviIDestra(pedina,
				coordinataOrizzontale, coordinataVerticale);
		return false;
	}

	@Override
	public boolean rendiDama(Pedina pedina) {
		if (pedina.getGiocatore().getFazione() == this
				&& pedina.getPosizioneVerticale() == 0) return true;
		return false;
	}

}
