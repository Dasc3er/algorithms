package clickball;

import java.awt.Polygon;
import java.util.Vector;

/**
 * @author Thomas Zilio
 */
public class Obstacle extends Obj {
	private Vector<Point> obj = new Vector<Point>(0, 1);

	public Obstacle(int x, int y, int n, int c) {
		super(c);
		if (n == 4) square(x, y);
		else triangle(x, y);
	}

	public void square(int x, int y) {
		obj.add(new Point(x, y));
		obj.add(new Point(x + 20, y));
		obj.add(new Point(x + 20, y + 20));
		obj.add(new Point(x, y + 20));
	}

	public void triangle(int x, int y) {
		obj.add(new Point(x, y));
		obj.add(new Point(x + 20, y));
		obj.add(new Point(x + 10, y + 20));
	}

	public void updateTriangle(int x, int y) {
		obj.elementAt(0).setLocation(x, y);
		obj.elementAt(1).setLocation(x + 20, y);
		obj.elementAt(2).setLocation(x + 10, y + 20);
	}

	public void updateSquare(int x, int y) {
		obj.elementAt(0).setLocation(x, y);
		obj.elementAt(1).setLocation(x + 20, y);
		obj.elementAt(2).setLocation(x + 20, y + 20);
		obj.elementAt(3).setLocation(x, y + 20);
	}

	public void aggiorna(int w, int h) {
		if (getDir() == 0) {
			if (!xInScreen(w, h)) setVx(-getVx());
			if (!yInScreen(w, h)) setVy(-getVy());
		}
		else {
			if (!allInScreen(w, h)) {
				if (!allXInScreen(w, h)) if (obj.size() == 4) updateSquare(-20,
						obj.elementAt(0).getY());
				else updateTriangle(-20, obj.elementAt(0).getY());
				if (!allYInScreen(w, h)) if (obj.size() == 4) updateSquare(
						obj.elementAt(0).getX(), -20);
				else updateTriangle(obj.elementAt(0).getX(), -20);

			}
		}
		move();
	}

	public void move() {
		for (int i = 0; i < obj.size(); i++) {
			obj.elementAt(i).setX(obj.elementAt(i).getX() + getVx());
			obj.elementAt(i).setY(obj.elementAt(i).getY() + getVy());
		}
	}

	public Vector<Point> getObj() {
		return obj;
	}

	public void setObj(Vector<Point> obj) {
		this.obj = obj;
	}

	public int getN() {
		return obj.size();
	}

	public boolean allInScreen(int w, int h) {
		boolean ret = true;
		for (int i = 0; i < obj.size(); i++)
			if (obj.elementAt(i).getX() > w + 20
					|| obj.elementAt(i).getY() > h + 20
					|| obj.elementAt(i).getX() < 0 - 20
					|| obj.elementAt(i).getY() < 0 - 20) ret = false;
		return ret;
	}

	public boolean allXInScreen(int w, int h) {
		boolean ret = true;
		for (int i = 0; i < obj.size(); i++)
			if (obj.elementAt(i).getX() >= w || obj.elementAt(i).getX() <= 0) ret =
					false;
		return ret;
	}

	public boolean allYInScreen(int w, int h) {
		boolean ret = true;
		for (int i = 0; i < obj.size(); i++)
			if (obj.elementAt(i).getY() >= h || obj.elementAt(i).getY() <= 0) ret =
					false;
		return ret;
	}

	public boolean xInScreen(int w, int h) {
		boolean ret = true;
		for (int i = 0; i < obj.size(); i++)
			if (obj.elementAt(i).getX() >= w || obj.elementAt(i).getX() <= 0) ret =
					false;
		return ret;
	}

	public boolean yInScreen(int w, int h) {
		boolean ret = true;
		for (int i = 0; i < obj.size(); i++)
			if (obj.elementAt(i).getY() >= h || obj.elementAt(i).getY() <= 0) ret =
					false;
		return ret;
	}

	public Polygon print() {
		Polygon x = new Polygon();
		for (int i = 0; i < obj.size(); i++)
			x.addPoint(obj.elementAt(i).getX(), obj.elementAt(i).getY());
		return x;
	}

	@Override
	public String toString() {
		return "Obstacle [obj=" + obj + ", toString()=" + super.toString()
				+ "]";
	}
}
