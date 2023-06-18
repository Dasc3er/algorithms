package layout;

import java.awt.Graphics;

/**
 * Classe dedita alla separazione di un determinato rettangolo i più parti
 * equivalenti.<br>
 * 
 * Default
 * new Layout(3,2)
 * 1 | 2 | 3
 * 4 | 5 | 6
 * 
 * new Layout(1,3)
 * 1
 * 2
 * 3
 * 
 * Invertito
 * new Layout(3,2)
 * 1 | 3 | 5
 * 2 | 4 | 6
 * 
 * new Layout(1,3)
 * 1
 * 2
 * 3
 * 
 * @author Thomas Zilio
 */
public class Layout {
	private int sezioniOrizzontali, sezioniVerticali, larghezza, altezza,
			inizioOrizzontale, inizioVerticale, ultimo = 0;
	private Elemento[] elementi;
	private boolean invertito = false;

	/**
	 * Costruttore principale.
	 * 
	 * @param sezioniOrizzontali
	 *        Numero di settori orizzontali
	 * @param sezioniVerticali
	 *        Numero di settori verticali
	 */
	public Layout(int sezioniOrizzontali, int sezioniVerticali) {
		setLarghezza(0);
		setAltezza(0);
		setInizioOrizzontale(1);
		setInizioVerticale(1);
		this.sezioniOrizzontali = sezioniOrizzontali;
		setSezioniVerticali(sezioniVerticali);
	}

	/**
	 * Costruttore secondario.
	 * 
	 * @param larghezza
	 *        Larghezza totale
	 * @param altezza
	 *        Altezza totale
	 * @param sezioniOrizzontali
	 *        Numero di settori orizzontali
	 * @param sezioniVerticali
	 *        Numero di settori verticali
	 */
	public Layout(int larghezza, int altezza, int sezioniOrizzontali,
			int sezioniVerticali) {
		setLarghezza(larghezza);
		setAltezza(altezza);
		setInizioOrizzontale(1);
		setInizioVerticale(1);
		this.sezioniOrizzontali = sezioniOrizzontali;
		setSezioniVerticali(sezioniVerticali);
	}

	/**
	 * Costruttore secondario con specificazione di spostamento iniziale del
	 * pannello.
	 * 
	 * @param larghezza
	 *        Larghezza totale
	 * @param altezza
	 *        Altezza totale
	 * @param sezioniOrizzontali
	 *        Numero di settori orizzontali
	 * @param sezioniVerticali
	 *        Numero di settori verticali
	 * @param inizioOrizzontale
	 *        Pixel orizzontali di spostamento
	 * @param inizioVerticale
	 *        Pixel verticali di spostamento
	 */
	public Layout(int larghezza, int altezza, int sezioniOrizzontali,
			int sezioniVerticali, int inizioOrizzontale, int inizioVerticale) {
		this(larghezza, altezza, sezioniOrizzontali, sezioniVerticali);
		setInizioOrizzontale(inizioOrizzontale);
		setInizioVerticale(inizioVerticale);
	}

	/**
	 * Costruttore per sistema invertito.
	 * 
	 * @param larghezza
	 *        Larghezza totale
	 * @param altezza
	 *        Altezza totale
	 * @param sezioniOrizzontali
	 *        Numero di settori orizzontali
	 * @param sezioniVerticali
	 *        Numero di settori verticali
	 * @param invertito
	 *        Invertire o meno
	 */
	public Layout(int larghezza, int altezza, int sezioniOrizzontali,
			int sezioniVerticali, boolean invertito) {
		this(larghezza, altezza, sezioniOrizzontali, sezioniVerticali);
		setInvertito(invertito);
	}

