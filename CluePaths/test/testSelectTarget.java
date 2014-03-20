package test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Set;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.*;
import clueGame.Card.CardType;

public class testSelectTarget {
	public static ClueGame c;
	@BeforeClass
	public static void setup() {
		c = new ClueGame();
		c.loadSetupFiles();
		c.loadDeck();
		c.deal();
		try {
			c.b.loadConfigFiles();
		} catch (FileNotFoundException | BadConfigFormatException e) {
			e.printStackTrace();
		}
		c.b.calcAdjacencies();
	}
	
	@Test
	public void testSelect() {
		c.ComputerPlayers.get(0).lastLocation = 5;
		c.b.calcTargets(5, 3);
		Set<BoardCell> targets = c.b.getTargets();
		Boolean door = false;
		for(BoardCell t : targets) {
			door = door || t.isDoorway();
		}
		Assert.assertTrue(door);	// Make sure the targets includes a doorway
		
		// If last location isnt the room should return index 5
		Assert.assertEquals(5, c.selectTarget(c.ComputerPlayers.get(0).lastLocation, (ArrayList<BoardCell>) targets).index);
	}

}
