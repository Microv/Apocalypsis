package main;

import javax.swing.JFrame;
/**
 * Classe principale contenente il metodo main per
 * l'avvio del gioco
 * 
 * @author Michele Roviello
 *
 */
public class Game {
	/**
	 * Metodo main per l'avvio del gioco
	 * @param args
	 */
	public static void main(String[] args){
		JFrame window = new JFrame();
		window.setContentPane(new Board());
		window.setTitle("Apocalypsis");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.pack();
		window.setVisible(true);
	}
}
