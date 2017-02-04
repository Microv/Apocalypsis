package levels;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import map.TileMap;
import entity.Player;

/**
 * Gestore dei livelli del gioco
 * 
 * @author Michele Roviello
 *
 */
public class LevelManager {
	
	private List<Level> levels;
	private int currentLevel;
	public static boolean levelRunning;
	private Player player;
	private Thread playerThread;
	
	private int difficulty;
	public static final int EASY = 1;
	public static final int MEDIUM = 2;
	public static final int HARD = 3;
	
	public LevelManager(){
		levels = new ArrayList<Level>();
		currentLevel = 0;
		levels.add(new Menu(this));
		levels.add(new Level1(this));
		levels.add(new Level2(this));
		levels.add(new ScoreSection(this));
		
		levels.get(currentLevel).init();
	}
	
	/**
	 * Invoca il metodo di aggiornamento dul livello attuale
	 */
	public void update(){;
		levels.get(currentLevel).update();
	}
	
	/**
	 * Invoca il metodo di disegno sul livello attuale
	 * @param g2d Elemento grafico su cui avviene il disegno
	 */
	public void draw(Graphics2D g2d){
		levels.get(currentLevel).draw(g2d);
	}
	
	/**
	 * Controlla lo stato del livello sul livello in esecuzione
	 */
	public void checkState(){
		if(!levels.get(currentLevel).getState()){
			levelRunning = false;
			String name = JOptionPane.showInputDialog("Name");
			int score = player.getScore();
			if(name != null && !name.equals(""))
				addToScores(name, score);
			currentLevel = 0;
			levels.get(currentLevel).init();
		}
	}
	
	/**
	 * Effettua la registrazione di un nuovo punteggio 
	 * alla terminazione di un livello
	 * @param name Nome del giocatore
	 * @param score Punteggio del giocatore
	 */
	public void addToScores(String name, int score) {
		
		try{
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse("score/score.xml");
			
			Element root = document.getDocumentElement();
			Element player = document.createElement("player");
			Element playerName = document.createElement("name");
			Element playerScore = document.createElement("score");
			
			root.appendChild(player);
			player.appendChild(playerName);
			player.appendChild(playerScore);
			playerName.appendChild(document.createTextNode(name));
			playerScore.appendChild(document.createTextNode(""+score));
			
			Transformer t = TransformerFactory.newInstance().newTransformer();
			DOMSource ds = new DOMSource(document);
			
			StreamResult sr = new StreamResult(new PrintWriter("score/score.xml"));
			t.setOutputProperty(OutputKeys.INDENT, "yes");
			t.transform(ds, sr);
			
		} catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * Invoca il metodo per la gestione della pressione 
	 * di un tasto sul livello in esecuzione
	 * @param k
	 */
	public void keyPressed(KeyEvent k){
		levels.get(currentLevel).keyPressed(k);
	}
	
	/**
	 * Invoca il metodo per la gestione del rilascio 
	 * di un tasto sul livello in esecuzione
	 * @param k
	 */
	public void keyReleased(KeyEvent k){
		levels.get(currentLevel).keyReleased(k);
	}
	
	/**
	 * Avvia un livello
	 * @param level Indice del livello da avviare
	 */
	public void startLevel(int level){
		currentLevel = level;
		levelRunning = true;
		levels.get(currentLevel).init();
	}
	
	/**
	 * Avvia il prossimo livello
	 */
	public void setNextLevel(){
		levelRunning = false;
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		startLevel(currentLevel +1);
	}
	
	/**
	 * Crea un nuovo oggetto Player o ne restituisce 
	 * una istanza in caso esso gia' esista
	 * @param tm mappa da associare all'oggetto Player
	 * @return Istanza della classe Player
	 */
	public Player getPlayer(TileMap tm){

		if(currentLevel == 1) {//|| player == null || !playerThread.isAlive()){
			player = new Player(tm);
			player.setLife(50);
			playerThread = new Thread(player);
			playerThread.start();
		}
		else
			player.setTileMap(tm);
		return player;
	
	}
	
	/**
	 * Imposta la difficolta' di una partita
	 * @param difficulty Indice di difficolta'
	 */
	public void setDifficulty(int difficulty){
		this.difficulty = difficulty;
	}
	
	/**
	 * Restituisce la difficolta' di una partita
	 * @return Difficolta' di una partita
	 */
	public int getDifficulty(){
		return difficulty;
	}
}
