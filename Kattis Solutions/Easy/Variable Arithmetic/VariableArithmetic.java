import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.lang.NumberFormatException;

public class VariableArithmetic {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        String[] pieces;
        HashMap<String, Integer> map = new HashMap<String, Integer>();

        while (scan.hasNextLine()) {
            pieces = scan.nextLine().split(" ");

            if (pieces.length == 1 && pieces[0].equals("0")) {
                break;
            }

            if (pieces.length > 1 && pieces[1].equals("=")) {
                map.put(pieces[0], new Integer(pieces[2]));
            } else {
                ArrayList<String> vars = new ArrayList<String>();

                int result = 0;
                for (int i = 0; i < pieces.length; i++) {
                    if (pieces[i].equals("+")) {
                        continue;
                    }

                    int temp;
                    try {
                        temp = Integer.parseInt(pieces[i]);
                        result += temp;
                    } catch (NumberFormatException e) {
                        Integer value = map.get(pieces[i]);

                        if (value == null) {
                            vars.add(pieces[i]);
                        } else {
                            temp = value.intValue();
                            result += temp;
                        }
                    }
                }

                if (result != 0) {
                    vars.add(0, "" + result);
                }

                String[] res = new String[vars.size()];
                vars.toArray(res);
                System.out.println(String.join(" + ", res));
            }
        }
    }
}