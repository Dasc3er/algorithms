package dama.fazioni;

import dama.Gioco;
import dama.Pedina;

/**
 * Implementazione della fazione nera.
 * 
 * @see Fazione
 * @author Thomas Zilio
 */
public class Nero extends Fazione {
	/**
	 * Costruttore di base.
	 * 
	 * @param gioco
	 */
	public Nero(Gioco gioco) {
		super(gioco);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean mangiareSinistra(Pedina pedina, int coordinataOrizzontale,
			int coordinataVerticale) {
		if (pedina.isDama()) return super.mangiareSinistra(pedina,
				coordinataOrizzontale, coordinataVerticale);
		return false;
	}

	@Override
	public boolean mangiareDestra(Pedina pedina, int coordinataOrizzontale,
			int coordinataVerticale) {
		if (pedina.isDama()) return super.mangiareDestra(pedina,
				coordinataOrizzontale, coordinataVerticale);
		return false;
	}

	@Override
	public boolean mangiareISinistra(Pedina pedina, int coordinataOrizzontale,
			int coordinataVerticale) {
		return super.mangiareISinistra(pedina, coordinataOrizzontale,
				coordinataVerticale);
	}

	@Override
	public boolean mangiareIDestra(Pedina pedina, int coordinataOrizzontale,
			int coordinataVerticale) {
		return super.mangiareIDestra(pedina, coordinataOrizzontale,
				coordinataVerticale);
	}

	@Override
	public boolean muoviSinistra(Pedina pedina, int coordinataOrizzontale,
			int coordinataVerticale) {
		if (pedina.isDama()) return super.muoviSinistra(pedina,
				coordinataOrizzontale, coordinataVerticale);
		return false;
	}

	@Override
	public boolean muoviDestra(Pedina pedina, int coordinataOrizzontale,
			int coordinataVerticale) {
		if (pedina.isDama()) return super.muoviDestra(pedina,
				coordinataOrizzontale, coordinataVerticale);
		return false;
	}

	@Override
	public boolean muoviISinistra(Pedina pedina, int coordinataOrizzontale,
			int coordinataVerticale) {
		return super.muoviISinistra(pedina, coordinataOrizzontale,
				coordinataVerticale);
	}

	@Override
	public boolean muoviIDestra(Pedina pedina, int coordinataOrizzontale,
			int coordinataVerticale) {
		return super.muoviIDestra(pedina, coordinataOrizzontale,
				coordinataVerticale);
	}

	@Override
	public boolean rendiDama(Pedina pedina) {
		if (pedina.getGiocatore().getFazione() == this
				&& pedina.getPosizioneVerticale() == getGioco().getLarghezza()
						- 1) return true;
		return false;
	}

}
