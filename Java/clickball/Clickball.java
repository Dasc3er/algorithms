package clickball;

import java.awt.Container;

import javax.swing.JFrame;

/**
 * @author Thomas Zilio
 */
public class Clickball {
	public static void main(String[] args) {
		JFrame win = new JFrame();
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = win.getContentPane();
		c.add(new BallPanel());
		win.setTitle("Clickball");
		win.setLocation(40, 100);
		win.setResizable(false);
		win.setSize(1200, 600);
		win.setVisible(true);
	}
}