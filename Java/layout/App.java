package layout;

import java.awt.Container;

import javax.swing.JFrame;

/**
 * @author Thomas Zilio
 */
public class App {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = frame.getContentPane();
		c.add(new Pannello());
		frame.setTitle("Layout");
		frame.setSize(500, 400);
		frame.setVisible(true);
	}
}
