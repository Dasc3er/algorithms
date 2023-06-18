package layout;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Elemento di base del Layout.
 * 
 * @author Thomas Zilio
 */
public class Elemento {
	private Color background;

	public Elemento() {
		setBackground(Color.white);
	}

	public Elemento(Color background) {
		setBackground(background);
	}

	public Color getBackground() {
		return background;
	}

	public void setBackground(Color background) {
		this.background = background;
	}

	/**
	 * Disegna l'elemento.
	 * 
	 * @param g
	 * @param coordinataOrizzontale
	 * @param coordinataVerticale
	 * @param width
	 * @param height
	 * @param invertito
	 */
	public void disegna(Graphics g, int coordinataOrizzontale,
			int coordinataVerticale, int width, int height, boolean invertito) {
		g.setColor(getBackground());
		g.fillRect(coordinataOrizzontale, coordinataVerticale, width, height);
	}
}