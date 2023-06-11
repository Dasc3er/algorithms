import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Arithmetic {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        String text = scan.next();
        scan.nextLine();

        BigInteger number = new BigInteger(text, 8);

        System.out.println(number.toString(16).toUpperCase());
    }
}