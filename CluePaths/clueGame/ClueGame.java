package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class ClueGame {
	public static List<Player> ComputerPlayers;
	public static List<String> Weapons;
	public static Player Human;
	public static Board b;
	private static String DEFAULT_NAME_FILE = "clueGame/names.txt";
	private static String DEFAULT_WEAPON_FILE = "clueGame/weapons.txt";
	private String nameConfigFile;
	private String weaponConfigFile;
	public ClueGame() {
		this.nameConfigFile = DEFAULT_NAME_FILE;
		this.weaponConfigFile = DEFAULT_WEAPON_FILE;
	}
	public ClueGame(String nameFile, String weaponFile) {
		this.nameConfigFile = nameFile;
		this.weaponConfigFile = weaponFile;
	}

	public void loadSetupFiles() {
		ComputerPlayers = new ArrayList<Player>();
		Weapons = new ArrayList<String>();
		b = new Board();
		try{
			loadPlayerConfig();
			loadWeaponsConfig();
		}
		catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void loadPlayerConfig() throws FileNotFoundException {
		FileReader reader = new FileReader(this.nameConfigFile);
	    Scanner in = new Scanner(reader);
	    int count = 0;
	    Player temp;
	    String name, color;
	    int pos;
	    while (in.hasNext()) {
	    	String line = in.nextLine();
	    	String[] inputs = line.split(",");
	    	if(count == 0) {
	    		temp = new HumanPlayer();
	    		temp.name = inputs[0];
	    		temp.color = inputs[1];
	    		temp.StartingLocation = Integer.parseInt(inputs[2]);
	    		Human = temp;
	    	} else {
	    		temp = new ComputerPlayer();
	    		temp.name = inputs[0];
	    		temp.color = inputs[1];
	    		temp.StartingLocation = Integer.parseInt(inputs[2]);
	    		ComputerPlayers.add(temp);
	    	}
	    	count=count+1;
	    }
	    in.close();
	}
	public void loadWeaponsConfig() throws FileNotFoundException {
		FileReader reader = new FileReader(this.weaponConfigFile);
	    Scanner in = new Scanner(reader);
	    while (in.hasNext()) {
	    	String next = in.nextLine();
	    	Weapons.add(next);
	    }
	    in.close();
	}
}
