package cesare_cryptosystem;
import java.util.Scanner;

class XP {
	public static void main(String args[]) {
		Scanner tast = new Scanner(System.in);
		System.out.println("Inserire la parola da codificare");
		String par = tast.nextLine();
		System.out.println("Inserire la parola da utilizzare come chiave di cifratura");
		String chr = tast.nextLine();
		int d = 0;
		int index;
		for (int i = 0; i < par.length(); i++) {
			if ((par.charAt(i) == ' ') || (par.charAt(i) == ',')
					|| (par.charAt(i) == '.') || (par.charAt(i) == '!')
					|| (par.charAt(i) == '?')) {
				System.out.print(par.charAt(i));
			}
			else if (((int) chr.charAt(d) >= 'A')
					&& ((int) chr.charAt(d) <= 'Z')) {
				index = ((int) par.charAt(i)) + (chr.charAt(d) - 'A') % 26;
				index = ((index - 'A') % 26) + 'A';
				System.out.print((char) index);
			}
			else if (((int) chr.charAt(d) >= 'a')
					&& ((int) chr.charAt(d) <= 'z')) {
				index = ((int) par.charAt(i)) + (chr.charAt(d) - 'a') % 26;
				index = ((index - 'a') % 26) + 'a';
				System.out.print((char) index);
			}
			else if (((int) chr.charAt(d) >= '0')
					&& ((int) chr.charAt(d) <= '9')) {
				if (((int) par.charAt(i) >= '0')
						&& ((int) par.charAt(i) <= '9')) {
					index = ((int) par.charAt(i)) + (chr.charAt(d) - '0') % 10;
					index = ((index - '0') % 10) + '0';
					System.out.print((char) index);
				}
				else if (((int) par.charAt(i) >= 'A')
						&& ((int) par.charAt(i) <= 'Z')) {
					index = ((int) par.charAt(i)) + (chr.charAt(d) - '0') % 26;
					index = ((index - 'A') % 26) + 'A';
					System.out.print((char) index);
				}
				else if (((int) par.charAt(i) >= 'a')
						&& ((int) par.charAt(i) <= 'z')) {
					index = ((int) par.charAt(i)) + (chr.charAt(d) - '0') % 26;
					index = ((index - 'a') % 26) + 'a';
					System.out.print((char) index);
				}
			}
			d++;
			d %= chr.length();
		}
	}
}