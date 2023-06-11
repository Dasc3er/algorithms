import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

public class CodeCleanups {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        HashMap<String, Integer> map = new HashMap<String, Integer>();

        int pushes = scan.nextInt();
        scan.nextLine();

        String[] pieces = scan.nextLine().split(" ");
        int[] list = new int[pushes];
        for (int i = 0; i < pushes; i++) {
            list[i] = Integer.parseInt(pieces[i]);
        }

        int done = 0;
        int count = 0;
        for (int i = 1; i < pushes; i++) {
            int weight = 0;

            for (int j = done; j < i; j++) {
                weight += list[i] - list[j];
            }

            if (weight >= 20) {
                done = i;
                count++;
            }
        }

        if (done != pushes) {
            count++;
        }

        System.out.println(count);
    }
}