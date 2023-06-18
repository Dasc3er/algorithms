/*
 * Creare una Slot Machine.
 * Generare 3 numeri casuali (da 1 a 20) all'interno di un vettore. Il giocatore
 * parte con 10 monete (ogni moneta corrisponde ad un tentativo), ad ogni
 * tentativo i numeri devono cambiare (quando il giocatore preme, ad esempio il
 * tasto "invio", i numeri della SlotMachine cambiano). Se esce una coppia di
 * numeri uguali si vincono 3 monete, se tutti e tre i numeri sono uguali si
 * vince il Jackpot, 100 monete. Il gioco finisce o quando si esauriscono le
 * monete oppure quando l'utente fa il jackpot.
 * 
 * Implementare il programma con l'uso di metodi. Esempio: 1 - metodo per
 * generare una combinazione della slot machine 2 - metodo per controllare una
 * combinazione e calcolare la vincita
 */
package games;

import java.util.Scanner;

public class Slot_machine {
	public static void main(String args[]) {
		Scanner tast = new Scanner(System.in);
		int gold = 10;
		int point = 0;
		int[] number = new int[3];
		String go;
		System.out.println("Benvenuto!!! Questo gioco rappresenta la slot machine tramite l'utilizzo dei numeri...");
		System.out.println("Buona fortuna!!!");
		while (point != 2 && gold > 0) {
			System.out.print("Hai " + gold
					+ " monete. Premi invio per provare di nuovo...");
			go = tast.nextLine();
			number = gen();
			System.out.println();
			point = slot(number[0], number[1], number[2]);
			gold += gold(point);
			gold--;
		}
		if (point != 2) System.out.println("Hai perso!!!");
		else System.out.println("Hai ottenuto " + gold + " monete!!!");
	}

	public static int rand(int min, int max) {
		int n = (int) (Math.random() * max + min);
		return n;
	}

	public static int slot(int first, int second, int third) {
		int point = 0;
		if ((first == second) && (second == third)) {
			System.out.println("Hai raggiunto il jackpot!!! Hai vinto!!!");
			point = 2;
		}
		else if ((first == second) || (second == third) || (first == third)) {
			System.out.println("Hai vinto 3 monete!!!");
			point = 1;
		}
		return point;
	}

	public static int gold(int point) {
		if (point == 2) return 100;
		else if (point == 1) return 3;
		else return 0;
	}

	public static int[] gen() {
		int[] n = new int[3];
		n[0] = rand(1, 20);
		n[1] = rand(1, 20);
		n[2] = rand(1, 20);
		System.out.printf("%5d", n[0]);
		System.out.printf("%5d", n[1]);
		System.out.printf("%5d", n[2]);
		return n;
	}
}