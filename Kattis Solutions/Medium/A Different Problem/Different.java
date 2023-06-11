import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Different {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        String[] pieces;
        long first, second, result;

        while (scan.hasNextLine()) {
            pieces = scan.nextLine().split(" ");

            first = Long.parseLong(pieces[0]);
            second = Long.parseLong(pieces[1]);

            result = Math.abs(first - second);
            System.out.println(result);

        }
    }
}