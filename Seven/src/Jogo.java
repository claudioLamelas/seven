import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Random;

public class Jogo extends Canvas implements Runnable{
	
	private static final long serialVersionUID = 911704525175208496L;
	
	protected static final int WIDTH = 800, HEIGHT = WIDTH / 12 * 9;
	
	private Random r = new Random();
	private Thread thread;
	public Euromilhoes euromilhoes;
	private Boletim boletim;
	private boolean running = false;
	private Menu menu;
	public EstadoJogo estado = EstadoJogo.menu;
	

	public Jogo () {
		new Window (WIDTH, HEIGHT, "SEVEN.", this);
		menu = new Menu(this);
		euromilhoes = new Euromilhoes(this);
		boletim = new Boletim(this);
		this.addMouseListener(menu);
		this.addMouseListener(euromilhoes);
		this.addMouseListener(boletim);
	}
	
	public synchronized void start () {
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public synchronized void stop () {
		try {
			thread.join();
			running = false;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while(running)
        {
                    long now = System.nanoTime();
                    delta += (now - lastTime) / ns;
                    lastTime = now;
                    while(delta >=1)
                            {
                                tick();
                                delta--;
                            }
                            if(running)
                                render();
                            frames++;
                            
                            if(System.currentTimeMillis() - timer > 1000)
                            {
                                timer += 1000;
                                //System.out.println("FPS: "+ frames);
                                frames = 0;
                            }
        }
                stop();
	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		if (estado == EstadoJogo.menu) {
			menu.render(g);
		} else if (estado == EstadoJogo.euromilhoes) {
			euromilhoes.render(g);
		} else if (estado == EstadoJogo.boletim) {
			boletim.render(g);
		}
		
		g.dispose();
		bs.show();
	}

	private void tick() {
		if (estado == EstadoJogo.menu) {
			menu.tick();
		} else if (estado == EstadoJogo.euromilhoes) {
			euromilhoes.tick();
		} else if (estado == EstadoJogo.boletim) {
			boletim.tick();
		}
	}
	
	//FUNÇAO QUE PERMITE VERIFICAR SE ESTAMOS A CLICAR EM CIMA DE ALGUMA COISA CLICAVEL
	public boolean checkBounds (int mx, int my, int x, int y, int width, int height) {
		if (mx > x && mx < x + width && my > y && my < y + height) {
			return true;
		}else return false;
	}
	
	//GERA A CHAVE RESULTADO - NUMEROS E ESTRELAS
	public ArrayList<Integer> gerarChave(Tipo t) {
		ArrayList<Integer> nums = new ArrayList();
		if (t == Tipo.numeros) {
			for (int i = 0; i < 5; i++) {
				int num = r.nextInt(50) + 1;
				while (nums.contains(num)) {
					num = r.nextInt(50) + 1;
				}
				nums.add(num);
			}
			return nums;
		} 
		else {
			for (int i = 0; i < 2; i++) {
				int num = r.nextInt(12) + 1;
				while (nums.contains(num)) {
					num = r.nextInt(12) + 1;
				}
				nums.add(num);
			}
			return nums;
		}
	}
	
	private String escreveChave(StringBuilder s, ArrayList<Integer> nums) {
		for (int i = 0; i < nums.size(); i++) {
	    	if (i == nums.size() - 1) {
	    		s.append(nums.get(i));
	    	} else s.append(nums.get(i) + ", ");
	    }
		return s.toString();
	}
	
	public static void main (String [] args) {
		new Jogo();
	}
}
