import java.util.Scanner;
import java.lang.Math;

public class Jam {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int numero = scan.nextInt();
        scan.nextLine();

        String frase = "welcome to code jam";

        for (int i = 0; i < numero; i++) {
            String testo = scan.nextLine();
            int risposta = ric(testo, 0, frase, 0);
            
            System.out.format("Case #%d: %04d%n", i + 1, risposta);
        }
    }

    public static int ric(String testo, int i, String frase, int j) {
        if (j >= frase.length())
            return 1;
        if (i >= testo.length())
            return 0;

        int result = ric(testo, i + 1, frase, j);
        if (testo.charAt(i) == frase.charAt(j)) {
            result += ric(testo, i + 1, frase, j + 1);
        }

        return result;
    }
}