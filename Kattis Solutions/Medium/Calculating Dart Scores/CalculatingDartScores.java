import java.util.Scanner;

public class CalculatingDartScores {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int score = scan.nextInt();

        String result = score(score, 0);
        System.out.println(result);
    }

    public static String score(int score, int depth) {
        String[] qt = { "single", "double", "triple" };

        // Risultati singoli
        for (int i = 20; i >= 1; i--) {
            for (int j = 3; j > 0; j--) {
                int temp = score - i * j;

                String r = qt[j - 1] + " " + i;

                if (temp == 0) {
                    return r;
                }
            }
        }

        // Risultati combinati
        for (int i = 20; i >= 1; i--) {
            for (int j = 3; j > 0; j--) {
                int temp = score - i * j;

                String r = qt[j - 1] + " " + i;

                if (depth < 2) {
                    String result = score(temp, depth + 1);
                    if (!result.equals("impossible")) {
                        return r + "\n" + result;
                    }
                }
            }
        }

        return "impossible";
    }
}