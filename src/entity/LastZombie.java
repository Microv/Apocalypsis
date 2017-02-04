package entity;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.HashMap;
import java.util.List;

import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;

import main.Sound;
import map.TileMap;

public class LastZombie extends MapObject {
	
	private static final int INCREASE_SCORE = 200;
	private static final int DECREASE_SCORE = -5;
	
	private Player player;
	private boolean starting, moving, attacking;
	private String side;
	private List<Shot> shots; 
	private int life;
	private Clip clip;
	
	public LastZombie(TileMap tm) {
		super(tm);
	}
	
	/**
	 * Inizializza le animazioni dell'elemento
	 */
	@Override
	protected void initAnimations() {
		
		width = 208;
		height = 130;
		
		animations = new HashMap<String, Image>();
		
		// starting right
		animations.put("starting1_right",
				(new ImageIcon(
						getClass().getResource(
								"/lastZombie/starting/starting1_right.png"))).getImage());
		animations.put("starting2_right",
				(new ImageIcon(
						getClass().getResource(
								"/lastZombie/starting/starting2_right.png"))).getImage());
		animations.put("starting3_right",
				(new ImageIcon(
						getClass().getResource(
								"/lastZombie/starting/starting3_right.png"))).getImage());
		animations.put("starting4_right",
				(new ImageIcon(
						getClass().getResource(
								"/lastZombie/starting/starting4_right.png"))).getImage());
		
		// starting left
		animations.put("starting1_left",
				(new ImageIcon(
						getClass().getResource(
								"/lastZombie/starting/starting1_left.png"))).getImage());
		animations.put("starting2_left",
				(new ImageIcon(
						getClass().getResource(
								"/lastZombie/starting/starting2_left.png"))).getImage());
		animations.put("starting3_left",
				(new ImageIcon(
						getClass().getResource(
								"/lastZombie/starting/starting3_left.png"))).getImage());
		animations.put("starting4_left",
				(new ImageIcon(
						getClass().getResource(
								"/lastZombie/starting/starting4_left.png"))).getImage());
		
		// moving right
		animations.put("moving1_right",
				(new ImageIcon(
						getClass().getResource(
								"/lastZombie/moving/moving1_right.png"))).getImage());
		animations.put("moving2_right",
				(new ImageIcon(
						getClass().getResource(
								"/lastZombie/moving/moving2_right.png"))).getImage());
		animations.put("moving3_right",
				(new ImageIcon(
						getClass().getResource(
								"/lastZombie/moving/moving3_right.png"))).getImage());
		
		// moving left
		animations.put("moving1_left",
				(new ImageIcon(
						getClass().getResource(
								"/lastZombie/moving/moving1_left.png"))).getImage());
		animations.put("moving2_left",
				(new ImageIcon(
						getClass().getResource(
								"/lastZombie/moving/moving2_left.png"))).getImage());
		animations.put("moving3_left",
				(new ImageIcon(
						getClass().getResource(
								"/lastZombie/moving/moving3_left.png"))).getImage());
				
		
		// attacking right
		animations.put("attacking1_right",
				(new ImageIcon(
						getClass().getResource(
								"/lastZombie/attacking/attacking1_right.png"))).getImage());
		animations.put("attacking2_right",
				(new ImageIcon(
						getClass().getResource(
								"/lastZombie/attacking/attacking2_right.png"))).getImage());
		animations.put("attacking3_right",
				(new ImageIcon(
						getClass().getResource(
								"/lastZombie/attacking/attacking3_right.png"))).getImage());
		animations.put("attacking4_right",
				(new ImageIcon(
						getClass().getResource(
								"/lastZombie/attacking/attacking4_right.png"))).getImage());
		animations.put("attacking5_right",
				(new ImageIcon(
						getClass().getResource(
								"/lastZombie/attacking/attacking5_right.png"))).getImage());
		animations.put("attacking6_right",
				(new ImageIcon(
						getClass().getResource(
								"/lastZombie/attacking/attacking6_right.png"))).getImage());
		animations.put("attacking7_right",
				(new ImageIcon(
						getClass().getResource(
								"/lastZombie/attacking/attacking7_right.png"))).getImage());
	
		
		// attacking left
		animations.put("attacking1_left",
				(new ImageIcon(
						getClass().getResource(
								"/lastZombie/attacking/attacking1_left.png"))).getImage());
		animations.put("attacking2_left",
				(new ImageIcon(
						getClass().getResource(
								"/lastZombie/attacking/attacking2_left.png"))).getImage());
		animations.put("attacking3_left",
				(new ImageIcon(
						getClass().getResource(
								"/lastZombie/attacking/attacking3_left.png"))).getImage());
		animations.put("attacking4_left",
				(new ImageIcon(
						getClass().getResource(
								"/lastZombie/attacking/attacking4_left.png"))).getImage());
		animations.put("attacking5_left",
				(new ImageIcon(
						getClass().getResource(
								"/lastZombie/attacking/attacking5_left.png"))).getImage());
		animations.put("attacking6_left",
				(new ImageIcon(
						getClass().getResource(
								"/lastZombie/attacking/attacking6_left.png"))).getImage());
		animations.put("attacking7_left",
				(new ImageIcon(
						getClass().getResource(
								"/lastZombie/attacking/attacking7_left.png"))).getImage());
										
		// dying right
		animations.put("dying_right",
				(new ImageIcon(
						getClass().getResource(
								"/lastZombie/dying/dying_right.png"))).getImage());
		
		// dying left
		animations.put("dying_left",
				(new ImageIcon(
						getClass().getResource(
								"/lastZombie/dying/dying_left.png"))).getImage());
						
	}
	
