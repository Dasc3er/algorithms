package interfacce;

/**
 * @author Thomas Zilio
 */
public interface IPannello {
	public void impostazioni();

//	public int inserisciPorta();

	public void UIConnessa();

	public void UIDisconnessa();

	public void scrivi(String testo);

	public void avviso(String testo);

	public void errore(String nome, String testo);

}