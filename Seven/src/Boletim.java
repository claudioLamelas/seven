import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Boletim extends MouseAdapter {

	private final int WIDTH = 40;
	private final int HEIGHT = 35;
	
	private Jogo jogo;
	private Euromilhoes euromilhoes;
	
	public ArrayList<Integer> nums = new ArrayList<Integer>();
	public ArrayList<Integer> estrelas = new ArrayList<Integer>();
	private Map<Rectangle, Integer> mNum;
	private Map<Rectangle, Integer> mEstrelas;
	
	public Boletim(Jogo jogo) {
		this.jogo = jogo;
		euromilhoes = jogo.euromilhoes;
		mNum = new HashMap<Rectangle, Integer>(50, 1);
		mEstrelas = new HashMap<Rectangle, Integer>(12, 1);
	}
	
	public void mousePressed (MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		
		if (jogo.estado == EstadoJogo.boletim) {
			
			//boletim dos numeros
			if (jogo.euromilhoes.count == 1) {
				if (jogo.checkBounds(mx, my, 70, 150, 200, 350)) {
					int v = mNum.get(wichRect(mx, my, mNum));
					if (nums.size() < 5 && !nums.contains(v)) {
						nums.add(v);
					}	
				}
			}
			
			
			//boletim das estrelas
			if (jogo.checkBounds(mx, my, 380, 360, 120, 140)) {
				int v = mEstrelas.get(wichRect(mx, my, mEstrelas));
				if (estrelas.size() < 2 && !estrelas.contains(v)) {
					estrelas.add(v);
				}	
			}
			
			//botao apagar numero
			if (jogo.checkBounds(mx, my, 94, 510, 150, 35)) {
				if (nums.size() > 0) 
					nums.remove(nums.size() - 1);
			}
			
			//botao apagar estrela
			if (jogo.checkBounds(mx, my, 368, 510, 150, 35)) {
				if (estrelas.size() > 0)
					estrelas.remove(estrelas.size() - 1);
			}
			
			//botao submeter
			if (jogo.checkBounds(mx, my, 575, 360, 170, 55)) {
				if (nums.size() == 5 && estrelas.size() == 2 && euromilhoes.nAposta <= 5) {
					euromilhoes.apostas.add(new Pair<>(euromilhoes.sort(copia(nums)), euromilhoes.sort(copia(estrelas))));  //mete no apostas os numeros e estrelas escolhidas ordenadas
					euromilhoes.nAposta++;
					nums.clear();
					estrelas.clear();
				}
			}
			
			//botao back
			if (jogo.checkBounds(mx, my, 595, 450, 150, 45)) {
				jogo.estado = EstadoJogo.euromilhoes;
				euromilhoes.count = 0;
			}
		}
	}
	
	public void tick() {
		if (euromilhoes.count == 0) euromilhoes.count++;
	}
	
	public void render (Graphics g) {
			Font fnt1 = new Font ("Arial", 1, 56);
			Font fnt2 = new Font ("Arial", 1, 22);
			
			g.setColor(Color.black);
			g.fillRect(0, 0, jogo.WIDTH, jogo.HEIGHT);
			
			g.setColor(Color.WHITE);
			g.drawString("Números - ", 535, 100);
			g.drawString("Estrelas - ", 540, 155);
			g.drawString(nums.toString(), 600, 100);   //numeros escolhidos
			g.drawString(estrelas.toString(), 600, 155);   //estrelas escohidas
			
			g.drawRect(575, 360, 170, 55);  //Submeter
			
			g.drawRect(595, 450, 150, 45);  //Back
			
			desenhaBoletim(g, 10, 5, 70, 140, mNum);
			g.drawRect(94, 510, 150, 35);   //apagar numero
				
			desenhaBoletim(g, 4, 3, 380, 350, mEstrelas);
			g.drawRect(368, 510, 150, 35);  //apagar estrela
			
			g.setFont(fnt2);
			g.drawString("Apagar", 127, 534);
			g.drawString("Apagar", 403, 534);
			g.drawString("Submeter", 610, 393);
			g.drawString("Back", 639, 480);
			
			g.setFont(fnt1);
			g.drawString("Boletim", 275, 70);
	}
	
	private void desenhaBoletim (Graphics g, int nRows, int nCols, int x, int y, Map<Rectangle, Integer> m) {
		int v = 1;
		g.setColor(Color.white);
		for (int j = 1; j <= nCols; j++) {
			int tempY = y;
			for (int i = 1; i <= nRows; i++) {
				m.put(new Rectangle(x, tempY, WIDTH, HEIGHT), v);
				g.drawRect(x, tempY, WIDTH, HEIGHT);
				g.drawString("" + v, x + (WIDTH/2) - 5, tempY +  (HEIGHT/2) + 4);
				tempY += HEIGHT;
				v++;
			}
			x += WIDTH;
		}
	}
	
	private Rectangle wichRect (int mx, int my, Map<Rectangle, Integer> m) {

		Set<Rectangle> s = m.keySet();
		for (Rectangle r : s) {
			if (r.contains(mx, my)) {
				return r;
			}
		}
		return null;
	}
	
	private ArrayList<Integer> copia (ArrayList<Integer> n) {
		ArrayList<Integer> novo = new ArrayList<Integer>();
		for (int i = 0; i < n.size(); i++) {
			novo.add(n.get(i));
		}
		return novo;
	}
}

