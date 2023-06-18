package dama.colori;

import java.awt.Color;
import java.util.Vector;

/**
 * Gestore dei colori.
 * 
 * @author Thomas Zilio
 */
public class Colori {
	private Vector<Colore> colori;
	private int selezionato;

	/**
	 * 
	 */
	public Colori() {
		setColori(new Vector<Colore>(2, 10));
		getColori().add(
				new Colore("Default", Color.white, Color.BLACK, Color.white, Color.DARK_GRAY, Color.yellow, Color.black, Color.gray, Color.blue, Color.red));
		getColori().add(
				new Colore("Halloween", Color.orange, Color.green, Color.white, Color.DARK_GRAY, Color.orange, Color.green, Color.gray, Color.magenta, Color.PINK));
		setSelezionato(0);
	}

	public Colore getColoreSelezionato() {
		return getColori().elementAt(getSelezionato());
	}

	public Vector<Colore> getColori() {
		return colori;
	}

	private void setColori(Vector<Colore> colori) {
		this.colori = colori;
	}

	public int getSelezionato() {
		return selezionato;
	}

	/**
	 * 
	 * @param selezionato
	 */
	public void setSelezionato(int selezionato) {
		if (selezionato >= 0
				&& selezionato < getColori().size()) this.selezionato =
						selezionato;
		else this.selezionato = 0;
	}
}
