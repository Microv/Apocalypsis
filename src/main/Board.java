package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import levels.LevelManager;

/**
 * Pannello principale del gioco
 * 
 * @author Michele Roviello
 *
 */
@SuppressWarnings("serial")
public class Board extends JPanel 
		implements Runnable, KeyListener{
	public static final int WIDTH = 640;
	public static final int HEIGHT = 450;
	public static final int SCALE = 1;
	
	public static int FPS = 60;
	
	private Graphics2D g2d;
	
	private LevelManager lm;
	private Image image;
	private Thread thread;
	
	public Board(){
		setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		setFocusable(true);
		requestFocus();
		
		lm = new LevelManager();
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g2d = (Graphics2D) image.getGraphics();
	}
	
	public void addNotify() {
		super.addNotify();
		if(thread == null){
			thread = new Thread(this);
			addKeyListener(this);
			thread.start();
		}
	}
	
	/**
	 * Gestisce la pressione di un tasto
	 */
	@Override
	public void keyPressed(KeyEvent k) {
		lm.keyPressed(k);
	}

	/**
	 * Gestisce il rilascio di un tasto 
	 * precedentemente premuto
	 */
	@Override
	public void keyReleased(KeyEvent k) {
		lm.keyReleased(k);
	}
	
	@Override
	public void keyTyped(KeyEvent k) {}
	
	/**
	 * Metodo di esecuzione del thread principale del gioco
	 */
	@Override
	public void run() {
		 while(true){
			 update();
			 draw(g2d);
			 drawToScreen();
			 checkState();
			 
			 try {
				Thread.sleep(FPS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		 }
	}

	/**
	 * Effettua l'aggiornamento degli elementi del gioco
	 */
	private void update() {
		lm.update();
	}

	/**
	 * Disegna l'immagine corrente del gioco
	 * @param g2d
	 */
	private void draw(Graphics2D g2d) {
		lm.draw(g2d);
	}

	/**
	 * Disegna sul pannello l'immagine corrente 
	 */
	private void drawToScreen() {
		Graphics g = getGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();
	}
	
	/**
	 * Verifica lo stato del gioco
	 */
	private void checkState(){
		lm.checkState();
	}
}
