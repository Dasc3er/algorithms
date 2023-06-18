package games;

import java.util.Scanner;

public class Number {

    public static void main(String args[]) {
        double y;
        Scanner tastiera = new Scanner(System.in);
        y = Math.random() * 100 + 1;
        int x = (int) y;
        int cont;
        cont = 1;
        System.out.println("E' stato creato un numero casuale compreso tra 1 e 100. Prova ad indovinarlo!!!");
        while (cont <= 10) {
            System.out.println("Inserisci un numero...");
            int a = tastiera.nextInt();
            if (a == x) {
                System.out.println("Hai vinto!!!");
                System.out.println("Tentativi: " + cont);
                cont = 11;
            }
            if (a != x) {
                if (a > x) {
                    System.out.println("Il numero x è minore di " + a);
                }
                if (a < x) {
                    System.out.println("Il numero x è maggiore di " + a);
                }
            }
            if (cont == 10) {
                System.out.println("Hai perso!!!");
                System.out.println("Il numero era: " + x);
            }
            cont++;
        }
    }
}
