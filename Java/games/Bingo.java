package games;

public class Bingo {

    public static void main(String[] args) {
        int a;
        int x;
        int b;
        int[] V;
        V = new int[90];
        for (a = 0; a <= 89; a++) {
            x = (int) (Math.random() * 90 + 1);
            for (b = 0; b <= 89; b++) {
                if (x == V[b]) {
                    x = (int) (Math.random() * 90 + 1);
                    b = -1;
                }
            }
            V[a] = x;
            System.out.println(V[a]);
        }
    }
}
