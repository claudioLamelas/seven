import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Menu extends MouseAdapter{
	private Jogo jogo;
	private boolean pressed = false;
	public String numeros, estrelas;
	
	public Menu (Jogo jogo) {
		this.jogo = jogo;
	}

	public void mousePressed (MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		
		if (jogo.estado == EstadoJogo.menu) {
			
			//Botão euromilhões
			if (jogo.checkBounds (mx, my, 265, 150, 240, 70)) {
				jogo.estado = EstadoJogo.euromilhoes;
			}
		}
		
	}
	
	public void mouseReleased (MouseEvent e) {
		
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		Font fnt1 = new Font ("Arial", 1, 70);
		Font fnt2 = new Font ("TimesRoman", 2, 28);
		
		g.setColor(Color.black);
		g.fillRect(0, 0, jogo.WIDTH, jogo.HEIGHT);
		
		g.setFont(fnt1);
		g.setColor(Color.yellow);
		g.drawString("$EVEN.", 265, 70);
		
		g.setColor(Color.white);
		g.drawRect(265, 150, 240, 70); //botão euromilhoes
		g.drawRect(265, 250, 240, 70); //
		g.drawRect(265, 350, 240, 70); //
		
		g.setFont(fnt2);
		g.drawString("Euromilhões", 310, 195);
		//outros botões a implementar
	}
	
	public boolean pressed () {
		return pressed;
	}
}
