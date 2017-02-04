package entity;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.HashMap;
import java.util.List;

import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;

import main.Sound;
import map.TileMap;

public class Munition extends MapObject {

	private Player player;
	private List<Munition> munitions;
	
	private Clip clip;
	
	public Munition(TileMap tm, List<Munition> munitions) {
		super(tm);

		this.munitions = munitions;
		
		clip = Sound.getClip("/pump.wav");
	}

	/**
	 * Inizializza le animazioni dell'elemento
	 */
	@Override
	protected void initAnimations() {
		
		width = 38;
		height = 40;
		
		animations = new HashMap<String, Image>();
		
		animations.put("munitions",
				(new ImageIcon(
						getClass().getResource(
								"/various/munitions.png"))).getImage());
	}

	/**
	 * Ottiene la prossima posizione dell'elemento
	 */
	@Override
	protected void getNextPosition() {
	}

	/**
	 * Controlla la presenza di collisioni con gli elementi della mappa
	 */
	@Override
	protected void checkMapCollision() {
	}

	/**
	 * Disegna l'elemento
	 */
	@Override
	public void draw(Graphics2D g2d) {
		g2d.drawImage(animations.get("munitions"),
				x + tileMap.getX(), 
				y + tileMap.getY(), 
				null);
	}
	
	/**
	 * Imposta l'oggetto player associato all'elemento
	 * @param player Istanza dell'oggetto player
	 */
	public void setPlayer(Player player){
		this.player = player;
	}
	
	/**
	 * Aggiorna lo stato dell'elemento
	 */
	public void update(){
		checkPlayerCollision();
	}
	
	/**
	 * Controlla la presenza di collisioni con le 
	 * coordinate dell'oggetto player
	 */
	private void checkPlayerCollision(){
		if(getBounds().intersects(player.getBounds())){
			player.addMunitions(50);
			munitions.remove(this);
			isAlive = false;
			
			//Sound.playSound("sound/pump.wav");
			clip.start();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			clip.close();
		}
			
	}
}
