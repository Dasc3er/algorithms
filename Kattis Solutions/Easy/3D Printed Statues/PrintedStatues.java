import java.util.Scanner;

public class PrintedStatues {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int number = scan.nextInt();
        scan.nextLine();

        int printers = 1;
        int days = 1;
        while(printers < number){
            printers = printers*2;
            days++;
        }

        System.out.println(days);
    }
}