package interfacce;

/**
 * @author Thomas Zilio
 */
import java.util.Locale;
import java.util.ResourceBundle;

public interface ITraduzione {
	Locale localizzazione = new Locale("it", "IT");
	ResourceBundle risorse = ResourceBundle.getBundle("Messages",
			localizzazione);

	default String traduzione(String nome) {
		return risorse.getString(nome);
	}
}
