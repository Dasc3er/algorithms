package flappy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import layout.Elemento;
import layout.Layout;

/**
 * @author Thomas Zilio
 */
public class FlappyPanel extends JPanel implements ActionListener,
		MouseListener {
	private Flappy game;
	private Layout layout;
	private int numeroX, numeroY;

	public FlappyPanel() {
		super();
		addMouseListener(this);
		setNumeroX(20);
		setNumeroY(20);
		setGame(new Flappy(getNumeroX(), getNumeroY()));
		layout();
		Timer t = new Timer(1000, this);
		t.start();
	}

	public Flappy getGame() {
		return this.game;
	}

	public void setGame(Flappy game) {
		this.game = game;
	}

	public void setLayout(Layout layout) {
		this.layout = layout;
	}

	public int getNumeroX() {
		return this.numeroX;
	}

	public void setNumeroX(int numeroX) {
		this.numeroX = numeroX;
	}

	public int getNumeroY() {
		return this.numeroY;
	}

	public void setNumeroY(int numeroY) {
		this.numeroY = numeroY;
	}

	public void layout() {
		layout =
				new Layout(getWidth() + 1, getHeight() + 2, getNumeroX(), getNumeroY());
		layout.setInvertito(true);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int i = 0; i < getNumeroX(); i++) {
			for (int j = 0; j < getNumeroY(); j++) {
				Color c;
				if (getGame().get(i, j)) c = Color.black;
				else c = Color.white;
				layout.sostituisci(i * getNumeroY() + j + 1, new Elemento(c));
			}
		}
		layout.sostituisci(getGame().getX() * getNumeroY() + getGame().getY()
				+ 1, new Elemento(Color.red));
		layout.disegna(g);
		repaint();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		getGame().down();
		getGame().right();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		getGame().up();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}
}
