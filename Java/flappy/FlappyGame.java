package flappy;

import java.awt.Dimension;

import javax.swing.JFrame;

/**
 * @author Thomas Zilio
 */
public class FlappyGame {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new FlappyPanel());
		frame.setTitle("Flappy");
		frame.setLocation(200, 200);
		frame.setMinimumSize(new Dimension(500 + frame.getInsets().left
				+ frame.getInsets().right, 400 + frame.getInsets().top
				+ frame.getInsets().bottom));
		frame.setVisible(true);
	}
}
