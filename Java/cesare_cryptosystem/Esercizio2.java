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
 * Esercizio 2 Idem come sopra in cui l'alfabeto di riferimento � la serie di
 * caratteri MAIUSCOLI da 'A' a 'Z', i caratteri minuscoli da 'a' a 'z', le
 * cifre da '0' a '9'. Caratteri diversi devono essere ignorati e non comparire
 * in output.
 */
package cesare_cryptosystem;

import java.util.Scanner;

class Esercizio2 {
	public static void main(String args[]) {
		Scanner tast = new Scanner(System.in);
		System.out.println("Inserire la parola da codificare");
		String par = tast.nextLine();
		System.out.println("Inserire il numero da utilizzare come chiave di cifratura");
		int n = tast.nextInt();
		int index;
		for (int i = 0; i < par.length(); i++) {
			if (par.charAt(i) == ' ') {
				System.out.print(" ");
			}
			else {
				if (((int) par.charAt(i) >= 'A')
						&& ((int) par.charAt(i) <= 'Z')) {
					index = ((int) par.charAt(i)) + n;
					index = ((index - 'A') % 26) + 'A';
					System.out.print((char) index);
				}
				else if (((int) par.charAt(i) >= 'a')
						&& ((int) par.charAt(i) <= 'z')) {
					index = ((int) par.charAt(i)) + n;
					index = ((index - 'a') % 26) + 'a';
					System.out.print((char) index);
				}
				else if (((int) par.charAt(i) >= '0')
						&& ((int) par.charAt(i) <= '9')) {
					index = ((int) par.charAt(i)) + n;
					index = ((index - '0') % 26) + '0';
					System.out.print((char) index);
				}
			}
		}
	}
}