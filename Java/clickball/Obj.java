package clickball;

/**
 * @author Thomas Zilio
 */
public class Obj {
	private int vx, vy, change, dir, r, g, b;

	public Obj(int c) {
		setChange(c);
		if (getChange() == 0) {
			setVy((int) (Math.random() * 10 + 1));
			setVx((int) (Math.random() * 10 + 1));
			setDir(0);
		}
		else {
			setVy((int) (Math.random() * 20 + 1) - 10);
			setVx((int) (Math.random() * 20 + 1) - 10);
			setDir((int) (Math.random() * 2));
		}
		setR((int) (Math.random() * 255));
		setG((int) (Math.random() * 255));
		setB((int) (Math.random() * 255));
	}

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	public int getG() {
		return g;
	}

	public void setG(int g) {
		this.g = g;
	}

	public int getB() {
		return b;
	}

	public void setB(int b) {
		this.b = b;
	}

	protected void setVx(int n) {
		this.vx = n;
	}

	public int getVx() {
		return this.vx;
	}

	protected void setVy(int n) {
		this.vy = n;
	}

	public int getVy() {
		return this.vy;
	}

	protected void setChange(int n) {
		this.change = n;
	}

	protected int getChange() {
		return this.change;
	}

	protected void setDir(int n) {
		this.dir = n;
	}

	protected int getDir() {
		return this.dir;
	}

	public void change() {
		if (getChange() >= 2) {
			int temp = getVx();
			setVx(getVy());
			setVy(temp);
		}
	}

	@Override
	public String toString() {
		return "Obj [vx=" + getVx() + ", vy=" + getVy() + ", change="
				+ getChange() + ", dir=" + getDir() + ", r=" + getR() + ", g="
				+ getG() + ", b=" + getB() + "]";
	}
}
