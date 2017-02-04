package entity;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.ImageIcon;

import main.Board;
import main.Sound;
import map.TileMap;

public class Player extends MapObject{

	private boolean shooting, falling;
	private int jumpSpeed, fallSpeed, maxSpeed;
	
	private int xOffset, yOffset;
	
	private List<Shot> shots;
	
	private int life, bullets;
	
	private int score;
	
	public Player(TileMap tm){
		super(tm);
		
		xOffset = - 20;
		yOffset = - 126 + 50;
		dy += fallSpeed;
		
		moveSpeed = 3;
		maxSpeed = 30;
		jumpSpeed = - 15;//- 15;
		fallSpeed = 2;
		
		rightSide = true;
		
		shots = new ArrayList<Shot>();
		
		bullets = 50;
		
		score = 0;
	}
	
	/**
	 * Imposta la mappa del livello
	 * @param tm Mappa del livello
	 */
	public void setTileMap(TileMap tm){
		tileMap = tm;
		tileSize = tm.getTileSize();
	}
	
	/**
	 * Inizializza le animazioni dell'elemento
	 */
	protected void initAnimations(){
		
		width = 60;
		height = 126;
		
		animations = new HashMap<String, Image>();
		
		// right movement
		animations.put("static_right",
				(new ImageIcon(
						getClass().getResource(
								"/soldier/static/r_static_soldier.png"))).getImage());
		animations.put("moving1_right",
				(new ImageIcon(
						getClass().getResource(
								"/soldier/moving/moving1_soldier.png"))).getImage());
		animations.put("moving2_right",
				(new ImageIcon(
						getClass().getResource(
								"/soldier/moving/moving2_soldier.png"))).getImage());
		animations.put("moving3_right",
				(new ImageIcon(
						getClass().getResource(
								"/soldier/moving/moving3_soldier.png"))).getImage());
		
		// left movement
		animations.put("static_left",
				(new ImageIcon(
						getClass().getResource(
								"/soldier/static/l_static_soldier.png"))).getImage());
		animations.put("moving1_left",
				(new ImageIcon(
						getClass().getResource(
								"/soldier/moving/moving4_soldier.png"))).getImage());
		animations.put("moving2_left",
				(new ImageIcon(
						getClass().getResource(
								"/soldier/moving/moving5_soldier.png"))).getImage());
		animations.put("moving3_left",
				(new ImageIcon(
						getClass().getResource(
								"/soldier/moving/moving6_soldier.png"))).getImage());
	
		// right shooting
		animations.put("shooting1_right",
				(new ImageIcon(
						getClass().getResource(
								"/soldier/shooting/shooting1_soldier.png"))).getImage());
		animations.put("shooting2_right",
				(new ImageIcon(
						getClass().getResource(
								"/soldier/shooting/shooting2_soldier.png"))).getImage());
		animations.put("shooting3_right",
				(new ImageIcon(
						getClass().getResource(
								"/soldier/shooting/shooting3_soldier.png"))).getImage());
		
		// left shooting
		animations.put("shooting1_left",
				(new ImageIcon(
						getClass().getResource(
								"/soldier/shooting/shooting4_soldier.png"))).getImage());
		animations.put("shooting2_left",
				(new ImageIcon(
						getClass().getResource(
								"/soldier/shooting/shooting5_soldier.png"))).getImage());
		animations.put("shooting3_left",
				(new ImageIcon(
						getClass().getResource(
								"/soldier/shooting/shooting6_soldier.png"))).getImage());
		
		// right jumping
		animations.put("jumping_right",
				(new ImageIcon(
						getClass().getResource(
								"/soldier/jumping/r_jumping_soldier.png"))).getImage());
		
		// left jumping
		animations.put("jumping_left",
				(new ImageIcon(
						getClass().getResource(
								"/soldier/jumping/l_jumping_soldier.png"))).getImage());
						
	}
	
