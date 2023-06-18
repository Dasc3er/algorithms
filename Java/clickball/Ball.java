package clickball;

/**
 * @author Thomas Zilio
 */
public class Ball extends Obj {
	private Point ball;
	private int cont = 0;

	public Ball() {
		super(0);
		ball = new Point(getDim(), getDim());
	}

	public Ball(int x, int y, int c) {
		super(c);
		ball = new Point(x, y);
		setChange(c);
		if (getChange() == 0) {
			setVy((int) (Math.random() * 10 + 1));
			setVx((int) (Math.random() * 10 + 1));
			setDir(0);
		}
		else {
			if (getChange() == 1) {
				setVy((int) (Math.random() * 15 + 1) - 7);
				setVx((int) (Math.random() * 15 + 1) - 7);
			}
			else {
				setVy((int) (Math.random() * 30 + 1) - 15);
				setVx((int) (Math.random() * 30 + 1) - 15);
			}
			setDir((int) (Math.random() * 2));
		}
	}

	public int getX() {
		return this.ball.getX();
	}

	public int getY() {
		return this.ball.getY();
	}

	public Point getBall() {
		return ball;
	}

	public void setBall(Point ball) {
		this.ball = ball;
	}

	public int getCont() {
		return cont;
	}

	public void setCont(int cont) {
		this.cont = cont;
	}

	public final int getDim() {
		return 30;
	}

	public void aggiorna(int w, int h) {
		if (getDir() == 0) {
			if (getX() >= w - getDim() || getX() <= 0) setVx(-getVx());
			if (getY() >= h - getDim() || getY() <= 0) setVy(-getVy());
		}
		else {
			if (getX() >= w || getX() <= 0) ball.setX(w - getX());
			if (getY() >= h || getY() <= 0) ball.setY(h - getY());
		}
		ball.setX(getX() + getVx());
		ball.setY(getY() + getVy());
		if (!inScreen(w, h)) {
			cont++;
		}
		if (cont == 30) {
			ball.setX(getDim());
			ball.setY(getDim());
		}
		if (inScreen(w, h)) {
			cont = 0;
		}
	}

	public boolean inScreen(int w, int h) {
		return getX() + getDim() <= w && getX() >= 0 && getY() + getDim() <= h
				&& getY() >= 0;
	}

	public boolean inside(int x, int y) {
		return y >= getY() && y <= getY() + getDim() && x >= getX()
				&& x <= getX() + getDim();
	}

	@Override
	public String toString() {
		return "Ball [ball=" + getBall() + ", cont=" + getCont()
				+ ", toString()=" + super.toString() + "]";
	}
}