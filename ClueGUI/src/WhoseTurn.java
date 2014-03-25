import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class WhoseTurn extends JPanel {
	public WhoseTurn() {
		setLayout(new GridLayout(2,1));
		JLabel nameLabel = new JLabel("Whose Turn?");
		JTextField name = new JTextField(20);
		add(nameLabel, BorderLayout.NORTH);
		add(name, BorderLayout.CENTER);
	}
}
