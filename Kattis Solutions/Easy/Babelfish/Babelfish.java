import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Babelfish {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        HashMap<String, String> dic = new HashMap<String, String>();

        while (scan.hasNextLine()) {
            String line = scan.nextLine();

            if (line.equals("")) {
                break;
            }

            String[] pieces = line.split(" ");

            dic.put(pieces[1], pieces[0]);
        }

        while (scan.hasNextLine()) {
            String line = scan.nextLine();

            String word = dic.get(line);
            if (word == null) {
                System.out.println("eh");
            } else {
                System.out.println(word);
            }
        }
    }
}