	/**
	 * Imposta l'oggetto player associato all'elemento
	 * @param player Istanza dell'oggetto Player
	 */
	public void setPlayer(Player player){
		this.player = player;
	}
	
	/**
	 * Imposta la direzione dell'elemento (sinistra o destra)
	 * @param side "left" per impostare la direzione a sinistra, 
	 * "right altrimenti"
	 */
	public void setSide(String side){
		this.side = side;
	}
	
	/**
	 * Imposta la resistenza del nemico
	 * @param life Resistenza (numero di vite)
	 */
	public void setLife(int life){
		this.life = life;
	}
	
	/**
	 * Ottiene la prossima posizione dell'elemento
	 */
	@Override
	protected void getNextPosition() {
		
		if(player.getX() > 300 && !moving && !attacking){
			starting = true;
			if(clip == null){
				clip = Sound.getClip("/No_mercy.wav");
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			}
		}
		
		if(attacking)
			if(Math.abs(x -player.getX()) < 500){
				player.setDamage();
				player.modifyScore(DECREASE_SCORE);
			}
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
		
		g2d.drawImage(animations.get("starting1_"+side),
				x + tileMap.getX(), 
				y + tileMap.getY(),
				null);
		if(life > 0){
			if(starting)
				drawStart(g2d);
			if(moving)
				drawMoving(g2d);
			if(attacking)	
				drawAttacking(g2d);
		}
		else
			drawDeath(g2d);
		
	}

	/**
	 * Disegna lo stato iniziale dell'elemento
	 * @param g2d Contesto grafico su cui avviene il disegno
	 */
	private void drawStart(Graphics2D g2d) {
		
		if(countMovements < 2){
			g2d.drawImage(animations.get("starting2_"+side),
					x + tileMap.getX(), 
					y + tileMap.getY(),
					null);
			countMovements++;
		}
		else if(countMovements < 4){
			g2d.drawImage(animations.get("starting3_"+side),
					x + tileMap.getX(), 
					y + tileMap.getY(),
					null);
			countMovements++;
		}
		else if(countMovements < 6){
			g2d.drawImage(animations.get("starting3_"+side),
					x + tileMap.getX(), 
					y + tileMap.getY(),
					null);
			countMovements++;
			
			if(countMovements == 5){
				countMovements = 0;
				starting = false;
				moving = true;
			}
		}
	}
	/**
	 * Disegna elemento in movimento
	 * @param g2d Contesto grafico su cui avviene il disegno
	 */
	private void drawMoving(Graphics2D g2d){
		
		if(countMovements < 2){
			g2d.drawImage(animations.get("moving1_"+side),
					x + tileMap.getX(), 
					y + tileMap.getY(),
					null);
			countMovements++;
		}
		else if(countMovements < 4){
			g2d.drawImage(animations.get("moving2_"+side),
					x + tileMap.getX(), 
					y + tileMap.getY(),
					null);
			countMovements++;
		}
		else if(countMovements < 6){
			g2d.drawImage(animations.get("moving3_"+side),
					x + tileMap.getX(), 
					y + tileMap.getY(),
					null);
			countMovements++;
			if(countMovements == 5){
				countMovements = 0;
				moving = false;
				attacking = true;
			}
		}
	}

	/**
	 * Disegna l'elemento nello stato di attacco
	 * @param g2d Contesto grafico su cui avviene il disegno
	 */
	private void drawAttacking(Graphics2D g2d){
		
		if(countMovements < 2){
			g2d.drawImage(animations.get("attacking1_"+side),
					x + tileMap.getX(), 
					y + tileMap.getY(),
					null);
			countMovements++;
		}
		else if(countMovements < 4){
			g2d.drawImage(animations.get("attacking2_"+side),
					x + tileMap.getX(), 
					y + tileMap.getY(),
					null);
			countMovements++;
		}
		else if(countMovements < 6){
			g2d.drawImage(animations.get("attacking3_"+side),
					x + tileMap.getX(), 
					y + tileMap.getY(),
					null);
			countMovements++;
		}
		else if(countMovements < 8){
			g2d.drawImage(animations.get("attacking4_"+side),
					x + tileMap.getX(), 
					y + tileMap.getY(),
					null);
			countMovements++;
		}
		else if(countMovements < 10){
			g2d.drawImage(animations.get("attacking5_"+side),
					x + tileMap.getX(), 
					y + tileMap.getY(),
					null);
			countMovements++;
		}
		else if(countMovements < 12){
			g2d.drawImage(animations.get("attacking6_"+side),
					x + tileMap.getX(), 
					y + tileMap.getY(),
					null);
			countMovements++;
		}
		else if(countMovements < 14){
			g2d.drawImage(animations.get("attacking7_"+side),
					x + tileMap.getX(), 
					y + tileMap.getY(),
					null);
			countMovements++;
			
			if(countMovements == 13){
				countMovements = 0;
				attacking = false;
				moving = true;
			}
		}
	}
	
	/**
	 * Disegna la morte dell'elemento
	 * @param g2d Contesto grafico su cui avviene il disegno
	 */
	private void drawDeath(Graphics2D g2d){
		g2d.drawImage(animations.get("dying_"+side),
				x + tileMap.getX(), 
				y + 70 +tileMap.getY(),
				null);
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
							clip.close();	
					}
				
			}
		}catch(IndexOutOfBoundsException e){
			checkShots();
		}
	}
	
	/**
	 * Aggiorna lo stato dell'elemento
	 */
	public void update(){
		super.update();
		checkShots();
		if(!player.getPlayerState())
			if(clip != null)
				clip.close();
	}
	
}
