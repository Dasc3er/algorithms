import java.util.Scanner;
import java.lang.Math;

public class Esami
{
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);

        int risp_corrette = Integer.parseInt(scan.nextLine());
        String mie = scan.nextLine();
        String amico = scan.nextLine();
        int i;
        int count = 0;

        for (i = 0; i<mie.length() && i<amico.length(); i++)
        {
            if (mie.charAt(i) == amico.charAt(i))
            {
                count++;
            }
        }

        int diff = Math.abs(risp_corrette - count);
        int prob = Math.abs((mie.length() - count) + (count-diff));

        System.out.println(prob);
    }
}