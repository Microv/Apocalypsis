package entity;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.HashMap;
import java.util.List;

import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;

import main.Sound;
import map.TileMap;

public class Zombie extends MapObject implements Runnable{
	
	private static final int INCREASE_SCORE = 10;
	private static final int DECREASE_SCORE = -3;
	
	private Player player;
	private List<Shot> shots;
	private int life;
	
	private int xOffset, yOffset;
	
	private Clip clip;
	
	public Zombie(TileMap tm){
		super(tm);
		
		xOffset = - 20;
		yOffset = - 126 + 50;
		
		dx = moveSpeed = 1;
		
	}
	
	/**
	 * Imizializza le animazioni dell'elemento
	 */
	@Override
	protected void initAnimations() {
		
		width = 60;
		height = 126;
		
		animations = new HashMap<String, Image>();
		
		// right
		animations.put("moving1_right",
				(new ImageIcon(
						getClass().getResource("/zombie/zombie1.png"))).getImage());
		animations.put("moving2_right",
				(new ImageIcon(
						getClass().getResource("/zombie/zombie2.png"))).getImage());
		animations.put("moving3_right",
				(new ImageIcon(
						getClass().getResource("/zombie/zombie3.png"))).getImage());
		
		//left
		animations.put("moving1_left",
				(new ImageIcon(
						getClass().getResource("/zombie/zombie4.png"))).getImage());
		animations.put("moving2_left",
				(new ImageIcon(
						getClass().getResource("/zombie/zombie5.png"))).getImage());
		animations.put("moving3_left",
				(new ImageIcon(
						getClass().getResource("/zombie/zombie6.png"))).getImage());
		
		// dying right
		animations.put("dying1_right",
				(new ImageIcon(
						getClass().getResource("/zombie/dying_zombie1.png"))).getImage());
		animations.put("dying2_right",
				(new ImageIcon(
						getClass().getResource("/zombie/dying_zombie2.png"))).getImage());
		animations.put("dying3_right",
				(new ImageIcon(
						getClass().getResource("/zombie/dying_zombie3.png"))).getImage());
		animations.put("dying4_right",
				(new ImageIcon(
						getClass().getResource("/zombie/dying_zombie4.png"))).getImage());
		
		// dying left
		animations.put("dying1_left",
				(new ImageIcon(
						getClass().getResource("/zombie/dying_zombie5.png"))).getImage());
		animations.put("dying2_left",
				(new ImageIcon(
						getClass().getResource("/zombie/dying_zombie6.png"))).getImage());
		animations.put("dying3_left",
				(new ImageIcon(
						getClass().getResource("/zombie/dying_zombie7.png"))).getImage());
		animations.put("dying4_left",
				(new ImageIcon(
						getClass().getResource("/zombie/dying_zombie8.png"))).getImage());
	}

	/**
	 * Ottiene la prossima posizione 
	 */
	@Override
	protected void getNextPosition() {
		if(dx < 0)
			dx -= moveSpeed;
		else 
			dx += moveSpeed;
		//dy = 1;
	}
	
	/**
	 * Controlla la presenza di collisioni con gli elementi delle mappa
	 */
	@Override
	protected void checkMapCollision() {
		int currCol = x / tileSize; 
		int currRow = y / tileSize;
		
		if(dx < 0){
			if(tileMap.isTile(currRow, currCol - 1) ||
					!tileMap.isTile(currRow + 1, currCol - 3)){
				dx = moveSpeed;
				rightSide = true;
			}
			else
				x += dx;
		}
		if(dx > 0){
			if(tileMap.isTile(currRow, currCol + 1) ||
					!tileMap.isTile(currRow + 1, currCol + 3)){
				dx = -moveSpeed;
				rightSide = false;
			}
			else
				x += dx;
		}
	}
	
