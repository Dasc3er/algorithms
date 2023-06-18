/*
 * Implementare il metodo per ordinamento merge sort.
 * 
 * Eseguire TANTISSIME volte la prova: Creare due vettori IDENTICI di n elementi
 * (1000, 10000, 100000) - riordinare il vettore con bubble sort, - riordinare
 * il vettore con merge sort. Confrontare e stampare i tempi. Memorizzare le
 * prove in una tabella (EXCEL). Almeno 50 prove con num di elementi diversi...
 * Esempio: 10 prove con 1000, 10 prove con 5000, 10 prove con 10000, 10 prove
 * con 50000, 10 prove con 100000
 */
package sort;

import java.util.Scanner;

public class Merge {
	public static void main(String args[]) {
		Scanner tast = new Scanner(System.in);
		int n = 100000;
		int[] array = new int[n];
		int[] bubble = new int[n];
		int[] merge = new int[n];
		gen(array);
		for (int i = 0; i < array.length; i++) {
			bubble[i] = array[i];
			merge[i] = array[i];
		}
		// System.out.print("L'array creato � : [");
		// stampa(array);
		// System.out.println("]");
		long t1 = System.currentTimeMillis();
		bubblesort(bubble);
		long t2 = System.currentTimeMillis();
		// System.out.print("L'array ordinato con l'algoritmo bubble sort  � : [");
		// stampa(bubble);
		// System.out.println("]");
		System.out.println("Il tempo necessario per l'ordinamento bubble sort � stato di "
				+ (t2 - t1) + " millisecondi");
		t1 = System.currentTimeMillis();
		mergesort(merge);
		t2 = System.currentTimeMillis();
		// System.out.print("L'array ordinato con l'algoritmo merge sort � : [");
		// stampa(merge);
		// System.out.println("]");
		System.out.println("Il tempo necessario per l'ordinamento merge sort � stato di "
				+ (t2 - t1) + " millisecondi");
	}

	public static void swap(int[] array, int first, int second) {
		int tmp = array[second];
		array[second] = array[first];
		array[first] = tmp;
	}

	public static void gen(int[] array) {
		for (int i = 0; i < array.length; i++)
			array[i] =
					(int) (Math.random() * array.length + 1) - array.length / 2;
	}

	public static void stampa(int[] a) {
		for (int i = 0; i < a.length; i++)
			System.out.print(a[i] + " ");
	}

	public static void bubblesort(int[] ord) {
		for (int i = 0; i < ord.length; i++)
			for (int j = 0; j < ord.length - 1 - i; j++)
				if (ord[j] > ord[j + 1]) swap(ord, j + 1, j);
	}

	public static void mergesort(int[] bob) {
		int[] tmp = new int[bob.length];
		mergesort(bob, tmp, 0, bob.length - 1);

	}

	public static void mergesort(int[] bob, int[] tmp, int first, int last) {
		if (first < last) {
			int mid = (int) ((first + last) / 2);
			mergesort(bob, tmp, first, mid);
			mergesort(bob, tmp, mid + 1, last);
			merge(bob, tmp, first, last);
		}
	}

	public static void merge(int[] bob, int[] tmp, int first, int last) {
		int mid = (int) ((first + last) / 2);
		int i = 0, j = first, d = mid + 1;
		while (j <= mid && d <= last) {
			if (bob[j] < bob[d]) tmp[i++] = bob[j++];
			else tmp[i++] = bob[d++];
		}
		while (j <= mid)
			tmp[i++] = bob[j++];
		while (d <= last)
			tmp[i++] = bob[d++];
		for (i = 0, j = first; j <= last; bob[j++] = tmp[i++]);
	}
}