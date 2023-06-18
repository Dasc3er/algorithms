package clickball;

/**
 * @author Thomas Zilio
 */
public final class Point {
	private int x, y;

	public Point() {
		setX(0);
		setY(0);
	}

	public Point(int x, int y) {
		setLocation(x, y);
	}

	protected void setX(int n) {
		this.x = n;
	}

	public int getX() {
		return this.x;
	}

	protected void setY(int n) {
		this.y = n;
	}

	public int getY() {
		return y;
	}

	protected void setLocation(int x, int y) {
		setX(x);
		setY(y);
	}

	public boolean inScreen(int h, int w) {
		return getX() <= w || getY() <= h && getX() >= 0 || getY() >= 0;
	}

	@Override
	public String toString() {
		return "Point [x=" + getX() + ", y=" + getY() + "]";
	}
}
