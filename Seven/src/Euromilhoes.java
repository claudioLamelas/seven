import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JLabel;

public class Euromilhoes extends MouseAdapter{

	private Jogo jogo;
	private boolean pressed = false;
	public ArrayList<Integer> numeros;
	public ArrayList<Integer> estrelas;
	private Boletim boletim;
	public ArrayList <Pair<ArrayList<Integer>, ArrayList<Integer>>> apostas;
	public int nAposta;
	public int count = 0;
	public ArrayList<Integer> acertouN, acertouE;
	
	public Euromilhoes(Jogo jogo) {
		this.jogo = jogo;
		boletim = new Boletim(jogo);
		apostas = new ArrayList<Pair<ArrayList <Integer>, ArrayList <Integer>>>(5);
		numeros = new ArrayList<Integer> ();
		estrelas = new ArrayList<Integer> ();
		nAposta = 1;
		acertouN = new ArrayList<Integer>(5);
		acertouE = new ArrayList<Integer>(2);
	}
	
	public void mousePressed (MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		
		if (jogo.estado == EstadoJogo.euromilhoes) {
			
			//Botão de escolher chave
			if (jogo.checkBounds (mx, my, 65, 450, 230, 75)) {
				pressed = false;
				jogo.estado = EstadoJogo.boletim;
			}
			 
			//Para apenas funcionar quando houver pelo menos uma aposta
			if (apostas.size() > 0) {
				
				//Botão de Gerar
				if (jogo.checkBounds (mx, my, 520, 370, 230, 75)) {
					acertouN.clear();
					pressed = true;
					numeros = sort(jogo.gerarChave(Tipo.numeros));
					estrelas = sort(jogo.gerarChave(Tipo.estrelas));
					
					for (int p = 0; p < apostas.size(); p++ ) {
						int acertouNumeros = 0;
						int acertouEstrelas = 0;
						
						for (int i = 0; i < 5; i++) {
							if (apostas.get(p).getX().contains(numeros.get(i))) {
								acertouNumeros++;
							}
						}
						acertouN.add(p,acertouNumeros);
							
						for (int j = 0; j < 2; j++) {
							if (apostas.get(p).getY().contains(estrelas.get(j))) {
								acertouEstrelas++;
							}
						}
						
						acertouE.add(p, acertouEstrelas);
					}
				}
				
				//Botão de apagar aposta
				if (jogo.checkBounds(mx, my, 75, 370, 110, 45)) {
					apostas.remove(apostas.size() - 1);
					nAposta--;
				}
					
			}
			
			//Botão back
			if (jogo.checkBounds (mx, my, 600, 470, 150, 55)) {
				pressed = false;
				jogo.estado = EstadoJogo.menu;
			}
		}
	}
	
	public void tick() {

	}
	
	public void render(Graphics g) {
		Font font = new Font ("TimesRoman",Font.PLAIN, 24);
		Font font2 = g.getFont();
		g.setColor(Color.black);
		g.fillRect(0, 0, jogo.WIDTH, jogo.HEIGHT);
				
		g.setColor(Color.white);
		g.drawRect(65, 450, 230, 75); //botão de escolher chave
		g.drawRect(520, 370, 230, 75); //botão gerar chave 
		g.drawRect(600, 470, 150, 55); //botão back
	
		if (apostas.size() > 0) {
			tabelaApostas(g);
			g.setFont(font);
			g.drawString("Apagar", 93, 398);
		}
		
		if (pressed) {
			g.setColor(Color.WHITE);
			g.drawString("Nº - " + numeros.toString(), 520, 200);
			g.drawString("Estrelas - " + estrelas.toString(), 520, 240);
			g.setFont(font2);
			nCertas(g);
		}
		
		g.setFont(font);
		g.drawString("Escolhe a tua chave", 84, 493);
		g.drawString("Gerar Chave", 570, 415);
		g.drawString("Back", 650, 505);
	}
	
	private void tabelaApostas (Graphics g) {
		int y = 60;
		int posInicialN = 70;
		int posInicialE = 90;
		
		g.setColor(Color.white);
		g.drawRect(65, 50, 130, 300);  //tabela de apostas
		
		g.drawRect(75, 370, 110, 45);  //Botão de apagar aposta
		
		for (int i = 0; i < apostas.size(); i++) {
			String nums = apostas.get(i).x.toString();
			String estrelas = apostas.get(i).y.toString();
			
			g.drawString("Nº - " + nums, 70, posInicialN + (i * y));
			g.drawString("Estrelas - " + estrelas, 70, posInicialE + (i * y));
		}
	
	}
	
	private void nCertas (Graphics g) {
		int y = 60;
		int posInicialN = 70;
		int posInicialE = 90;
		
		g.setColor(Color.white);
		
		for (int i = 0; i < apostas.size(); i++) {
			g.drawString("Nºs certos - " + acertouN.get(i), 210, posInicialN + (i * y));
			g.drawString("Estrelas certas - " + acertouE.get(i), 210, posInicialE + (i * y));
		}

	}
	
	public ArrayList<Integer> sort (ArrayList<Integer> list) {                   
        for(int i=1;i<list.size();i++){
            
            int key = list.get(i);
            
            for(int j= i-1;j>=0;j--){
                if(key<list.get(j)){
                    // Shifting Each Element to its right as key is less than the existing element at current index
                    list.set(j+1,list.get(j));
                    
                    // Special case scenario when all elements are less than key, so placing key value at 0th Position
                    if(j==0){
                        list.set(0, key);
                    }
                }else{
                    // Putting Key value after element at current index as Key value is no more less than the existing element at current index
                    list.set(j+1,key);
                    break; // You need to break the loop to save un necessary iteration
                }
            }
        }
        
        return list;
	}   
    
}
