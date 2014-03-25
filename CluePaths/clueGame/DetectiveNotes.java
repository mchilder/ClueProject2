package clueGame;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class DetectiveNotes extends JDialog{
	public DetectiveNotes(ArrayList<String> People, ArrayList<String> Rooms, ArrayList<String> Weapons) {
		setTitle("Detective Notes");
		setSize(600, 600);
		setLayout(new GridLayout(3, 2));
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		add(new checks(People, "People"));
		add(new comboBoxes(People, "Person Guess"));
		add(new checks(Rooms, "Rooms"));
		add(new comboBoxes(Rooms, "Room Guess"));
		add(new checks(Weapons, "Weapons"));
		add(new comboBoxes(Weapons, "Weapon Guess"));
	}
	
	
	public class checks extends JPanel {
		public checks(ArrayList<String> choices, String name)
		{
			for(String a : choices) {
				JCheckBox temp = new JCheckBox(a);
				add(temp);
			}
			setBorder(new TitledBorder (new EtchedBorder(), name));
			setLayout(new GridLayout(0, 2));
		}
	}
	public class comboBoxes extends JPanel {
		public comboBoxes(ArrayList<String> choices, String name)
		{
			JComboBox<String> combo = new JComboBox<String>();
			combo.addItem("Unsure");
			for(String a : choices) {
				combo.addItem(a);
			}
			add(combo);
			setBorder(new TitledBorder (new EtchedBorder(), name));
		}
	}
}
