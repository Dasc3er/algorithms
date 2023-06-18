package dama;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Vector;

/**
 * @author Thomas Zilio
 */
public class Log {
	private String file;
	private Vector<String> mosse = new Vector(100, 100);
	private Vector<String> mostra = new Vector(50, 10);
	private int place;
	private boolean guardo;

	/**
	 * 
	 */
	public Log() {
		this("log.txt");
	}

	/**
	 * 
	 * @param file
	 */
	public Log(String file) {
		setFile(file);
		read();
		setGuardo(false);
	}

	/**
	 * 
	 * @param value
	 */
	public void add(String value) {
		mosse.addElement(value);
		registra();
	}

	/**
	 * 
	 */
	public void read() {
		File log = new File(getFile());
		try {
			if (!log.exists()) log.createNewFile();
			FileInputStream fin = new FileInputStream(getFile());
			Scanner in = new Scanner(fin);
			while (in.hasNextLine()) {
				mosse.addElement(in.nextLine());
			}
		}
		catch (IOException e) {
			System.out.println("Impossibile accedere al file di log!!!");
		}
	}

	/**
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		return (mosse.isEmpty());
	}

	/**
	 * 
	 */
	public void removeLast() {
		if (!isGuardo()) {
			mosse.remove(mosse.size() - 1);
			registra();
		}
	}

	/**
	 * 
	 * @return
	 */
	public String last() {
		if (mosse.isEmpty()) return "";
		else return mosse.lastElement();
	}

	/**
	 * 
	 */
	public void registra() {
		if (!isGuardo()) {
			File log = new File(getFile());
			try {
				if (!log.exists()) log.createNewFile();
				PrintWriter pw = new PrintWriter(getFile());
				for (int i = 0; i < mosse.size(); i++)
					pw.println(mosse.elementAt(i));

				pw.close();
				/*
				 * FileWriter pw = new FileWriter(getFile(), true);
				 * BufferedWriter bufferedWriter = new BufferedWriter(pw);
				 * for (int i = 0; i < mosse.size(); i++)
				 * bufferedWriter.write(mosse.elementAt(i) + "\n");
				 * bufferedWriter.close();
				 * pw.close();
				 */
			}
			catch (IOException e) {
				System.out.println("Impossibile accedere al file di log!!!");
			}
		}
	}

	/**
	 * 
	 * @param numero
	 */
	public void mostra(int numero) {
		int cont = 0;
		for (String s : getMosse()) {
			if (s.split(";")[0].equalsIgnoreCase("inizio")) cont++;
			if (cont == numero && !s.equalsIgnoreCase("")
					&& !s.split(";")[0].equalsIgnoreCase("inizio")
					&& !s.split(";")[0].equalsIgnoreCase("fine")) mostra.add(s);
		}
		setPlace(0);
	}

	/**
	 * 
	 */
	public void avanti() {
		if (getPlace() + 1 <= mostra.size()
				&& getPlace() + 1 >= 0) setPlace(getPlace() + 1);
	}

	/**
	 * 
	 */
	public void indietro() {
		if (getPlace() - 1 <= mostra.size()
				&& getPlace() - 1 >= 0) setPlace(getPlace() - 1);
	}

	/**
	 * 
	 * @return
	 */
	public String mossa() {
		if (getPlace() >= 0 && getPlace() < mostra.size()) {
			String x = mostra.elementAt(getPlace());
			avanti();
			return x;
		}
		else return null;
	}

	public String torna() {
		if (getPlace() - 1 <= mostra.size() && getPlace() - 1 >= 0) {
			indietro();
			return mostra.elementAt(getPlace());
		}
		else return null;
	}

	public String getFile() {
		return this.file;
	}

	public void setFile(String value) {
		this.file = value;
	}

	public boolean isGuardo() {
		return this.guardo;
	}

	public void setGuardo(boolean guardo) {
		this.guardo = guardo;
	}

	public int getPlace() {
		return this.place;
	}

	public void setPlace(int place) {
		this.place = place;
	}

	public Vector<String> getMosse() {
		return this.mosse;
	}

	public Vector<String> getMostra() {
		return this.mostra;
	}

}
