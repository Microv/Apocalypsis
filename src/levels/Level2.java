package levels;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import main.Board;
import main.Sound;
import map.Level2Map;
import entity.Elevator;
import entity.Kit;
import entity.LastZombie;
import entity.Munition;
import entity.Shot;

/**
 * Secondo livello del gioco
 * 
 * @author Michele Roviello
 *
 */
public class Level2 extends Level {
	
	private LastZombie lastZombie;
	private Thread lzThread;
	private List<Kit> kits;
	private List<Munition> munitions;
	private Elevator elevator;
	private Thread elevatorThread;
	
	public Level2(LevelManager lm) {
		super(lm);
		
	}

	/**
	 * Inizializza le variabili del gioco
	 */
	@Override
	public synchronized void init() {
		
		// background
		background = (new ImageIcon(
				getClass().getResource("/background/level2.png"))).getImage();
		
		// map
		tilemap = new Level2Map(32);
		tilemap.loadTiles();
		tilemap.loadMap();
				
				
		// player
		player = lm.getPlayer(tilemap);
		player.setTileMap(tilemap);
		player.setPosition(100, 350);
		
		// last zombies
		lastZombie = new LastZombie(tilemap);
		lastZombie.setPosition(44, 290);
		lastZombie.setSide("right");
		lastZombie.setShots(player.getShots());
		lastZombie.setPlayer(player);
		if(lm.getDifficulty() == LevelManager.EASY)
			lastZombie.setLife(40);
		else if(lm.getDifficulty() == LevelManager.MEDIUM)
			lastZombie.setLife(70);
		else if(lm.getDifficulty() == LevelManager.HARD)
			lastZombie.setLife(100);
		lzThread = new Thread(lastZombie);
		lzThread.start();
		
		// kits
		kits = new ArrayList<Kit>();
		addMedicalKit(1458, 284);
		addMedicalKit(1592, 284);
		addMedicalKit(1306, 308);
		
		// munitions
		munitions = new ArrayList<Munition>();
		addMunitions(1884, 220);
		addMunitions(1994, 220);
		addMunitions(2102, 220);
		addMunitions(2200, 220);
		
		// elevator
		elevator = new Elevator(tilemap);
		elevator.setPosition(2808, 125);
		elevator.setPlayer(player);
		elevatorThread = new Thread(elevator);
		elevatorThread.start();
		
		// font
		font = new Font("Times", Font.BOLD, 22);
				
		// sound
		clip = Sound.getClip("/Hearbeat.wav");
		clip.loop(Clip.LOOP_CONTINUOUSLY);
				
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
	 * Aggiorna lo stato degli elemnti del livello
	 */
	@Override
	public synchronized void update() {
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
		
		lastZombie.draw(g2d);
		
		for(int i = 0; i < kits.size(); i++)
			kits.get(i).draw(g2d);
		
		for(int i = 0; i < munitions.size(); i++)
			munitions.get(i).draw(g2d);
		
		List<Shot> shots = player.getShots();
		for(int i = 0; i < shots.size(); i++)
			shots.get(i).draw(g2d);
		
		elevator.draw(g2d);
		
		if(!player.getPlayerState()){
			Image gameover = (new ImageIcon("img/background/gover.png")).getImage();
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
	@Override
	public synchronized boolean getState() {
		if(!player.getPlayerState())
			clip.stop();
		if(!elevatorThread.isAlive() &&
				!lzThread.isAlive()){
			clip.stop();
			String name = JOptionPane.showInputDialog("Name");
			int score = player.getScore();
			if(name != null && !name.equals(""))
				lm.addToScores(name, score);
			lm.startLevel(3);
		}
		return player.getPlayerState();
	}

}
