package layout;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

/**
 * @author Thomas Zilio
s */
public class Pannello extends JPanel implements MouseListener {
	private Layout[] layout;

	public Pannello() {
		super();
		addMouseListener(this);
		setLayout(new Layout[2]);
		layout();
	}

	public void setLayout(Layout[] layout) {
		this.layout = layout;
	}

	public void layout() {
		layout[0] = new Layout(getWidth() + 1, getHeight() + 1, 2, 3);
//		layout[0].setInvertito(true);
		layout[0].aggiungi(new ElementoString(Color.yellow, "Bianco", Color.red));
		layout[0].aggiungi(new ElementoString(Color.yellow, "Nero", Color.red));
		layout[0].aggiungi(new ElementoString(Color.yellow, "Prova", Color.red));
		layout[0].aggiungi(new ElementoString(Color.yellow, "Pizza", Color.red));
		layout[0].aggiungi(new ElementoString(Color.yellow, "Bot vs bot", Color.red));
		layout[0].aggiungi(new ElementoString(Color.red, "Impostazioni", Color.white));
		layout[1] = layout[0].dividi(3, 2, 2);
		layout[1].aggiungi(new ElementoString(Color.blue, "A", Color.yellow));
		layout[1].aggiungi(new ElementoString(Color.blue, "B", Color.yellow));
		layout[1].aggiungi(new ElementoString(Color.blue, "C", Color.yellow));
		layout[1].aggiungi(new ElementoString(Color.blue, "D", Color.yellow));
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
//		layout[0].modificaDimensioni(getWidth() + 1, getHeight() + 1);
		layout[0].disegna(g);
		layout[1].disegna(g);
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
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("Posizione individuata nel primo layout: "
				+ layout[0].numeroCoordinate(e.getX(), e.getY()));
		System.out.println("Posizione individuata nel secondo layout: "
				+ layout[1].numeroCoordinate(e.getX(), e.getY()));
		System.out.println("------");
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}
}
