import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ButtonsPanel extends JPanel {
	public ButtonsPanel() {
		//setLayout(new GridLayout(1,3));
		JButton nextPlayerButton = new JButton("Next Player");
		JButton accusationButton = new JButton("Make An Accusation");
		nextPlayerButton.setSize(200, 50);
		accusationButton.setSize(200, 50);
		add(new WhoseTurn(), BorderLayout.WEST);
		add(nextPlayerButton, BorderLayout.CENTER);
		add(accusationButton, BorderLayout.EAST);
	}
}
