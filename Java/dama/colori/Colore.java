package dama.colori;

import java.awt.Color;

/**
 * Singolo set di colori.
 * 
 * @author Thomas Zilio
 */
public class Colore {
	private String nome;
	private Color background, coloreNome, quadranteBianca, quadranteNero,
			pedinaBianca, pedinaNera, pedinaTemporanea, pedinaDamaBianca,
			pedinaDamaNera;

	/**
	 * 
	 * @param nome
	 * @param background
	 * @param coloreNome
	 * @param quadranteBianca
	 * @param quadranteNero
	 * @param pedinaBianca
	 * @param pedinaNera
	 * @param pedinaTemporanea
	 * @param pedinaDamaBianca
	 * @param pedinaDamaNera
	 */
	public Colore(String nome, Color background, Color coloreNome,
			Color quadranteBianca, Color quadranteNero, Color pedinaBianca,
			Color pedinaNera, Color pedinaTemporanea, Color pedinaDamaBianca,
			Color pedinaDamaNera) {
		this.nome = nome;
		this.background = background;
		this.coloreNome = coloreNome;
		this.quadranteBianca = quadranteBianca;
		this.quadranteNero = quadranteNero;
		this.pedinaBianca = pedinaBianca;
		this.pedinaNera = pedinaNera;
		this.pedinaTemporanea = pedinaTemporanea;
		this.pedinaDamaBianca = pedinaDamaBianca;
		this.pedinaDamaNera = pedinaDamaNera;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Color getBackground() {
		return background;
	}

	public void setBackground(Color background) {
		this.background = background;
	}

	public Color getColoreNome() {
		return coloreNome;
	}

	public void setColoreNome(Color coloreNome) {
		this.coloreNome = coloreNome;
	}

	public Color getQuadranteBianca() {
		return quadranteBianca;
	}

	public void setQuadranteBianca(Color bianco) {
		this.quadranteBianca = bianco;
	}

	public Color getQuadranteNero() {
		return quadranteNero;
	}

	public void setQuadranteNero(Color nero) {
		this.quadranteNero = nero;
	}

	public Color getPedinaBianca() {
		return pedinaBianca;
	}

	public void setPedinaBianca(Color bianchi) {
		this.pedinaBianca = bianchi;
	}

	public Color getPedinaNera() {
		return pedinaNera;
	}

	public void setPedinaNera(Color neri) {
		this.pedinaNera = neri;
	}

	public Color getPedinaTemporanea() {
		return pedinaTemporanea;
	}

	public void setPedinaTemporanea(Color grigi) {
		this.pedinaTemporanea = grigi;
	}

	public Color getPedinaDamaBianca() {
		return pedinaDamaBianca;
	}

	public void setPedinaDamaBianca(Color damaBianca) {
		this.pedinaDamaBianca = damaBianca;
	}

	public Color getPedinaDamaNera() {
		return pedinaDamaNera;
	}

	public void setPedinaDamaNera(Color damaNera) {
		this.pedinaDamaNera = damaNera;
	}

}
