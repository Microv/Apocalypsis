package entity;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Map;

import levels.LevelManager;
import main.Board;
import map.TileMap;

public abstract class MapObject implements Runnable{
	
	protected TileMap tileMap;
	protected int tileSize;
	
	protected int x, dx;
	protected int y, dy;
	
	protected int width, height;
	
	protected int moveSpeed;
	
	protected boolean left, right, up, down;
	
	protected Map<String, Image> animations;
	protected int countMovements;
	protected boolean rightSide;
	
	protected boolean isAlive;
	
	public MapObject(TileMap tm){
		tileMap = tm;
		tileSize = tm.getTileSize();
		
		initAnimations();
		
		isAlive = true;
	}
	
	/**
	 * Inizializza le animazioni associate all'elemento
	 */
	protected abstract void initAnimations();
	
	/**
	 * Imposta la posizione dell'elemento
	 * @param x Ascissa dell'angolo in alto a sinistra del
	 * rettangolo che racchiude l'elemento 
	 * @param y Ordinata dell'angolo in alto a sinistra del
	 * rettangolo che racchiude l'elemento
	 */
	public void setPosition(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Imposta la direzione dell'elemnto
	 * @param dx Acissa della direzione dell'elemento
	 * @param dy Ordinata della direzione dell'elemento
	 */
	public void setVector(int dx, int dy){
		this.dx = dx;
		this.dy = dy;
	}
	
	/**
	 * Imposta la direzione dell'elemento a sinistra
	 * @param b true per impostare la direzione a sinistra,
	 * false altrimenti
	 */
	public void setLeft(boolean b){
		left = b; 
	}
	
	/**
	 * Imposta la direzione dell'elemento a destra
	 * @param b true per impostare la direzione a destra,
	 * false altrimenti
	 */
	public void setRight(boolean b) { 
		right = b; 
	}
	
	/**
	 * Imposta la direzione dell'elemento verso l'alto
	 * @param b true per impostare la direzione verso l'alto,
	 * false altrimenti
	 */
	public void setUp(boolean b){
		up = b; 
	}
	
	/**
	 * Imposta la direzione dell'elemento verso il basso
	 * @param b true per impostare la direzione verso il basso,
	 * false altrimenti
	 */
	public void setDown(boolean b){ 
		down = b; 
	}
	
	/**
	 * Ottiene la prossima posizione dell'elemento
	 */
	protected abstract void getNextPosition();
	
	/**
	 * Controlla la presenza di collisioni con gli elementi della mappa
	 */
	protected abstract void checkMapCollision();
	
	/**
	 * Aggiorna lo stato dell'elemento
	 */
	public void update() {
		getNextPosition();
		checkMapCollision();
		setPosition(x, y);
	}
	
	/**
	 * Disegna l'elemento
	 * @param g2d Contesto grafico su cui avviene il disegno
	 */
	public abstract void draw(Graphics2D g2d);
	
	/**
	 * Restituisce l'ascissa dell'elemento
	 * @return Ascissa dell'elemento
	 */
	public int getX(){
		return x;
	}
	
	/**
	 * Restituisce l'ordinata dell'elemento
	 * @return Ordinata dell'elemento
	 */
	public int getY(){
		return y;
	}
	
	/**
	 * Restituisce il rettangolo che racchiude l'elemento
	 * @return Rettangolo che racchiude l'elemento
	 */
	public Rectangle getBounds(){
		return new Rectangle(x, y - height, width, height);
	}
	
	/**
	 * Metodo di esecuzione delle azioni associate all'elemento
	 */
	@Override
	public void run() {
		while(isAlive && LevelManager.levelRunning){
			
			update();

			try {
				Thread.sleep(Board.FPS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//System.out.println("Thread Killed");
	}
}
