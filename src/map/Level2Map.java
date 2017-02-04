package map;

import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * Classe per la creazione della mappa 
 * del primo livello del gioco
 * 
 * @author Michele Roviello
 *
 */
public class Level2Map extends TileMap {
	
	public Level2Map(int tileSize) {
		super(tileSize);
		
		path = "/level2.txt";
	}
	
	/**
	 * Inizializza un array con le immagini degli elementi 
	 * della mappa del primo livello
	 */
	public void loadTiles(){
		tileSet = new Image[20];
		tileSet[1] = (new ImageIcon(
				getClass().getResource("/tiles/level1/l_brick.png"))).getImage();
		tileSet[3] = (new ImageIcon(
				getClass().getResource("/tiles/level1/r_brick.png"))).getImage();
		tileSet[4] = (new ImageIcon(
				getClass().getResource("/tiles/level1/bl_floor.png"))).getImage();
		tileSet[7] = (new ImageIcon(
				getClass().getResource("/tiles/level1/c_floor.png"))).getImage();
		tileSet[8] = (new ImageIcon(
				getClass().getResource("/tiles/level1/l_floor.png"))).getImage();
		tileSet[11] = (new ImageIcon(
				getClass().getResource("/tiles/level1/c_brick.png"))).getImage();
		tileSet[13] = (new ImageIcon(
				getClass().getResource("/tiles/level1/r_floor.png"))).getImage();
		tileSet[14] = (new ImageIcon(
				getClass().getResource("/tiles/level1/bb_floor.png"))).getImage();
		tileSet[15] = (new ImageIcon(
				getClass().getResource("/tiles/level1/roof.png"))).getImage();
	}
	
	

}
