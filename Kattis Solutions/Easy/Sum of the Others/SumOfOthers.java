import java.util.Scanner;

public class SumOfOthers {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        String[] pieces;
        int[] results = new int[200];
        int tot = 0;
        while (scan.hasNextLine()) {
            pieces = scan.nextLine().split(" ");

            int result = 0;
            for (int j = 0; j < pieces.length; j++) {
                result += Integer.parseInt(pieces[j]);
            }

            results[tot++] = result / 2;
        }

        for (int i = 0; i < tot; i++) {
            System.out.println(results[i]);
        }
    }
}