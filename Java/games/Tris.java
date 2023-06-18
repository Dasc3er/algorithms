/*
 * Implementare il gioco del Tris.
 * 
 * Si devono implementare dei metodi: - uno per controllare se c'� una vincita
 * in riga, - uno per controllare se c'� una vincita in colonna, - uno per
 * controllare se c'� una vincita in obliquo, - uno complessivo che, richiamando
 * gli altri, stabilisce se c'� una vincita. Ad ogni mossa si deve visualizzare
 * la scacchiera, l'eventuale vincita e il numero di mosse effettuate.
 * 
 * 
 * 
 * Il gioco si svolge su una scacchiera 3 x 3 ed � per due giocatori. Ogni
 * giocatore ha un segnalino O o X. La scacchiera � inizialmente vuota. | |
 * ---+---+--- | | ---+---+--- | | Durante il gioco le caselle si identificano
 * per riga/colonna. La prima casella in alto a sinistra � a riga 1 colonna 1,
 * l'ultima in basso a destra � a riga 3 colonna 3. I giocatori muovono a turno.
 * Vince chi fa tris in riga, colonna o obliquo.
 */
package games;

import java.util.Scanner;

public class Tris {
	public static void main(String args[]) {
		Scanner tast = new Scanner(System.in);
		char[][] tris = new char[3][3];
		stampa(tris);
		int r, c, f, win = 0;
		for (int i = 0; i < 9 && win == 0; i++) {
			System.out.print("Turno " + (i + 1));
			if (i % 2 == 0) System.out.println(" (X)");
			else System.out.println(" (O)");
			f = 0;
			do {
				if (f > 0) System.out.println("La posizione � gi� stata occupata. Riprova!!!");
				do {
					System.out.println("Scrivi la riga in cui inserire il proprio simbolo (1, 2, 3)");
					r = tast.nextInt();
				}while (r < 1 || r > 3);
				do {
					System.out.println("Scrivi la cella della riga sopra selezionata in cui inserire il proprio simbolo (1, 2, 3)");
					c = tast.nextInt();
				}while (c < 1 || c > 3);
				r--;
				c--;
				f++;
			}while (tris[r][c] == 'X' || tris[r][c] == 'O');
			if (i % 2 == 0) {
				tris[r][c] = 'X';
			}
			else {
				tris[r][c] = 'O';
			}
			win = check(tris);
			stampa(tris);
		}
		if (win == 0) System.out.print("Avete pareggiato!!!");
		if (win == 1) System.out.print("Ha vinto il giocatore X!!!");
		if (win == 2) System.out.print("Ha vinto il giocatore O!!!");
	}

	public static int check(char[][] data) {
		int win = 0, ob = 0, r = 0, c = 0;
		ob = obliquo(data);
		r = riga(data);
		c = colonna(data);
		if (ob != 0) win = ob;
		if (r != 0) win = r;
		if (c != 0) win = c;
		return win;
	}

	public static int obliquo(char[][] data) {
		int win = 0;
		if (data[0][0] == data[1][1] && data[1][1] == data[2][2]) {
			if (data[0][0] == 'X') {
				win = 1;
			}
			else if (data[0][0] == 'O') {
				win = 2;
			}
		}
		if (data[0][2] == data[1][1] && data[1][1] == data[2][0]) {
			if (data[2][0] == 'X') {
				win = 1;
			}
			else if (data[2][0] == 'O') {
				win = 2;
			}
		}
		return win;
	}

	public static int riga(char[][] data) {
		int win = 0;
		for (int i = 0; i < 3 && win == 0; i++) {
			if (data[i][0] == data[i][1] && data[i][1] == data[i][2]) {
				if (data[i][0] == 'X') {
					win = 1;
				}
				else if (data[i][0] == 'O') {
					win = 2;
				}
			}
		}
		return win;
	}

	public static int colonna(char[][] data) {
		int win = 0;
		for (int i = 0; i < 3 && win == 0; i++) {
			if (data[0][i] == data[1][i] && data[1][i] == data[2][i]) {
				if (data[0][i] == 'X') {
					win = 1;
				}
				else if (data[0][i] == 'O') {
					win = 2;
				}
			}
		}
		return win;
	}

	public static void stampa(char[][] st) {
		System.out.println();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (j == 1) System.out.print("|");
				System.out.print(" " + st[i][j] + " ");
				if (j == 1) System.out.print("|");
			}
			System.out.println();
			if (i != 2) System.out.println("---+---+---");
		}
		System.out.println();
	}
}