	/**
	 * Ottiene la prossima posizione
	 */
	protected void getNextPosition(){
		if(left){
			dx -= moveSpeed;
			rightSide = false;
			
			if(dx < -maxSpeed)
				dx = -maxSpeed;
		}
		else if(right){
			dx += moveSpeed;
			rightSide = true;
			
			if(dx > maxSpeed)
				dx = maxSpeed;
		}
		else
			dx = 0;
		if(shooting)
			dx /= 2;
		if(falling)
			dx -= dx/10; 
		if(up && !falling){
			dy = jumpSpeed;
			falling = true;
		}
		if(falling){
			if(dy == 0) falling = false;
		}
		dy += fallSpeed; // legge di gravita'
	}
	
	/**
	 * Controlla la presenza di collisioni con gli elementi della mappa
	 */
	protected void checkMapCollision(){
		
		int currCol = x / tileSize; 
		int currRow = y / tileSize;
		
		if(dx < 0){
			if(tileMap.isTile(currRow, currCol - 1))
				dx = 0;
			else
				x += dx; 
		}
		if(dx > 0){
			if(tileMap.isTile(currRow, currCol + 1))
				dx = 0;
			else
				x += dx;
		}
		if(dy < 0){
			if(tileMap.isTile(currRow - height / tileSize, currCol)){
				dy = 0;
			}
			else
				y += dy;
		}
		else{
			if(dy > 0){
				if(tileMap.isTile(currRow + 1, currCol)){
					dy = 0;
					//y += tileSize / 2; 
					//falling = false;
				}
				else
					y += dy;
			}
		}
	}
	
	/**
	 * Disegna l'elemento
	 */
	public void draw(Graphics2D g2d){
		//g2d.fillOval(x + tileMap.getX(), y + tileMap.getY(), 32, 32);
		if(!shooting && !falling)
			if(dx == 0)
				drawStatic(g2d);
			else
				if(dx > 0)
					drawMovement(g2d);
				else if(dx < 0)
					drawMovement(g2d);
		if(shooting)
			drawShooting(g2d);
		if(falling && !shooting)
			drawJumping(g2d);
		
		//System.out.println("X = "+x+" Y = "+y);
	}

	/**
	 * Disegna l'elemento in movimento
	 * @param g2d Contesto grafico su cui avviene il disegno
	 */
	private void drawMovement(Graphics2D g2d) {
		
		String side = "";
		
		if(rightSide)
			side = "right";
		else
			side = "left";
		
		if(countMovements < 2)
			g2d.drawImage(animations.get("moving1_"+side),
					x  + xOffset + tileMap.getX(), 
					y  + yOffset + tileMap.getY(),
					null);
		else if(countMovements < 4)
			g2d.drawImage(animations.get("moving2_"+side),
					x  + xOffset + tileMap.getX(), 
					y  + yOffset + tileMap.getY(),
					null);
		else if(countMovements < 6){
			g2d.drawImage(animations.get("moving3_"+side),
					x  + xOffset + tileMap.getX(), 
					y  + yOffset + tileMap.getY(),
					null);
			if(countMovements == 5) countMovements = 0;
		}
		countMovements++;
	}
	
	/**
	 * Disegna l'elemento in posizione statica
	 * @param g2d Contesto grafico su cui avviene il disegno
	 */
	private void drawStatic(Graphics2D g2d) {
		if(!rightSide)
			g2d.drawImage(animations.get("static_left"),
					x + xOffset + tileMap.getX(), 
					y + yOffset + tileMap.getY(),
					null);
		else	
			g2d.drawImage(animations.get("static_right"),
						x  + xOffset + tileMap.getX(), 
						y - 126 + 55 + tileMap.getY(),
						null);
		countMovements = 0;
	}
	
	/**
	 * Disegna l'elemento mentre spara
	 * @param g2d Contesto grafico su cui avviene il disegno
	 */
	private void drawShooting(Graphics2D g2d){
		String side = "";
		if(!rightSide)
			side = "left";
		else
			side = "right";
	
		if(countMovements < 2)
			g2d.drawImage(animations.get("shooting1_"+side),
					x  + xOffset + tileMap.getX(), 
					y  + yOffset + tileMap.getY(),
					null);
		else if(countMovements < 4)
			g2d.drawImage(animations.get("shooting2_"+side),
					x  + xOffset + tileMap.getX(), 
					y  + yOffset + tileMap.getY(),
					null);
		else if(countMovements < 6){
			g2d.drawImage(animations.get("shooting3_"+side),
					x  + xOffset + tileMap.getX(), 
					y  + yOffset + tileMap.getY(),
					null);
			if(countMovements == 5) countMovements = 0;
		}
		countMovements++;
		
		
	}
	
