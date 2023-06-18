/*
 * Scrivere un programma che genera un vettore di numeri interi casuali, li visualizza, li
 * riordina con il procedimento bubble sort, li rivisualizza.
 * 
 * Ordinamento con il metodo bubble sort.
 * 
 * Facciamo l'esempio di dover riordinare i numeri: 12 1 4 6 8 9 il numero pi� a
 * sinistra � quello a posizione 0, quello a destra a posizione 5. Se il numero
 * di elementi � n, l'algoritmo fa una serie di passate per controllare gli
 * elementi 2 a 2. Si termina quando non sono stati effettuati scambi.
 * 
 * Passata n. 1 (n-1 controlli) - controllo elementi a pos 0 e 1 (12 e 1): sono
 * da scambiare 1 12 4 6 8 9 - controllo elementi a pos 1 e 2 (12 e 4): sono da
 * scambiare 1 4 12 6 8 9 - controllo elementi a pos 2 e 3 (12 e 6): sono da
 * scambiare 1 4 6 12 8 9 - controllo elementi a pos 3 e 4 (12 e 8): sono da
 * scambiare 1 4 6 8 12 9 - controllo elementi a pos 4 e 5 (12 e 9): sono da
 * scambiare 1 4 6 8 9 12
 * 
 * siccome sono stati effettuati degli scambi serve una altra passata...
 * 
 * Passata n. 2 (n-2 controlli) - controllo elementi a pos 0 e 1 (1 e 4): NON
 * sono da scambiare 1 4 6 8 9 12 - controllo elementi a pos 1 e 2 (4 e 6): NON
 * sono da scambiare 1 4 6 8 9 12 - controllo elementi a pos 2 e 3 (6 e 8): NON
 * sono da scambiare 1 4 6 8 9 12 - controllo elementi a pos 3 e 4 (8 e 9): NON
 * sono da scambiare 1 4 6 8 9 12
 * 
 * 
 * L'algoritmo � terminato in due passate (inferiore al numero max = n-1=5)
 * perch� NON sono stati effettuati degli scambi.
 */
package sort;
import java.util.Scanner;

class Esercizio1 {
	public static void main(String args[]) {
		Scanner tast = new Scanner(System.in);
		int n = tast.nextInt();
		int[] array = new int[n];
		array = gen(n);
		System.out.print("l'array creato � : [");
		Stampa(array, n);
		System.out.println("]");
		int a;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n - 1 - i; j++) {
				if (array[j] > array[j + 1]) {
					a = array[j];
					array[j] = array[j + 1];
					array[j + 1] = a;
				}
			}
		}
		System.out.print("l'array ordinato � : [");
		Stampa(array, n);
		System.out.print("]");
	}

	public static int[] gen(int n) {
		int[] a = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = (int) (Math.random() * 100 + 1);
		}
		return a;
	}

	public static void Stampa(int[] a, int n) {
		for (int i = 0; i < n; i++) {
			System.out.print(a[i] + " ");
		}
	}
}