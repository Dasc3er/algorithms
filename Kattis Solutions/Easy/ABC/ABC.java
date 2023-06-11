import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ABC {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        String[] pieces = scan.nextLine().split(" ");

        ArrayList<Integer> numbers = new ArrayList<Integer>();
        for (int i = 0; i < 3; i++) {
            numbers.add(new Integer(pieces[i]));
        }
        Collections.sort(numbers);

        String order = scan.nextLine();
        for (int i = 0; i < 3; i++) {
            int index = 0;
            if (order.charAt(i) == 'B') {
                index = 1;
            } else if (order.charAt(i) == 'C') {
                index = 2;
            }

            System.out.print(numbers.get(index) + " ");
        }
    }
}