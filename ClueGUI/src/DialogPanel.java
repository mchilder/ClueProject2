import java.awt.BorderLayout;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class DialogPanel extends JPanel{
	public DialogPanel(String a, String b) {
		JLabel nameLabel = new JLabel(b);
		JTextField name = new JTextField(4);
		name.setEditable(false);
		add(nameLabel, BorderLayout.WEST);
		add(name, BorderLayout.EAST);
		setBorder(new TitledBorder (new EtchedBorder(), a));
	}
}