	/**
	 * Costrtuttore per sistema invertito automatico, con spostamento iniziale.
	 * 
	 * @param larghezza
	 *        Larghezza totale
	 * @param altezza
	 *        Altezza totale
	 * @param sezioniOrizzontali
	 *        Numero di settori orizzontali
	 * @param sezioniVerticali
	 *        Numero di settori verticali
	 * @param inizioOrizzontale
	 *        Pixel orizzontali di spostamento
	 * @param inizioVerticale
	 *        Pixel verticali di spostamento
	 * @param invertito
	 *        Invertire o meno
	 */
	public Layout(int larghezza, int altezza, int sezioniOrizzontali,
			int sezioniVerticali, int inizioOrizzontale, int inizioVerticale,
			boolean invertito) {
		this(larghezza, altezza, sezioniOrizzontali, sezioniVerticali, inizioOrizzontale, inizioVerticale);
		setInvertito(invertito);
	}

	/**
	 * Modifica le dimensioni del pannello.
	 * 
	 * @param larghezza
	 * @param altezza
	 */
	public void modificaDimensioni(int larghezza, int altezza) {
		setLarghezza(larghezza);
		setAltezza(altezza);
	}

	/**
	 * Modifica le sezioni del pannello.
	 * 
	 * @param sezioniOrizzontali
	 * @param sezioniVerticali
	 */
	public void modificaSezioni(int sezioniOrizzontali, int sezioniVerticali) {
		this.sezioniOrizzontali = sezioniOrizzontali;
		setSezioniVerticali(sezioniVerticali);
	}

	public void reset() {
		setElementi(new Elemento[getElementi().length]);
		ultimo = 0;
	}

	/**
	 * Controlla se il numero inserito rappresenta una sezione.
	 * 
	 * @param index
	 *        Numero da controllare
	 * @return
	 */
	private boolean inArray(int index) {
		return (index < getElementi().length && index >= 0);
	}

	/**
	 * Ottiene l'elemento presente nell'indice indicato.
	 * 
	 * @param index
	 * @return
	 */
	private Elemento getElemento(int index) {
		if (inArray(index)) return this.elementi[index];
		else return null;
	}

	/**
	 * Sostisuisce l'elemento presente nell'indice indicato con quello passato.
	 * 
	 * @param index
	 * @param elemento
	 */
	private void setElemento(int index, Elemento elemento) {
		if (inArray(index)) this.elementi[index] = elemento;
	}

	/**
	 * Ottiene l'elemento presente nel numero di sezione indicato.
	 * 
	 * @param numeroSezione
	 * @return
	 */
	public Elemento ottieniElemento(int numeroSezione) {
		return getElemento(numeroSezione - 1);
	}

	/**
	 * Calcola la larghezza della sezione in base allo spazio disponibile e al
	 * numero di sezioni da rappresentare.
	 * 
	 * @return
	 */
	private int larghezzaSezione() {
		return getLarghezza() / getSezioniOrizzontali();
	}

	/**
	 * Calcola l'altezza della sezione in base allo spazio disponibile e al
	 * numero di sezioni da rappresentare.
	 * 
	 * @return
	 */
	private int altezzaSezione() {
		return getAltezza() / getSezioniVerticali();
	}

	/**
	 * Adatta l'array al numero di sezioni inserito.
	 */
	public void adatta() {
		Elemento el[] =
				new Elemento[getSezioniOrizzontali() * getSezioniVerticali()];
		for (int i = 0; i < el.length; i++) {
			if (getElementi() != null
					&& i < getElementi().length) el[i] = getElemento(i);
			else el[i] = new Elemento();
		}
		setElementi(el);
	}

	/**
	 * Restituisce l'indice orizzontale della sezione indicata.
	 * 
	 * @param numeroSezione
	 * @return
	 */
	public int indiceOrizzontale(int numeroSezione) {
		numeroSezione--;
		if (isInvertito()) return numeroSezione / getSezioniVerticali();
		else return numeroSezione % getSezioniOrizzontali();
	}

	/**
	 * Restituisce l'indice verticale della sezione indicata.
	 * 
	 * @param numeroSezione
	 * @return
	 */
	public int indiceVerticale(int numeroSezione) {
		numeroSezione--;
		if (isInvertito()) return numeroSezione % getSezioniVerticali();
		else return numeroSezione / getSezioniOrizzontali();
	}

