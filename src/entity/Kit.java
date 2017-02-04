package entity;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.HashMap;
import java.util.List;

import javax.swing.ImageIcon;

import map.TileMap;

public class Kit extends MapObject {
	
	private Player player;
	private List<Kit> kits;
	
	public Kit(TileMap tm, List<Kit> kits) {
		super(tm);

		this.kits = kits;
	}

	/**
	 * Inizializza le animazioni dell'elemento
	 */
	@Override
	protected void initAnimations() {

		width = 38;
		height = 40;
		
		animations = new HashMap<String, Image>();
		
		animations.put("kit", (new ImageIcon(
				getClass().getResource("/various/kit.png"))).getImage());
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
		g2d.drawImage(animations.get("kit"),
				x + tileMap.getX(), 
				y + tileMap.getY(), 
				null);
	}
	
	/**
	 * Imposta l'oggetto player associato all'elemento
	 * @param player Istanza dell'oggetto Player
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
	 * Controlla la presenza di collisioni con le coordinate dell'oggetto player
	 */
	private void checkPlayerCollision(){
		if(getBounds().intersects(player.getBounds())){
			player.cure(10);
			kits.remove(this);
			isAlive = false;
		}
			
	}
}
