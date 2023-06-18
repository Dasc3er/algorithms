package other;

import java.util.Scanner;

public class RicercaBinaria {

	public static void main(String args[]) {
		Scanner tast = new Scanner(System.in);
		int n = 10000000;
		int[] array = new int[n];
		gen(array);
		long t1 = System.currentTimeMillis();
		int x = binary(array, 3);
		long t2 = System.currentTimeMillis();
		System.out.println("Tempo di " + (t2 - t1) + " millisecondi " + x + " "
				+ array[x]);
	}

	public static void gen(int[] array) {
		for (int i = 0; i < array.length; i++)
			array[i] = (int) (Math.random() * 5 + 1);
	}

	public static int binary(int[] array, int elemento) {
		int start = 0, end = array.length - 1, centro = 0;
		while (start <= end) {
			centro = (start + end) / 2;
			if (elemento < array[centro]) {
				end = centro - 1;
			}
			else {
				if (elemento > array[centro]) start = centro + 1;
				else return centro; // Caso: elemento==array[centro]
			}
		}
		return -1;
	}

}