	/**
	 * Disegna l'elemento durante il salto
	 * @param g2d Contesto grafico su cui avviene il disegno
	 */
	private void drawJumping(Graphics2D g2d){
		if(!rightSide)
			g2d.drawImage(animations.get("jumping_left"),
					x  + xOffset + tileMap.getX(), 
					y - 126 + 55 + tileMap.getY(),
					null);
		else	
			g2d.drawImage(animations.get("jumping_right"),
						x  + xOffset + tileMap.getX(), 
						y - 126 + 55 + tileMap.getY(),
						null);
	}
	
	/**
	 * Metodo per far sparare il personaggio
	 * @param b True per far sparare il personaggio, false atrimenti
	 */
	public void setShoot(boolean b){
		if(b && bullets > 0)
			shooting = b;
		else{
			shooting = false;
			if(bullets == 0)
				Sound.playSound("/drygun.wav");
		}
	}
	
	/**
	 * Provoca uno sparo
	 */
	public void shoot(){
		
		if(bullets <= 0){
			shooting = false;
			return;
		}
		
		Shot s = new Shot(tileMap, this);
		
		if(rightSide){
			s.setInitialPosition(x +60, y + yOffset + 10);
			s.setPosition(x + 60, y + yOffset + 10);
		}
		else{
			s.setInitialPosition(x - 60, y + yOffset + 10);
			s.setPosition(x - 60, y + yOffset + 10);
		}
		
		if(rightSide)
			s.setVector(1, 0);
		else
			s.setVector(-1, 0);
		
		(new Thread(s)).start();
		
		shots.add(s);
		bullets--;
	}
	
	/**
	 * Aggiorna lo stato del personaggio
	 */
	public void update(){
		super.update();
		if(shooting)
			shoot();
	}
	
	/**
	 * Restituisce la lista degli spari
	 * @return Lista degli spari
	 */
	public List<Shot> getShots(){
		return shots;
	}
	
	/**
	 * Imposta la resistenza (numero di vite) del personaggio
	 * @param life Resistenza del personaggio
	 */
	public void setLife(int life){
		this.life = life;
	}
	
	/**
	 * Imposta un danno alla resistenza del personaggio
	 */
	public synchronized void setDamage(){
		life--;
	}
	
	/**
	 * Restituisce lo stato del Player
	 * @return true se l'elemento è attivo
	 */
	public boolean getPlayerState(){
		if(life <= 0)
			isAlive = false;
		if(y > 1000)
			isAlive = false;
		return isAlive;
	}
	
	/**
	 * Restituisce la resistenza del personaggio
	 * @return Resistenza del personaggio
	 */
	public int getLife(){
		return life;
	}
	
	/**
	 * Aggiunge una quantita' alla resistenza del personaggio
	 * @param cure Quantita' da aggiungere alla resistenza del personaggio
	 */
	public synchronized void cure(int cure) {
		life += cure;
	}
	
	/**
	 * Restituisce il numero di proiettili disponibili
	 * @return Numero di proiettili disponibili
	 */
	public int getNumBullets(){
		return bullets;
	}
	
	/**
	 * Incrementa il numero di munizioni disponibili
	 * @param munitions Numero di munizioni da aggiungere
	 */
	public synchronized void addMunitions(int munitions){
		bullets += munitions;
	}
	
	/**
	 * Modifica il punteggio associato al Player
	 * @param score Punteggio
	 */
	public synchronized void modifyScore(int score){
		this.score += score;
	}
	
	/**
	 * Restituisce il punteggio associato al Player
	 * @return Punteggio 
	 */
	public int getScore(){
		return score;
	}
	
	/**
	 * Metodo di esecuzione dell'oggetto Player
	 */
	@Override
	public void run(){
		while(isAlive){
			
			update();

			try {
				Thread.sleep(Board.FPS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
