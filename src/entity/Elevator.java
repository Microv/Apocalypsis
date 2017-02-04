package entity;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.HashMap;
import javax.swing.ImageIcon;
import map.TileMap;

public class Elevator extends MapObject {

	private Player player;
	
	public Elevator(TileMap tm) {
		super(tm);
	}
	
	/**
	 * Inizializza le animazioni dell'elemento
	 */
	@Override
	protected void initAnimations() {

		width = 106;
		height = 130;
		
		animations = new HashMap<String, Image>();
		
		animations.put("elevator", (new ImageIcon(
				getClass().getResource("/various/elevator.png"))).getImage());
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
		g2d.drawImage(animations.get("elevator"),
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
	 * coordinate dell'oggetto Player
	 */
	private void checkPlayerCollision(){
		if(getBounds().intersects(player.getBounds())){
			isAlive = false;
		}
			
	}
}
