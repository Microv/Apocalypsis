package levels;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;

import entity.Elevator;
import entity.Kit;
import entity.Munition;
import entity.Shot;
import entity.Zombie;
import main.Board;
import main.Sound;
import map.Level1Map;

/**
 * Primo livello del gioco 
 * 
 * @author Michele Roviello
 *
 */
public class Level1 extends Level {
	
	
	private List<Zombie> zombies;
	private List<Kit> kits;
	private List<Munition> munitions;
	
	private Elevator elevator;
	private Thread elevatorThread;
	
	public Level1(LevelManager lm){
		super(lm);
		
		//init();
	}
	
	/**
	 * Inizializza le variabili del livello
	 */
	@Override
	public synchronized void init() {
		
		// background
		background = (new ImageIcon(
				getClass().getResource("/background/level1.png"))).getImage();
		
		// map
		tilemap = new Level1Map(32);
		tilemap.loadTiles();
		tilemap.loadMap();
		
		
		// player
		player = lm.getPlayer(tilemap);
		player.setTileMap(tilemap);
		player.setPosition(100, 300);
		//player.setVector(10, 0);
		
		// zombies
		zombies = new ArrayList<Zombie>();
		addZombie(500, 270);
		addZombie(1095, 324);
		addZombie(1333, 324);
		addZombie(2526, 359);
		addZombie(3270, 359);
		addZombie(2818, 359);
		addZombie(3072, 359);
		addZombie(4744, 232);
		addZombie(4590, 232);
		addZombie(4428, 232);
		addZombie(8170, 354);
		
		// kits
		kits = new ArrayList<Kit>();
		addMedicalKit(1750, 250);
		addMedicalKit(3404, 317);
		addMedicalKit(5334, 245);
		addMedicalKit(5698, 245);
		addMedicalKit(6336, 156);
		
		// munitions
		munitions = new ArrayList<Munition>();
		addMunitions(1572, 280);
		addMunitions(3510, 277);
		addMunitions(3604, 277);
		addMunitions(5426, 245);
		addMunitions(5516, 245);
		addMunitions(5596, 245);
		
		// font
		font = new Font("Times", Font.BOLD, 22);
		
		// sound
		//Sound.loopSound("sound/Horror_Ambiance.wav");
		clip = Sound.getClip("/Horror_Ambiance.wav");
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		
		// elevator
		elevator = new Elevator(tilemap);
		elevator.setPosition(8640, 280);
		elevator.setPlayer(player);
		elevatorThread = new Thread(elevator);
		elevatorThread.start();
	}
	
	/**
	 * Aggiunge uno zombie al livello
	 * @param x Ordinata dell'angono in alto a sinistra dell'elemento
	 * @param y Ascissa dell'angono in alto a sinistra dell'elemento
	 */
	private void addZombie(int x, int y){
		Zombie zombie = new Zombie(tilemap);
		zombie.setPosition(x, y);
		zombie.setShots(player.getShots());
		zombie.setPlayer(player);
		if(lm.getDifficulty() == LevelManager.EASY)
			zombie.setLife(5);
		else if(lm.getDifficulty() == LevelManager.MEDIUM)
			zombie.setLife(10);
		else if(lm.getDifficulty() == LevelManager.HARD)
			zombie.setLife(20);
		(new Thread(zombie)).start();
		zombies.add(zombie);
	}
	
	/**
	 * Aggiunge un kit medico al livello
	 * @param x Ordinata dell'angono in alto a sinistra dell'elemento
	 * @param y Ascissa dell'angono in alto a sinistra dell'elemento
	 */
	private void addMedicalKit(int x, int y){
		Kit kit = new Kit(tilemap, kits);
		kit.setPosition(x, y);
		kit.setPlayer(player);
		(new Thread(kit)).start();
		kits.add(kit);
	}
	
	/**
	 * Aggiunge una scorta di munizioni al livello
	 * @param x Ordinata dell'angono in alto a sinistra dell'elemento
	 * @param y Ascissa dell'angono in alto a sinistra dell'elemento
	 */
	private void addMunitions(int x, int y){
		Munition m = new Munition(tilemap, munitions);
		m.setPosition(x, y);
		m.setPlayer(player);
		(new Thread(m)).start();
		munitions.add(m);
	}
	
	/**
	 * Aggiorna lo stato degli elementi del livello
	 */
	@Override
	public synchronized void update() {
		//tilemap.setPosition(-player.getX(), 0);
		tilemap.setPosition(Board.WIDTH / 2  - player.getX(), 0);
	}


	/**
	 * Disegna gli elementi del livello
	 */
	@Override
	public void draw(Graphics2D g2d) {
	
		g2d.drawImage(background, 
						0, 
						0, 
						Board.WIDTH * Board.SCALE, 
						Board.HEIGHT * Board.SCALE,
						null);
		tilemap.draw(g2d);
		player.draw(g2d);
		
		for(int i = 0; i < zombies.size(); i++)
			zombies.get(i).draw(g2d);
		
		for(int i = 0; i < kits.size(); i++)
			kits.get(i).draw(g2d);
		
		for(int i = 0; i < munitions.size(); i++)
			munitions.get(i).draw(g2d);
		
		List<Shot> shots = player.getShots();
		for(int i = 0; i < shots.size(); i++)
			shots.get(i).draw(g2d);
		
		elevator.draw(g2d);
		
		if(!player.getPlayerState()){
			Image gameover = (new ImageIcon(
					getClass().getResource("/background/gover.png"))).getImage();
			g2d.drawImage(gameover, 
							0, 
							0, 
							Board.WIDTH * Board.SCALE, 
							Board.HEIGHT * Board.SCALE,
							null);
		}
		
		g2d.setFont(font);
		g2d.setColor(Color.RED);
		g2d.drawString("Life "+player.getLife(), 20, 30);
		g2d.drawString("Bullets "+player.getNumBullets(), 20, 60);
		g2d.drawString("Score "+player.getScore(), Board.WIDTH - 200, 30);
	}
	
	/**
	 * Restituisce lo stato del livello
	 */
	public synchronized boolean getState(){
		if(!player.getPlayerState())
			clip.stop();
		if(!elevatorThread.isAlive()){
			clip.stop();
			lm.setNextLevel();
		}
		return player.getPlayerState();
	}
	
	

}
