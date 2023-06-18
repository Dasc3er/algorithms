package games;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class Chess extends JPanel {

    public void Chess() {

        String[] columnNames = {"1",
            "2",
            "3",
            "4",
            "5",
            "6",
            "7",
            "8"};

        Object[][] data = {
            {"T", "C", "A", "R", "RE", "A", "C", "T"},
            {"P", "P", "P", "P", "P", "P", "P", "P"},
            {"", "", "", "", "", "", "", ""},
            {"", "", "", "", "", "", "", ""},
            {"", "", "", "", "", "", "", ""},
            {"", "", "", "", "", "", "", ""},
            {"P", "P", "P", "P", "P", "P", "P", "P"},
            {"T", "C", "A", "R", "RE", "A", "C", "T"}
        };

        final JTable table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(500, 150));
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
    }
//    private void printDebugData(JTable table){
//        int numRows = table.getRowCount();
//        int numCols = table.getColumnCount();
//        javax.swing.table.TableModel model = table.getModel();
//
//        System.out.println("Value of data: ");
//        for (int i=0; i < numRows; i++){
//            System.out.print("    row " + i + ":");
//            for (int j=0; j < numCols; j++){
//                System.out.print("  " + model.getValueAt(i, j));
//            }
//            System.out.println();
//        }
//        System.out.println("--------------------------");
//    }

    public static void main(String args[]) {
        JFrame frame = new JFrame("Chess");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Chess newContentPane = new Chess();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        frame.pack();
        frame.setVisible(true);

        int a;
        int d;
        int e;
        int[] neri = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        int[] bianchi = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        int[] xb = {1, 2, 3, 4, 5, 6, 7, 8, 1, 2, 3, 4, 5, 6, 7, 8};
        int[] yb = {2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1};
        int[] xn = {1, 2, 3, 4, 5, 6, 7, 8, 1, 2, 3, 4, 5, 6, 7, 8};
        int[] yn = {7, 7, 7, 7, 7, 7, 7, 7, 8, 8, 8, 8, 8, 8, 8, 8};
        int[] mxb = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        int[] myb = {1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0};
        int[] mxn = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        int[] myn = {-1, -1, -1, -1, -1, -1, -1, -1, 0, 0, 0, 0, 0, 0, 0, 0};
        while ((neri[15] == 1) && (bianchi[15] == 1)) {
            int[] movb = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            int[] movn = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            int bcont = 0;
            int ncont = 0;
            int contx = 0;
            int conty = 0;
            while (bcont == 0) {
                int[] px = xb;
                int[] py = yb;
                int[] pxx = xb;
                int[] pxy = yb;
                for (a = 0; a < 16; a++) {
                    for (d = 0; d < 16; d++) {
                        for (e = 0; e < 16; e++) {
                            if ((pxx[d] + mxb[d] == xb[e]) || (pxx[d] + mxb[d] > 8) || (pxx[d] + mxb[d] < 1)) {
                                contx = 1;
                            }
                        }
                        for (e = 0; e < 16; e++) {
                            if ((pxy[d] + myb[d] == yb[e]) || (pxy[d] + myb[d] > 8) || (pxy[d] + myb[d] < 1)) {
                                conty = 1;
                            }
                        }
                        if ((conty == 0) && (contx == 0)) {
                            if ((pxx[d] == xn[e]) && (pxy[d] == yn[e])) {
                                movb[d] = 1;
                                break;
                            }
                        }
                    }
                }
                for (d = 0; d < 16; d++) {
                    if (movb[d] == 1) {
                        px[d] += mxb[d];
                        py[d] += myb[d];
                        for (e = 0; e < 16; e++) {
                            if (((px[d] == xn[e]) && (py[d] == yn[e])) || (movb[d] == 1)) {
                                xb[d] = px[d];
                                yb[d] = py[d];
                                bcont = 1;
                                System.out.print("Tocca al giocatore con gli scacchi neri");
                                break;

                            }
                        }
                        break;
                    }
                }
            }
            contx = 0;
            conty = 0;
            while (ncont == 0) {
                int[] px = xn;
                int[] py = yn;
                int[] pxx = xn;
                int[] pxy = yn;
                for (a = 0; a < 16; a++) {
                    for (d = 0; d < 16; d++) {
                        for (e = 0; e < 16; e++) {
                            if ((pxx[d] + mxn[d] == xn[e]) || (pxx[d] + mxn[d] > 8) || (pxx[d] + mxn[d] < 1)) {
                                contx = 1;
                            }
                        }
                        for (e = 0; e < 16; e++) {
                            if ((pxy[d] + myn[d] == yn[e]) || (pxy[d] + myn[d] > 8) || (pxy[d] + myn[d] < 1)) {
                                conty = 1;
                            }
                        }
                        if ((conty == 0) && (contx == 0)) {
                            if ((pxx[d] == xb[e]) && (pxy[d] == yb[e])) {
                                movn[d] = 1;
                                break;
                            }
                        }
                    }
                }
                for (d = 0; d < 16; d++) {
                    if (movn[d] == 1) {
                        px[d] += mxn[d];
                        py[d] += myn[d];
                        for (e = 0; e < 16; e++) {
                            if (((px[d] == xb[e]) && (py[d] == yb[e])) || (movn[d] == 1)) {
                                xn[d] = px[d];
                                yn[d] = py[d];
                                ncont = 1;
                                System.out.print("Tocca al giocatore con gli scacchi bianchi");
                                break;
                            }
                        }
                    }
                }
            }
        }
        System.out.print("Ha vinto il giocatore con gli scacchi ");
        if (neri[15] == 0) {
            System.out.println("neri");
        } else {
            System.out.println("bianchi");
        }
    }
}
