package levels;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import main.Board;

/**
 * Lista dei punteggi dei giocatori
 * 
 * @author Michele Roviello
 *
 */
public class ScoreSection extends Level {
	
	private Document document;
	private String output;
	private int x, y;
	
	public ScoreSection(LevelManager lm){
		super(lm);
	}
	
	/**
	 * Inizializza le variabili della schermata dei punteggi
	 */
	@Override
	public synchronized void init() {
		
		x = Board.WIDTH / 2;
		y = Board.HEIGHT / 2;
		
		background = (new ImageIcon(
				getClass().getResource("/background/scorebg.png"))).getImage();
		
		font = new Font("Times", Font.BOLD, 22);
		
		try {
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.parse("score/score.xml");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Element root = document.getDocumentElement();
		
		NodeList names = root.getElementsByTagName("name");
		NodeList scores = root.getElementsByTagName("score");
		
		output = "";
		for(int i=names.getLength()-1; i >= 0; i--)
			output += names.item(i).getFirstChild().getTextContent()
			+" - "+scores.item(i).getFirstChild().getTextContent()+"\n";
		
	}

	@Override
	public void update() {
	}

	/**
	 * Disegna la schermata dei punteggi
	 */
	@Override
	public  synchronized void draw(Graphics2D g2d) {
		g2d.drawImage(background, 0, 0, Board.WIDTH, Board.HEIGHT, null);
		g2d.setFont(font);
		g2d.setColor(Color.RED);
		drawString(g2d, output, x, y);
	}
	
	/**
	 * Disegna una stringa rispettando i caratteri
	 * di newline presenti
	 * @param g Elemento grafico su cui avvene il disegno
	 * @param text Stringa da disegnare 
	 * @param x Ordinata per il disegno
	 * @param y Ascissa per il disegno
	 */
	private void drawString(Graphics2D g, String text, int x, int y) {
	    for (String line : text.split("\n"))
	        g.drawString(line, x, y += g.getFontMetrics().getHeight());
	}
	
	@Override
	public boolean getState() {
		// TODO Auto-generated method stub
		return true;
	}
	
	/**
	 * Gestisce la pressione di un tasto
	 */
	@Override
	public void keyPressed(KeyEvent k) {
		if(k.getKeyCode() == KeyEvent.VK_UP)
			y -= 2;
		if(k.getKeyCode() == KeyEvent.VK_DOWN)
			y += 2;
		if(k.getKeyCode() == KeyEvent.VK_ESCAPE)
			lm.startLevel(0);
	}


	@Override
	public void keyReleased(KeyEvent k) {
	}
}
