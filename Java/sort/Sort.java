package sort;
import java.util.Scanner;

public class Sort {
	public static void main(String args[]) {
		Scanner tast = new Scanner(System.in);
		int n = 10000000;
		int[] array = new int[n];
		// int[] bubble = new int[n];
		int[] merge = new int[n];
		int[] tmp = new int[n];
		// int[] insertion = new int[n];
		// int[] quick = new int[n];
		int[] bucket = new int[n];
		gen(array);
		for (int i = 0; i < array.length; i++) {
			// bubble[i] = array[i];
			merge[i] = array[i];
			// insertion[i] = array[i];
			// quick[i] = array[i];
			bucket[i] = array[i];
		}
		// System.out.print("L'array creato � : [");
		// stampa(array);
		// System.out.println("]");
		// long t1 = System.currentTimeMillis();
		// bubblesort(bubble);
		// long t2 = System.currentTimeMillis();
		// System.out.print("L'array ordinato con l'algoritmo bubble sort  � : [");
		// stampa(bubble);
		// System.out.println("]");*/
		// System.out.println("Il tempo necessario per l'ordinamento bubble sort � stato di "
		// + (t2 - t1) + " millisecondi");
		// long t1 = System.currentTimeMillis();
		// insertionsort(insertion);
		// long t2 = System.currentTimeMillis();
		// System.out.print("L'array ordinato con l'algoritmo insertion sort � : [");
		// stampa(insertion);
		// System.out.println("]");
		// System.out.println("Il tempo necessario per l'ordinamento insertion sort � stato di "
		// + (t2 - t1) + " millisecondi");
		long t1 = System.currentTimeMillis();
		mergesort(merge, tmp, 0, merge.length - 1);
		long t2 = System.currentTimeMillis();
		// System.out.print("L'array ordinato con l'algoritmo merge sort � : [");
		// stampa(merge);
		// System.out.println("]");
		System.out.println("Il tempo necesario per l'ordinamento merge sort � stato di "
				+ (t2 - t1) + " millisecondi");
		// t1 = System.currentTimeMillis();
		// quicksort(quick, 0, quick.length - 1);
		// t2 = System.currentTimeMillis();
		// System.out.print("L'array ordinato con l'algoritmo quick sort � : [");
		// stampa(quick);
		// System.out.println("]");
		// System.out.println("Il tempo necessario per l'ordinamento quick sort � stato di "
		// + (t2 - t1) + " millisecondi");
		t1 = System.currentTimeMillis();
		bucketsort(bucket);
		t2 = System.currentTimeMillis();
		// System.out.print("L'array ordinato con l'algoritmo bucket sort � : [");
		// stampa(bucket);
		// System.out.println("]");
		System.out.println("Il tempo necessario per l'ordinamento bucket sort � stato di "
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
		while (j <= mid && d <= last)
			if (bob[j] < bob[d]) tmp[i++] = bob[j++];
			else tmp[i++] = bob[d++];
		while (j <= mid)
			tmp[i++] = bob[j++];
		while (d <= last)
			tmp[i++] = bob[d++];
		for (i = 0, j = first; j <= last; bob[j++] = tmp[i++]);
	}

	public static void insertionsort(int[] ins) {
		int curr, j;
		for (int i = 1; i < ins.length; i++) {
			curr = ins[i];
			j = i - 1;
			while (j >= 0 && ins[j] > curr) {
				swap(ins, j + 1, j);
				j--;
			}
			ins[j + 1] = curr;
		}
	}

	public static void quicksort(int[] bob, int first, int last) {
		if (first < last) {
			int p = bob[last], l = first, r = last - 1;
			while (l <= r) {
				while (l <= r && bob[l] <= p)
					l++;
				while (l <= r && bob[r] >= p)
					r--;
				if (l < r) swap(bob, l, r);
			}
			swap(bob, l, last);
			quicksort(bob, first, l - 1);
			quicksort(bob, l + 1, last);
		}
	}

	public static void bucketsort(int[] array) {
		int max = array[0], min = array[0], d = 0;
		for (int i = 0; i < array.length; i++) {
			if (max < array[i]) max = array[i];
			if (min > array[i]) min = array[i];
		}
		int[] bucket = new int[-min + max + 1];
		// for (int i = 0; i < bucket.length; i++) bucket[i] = 0; non necessario
		// in java
		for (int i = 0; i < array.length; i++)
			bucket[array[i] - min]++;
		for (int i = 0; i < bucket.length; i++)
			for (int j = 0; j < bucket[i]; j++)
				array[d++] = i + min;
	}
}
