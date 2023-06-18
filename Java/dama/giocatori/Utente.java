package dama.giocatori;

import java.util.Vector;

import dama.Gioco;
import dama.Pedina;
import dama.fazioni.Fazione;

public class Utente extends Giocatore {
	private boolean mangiare = false, mangiato = false;
	private Vector<Mossa> mosse;

	public Utente(Gioco gioco, String name, Fazione fazione) {
		super(gioco, name, fazione);
		setMosse(new Vector<Mossa>(2, 2));
	}

	private boolean selezionato() {
		return !getMosse().isEmpty();
	}

	@Override
	public void muovi() {

	}

	// Non gestisce doppio mangia e, se si muove e poi può mangiare rende
	// disponibile
	@Override
	public void muovi(int coordinataOrizzontale, int coordinataVerticale) {
		Pedina pedina = getPedina(coordinataOrizzontale, coordinataVerticale);
		if (mangiare || (selezionato() && pedina != null && pedina.isTemp())) {
			Mossa mossa = null;
			for (Mossa m : mosse) {
				if (m.getPosizioneOrizzontaleFinale() == coordinataOrizzontale
						&& m.getPosizioneVerticaleFinale() == coordinataVerticale) mossa = m;
			}
			if (mossa != null) {
				getGioco().eseguiMossa(mossa);
				if (mossa.getPunteggioMossa() != 1) {
					System.out.println("Ha mangiato");
					mangiato = true;
				}
			}
			pedina = getPedina(coordinataOrizzontale, coordinataVerticale);
			if (pedina != null && mangiato && possibileMangiare(pedina)) {
				mosse = trovaMovimentiMangiare(pedina);
				System.out.println("Può mangiare ancora");
				aggiungiPedinaTemporanee();
				mangiare = true;
			}
			else {
				System.out.println("Passa turno");
				mangiato = false;
				mangiare = false;
				passaTurno();
			}
		}
		else {
			if (pedina != null) {
				mosse = trovaMovimentiPossibili(pedina);
				aggiungiPedinaTemporanee();
			}
		}
	}

	public void aggiungiPedinaTemporanee() {
		cancellaTemporanei();
		for (Mossa m : mosse) {
			new Pedina(this, m.getPosizioneOrizzontaleFinale(), m.getPosizioneVerticaleFinale(), true);
		}
	}

	public boolean isMangiare() {
		return mangiare;
	}

	public void setMangiare(boolean mangiare) {
		this.mangiare = mangiare;
	}

	public Vector<Mossa> getMosse() {
		return mosse;
	}

	public void setMosse(Vector<Mossa> mosse) {
		this.mosse = mosse;
	}

}
