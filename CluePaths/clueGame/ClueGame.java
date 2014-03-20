package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import clueGame.Card.CardType;

public class ClueGame {
	public static Solution s;
	public static List<Player> ComputerPlayers;
	public static List<String> Weapons;
	public static List<Card> Cards;
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
		
		try {
			b.loadConfigFiles();
		} catch (FileNotFoundException | BadConfigFormatException e1) {
			e1.printStackTrace();
		}
		
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
	
	public void loadDeck() {
		Cards = new ArrayList<Card>();
		for(String a : Weapons) {
			Card temp = new Card();
			temp.name = a;
			temp.type = CardType.WEAPON;
			Cards.add(temp);
		}
		Map<Character, String> rooms = b.getRooms();
		for(Character key : rooms.keySet()) {
			if(key!='X') {
				Card temp = new Card();
				temp.name = rooms.get(key);
				temp.type = CardType.ROOM;
				Cards.add(temp);
			}
		}
		for(Player a : ComputerPlayers) {
			Card temp = new Card();
			temp.name = a.name;
			temp.type = CardType.PERSON;
			Cards.add(temp);
		}
		Card temp = new Card();
		temp.name = Human.name;
		temp.type = CardType.PERSON;
		Cards.add(temp);
		Collections.shuffle(Cards);
	}
	
	public void deal() {
		s = new Solution();
		Boolean p=false,r=false,w=false;
		for(Player a : ComputerPlayers)
			a.myCards = new ArrayList<Card>();
		Human.myCards = new ArrayList<Card>();
		
		int index = 0;
		for(Card a : Cards) {
			if(a.type == CardType.ROOM && !r) {
				s.room = a.name;
				r = true;
			} else if(a.type == CardType.WEAPON && !w) {
				s.weapon = a.name;
				w = true;
			} else if(a.type == CardType.PERSON && !p) {
				s.person = a.name;
				p = true;
			} else {
				if(index%6 == 5)
					Human.myCards.add(a);
				else
					ComputerPlayers.get(index%6).myCards.add(a);
				index++;
			}
		}
	}
	
	public Card disproveSuggestion(Solution s1, int turn) {
		for(int i = turn+1; i < turn + 6; i ++) {
			int check = i % 6;
			if(check == 5) {
				Collections.shuffle(Human.myCards);
				for(Card c : Human.myCards) {
					if(c.name.equals(s1.person) || c.name.equals(s1.room) || c.name.equals(s1.weapon))
						return c;
				}
			} else {
				Collections.shuffle(ComputerPlayers.get(check).myCards);
				for(Card c : ComputerPlayers.get(check).myCards) {
					if(c.name.equals(s1.person) || c.name.equals(s1.room) || c.name.equals(s1.weapon))
						return c;
				}
			}
		}
		return new Card();
	}
	
	public BoardCell selectTarget(int lastLocation, ArrayList<BoardCell> targets) {
		Random r = new Random();
		ArrayList<BoardCell> doors = new ArrayList<BoardCell>();
		for(BoardCell t : targets) {
			if(t.isDoorway() && t.index != lastLocation)
				doors.add(t);
		}
		if(doors.size() == 1)
			return doors.get(0);
		if(doors.size() > 1) {
			return doors.get(r.nextInt(doors.size()));
		}
		return targets.get(r.nextInt(targets.size()));
	}
	
	public Boolean makeAccusation(Solution check) {
		return (check.person == s.person && check.weapon == s.weapon && check.room == s.room);
	}
}
