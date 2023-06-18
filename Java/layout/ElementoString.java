package layout;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 * Elemento con stringa.
 * 
 * @author Thomas Zilio
 * @bug Le stringhe vengono stampate direttamente accentrate nella sezione, ma
 *      se la lunghezza è eccessiva le parole possono invadere altre posizioni.
 */
public class ElementoString extends Elemento {
	private Color colore = Color.black;
	private String stringa;
	private Font font;

	public ElementoString() {
		super();
	}

	public ElementoString(Color background) {
		super(background);
	}

	public ElementoString(String stringa) {
		this();
		setStringa(stringa);
	}

	public ElementoString(String stringa, Font font) {
		this();
		setStringa(stringa);
		setFont(font);
	}

	public ElementoString(String stringa, Color colore) {
		setStringa(stringa);
		setColore(colore);
	}

	public ElementoString(String stringa, Color colore, Font font) {
		this(stringa, colore);
		setFont(font);
	}

	public ElementoString(Color background, String stringa, Color colore) {
		setBackground(background);
		setStringa(stringa);
		setColore(colore);
	}

	public ElementoString(Color background, String stringa, Color colore,
			Font font) {
		this(background, stringa, colore);
		setFont(font);
	}

	public Color getColore() {
		return this.colore;
	}

	public void setColore(Color colore) {
		this.colore = colore;
	}

	public String getStringa() {
		return this.stringa;
	}

	public void setStringa(String stringa) {
		this.stringa = stringa;
	}

	public Font getFont() {
		return this.font;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public void disegna(Graphics g, int coordinataOrizzontale,
			int coordinataVerticale, int width, int height, boolean invertito) {
		super.disegna(g, coordinataOrizzontale, coordinataVerticale, width,
				height, invertito);
		if (getStringa() != null) {
			g.setFont(getFont());
			g.setColor(getColore());
			g.drawString(getStringa(), coordinataOrizzontale + width / 2
					- getStringa().length() / 2 * 13, coordinataVerticale
					+ height / 2 + 7);
		}
	}
}
