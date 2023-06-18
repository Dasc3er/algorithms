package flappy;

/**
 * @author Thomas Zilio
 */
public class Flappy {
	private boolean[][] all;// griglia in base a schermo, 0 in alto a sinistra,
							// 7 in bassso na destra
	private int x, y;

	public Flappy(int x, int y) {
		setAll(new boolean[x][y]);
		set(3, 0, true);
		set(3, 1, true);
		set(3, 2, true);
		set(3, 3, true);
		set(4, 0, true);
		set(4, 1, true);
		set(4, 2, true);
		set(4, 3, true);
		setX(2);
		setY((int) getAll()[0].length / 2);
	}

	public boolean[][] getAll() {
		return this.all;
	}

	public void setAll(boolean[][] all) {
		this.all = all;
	}

	public int getX() {
		return this.x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return this.y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean get(int x, int y) {
		if (inArray(x, y)) return all[x][y];
		else return false;
	}

	public void set(int x, int y, boolean p) {
		if (inArray(x, y)) all[x][y] = p;
	}

	private boolean inArray(int x, int y) {
		return x >= 0 && x < all.length && y >= 0 && y < all[0].length;
	}

	public boolean isNull(int x, int y) {
		return inArray(x, y) && get(x, y) == false;
	}

	public void up() {
		int y = getY() - 1;
		if (y < 0) y = all[0].length - 1;
		if (isNull(getX(), y)) setY(y);
	}

	public void down() {
		if (isNull(getX(), (getY() + 1) % all[0].length)) setY((getY() + 1)
				% all[0].length);
	}

	public void right() {
		if (inArray(getX() + 1, getY()) && isNull(getX() + 1, getY())) check();
	}

	public void left() {
		if (inArray(getX() - 1, getY()) && isNull(getX() - 1, getY())) setX(getX() - 1);
	}

	public void check() {
		for (int i = 0; i < getAll().length - 1; i++)
			all[i] = all[i + 1];
		all[getAll().length - 1] = new boolean[getAll()[0].length];
		boolean xp = true;
		for (int i = getAll().length - 5; i < getAll().length; i++)
			for (int j = 0; j < getAll()[0].length; j++)
				if (!isNull(i, j)) xp = false;
		if (xp) {
			int x = (int) (Math.random() * getAll().length);
			for (int j = 0; j < getAll()[0].length; j++)
				if (j < x - 2 || j > x + 2) all[getAll().length - 1][j] = true;
		}

	}
}