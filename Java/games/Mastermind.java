/*
 * MasterMind
 * 
 * Implementare il gioco del MasterMind tenendo conto che la combinazione �
 * formata da quttro numeri da 1 a 8 (numeri al posto dei colori). In
 * alternativa usare le lettere da A a H. Il numero massimo di tentativi � 14.
 * 
 * Il gioco del MasterMind consiste in una tavola con delle righe di fori (4 o
 * 6) in cui inserire dei segnalini colorati. Il gioco consiste nello scoprire
 * una combinazione di colori nascosta. Un giocatore (A) prepara la combinazione
 * da trovare. L'altro giocatore (B) deve scoprirla. 1 - B inserisce una
 * combinazione di colori alla prima posizione; 2 - A fornisce un responso con
 * le seguenti indicazioni: un segnalino nero per ogni colore giusto al posto
 * giusto, un segnalino bianco per ogni colore giusto al posto sbagliato.
 * ATTENZIONE: il giocatore che deve indovinare deve sapere solo il numero di
 * neri/bianchi ma quali sono giusti. 3 - B inserisce una combinazione alla
 * seconda posizione cercando di capire quali sono i colori giusti... Il gioco
 * termina quando B ha indovinato o viene superato il numero massimo di
 * tentativi.
 * 
 * Implementare il programma con l'uso di metodi. Esempio: 1 - metodo per
 * generare una combinazione "segreta" (ritorna un vettore di quattro interi
 * casuali da 1 a 8) 2 - metodo per controllare e calcolare numero di
 * neri/bianchi (ritorna un vettore di due interi...)
 */

package games;

import java.util.Scanner;

public class Mastermind {
	public static void main(String args[]) {
		Scanner tast = new Scanner(System.in);
		System.out.println("Benvenuto!!! Questo gioco rappresenta mastermind attraverso l'utilizzo dei numeri al posto dei colori...");
		System.out.println("Prova ad indovinare, e buona fortuna!!!");
		int[] comb = new int[4];
		int[] segreta = new int[4];
		segreta = rand();
		int volte = 0;
		int cont;
		boolean trovata = false;
		while ((volte < 15) && (trovata == false)) {
			for (int i = 0; i < 4; i++) {
				cont = 0;
				do {
					if (cont != 0) System.out.println("Inserisci nuovamente il colore...");
					comb[i] = tast.nextInt();
					cont++;
				}while ((comb[i] < 1) || (comb[i] > 8));
			}
			trovata = find(segreta, comb);
			volte++;
		}
		if (trovata == true) System.out.println("Ottimo!!! Hai vinto in "
				+ volte + " tentativi!!!");
		else System.out.println("Hai finito i tentativi... Grazie per aver giocato!!!");
	}

	public static int[] rand() {
		System.out.println("La combinazione segreta �:");
		int[] c = new int[4];
		for (int i = 0; i < 4; i++) {
			c[i] = (int) (Math.random() * 8 + 1);
			System.out.print(c[i]);
		}
		System.out.println("\nTi piace vincere facile, eh?");
		return c;
	}

	public static boolean find(int[] segreta, int[] comb) {
		int[] s = new int[4];
		for (int i = 0; i < s.length; i++) {
			s[i] = segreta[i];
		}
		int[] c = new int[4];
		for (int i = 0; i < c.length; i++) {
			c[i] = comb[i];
		}
		int white = 0, black = 0;
		for (int i = 0; i < s.length; i++) {
			if (c[i] == s[i]) {
				c[i] = -1;
				s[i] = 0;
				black++;
			}
		}
		for (int i = 0; i < s.length; i++) {
			for (int j = 0; j < s.length; j++) {
				if (c[i] == s[j]) {
					c[i] = -1;
					s[j] = 0;
					j = s.length;
					white++;
				}
			}
		}
		if (black == 4) return true;
		else {
			System.out.println("Ci sono " + black
					+ " colori al posto giusto, e " + white
					+ " colori sono giusti ma al posto sbagliato");
			return false;
		}
	}
}
