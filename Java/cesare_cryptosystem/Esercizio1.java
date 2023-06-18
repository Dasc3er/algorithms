/*
 * Il crittosistema di
 * Cesare Svetonio, nella Vita dei dodici Cesari, racconta che Giulio Cesare
 * utilizzava un sistema di cifrazione molto semplice: ogni lettera va
 * sostituita con quella che si trova tre posti dopo. Ad esempio la frase:
 * DOMANI ATTACCHEREMO (testo in chiaro), diventer�: GRPDQN DZZDFFMHUHPR (testo
 * cifrato). Se utilizziamo un alfabeto di 26 caratteri e generalizzando il
 * sistema si pu� utilizzare come chiave di cifratura qualsiasi numero compreso
 * tra 1 e 25. I numeri 0 e 26 producono una cifratura non troppo segreta!
 * 
 * Esercizio 1 Implementare il Cifrario di Cesare leggendo una linea di testo e
 * un numero intero. Il risultato deve essere il testo cifrato. L'alfabeto di
 * riferimento � la serie di caratteri MAIUSCOLI da 'A' a 'Z'. Caratteri diversi
 * devono essere ignorati e non comparire in output.
 */
package cesare_cryptosystem;

import java.util.Scanner;

class Esercizio1 {
	public static void main(String args[]) {
		Scanner tast = new Scanner(System.in);
		System.out.println("Inserire la parola da codificare");
		String par = tast.nextLine();
		System.out.println("Inserire il numero da utilizzare come chiave di cifratura");
		int n = tast.nextInt();
		int index;
		for (int i = 0; i < par.length(); i++) {
			if (((int) par.charAt(i) <= 'Z') && ((int) par.charAt(i) >= 'A')) {
				index = ((int) par.charAt(i)) + n;
				index = ((index - 'A') % 26) + 'A';
				System.out.print((char) index);
			}
		}
	}
}