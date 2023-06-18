package dama.giocatori;

import dama.Pedina;

/**
 * Rappresentazione dei movimenti delle pedine.
 * 
 * @author Thomas Zilio
 */
public class Mossa implements Comparable<Mossa> {
	private final Pedina pedina;
	private final int posizioneOrizzontaleIniziale, posizioneVerticaleIniziale,
			posizioneOrizzontaleFinale, posizioneVerticaleFinale;
	private int punteggioMossa;

	public Mossa(Pedina pedina, int differenzaOrizzontale,
			int differenzaVerticale) {
		this.pedina = pedina;
		this.posizioneOrizzontaleIniziale = pedina.getPosizioneOrizzontale();
		this.posizioneVerticaleIniziale = pedina.getPosizioneVerticale();
		this.posizioneOrizzontaleFinale =
				posizioneOrizzontaleIniziale + differenzaOrizzontale;
		this.posizioneVerticaleFinale =
				posizioneVerticaleIniziale + differenzaVerticale;
		calcolaPunteggio();
	}

	private void calcolaPunteggio() {
		if (Math.abs(posizioneOrizzontaleIniziale
				- posizioneOrizzontaleFinale) == 1) this.punteggioMossa = 1;
		else this.punteggioMossa = 5;
	}

	public Pedina getPedina() {
		return pedina;
	}

	public int getPosizioneOrizzontaleIniziale() {
		return posizioneOrizzontaleIniziale;
	}

	public int getPosizioneVerticaleIniziale() {
		return posizioneVerticaleIniziale;
	}

	public int getPosizioneOrizzontaleFinale() {
		return posizioneOrizzontaleFinale;
	}

	public int getPosizioneVerticaleFinale() {
		return posizioneVerticaleFinale;
	}

	public int getPunteggioMossa() {
		return punteggioMossa;
	}

	public void seleziona() {
		getPedina().getGiocatore().getGioco().eseguiMossa(this);
	}

	@Override
	public int compareTo(Mossa arg0) {
		return new Integer(getPunteggioMossa()).compareTo(
				arg0.getPunteggioMossa());
	}

	@Override
	public String toString() {
		return "mossa;" + this.posizioneOrizzontaleIniziale + ";"
				+ this.posizioneVerticaleIniziale + ";"
				+ this.posizioneOrizzontaleFinale + ";"
				+ this.posizioneVerticaleFinale;
	}

}
