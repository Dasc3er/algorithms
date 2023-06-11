import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AmalgamatedArtichoies {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        String[] pieces = scan.nextLine().split(" ");

        int p = Integer.parseInt(pieces[0]);
        int a = Integer.parseInt(pieces[1]);
        int b = Integer.parseInt(pieces[2]);
        int c = Integer.parseInt(pieces[3]);
        int d = Integer.parseInt(pieces[4]);

        int n = Integer.parseInt(pieces[5]);

        double result = 0;
        int index = 0;
        ArrayList<Double> prices = new ArrayList<Double>();
        for (int i = 1; i <= n; i++) {
            prices.add(p * (Math.sin(a * i + b) + Math.cos(c * i + d) + 2));

            if (prices.get(prices.size() - 1) > prices.get(index)) {
                index = prices.size() - 1;
            } else {
                double r = prices.get(index) - prices.get(prices.size() - 1);
                if (r > result) {
                    result = r;
                }
            }
        }

        System.out.println(result);
    }
}