	/**
	 * Restituisce il valore della coordinata orizzontale nel punto finale della
	 * sezione indicata.
	 * 
	 * @param numeroSezione
	 * @return
	 */
	public int ultimaCoordinataOrizzontale(int numeroSezione) {
		int x = indiceOrizzontale(numeroSezione);
		return x * larghezzaSezione() + larghezzaSezione()
				+ getInizioOrizzontale();
	}

	/**
	 * Restituisce il valore della coordinata verticale nel punto finale della
	 * sezione indicata.
	 * 
	 * @param numeroSezione
	 * @return
	 */
	public int ultimaCoordinataVerticale(int numeroSezione) {
		int y = indiceVerticale(numeroSezione);
		return y * altezzaSezione() + altezzaSezione() + getInizioVerticale();
	}

	/**
	 * Restituisce il valore della coordinata orizzontale nel punto iniziale
	 * della sezione indicata.
	 * 
	 * @param numeroSezione
	 * @return
	 */
	public int coordinataOrizzontale(int numeroSezione) {
		int x = indiceOrizzontale(numeroSezione);
		return x * larghezzaSezione() + getInizioOrizzontale();
	}

	/**
	 * Restituisce il valore della coordinata orizzontale nel punto iniziale
	 * della sezione indicata.
	 * 
	 * @param numeroSezione
	 * @return
	 */
	public int coordinataVerticale(int numeroSezione) {
		int y = indiceVerticale(numeroSezione);
		return y * altezzaSezione() + getInizioVerticale();
	}

	/**
	 * Divide una sezione in ulteriori
	 * sezioni, restituendo un Layout dedito alla loro gestione.
	 * 
	 * @param numeroSezione
	 *        Sezione da dividere
	 * @param sezioniOrizzontali
	 *        Sezioni orizzontali
	 * @param sezioniVerticali
	 *        Sezioni verticali
	 * @return Nuovo Layout
	 */
	public Layout dividi(int numeroSezione, int sezioniOrizzontali,
			int sezioniVerticali) {
		return new Layout(larghezzaSezione(), altezzaSezione(), sezioniOrizzontali, sezioniVerticali, coordinataOrizzontale(
				numeroSezione), coordinataVerticale(numeroSezione));
	}

	/**
	 * Divide una sezione, estesa alle vicine (in base ai parametri
	 * sezioniAggiuntiveOrizzontali e sezioniAggiuntiveVerticali), in ulteriori
	 * sezioni, restituendo un Layout dedito alla loro gestione.
	 * 
	 * @param numeroSezione
	 *        Sezione di partenza
	 * @param sezioniOrizzontali
	 *        Sezioni orizzontali
	 * @param sezioniVerticali
	 *        Sezioni verticali
	 * @param sezioniAggiuntiveOrizzontali
	 *        Numero di sezioni orizzontali da dividere
	 * @param sezioniAggiuntiveVerticali
	 *        Numero di sezioni verticali da dividere
	 * @return Nuovo Layout
	 */
	public Layout dividi(int numeroSezione, int sezioniOrizzontali,
			int sezioniVerticali, int sezioniAggiuntiveOrizzontali,
			int sezioniAggiuntiveVerticali) {
		return new Layout(larghezzaSezione()
				* sezioniAggiuntiveOrizzontali, altezzaSezione()
						* sezioniAggiuntiveVerticali, sezioniOrizzontali, sezioniVerticali, coordinataOrizzontale(
								numeroSezione), coordinataVerticale(
										numeroSezione));
	}

	/**
	 * Aggiunge un'elemento seguendo l'ordine di riempimento (dall'inizio alla
	 * fine).
	 * 
	 * @param elemento
	 */
	public void aggiungi(Elemento elemento) {
		if (inArray(ultimo)) {
			setElemento(ultimo, elemento);
			ultimo++;
		}
	}

