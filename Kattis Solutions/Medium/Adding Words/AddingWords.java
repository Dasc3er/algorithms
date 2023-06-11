import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

public class AddingWords {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        String[] pieces;
        HashMap<String, Integer> map = new HashMap<String, Integer>();

        while (scan.hasNextLine()) {
            pieces = scan.nextLine().split(" ");

            if (pieces[0].equals("def")) {
                map.put(pieces[1], new Integer(pieces[2]));
            } else if (pieces[0].equals("clear")) {
                map.clear();
            } else if (pieces[0].equals("calc")) {
                String text = null;

                int result = 0;
                for (int i = 1; i < pieces.length; i++) {
                    if (!pieces[i].equals("=") && !pieces[i].equals("+") && !pieces[i].equals("-")) {
                        Integer value = map.get(pieces[i]);

                        if (value == null) {
                            text = "unknown";
                            break;
                        }

                        int temp = value.intValue();
                        if (pieces[i - 1].equals("-")) {
                            temp = -temp;
                        }
                        result += temp;
                    }
                }

                String[] newArray = new String[pieces.length];
                System.arraycopy(pieces, 1, newArray, 0, pieces.length - 1);

                if (text == null) {
                    Integer final_result = new Integer(result);
                    for (Entry<String, Integer> entry : map.entrySet()) {
                        if (entry.getValue().equals(final_result)) {
                            text = entry.getKey();
                            break;
                        }
                    }
                }

                if (text == null) {
                    text = "unknown";
                }

                newArray[pieces.length - 1] = text;

                System.out.println(String.join(" ", newArray));
            }
        }
    }
}