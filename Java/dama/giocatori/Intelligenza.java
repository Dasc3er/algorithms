package dama.giocatori;

import java.util.Collections;
import java.util.Vector;

import dama.Gioco;
import dama.Pedina;
import dama.fazioni.Fazione;

/**
 * Intelligenza di base, da modello per future e migliori implementazioni.
 * Controlla per ogni pedina a disposizione quella con mossa migliore (priorità
 * verso il mangiare l'avversario).
 * 
 * @author Thomas Zilio
 */
public class Intelligenza extends Giocatore {

	public Intelligenza(Gioco gioco, String name, Fazione fazione) {
		super(gioco, name, fazione);
	}

	@Override
	public void muovi() {
		if (getGriglia().size() != 0) {
			Mossa migliore = null;
			for (Pedina p : getGriglia().values()) {
				Vector<Mossa> mosse = trovaMovimentiPossibili(p);
				Collections.sort(mosse);
				if (mosse.size() != 0 && (migliore == null || mosse.get(
						0).getPunteggioMossa() > migliore.getPunteggioMossa())) {
					migliore = mosse.get(0);
				}
			}
			migliore.seleziona();
		}
		passaTurno();
	}

	@Override
	public void muovi(int coordinataOrizzontale, int coordinataVerticale) {
		muovi();
	}
}
