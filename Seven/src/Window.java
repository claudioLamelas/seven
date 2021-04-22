import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Window extends Canvas {

	private static final long serialVersionUID = -8255319694373975038L;

	public Window (int width, int height, String title, Jogo gen) {
		JFrame frame = new JFrame(title);
		
		Dimension dimension = new Dimension (width, height);
		
		frame.setPreferredSize (dimension);
		frame.setMaximumSize(dimension);
		frame.setMinimumSize(dimension);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.add(gen);
		gen.start();
	}
}
