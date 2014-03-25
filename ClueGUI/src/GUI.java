import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class GUI extends JFrame {
	public GUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600,200);
		createLayout();	
	}
	private void createLayout() {
		//setLayout(new GridLayout(4,4));
		ButtonsPanel b = new ButtonsPanel();
		add(b, BorderLayout.NORTH);
		add(new DialogPanel("Die", "Roll"), BorderLayout.WEST);
		add(new DialogPanel("Guess", "Guess"), BorderLayout.CENTER);
		add(new DialogPanel("Guess Result", "Response"), BorderLayout.EAST);
	}
	public static void main(String[] args) {
		GUI gui = new GUI();	
		gui.setVisible(true);
	}
}
