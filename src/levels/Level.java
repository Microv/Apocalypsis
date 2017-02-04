package levels;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.sound.sampled.Clip;

import map.TileMap;
import entity.Player;

/**
 * Classe astratta per la definizione 
 * di un livello del gioco
 * 
 * @author Michele Roviello
 *
 */
public abstract class Level {
	
	protected LevelManager lm;
	protected TileMap tilemap;
	protected Player player;
	protected Font font;
	protected Clip clip;
	protected Image background;
	
	public Level(LevelManager lm){
		this.lm = lm;
	}
	
	/**
	 * Inizializza le variabili del livello
	 */
	public abstract void init(); 
	
	/**
	 * Aggiorna lo stato degli elementi del livello
	 */
	public abstract void update();
	
	/**
	 * Disegna lo stato degli elementi del livello
	 * @param g2d Elemento grafico su cui avviene il disegno
	 */
	public abstract void draw(Graphics2D g2d);
	
	/**
	 * Restituisce lo stato del livello
	 * @return true se il livello e' in esecuzione, 
	 * false altrimenti
	 */
	public abstract boolean getState();
	
	
	/**
	 * Gestisce la pressione di un tasto 
	 * @param k 
	 */
	public void keyPressed(KeyEvent k) {
		if(k.getKeyCode() == KeyEvent.VK_UP)
			player.setUp(true);
		if(k.getKeyCode() == KeyEvent.VK_LEFT)
			player.setLeft(true);
		if(k.getKeyCode() == KeyEvent.VK_RIGHT)
			player.setRight(true);
		if(k.getKeyCode() == KeyEvent.VK_E){
			player.setShoot(true);
		}
	}
	
	/**
	 * Gestisce il rilascio di un tasto 
	 * @param k
	 */
	public void keyReleased(KeyEvent k) {
		if(k.getKeyCode() == KeyEvent.VK_UP)
			player.setUp(false);
		if(k.getKeyCode() == KeyEvent.VK_LEFT)
			player.setLeft(false);
		if(k.getKeyCode() == KeyEvent.VK_RIGHT)
			player.setRight(false);
		if(k.getKeyCode() == KeyEvent.VK_E)
			player.setShoot(false);
	}
	
}
