import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Beehives {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            if (line.equals("0.0 0"))
                break;

            String[] pieces = line.split(" ");
            double dist = Double.parseDouble(pieces[0]);
            int number = Integer.parseInt(pieces[1]);

            double[] x = new double[number];
            double[] y = new double[number];
            boolean[] state = new boolean[number];
            for (int i = 0; i < number; i++) {
                pieces = scan.nextLine().split(" ");

                x[i] = Double.parseDouble(pieces[0]);
                y[i] = Double.parseDouble(pieces[1]);
            }

            for (int i = 0; i < number; i++) {
                for (int j = i + 1; j < number; j++) {
                    double xx = x[i] - x[j];
                    double yy = y[i] - y[j];

                    double distance = Math.sqrt(xx * xx + yy * yy);
                    if (distance < dist) {
                        state[j] = true;
                        state[i] = true;
                    }
                }
            }

            int sour = 0, sweet = 0;
            for (int i = 0; i < number; i++) {
                if (state[i] == true) {
                    sour++;
                } else {
                    sweet++;
                }
            }

            System.out.println(sour + " sour, " + sweet + " sweet");
        }
    }
}