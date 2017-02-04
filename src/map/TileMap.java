package map;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Scanner;

/**
 * Classe astratta che definisce ed implementa 
 * alcuni metodi per la gestione delle mappe dei livelli
 * 
 * @author Michele Roviello
 *
 */
public abstract class TileMap {
	
	protected int tileSize ;
	protected Image[] tileSet;
	protected int map[][];
	protected int numCols;
	protected int numRows;
	protected String path;
	
	private int x;
	private int y;
	
	public TileMap(int tileSize) {
		this.tileSize = tileSize;
	}
	
	/**
	 * Carica in memoria le singole immagini per costruire la mappa
	 */
	public abstract void loadTiles();
	
	/**
	 * Crea una matrice di interi associata ad una mappa
	 * presente in un file di testo 
	 */
	public void loadMap() {
		
		//File mapFile;
		Scanner in; 
		
		try{
			
			//mapFile = new File(path);
			in = new Scanner(getClass().getResource(path).openStream());
			
			numCols = Integer.parseInt(in.nextLine());
			numRows = Integer.parseInt(in.nextLine());
			map = new int[numRows][numCols];
			
			String delims = "\\s+";

			int row = 0; 
			int col = 0;
			
			while(in.hasNextLine()){
				String line = in.nextLine();
				String[] tokens = line.split(delims);
			
				for(int i = 0; i < tokens.length; i++){
					map[row][col] = Integer.parseInt(tokens[i]);
					col++;
					if(col == numCols){
						row++;
						col = 0;
					}
				}
			}
				
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			in = null;
		}
	}
	
	/**
	 * Verifica la presenza di un elemento della mappa 
	 * in una posizione specifica
	 * @param row riga da controllare
	 * @param col colonna da controllare
	 * @return true se nella posizione specificata
	 *  è presente un elemento della mappa, false altrimenti
	 */
	public boolean isTile(int row, int col){
		try{
			return map[row][col] != 0;
		}catch(ArrayIndexOutOfBoundsException e){
			return false;
		}
	}
	
	/**
	 * Disegna la mappa associata ad una matrice
	 * @param g2d Elemento grafico su cui disegnare la mappa
	 */
	public void draw(Graphics2D g2d){
		
		for(int row = 0; row < numRows; row++){
			for(int col = 0; col < numCols; col++){
				
				if(map[row][col] == 0) continue;
				
				g2d.drawImage(tileSet[map[row][col]],
						x + col*tileSize,
						y + row*tileSize,
						tileSize, tileSize, null);	
			}
		}	
		
	}
	
	/**
	 * Restituisce la dimensione di un elemento della mappa
	 * @return dimensione di un elemento della mappa
	 */
	public int getTileSize(){
		return tileSize;
	}
	
	/**
	 * Imposta la posizione della mappa
	 * @param x Ordinata dell'angolo in alto a sinistra
	 * @param y Ascissa dell'angolo in alto a sinistra
	 */
	public void setPosition(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Restituisce l'ordinata dell'angolo in alto 
	 * a sinistra della mappa
	 * @return Ordinata dell'angolo in alto a sinistra
	 */
	public int getX(){
		return x;
	}
	
	/**
	 * Restituisce l'ascissa dell'angolo in alto 
	 * a sinistra della mappa
	 * @return Ascissa dell'angolo in alto a sinistra
	 */
	public int getY(){
		return y;
	}
}
