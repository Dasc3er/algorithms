import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Akcija {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int number = scan.nextInt();
        scan.nextLine();

        ArrayList<Integer> books = new ArrayList<Integer>();
        for (int i = 0; i < number; i++) {
            books.add(new Integer(scan.nextLine()));
        }
        Collections.sort(books, Collections.reverseOrder());

        int sum = 0;
        for (int i = 0; i < books.size(); i++) {
            if ((i + 1) % 3 != 0) {
                sum += books.get(i).intValue();
            }
        }

        System.out.println(sum);
    }
}