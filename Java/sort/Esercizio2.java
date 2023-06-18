/*
 * Utilizzo della funzione
 * 'System.currentTimeMillis();' Questa funzione serve per sapere quanto tempo
 * ci mette il programma ad effettuare tutte le operazioni e i calcoli. Come
 * unit� di misura si utilizzano i millisecondi. Implementala su qualche tuo
 * esercizio in questo modo:
 * 
 * Inizia a contare il tempo: long tempo1=System.currentTimeMillis();
 * 
 * Algoritmo....
 * 
 * Finisce di contare il tempo: long tempo2=System.currentTimeMillis();
 * 
 * Il tempo risultante si calcola sottraendo i tempi ottenuti:
 * 
 * long tempo=tempo2-tempo1;
 * 
 * Per visualizzare il tempo, basta dare un System.out.print(tempo);
 * 
 * (c) Canevarolo (Mirco) - Raimondi (Eddi) Es 2 Scrivere un metodo che dato un
 * vettore di interi lo riordini con il sistema bubble sort. Scrivere altri due
 * metodi pr generare e visualizzare un vettore di interi. Scrivere un programma
 * che esegua lo stesso compito che Es 1 ma utilizzando i metodi. Ordinamento
 * con il metodo bubble sort. Facciamo l'esempio di dover riordinare i numeri: 6
 * 4 9 12 1 8 il numero pi� a sinistra � quello a posizione 0, quello a destra a
 * posizione 5. Se il numero di elementi � n, l'algoritmo fa una serie di
 * passate per controllare gli elementi 2 a 2. Si termina quando non sono stati
 * effettuati scambi.
 * 
 * Passata n. 1 (n-1 controlli) - controllo elementi a pos 0 e 1 (6 e 4): sono
 * da scambiare 4 6 9 12 1 8 - controllo elementi a pos 1 e 2 (6 e 9): NON sono
 * da scambiare 4 6 9 12 1 8 - controllo elementi a pos 2 e 3 (9 e 12): NON sono
 * da scambiare 4 6 9 12 1 8 - controllo elementi a pos 3 e 4 (12 e 1): sono da
 * scambiare 4 6 9 1 12 8 - controllo elementi a pos 4 e 5 (12 e 8): sono da
 * scambiare 4 6 9 1 8 12
 * 
 * siccome sono stati effettuati degli scambi serve una altra passata...
 * 
 * Passata n. 2 (n-2 controlli) - controllo elementi a pos 0 e 1 (4 e 6): NON
 * sono da scambiare 4 6 9 1 8 12 - controllo elementi a pos 1 e 2 (6 e 9): NON
 * sono da scambiare 4 6 9 1 8 12 - controllo elementi a pos 2 e 3 (9 e 1): sono
 * da scambiare 4 6 1 9 8 12 - controllo elementi a pos 3 e 4 (9 e 8): sono da
 * scambiare 4 6 1 8 9 12
 * 
 * siccome sono stati effettuati degli scambi serve una altra passata...
 * 
 * Passata n. 3 (n-3 controlli) - controllo elementi a pos 0 e 1 (4 e 6): NON
 * sono da scambiare 4 6 1 8 9 12 - controllo elementi a pos 1 e 2 (6 e 1): sono
 * da scambiare 4 1 6 8 9 12 - controllo elementi a pos 2 e 3 (6 e 8): NON sono
 * da scambiare 4 1 6 8 9 12
 * 
 * siccome sono stati effettuati degli scambi serve una altra passata...
 * 
 * Passata n. 4 (n-4 controlli) - controllo elementi a pos 0 e 1 (4 e 1): sono
 * da scambiare 4 1 6 8 9 12 - controllo elementi a pos 1 e 2 (4 e 6): NON sono
 * da scambiare 1 4 6 8 9 12
 * 
 * siccome sono stati effettuati degli scambi serve una altra passata...
 * 
 * Passata n. 5 (n-5 controlli) - controllo elementi a pos 0 e 1 (1 e 1): NON
 * sono da scambiare 1 4 6 8 9 12
 * 
 * L'algoritmo � terminato con il massimo numero di passate
 */
package sort;

import java.util.Scanner;

class Esercizio2 {
	public static void main(String args[]) {
		Scanner tast = new Scanner(System.in);
		long t1 = System.currentTimeMillis();
		int n = tast.nextInt();
		int[] array = new int[n];
		array = gen(n);
		System.out.print("L'array creato � : [");
		Stampa(array, n);
		System.out.println("]");
		ordina(array);
		System.out.print("L'array ordinato � : [");
		Stampa(array, n);
		System.out.println("]");
		long t2 = System.currentTimeMillis();
		System.out.print("Il tempo necesario per l'operazione � stato di "
				+ (t2 - t1) / 1000 + " secondi");
	}

	public static int[] gen(int n) {
		int[] a = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = (int) (Math.random() * 100 + 1) - 50;
		}
		return a;
	}

	public static void Stampa(int[] a, int n) {
		for (int i = 0; i < n; i++) {
			System.out.print(a[i] + " ");
		}
	}

	public static void ordina(int[] ord) {
		int a;
		for (int i = 0; i < ord.length; i++) {
			for (int j = 0; j < ord.length - 1 - i; j++) {
				if (ord[j] > ord[j + 1]) {
					a = ord[j];
					ord[j] = ord[j + 1];
					ord[j + 1] = a;
				}
			}
		}
	}
}