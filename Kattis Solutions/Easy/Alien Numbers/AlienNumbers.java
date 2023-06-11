import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AlienNumbers {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int times = scan.nextInt();
        scan.nextLine();

        for (int i = 0; i < times; i++) {
            String number = scan.next();
            String original = scan.next();
            String resultant = scan.next();

            int current = 0;
            for (int j = 0; j < number.length(); j++) {
                int value = original.indexOf(number.charAt(j));

                current += value * Math.pow(original.length(), number.length() - j - 1);
            }

            int charset = resultant.length();
            StringBuilder result = new StringBuilder();
            while (current != 0) {
                int resto = current % charset;

                result.insert(0, resultant.charAt(resto));

                current /= charset;
            }

            System.out.println("Case #" + (i + 1) + ": " + result);
        }
    }
}