package layout;

import java.awt.Graphics;
import java.util.HashMap;
import java.util.Vector;

/**
 * @author Thomas Zilio
 */
public class GestoreLayout {
	private HashMap<String, Vector<Layout>> layout;
	private String selezionato;

	/**
	 * 
	 */
	public GestoreLayout() {
		setLayout(new HashMap<String, Vector<Layout>>());
	}

	/**
	 * 
	 * @param nome
	 */
	public void aggiungiLayout(String nome) {
		getLayout().put(nome, new Vector<Layout>(1, 1));
		if (getSelezionato() == null) setSelezionato(nome);
	}

	/**
	 * 
	 * @param nome
	 * @param layout
	 */
	public void aggiungiLayout(String nome, Vector<Layout> layout) {
		aggiungiLayout(nome);
		getLayout(nome).addAll(layout);
	}

	/**
	 * 
	 * @param nome
	 * @param layout
	 */
	public void aggiungiLayout(String nome, Layout layout) {
		aggiungiLayout(nome);
		getLayout(nome).add(layout);
	}

	/**
	 * 
	 * @param g
	 */
	public void disegna(Graphics g) {
		if (getLayoutSelezionato() != null) {
			for (Layout l : getLayoutSelezionato()) {
				l.disegna(g);
			}
		}
	}

	/**
	 * 
	 * @param nome
	 * @return
	 */
	public Vector<Layout> getLayout(String nome) {
		return getLayout().get(nome);
	}

	/**
	 * 
	 * @return
	 */
	public Vector<Layout> getLayoutSelezionato() {
		return getLayout().get(getSelezionato());
	}

	public void rimuovi(String nome) {
		getLayout().remove(nome);
	}

	public HashMap<String, Vector<Layout>> getLayout() {
		return layout;
	}

	public void setLayout(HashMap<String, Vector<Layout>> layout) {
		this.layout = layout;
	}

	public String getSelezionato() {
		return selezionato;
	}

	public void setSelezionato(String selezionato) {
		this.selezionato = selezionato;
	}

}
