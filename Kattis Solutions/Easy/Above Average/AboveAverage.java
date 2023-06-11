import java.util.Scanner;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class AboveAverage {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int number = scan.nextInt();
        scan.nextLine();

        NumberFormat formatter = new DecimalFormat("#0.000");

        for (int i = 0; i < number; i++) {
            String[] pieces = scan.nextLine().split(" ");

            int sum = 0;
            for (int j = 1; j < pieces.length; j++) {
                sum += Integer.parseInt(pieces[j]);
            }
            double average = sum / (pieces.length - 1);

            double count = 0;
            for (int j = 1; j < pieces.length; j++) {
                if (Integer.parseInt(pieces[j]) > average)
                    count++;
            }

            double result = count / (pieces.length - 1) * 100;

            System.out.println(formatter.format(result) + "%");
        }

    }
}