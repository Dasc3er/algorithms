import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ArmyStrength {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int tests = scan.nextInt();
        scan.nextLine();

        String[] pieces;
        ArrayList<Long> godzilla, mecha;
        for (int i = 0; i < tests; i++) {
            scan.nextLine();
            scan.nextLine();

            godzilla = new ArrayList<Long>();
            mecha = new ArrayList<Long>();

            pieces = scan.nextLine().split(" ");
            for (int j = 0; j < pieces.length; j++) {
                godzilla.add(new Long(pieces[j]));
            }

            pieces = scan.nextLine().split(" ");
            for (int j = 0; j < pieces.length; j++) {
                mecha.add(new Long(pieces[j]));
            }

            Collections.sort(godzilla, Collections.reverseOrder());
            Collections.sort(mecha, Collections.reverseOrder());

            while (!mecha.isEmpty() && !godzilla.isEmpty()) {
                long last_mecha = mecha.get(mecha.size() - 1);
                long last_godzilla = godzilla.get(godzilla.size() - 1);

                if (last_mecha <= last_godzilla) {
                    mecha.remove(mecha.size() - 1);
                } else {
                    godzilla.remove(godzilla.size() - 1);
                }
            }

            if (mecha.isEmpty() && godzilla.isEmpty()) {
                System.out.println("uncertain");
            } else if (mecha.isEmpty()) {
                System.out.println("Godzilla");
            } else {
                System.out.println("MechaGodzilla");
            }
        }
    }
}