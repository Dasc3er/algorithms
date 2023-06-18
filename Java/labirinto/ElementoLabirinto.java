package labirinto;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import layout.ElementoString;

/**
 * Rappresentazione degli elementi del labirinto.
 * 
 * @author Thomas Zilio
 */
public class ElementoLabirinto extends ElementoString {
	public static int width;
	private Point posizione;
	private boolean sinistra = false, destra = false, alto = false,
			basso = false, sinistraEsiste = true, destraEsiste = true,
			altoEsiste = true, bassoEsiste = true;

	public ElementoLabirinto(int x, int y) {
		super("" + (x * (getWidth() + 1) + y + 1));
		setPosizione(new Point(x, y));
		if (y == 0) setSinistraEsiste(false);
		else if (y == width) setDestraEsiste(false);
		if (x == 0) setAltoEsiste(false);
		else if (x == width) setBassoEsiste(false);
	}

	public String getNome() {
		return ""
				+ (getPosizione().x * (getWidth() + 1) + getPosizione().y + 1);
	}

	public Point getPosizione() {
		return this.posizione;
	}

	public void setPosizione(Point posizione) {
		this.posizione = posizione;
	}

	public boolean isSinistra() {
		return this.sinistra;
	}

	public void setSinistra(boolean sinistra) {
		this.sinistra = sinistra;
	}

	public boolean isDestra() {
		return this.destra;
	}

	public void setDestra(boolean destra) {
		this.destra = destra;
	}

	public boolean isAlto() {
		return this.alto;
	}

	public void setAlto(boolean alto) {
		this.alto = alto;
	}

	public boolean isBasso() {
		return this.basso;
	}

	public void setBasso(boolean basso) {
		this.basso = basso;
	}

	public boolean isSinistraEsiste() {
		return this.sinistraEsiste;
	}

	public void setSinistraEsiste(boolean sinistraEsiste) {
		this.sinistraEsiste = sinistraEsiste;
	}

	public boolean isDestraEsiste() {
		return this.destraEsiste;
	}

	public void setDestraEsiste(boolean destraEsiste) {
		this.destraEsiste = destraEsiste;
	}

	public boolean isAltoEsiste() {
		return this.altoEsiste;
	}

	public void setAltoEsiste(boolean altoEsiste) {
		this.altoEsiste = altoEsiste;
	}

	public boolean isBassoEsiste() {
		return this.bassoEsiste;
	}

	public void setBassoEsiste(boolean bassoEsiste) {
		this.bassoEsiste = bassoEsiste;
	}

	public boolean visitato() {
		return isDestra() || isSinistra() || isAlto() || isBasso();
	}

	public static int getWidth() {
		return width;
	}

	public boolean isolatoDestra() {
		return !isDestraEsiste() || !isDestra();
	}

	public boolean isolatoSinistra() {
		return !isSinistraEsiste() || !isSinistra();
	}

	public boolean isolatoBasso() {
		return !isBassoEsiste() || !isBasso();
	}

	public boolean isolatoAlto() {
		return !isAltoEsiste() || !isAlto();
	}

	public static void setWidth(int width) {
		ElementoLabirinto.width = width - 1;
	}

	public void disegna(Graphics g, int coordinataOrizzontale,
			int coordinataVerticale, int width, int height, boolean invertito) {
		super.disegna(g, coordinataOrizzontale, coordinataVerticale, width,
				height, invertito);

		g.setColor(Color.black);
		if (isolatoSinistra()) g.drawLine(coordinataOrizzontale,
				coordinataVerticale, coordinataOrizzontale, coordinataVerticale
						+ height);
		if (isolatoDestra()) g.drawLine(coordinataOrizzontale + width,
				coordinataVerticale, coordinataOrizzontale + width,
				coordinataVerticale + height);

		if (isolatoAlto()) g.drawLine(coordinataOrizzontale,
				coordinataVerticale, coordinataOrizzontale + width,
				coordinataVerticale);
		if (isolatoBasso()) g.drawLine(coordinataOrizzontale,
				coordinataVerticale + height, coordinataOrizzontale + width,
				coordinataVerticale + height);
	}

	@Override
	public String toString() {
		return "ElementoLabirinto [posizione=" + posizione + ", sinistra="
				+ sinistra + ", destra=" + destra + ", alto=" + alto
				+ ", basso=" + basso + ", sinistraEsiste=" + sinistraEsiste
				+ ", destraEsiste=" + destraEsiste + ", altoEsiste="
				+ altoEsiste + ", bassoEsiste=" + bassoEsiste + ", getNome()="
				+ getNome() + ", getPosizione()=" + getPosizione()
				+ ", isSinistra()=" + isSinistra() + ", isDestra()="
				+ isDestra() + ", isAlto()=" + isAlto() + ", isBasso()="
				+ isBasso() + ", isSinistraEsiste()=" + isSinistraEsiste()
				+ ", isDestraEsiste()=" + isDestraEsiste()
				+ ", isAltoEsiste()=" + isAltoEsiste() + ", isBassoEsiste()="
				+ isBassoEsiste() + ", visitato()=" + visitato()
				+ ", isolatoDestra()=" + isolatoDestra()
				+ ", isolatoSinistra()=" + isolatoSinistra()
				+ ", isolatoBasso()=" + isolatoBasso() + ", isolatoAlto()="
				+ isolatoAlto() + "]";
	}

}
