package clickball;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * @author Thomas Zilio
 */
public class BallPanel extends JPanel implements ActionListener, MouseListener {
	private int l = 0, cl = 0, c = 0, sl = 0, time = 0;
	boolean white = false, black = false;
	private Vector<Ball> balls = new Vector<Ball>(0, 1);
	private Vector<Obstacle> obstacles = new Vector<Obstacle>(0, 1);

	public BallPanel() {
		super();
		super.setBackground(Color.WHITE);
		addMouseListener(this);
		Timer t = new Timer(20, this);
		t.start();
	}

	private void setL(int n) {
		this.l = n;
	}

	public int getL() {
		return this.l;
	}

	private void setSl(int n) {
		if (n > 2 || n < 0) {
			setL(getL() + 1);
			this.sl = 0;
		}
		else this.sl = n;
	}

	public int getSl() {
		return this.sl;
	}

	private void setC(int n) {
		this.c = n;
	}

	public int getC() {
		return this.c;
	}

	private void setCl(int n) {
		this.cl = n;
	}

	public int getCl() {
		return this.cl;
	}

	public int getN() {
		if (getL() <= 3) {
			return getL() * 3 + 6;
		}
		else if (getL() <= 6) {
			return (getL()) * 2;
		}
		else {
			return (int) (getL() * 1.5);
		}
	}

	public int getTime() {
		return time;
	}

	public void setTime(int n) {
		this.time = n;
	}

	public boolean isBlack() {
		return black;
	}

	public void setBlack(boolean n) {
		this.black = n;
	}

	public boolean isWhite() {
		return white;
	}

	public void setWhite(boolean n) {
		this.white = n;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (getSl() % 3 == 0) {
			g.setColor(Color.black);
			g.setFont(new Font("Calibri", Font.PLAIN, 20));
			if (getL() != 0) g.drawString("Hai vinto in " + getCl()
					+ " tentativi!!!", getWidth() / 2 - 160,
					getHeight() / 2 - 35);
			g.setFont(new Font("Calibri", Font.BOLD, 30));
			if (getL() != 0) g.drawString("Livello " + getL()
					+ ": clicca per iniziare", getWidth() / 2 - 210,
					getHeight() / 2 - 5);
			else g.drawString("Addestramento!!!", getWidth() / 2 - 170,
					getHeight() / 2 - 5);
			repaint();
		}
		else if (getSl() % 3 == 2) {
			for (int i = 0; i < balls.size(); i++) {
				if (isWhite()) g.setColor(Color.WHITE);
				else g.setColor(new Color(balls.elementAt(i).getR(), balls.elementAt(
						i).getG(), balls.elementAt(i).getB(), 128));
				g.fillOval(balls.elementAt(i).getX(),
						balls.elementAt(i).getY(), balls.elementAt(i).getDim(),
						balls.elementAt(i).getDim());
			}
			for (int i = 0; i < obstacles.size(); i++) {
				if (isWhite()) g.setColor(Color.WHITE);
				else g.setColor(new Color(obstacles.elementAt(i).getR(), obstacles.elementAt(
						i).getG(), obstacles.elementAt(i).getB(), 128));
				g.fillPolygon(obstacles.elementAt(i).print());
			}
			g.setColor(Color.black);
			if (getL() != 0) g.drawString("Livello " + getL()
					+ ": devi ancora colpire " + balls.size() + " palline!!!",
					20, 20);
			else g.drawString("Addestramento!!!", 20, 20);
			repaint();
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (getSl() % 3 == 1) {
			int c, n = getN();
			if (getL() <= 3) {
				c = 0;
			}
			else if (getL() <= 12) {
				c = 1;
			}
			else {
				c = 2;
			}
			for (int i = 0; i < n; i++) {
				int posx = (int) (Math.random() * 100 + 1) + 200;
				int posy = (int) (Math.random() * 100 + 1) + 200;
				balls.add(new Ball(posx, posy, c));
				if (getL() > 9) if (i <= n / 6) obstacles.add(new Obstacle(posx, posy, 4, c));
				if (getL() > 12) if (i <= n / 9) obstacles.add(new Obstacle(posx, posy, 3, c));
			}
			setSl(getSl() + 1);
			setCl(0);
			setC(getC() - 2);
			setWhite(false);
			setBlack(false);
			setTime(0);
		}
		else if (getSl() % 3 == 2) {
			if (isWhite() || isBlack()) setTime(getTime() + 1);
			if (isBlack() && getTime() >= 30) {
				for (int j = 0; j < 1000; j++) {
					for (int i = 0; i < balls.size(); i++)
						balls.elementAt(i).aggiorna(getWidth(), getHeight());
					for (int i = 0; i < obstacles.size(); i++)
						obstacles.elementAt(i).aggiorna(getWidth(), getHeight());
				}
				repaint();
				setBlack(false);
				setTime(0);
			}
			else {
				for (int i = 0; i < balls.size(); i++)
					balls.elementAt(i).aggiorna(getWidth(), getHeight());
				for (int i = 0; i < obstacles.size(); i++)
					obstacles.elementAt(i).aggiorna(getWidth(), getHeight());
				repaint();
			}
			if (isWhite() && getTime() >= 50) {
				setTime(0);
				setWhite(false);
			}

		}
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
		setCl(getCl() + 1);
		setC(getC() + 1);
		for (int i = 0; i < balls.size(); i++) {
			balls.elementAt(i).change();
			if (balls.elementAt(i).inside(arg0.getX(), arg0.getY())) balls.removeElementAt(i);
		}
		for (int i = 0; i < obstacles.size(); i++) {
			obstacles.elementAt(i).change();
			if (obstacles.elementAt(i).print().contains(arg0.getX(),
					arg0.getY())) {
				if (obstacles.elementAt(i).getN() == 4) setWhite(true);
				else if (obstacles.elementAt(i).getN() == 3) setBlack(true);
				obstacles.removeElementAt(i);
			}
		}
		if (balls.isEmpty()) setSl(getSl() + 1);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}
}
