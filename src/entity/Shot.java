package entity;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.HashMap;

import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;

import main.Sound;
import map.TileMap;

public class Shot extends MapObject
			implements Runnable {
	
	private Player player;
	@SuppressWarnings("unused")
	private int initialX, initialY;
	private int maxLength;
	
	private Clip clip;
	
	public Shot(TileMap tm, Player p){
		super(tm);
		
		player = p;
		moveSpeed = 5;
		
		maxLength = 500;
		
		//Sound.playSound("sound/shot.wav");
		
		clip = Sound.getClip("/shot.wav");
		clip.start();
	}
	
	/**
	 * Inizializza le animazioni dell'elemento
	 */
	@Override
	protected void initAnimations() {
		
		width = 20;
		height = 16;
		
		animations = new HashMap<String, Image>();
		
		animations.put("shot", 
				(new ImageIcon(
						getClass().getResource(
								"/soldier/shooting/shot.png")).getImage()));
	}

	/**
	 * Ottiene la prossima posizione edell'elemento
	 */
	@Override
	protected void getNextPosition() {
		if(dx > 0)
			dx += moveSpeed;
		else if(dx <0)
			dx -= moveSpeed;
	}

	/**
	 * Controlla la presenza di collisioni con gli elementi della mappa
	 */
	@Override
	protected void checkMapCollision() {
		int currCol = x / tileSize; 
		int currRow = y / tileSize;
		
		if(dx < 0){
			if(tileMap.isTile(currRow, currCol - 1)){
				dx = 0;
				collide();
			}
			else
				x += dx; 
		}
		if(dx > 0){
			if(tileMap.isTile(currRow, currCol + 1)){
				dx = 0;
				collide();
			}
			else
				x += dx;
		}
	}
	
	/**
	 * Effettua una collisione
	 */
	public void collide(){
		
		synchronized(player.getShots()){
			player.getShots().remove(this);
		}
		
		isAlive = false;
		clip.close();
	}

	/**
	 * Disegna l'elemento
	 */
	@Override
	public void draw(Graphics2D g2d) {
		g2d.drawImage(animations.get("shot"),
				x + tileMap.getX(),
				y +tileMap.getY(), 
				null);
	}
	
	/**
	 * Imposta la posizione iniziale
	 * @param x Ascissa iniziale
	 * @param y Ordinata iniziale
	 */
	public void setInitialPosition(int x, int y){
		initialX = x;
		initialY = y;
	}
	
	/**
	 * Aggiorna lo stato dell'elemento
	 */
	public void update(){
		
		super.update();
		
		if(Math.abs(x -initialX) > maxLength){
			synchronized (player.getShots()) {
				player.getShots().remove(this);
			}	
			isAlive = false;
			clip.close();
		}
	}
}