	/**
	 * Inserisce l'elemento passato nella sezione indicata, spostando tutti gli
	 * elementi successivi avanti di 1.
	 * 
	 * @param numeroSezione
	 * @param elemento
	 */
	public void inserisci(int numeroSezione, Elemento elemento) {
		numeroSezione--;
		for (int i = getElementi().length - 1; i > numeroSezione; i--) {
			setElemento(i, ottieniElemento(i));
		}
		setElemento(numeroSezione, elemento);
	}

	/**
	 * Sostituisce l'elemento nella sezione indicata con quello passato.
	 * 
	 * @param numeroSezione
	 * @param elemento
	 */
	public void sostituisci(int numeroSezione, Elemento elemento) {
		setElemento(numeroSezione - 1, elemento);
	}

	/**
	 * Elimina le caratteristiche della sezione indicata.
	 * 
	 * @param numero
	 *        Numero di sezione
	 */
	public void rimuovi(int numero) {
		setElemento(numero - 1, new Elemento());
	}

	/**
	 * Restituisce la sezione che possiede le coordinate inserite, o 0 se non
	 * esiste.
	 * 
	 * @param x
	 *        Coordinata orizzontale
	 * @param y
	 *        Coordinata verticale
	 * @return Sezione delle coordinate, o 0
	 */
	public int numeroCoordinate(int coordinataOrizzontale,
			int coordinataVerticale) {
		int x = coordinataOrizzontale - getInizioOrizzontale();
		int y = coordinataVerticale - getInizioVerticale();
		int result = x / larghezzaSezione()
				+ y / altezzaSezione() * getSezioniOrizzontali() + 1;
		if (x < 0 || y < 0
				|| result > getSezioniOrizzontali() * getSezioniVerticali()
				|| x > getLarghezza() || y > getAltezza() || x < 0
				|| y < 0) return 0;
		else return result;
	}

	/**
	 * Rappresenta tutte le informazioni inserite (da richiamare in <b>protected
	 * void paintComponent(Graphics g)</b>).
	 * 
	 * @param g
	 *        Grafica
	 */
	public void disegna(Graphics g) {
		for (int i = 1; i <= getElementi().length; i++) {
			if (ottieniElemento(i) != null) ottieniElemento(i).disegna(g,
					coordinataOrizzontale(i), coordinataVerticale(i),
					larghezzaSezione(), altezzaSezione(), isInvertito());
		}
	}

	// Get & set

	public int getSezioniOrizzontali() {
		return this.sezioniOrizzontali;
	}

	/**
	 * Assegna il valore indicato come numero di sezioni orizzontali da
	 * rappresentare e adatta il layout.
	 * 
	 * @param sezioniOrizzontali
	 */
	private void setSezioniOrizzontali(int sezioniOrizzontali) {
		this.sezioniOrizzontali = sezioniOrizzontali;
		adatta();
	}

	public int getSezioniVerticali() {
		return this.sezioniVerticali;
	}

	/**
	 * Assegna il valore indicato come numero di sezioni verticali da
	 * rappresentare e adatta il layout.
	 * 
	 * @param sezioniVerticali
	 */
	private void setSezioniVerticali(int sezioniVerticali) {
		this.sezioniVerticali = sezioniVerticali;
		adatta();
	}

	private int getLarghezza() {
		return this.larghezza;
	}

	private void setLarghezza(int larghezza) {
		this.larghezza = larghezza;
	}

	private int getAltezza() {
		return this.altezza;
	}

	private void setAltezza(int altezza) {
		this.altezza = altezza;
	}

	public Elemento[] getElementi() {
		return elementi;
	}

	private void setElementi(Elemento[] elementi) {
		this.elementi = elementi;
	}

	private int getInizioOrizzontale() {
		return inizioOrizzontale;
	}

	public void setInizioOrizzontale(int startx) {
		this.inizioOrizzontale = startx;
	}

	private int getInizioVerticale() {
		return inizioVerticale;
	}

	public void setInizioVerticale(int startY) {
		this.inizioVerticale = startY;
	}

	public boolean isInvertito() {
		return this.invertito;
	}

	public void setInvertito(boolean invertito) {
		this.invertito = invertito;
	}

}
