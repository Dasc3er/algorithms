package labirinto;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Stack;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import dijkstra.Dijkstra;
import layout.Elemento;
import layout.Layout;

/**
 * Labirinto.
 * 
 * @author Thomas Zilio
 */
public class Labirinto extends JPanel implements MouseListener {
	private ElementoLabirinto[][] mappa;
	private Layout layout;
	private Dijkstra dijkstra;

	public Labirinto(int dimensione) {
		ElementoLabirinto.setWidth(dimensione);
		setMappa(new ElementoLabirinto[dimensione][dimensione]);
		layout = new Layout(getWidth(), getHeight(), mappa.length, mappa.length);
//		layout.setInvertito(true);
		for (int i = 0; i < dimensione; i++) {
			for (int j = 0; j < dimensione; j++) {
				mappa[i][j] = new ElementoLabirinto(i, j);
				layout.aggiungi(mappa[i][j]);
			}
		}
		Vector<Dijkstra.Tratto> GRAPH = new Vector<Dijkstra.Tratto>(10, 10);
		Stack<ElementoLabirinto> stack = new Stack<ElementoLabirinto>();
		stack.push(mappa[0][0]);
		ElementoLabirinto edit;
		while (!stack.isEmpty()) {
			edit = stack.peek();
			int x = edit.getPosizione().x;
			int y = edit.getPosizione().y;
			if (!liberoDestra(x, y) && !liberoSinistra(x, y)
					&& !liberoAlto(x, y) && !liberoBasso(x, y)) stack.pop();
			else {
				boolean result = false;
				while (!result) {
					int rand = (int) (Math.random() * 4);
					switch (rand) {
					case 1:
						if (liberoSinistra(x, y)) {
							GRAPH.add(
									new Dijkstra.Tratto(edit.getNome(), mappa[x][y
											- 1].getNome(), 1));
							edit.setSinistra(true);
							mappa[x][y - 1].setDestra(true);
							stack.push(mappa[x][y - 1]);
							result = true;
						}
						break;
					case 2:
						if (liberoBasso(x, y)) {
							GRAPH.add(
									new Dijkstra.Tratto(edit.getNome(), mappa[x
											+ 1][y].getNome(), 1));
							edit.setBasso(true);
							mappa[x + 1][y].setAlto(true);
							stack.push(mappa[x + 1][y]);
							result = true;
						}
						break;
					case 3:
						if (liberoAlto(x, y)) {
							GRAPH.add(
									new Dijkstra.Tratto(edit.getNome(), mappa[x
											- 1][y].getNome(), 1));
							edit.setAlto(true);
							mappa[x - 1][y].setBasso(true);
							stack.push(mappa[x - 1][y]);
							result = true;
						}
						break;
					default:
						if (liberoDestra(x, y)) {
							GRAPH.add(
									new Dijkstra.Tratto(edit.getNome(), mappa[x][y
											+ 1].getNome(), 1));
							edit.setDestra(true);
							mappa[x][y + 1].setSinistra(true);
							stack.push(mappa[x][y + 1]);
							result = true;
						}
						break;
					}
				}
			}
		}
		Dijkstra.Tratto[] grafo = new Dijkstra.Tratto[GRAPH.size()];
		for (int i = 0; i < grafo.length; i++) {
			grafo[i] = GRAPH.elementAt(i);
		}
		dijkstra = new Dijkstra(grafo, true);
		addMouseListener(this);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		layout.modificaDimensioni(getWidth() + 1, getHeight() + 1);
		layout.disegna(g);
	}

	public boolean inArray(int x, int y) {
		return x >= 0 && x < mappa.length && y >= 0 && y < mappa.length;
	}

	public boolean isolato(int x, int y) {
		return inArray(x, y) && !mappa[x][y].visitato();
	}

	public boolean liberoDestra(int x, int y) {
		return mappa[x][y].isolatoDestra() && isolato(x, y + 1);
	}

	public boolean liberoSinistra(int x, int y) {
		return mappa[x][y].isolatoSinistra() && isolato(x, y - 1);
	}

	public boolean liberoAlto(int x, int y) {
		return mappa[x][y].isolatoAlto() && isolato(x - 1, y);
	}

	public boolean liberoBasso(int x, int y) {
		return mappa[x][y].isolatoBasso() && isolato(x + 1, y);
	}

	public ElementoLabirinto[][] getMappa() {
		return this.mappa;
	}

	public void setMappa(ElementoLabirinto[][] mappa) {
		this.mappa = mappa;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		for (Elemento elemento : layout.getElementi()) {
			elemento.setBackground(Color.white);
		}
		int arrivo = layout.numeroCoordinate(arg0.getX(), arg0.getY());
//		System.out.println(arrivo);
		if (arrivo != 0) {
			String percorso =
					dijkstra.dijkstra(mappa[0][0].getNome(), "" + arrivo);
			System.out.println(percorso);
			if (percorso.indexOf("(Destinazione non raggiungibile)") == -1) {
				String el[] =
						percorso.substring(0, percorso.indexOf('(')).split(";");
				for (String elemento : el) {
					layout.ottieniElemento(
							Integer.parseInt(elemento.trim())).setBackground(
									Color.yellow);
				}
			}
		}
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame frame = new JFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.getContentPane().add(new Labirinto(10));
				frame.setTitle("Labirinto");
				frame.setMinimumSize(new Dimension(400, 400));
				frame.setVisible(true);
			}
		});
	}

}
