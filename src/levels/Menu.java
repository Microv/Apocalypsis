package levels;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

import main.Board;

/**
 * Menu del gioco
 * 
 * @author Michele Roviello
 *
 */
public class Menu extends Level{

	private String[] options;
	private int currentOption;
	
	public Menu(LevelManager lm){
		super(lm);
	}
	
	/**
	 * Inizializza le variabili del menu
	 */
	@Override
	public void init() {
		background = (new ImageIcon(
				getClass().getResource("/background/menubg.png"))).getImage();
		
		font = new Font("Times", Font.BOLD, 22);
		
		options = new String[5];
		options[0] = "Easy";
		options[1] = "Medium";
		options[2] = "Hard";
		options[3] = "Scores";
		options[4] = "Quit";
		currentOption = 0;
		
	}

	@Override
	public void update() {}

	/**
	 * Disegna lo stato del menu
	 */
	@Override
	public void draw(Graphics2D g2d) {
		g2d.drawImage(background, 0, 0, Board.WIDTH, Board.HEIGHT, null);
		g2d.setFont(font);
		
		for(int i = 0; i < options.length; i++)
			if(i == currentOption){
				g2d.setColor(Color.RED);
				g2d.drawString(
						options[i], 
						Board.WIDTH / 2, 
						Board.HEIGHT / 2 + 25*i);
			}
			else{
				g2d.setColor(Color.WHITE);
				g2d.drawString(
						options[i], 
						Board.WIDTH / 2, 
						Board.HEIGHT / 2 + 25*i);
			}
		}

	@Override
	public boolean getState() {
		return true;
	}

	/**
	 * Gestisce la pressione di un tasto
	 */
	@Override
	public void keyPressed(KeyEvent k) {
		if(k.getKeyCode() == KeyEvent.VK_UP)
			if(currentOption > 0)
				currentOption--;
		if(k.getKeyCode() == KeyEvent.VK_DOWN)
			if(currentOption < options.length -1)
				currentOption++;
		if(k.getKeyCode() == KeyEvent.VK_ENTER)
			select(currentOption);
		
	}
	
	/**
	 * Effettua una scelta tra le opzioni disponibili
	 * @param currentOption Indice della scelta 
	 */
	private void select(int currentOption) {
		if(currentOption == 0){
			lm.setDifficulty(LevelManager.EASY);
			lm.startLevel(1);
		}
		if(currentOption == 1){
			lm.setDifficulty(LevelManager.MEDIUM);
			lm.startLevel(1);
		}
		if(currentOption == 2){
			lm.setDifficulty(LevelManager.HARD);
			lm.startLevel(1);
		}
		if(currentOption == 3){
			lm.startLevel(3);
		}
		if(currentOption == 4)
			System.exit(0);
	}

	@Override
	public void keyReleased(KeyEvent k) {
	}

}