	/**
	 * Disegna l'elemento
	 */
	@Override
	public void draw(Graphics2D g2d) {
		if(isAlive){
			if(dx > 0)
				drawMovement(g2d);
			else if(dx < 0)
				drawMovement(g2d);
		}
		else{
			drawDeath(g2d);
		}
	}
	
	/**
	 * Disegna l'elemento in movimento
	 * @param g2d Contesto grafico su cui avviene il disegno
	 */
	private void drawMovement(Graphics2D g2d){
		
		String side = "";
		
		if(rightSide)
			side  = "right";
		else
			side = "left";
		
		if(countMovements < 2){
			g2d.drawImage(animations.get("moving1_"+side),
					x + xOffset + tileMap.getX(), 
					y + yOffset + tileMap.getY(),
					null);
		}
		else if(countMovements < 4)
			g2d.drawImage(animations.get("moving2_"+side),
					x + xOffset + tileMap.getX(), 
					y + yOffset + tileMap.getY(),
					null);
		else if(countMovements < 6){
			g2d.drawImage(animations.get("moving3_"+side),
					x + xOffset + tileMap.getX(), 
					y + yOffset + tileMap.getY(),
					null);
			if(countMovements == 5) countMovements = 0;
		}
		countMovements++;
	}
	
	/**
	 * Disegna la morte dell'elemento
	 * @param g2d Contesto grafico su cui avviene il disegno
	 */
	private void drawDeath(Graphics2D g2d) {
		
		String side = "";
		
		if(rightSide)
			side  = "right";
		else
			side = "left";
		
		if(countMovements < 2){
			g2d.drawImage(animations.get("dying1_"+side),
					x + xOffset + tileMap.getX(), 
					y + yOffset + tileMap.getY(),
					null);
			countMovements++;
		}
		else if(countMovements < 4){
			g2d.drawImage(animations.get("dying2_"+side),
					x + xOffset + tileMap.getX(), 
					y + yOffset + tileMap.getY(),
					null);
			countMovements++;
		}
		else if(countMovements < 6){
			g2d.drawImage(animations.get("dying3_"+side),
					x + xOffset + tileMap.getX(), 
					y + yOffset + tileMap.getY(),
					null);
			countMovements++;
		}
			else
				g2d.drawImage(animations.get("dying4_"+side),
						x + xOffset + tileMap.getX(), 
						y + yOffset + 10 + tileMap.getY(),
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
	 * Associa l'elemento alla lista di spari effettuati 
	 * dal personaggio principale
	 * @param shots Lista di spari
	 */
	public void setShots(List<Shot> shots){
		this.shots = shots;
	}
	
	/**
	 * Imposta la resistenza dell'elemento
	 * @param life Resistenza (numero di vire)
	 */
	public void setLife(int life){
		this.life = life;
	}
	
	/**
	 * Controlla se l'elemento sia stato colpito da uno sparo
	 */
	private void checkShots(){
		try{
			for(int i = 0; i < shots.size(); i++)
				if(getBounds().intersects(shots.get(i).getBounds())){
					life--;
					player.modifyScore(INCREASE_SCORE);
					shots.get(i).collide();
					if(life == 0){
						isAlive = false;
						
						if(clip != null)
							;//clip.close();
					}
				}
		}catch(IndexOutOfBoundsException e){
			checkShots();
		}
	}
	
	/**
	 * Controlla la presenza di collisioni con l'oggetto Player
	 */
	private void checkPlayerCollision(){
		if(getBounds().intersects(player.getBounds())){
				player.setDamage();
				player.modifyScore(DECREASE_SCORE);
				//Sound.playSound("sound/bite.wav");
				if(clip == null){
					clip = Sound.getClip("/bite.wav");
					clip.start();
				}
		}
		else
			if(clip != null){
				//clip.close();
				clip = null;
			}
	}
	
	/**
	 * Aggiorna lo stato dell'elemento
	 */
	public void update(){
		super.update();
		checkShots();
		checkPlayerCollision();
	}
}
