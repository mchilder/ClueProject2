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
		Set<BoardCell> targets2 = c.b.getTargets();
		Boolean door = false;
		for(BoardCell t : targets2) {
			door = door || t.isDoorway();
		}
		Assert.assertTrue(door);	// Make sure the targets includes a doorway
		// If last location isnt the room should return index 5
		
		ArrayList<BoardCell> targets = new ArrayList<BoardCell>();
		
		for(BoardCell t : targets2) {
			targets.add(t);
		}
		
		for(int i = 0; i < 10; i++) // Make sure the door is selected every time
			Assert.assertEquals(18, c.selectTarget(c.ComputerPlayers.get(0).lastLocation, targets).index);
		
		ArrayList<Integer> target_hits = new ArrayList<Integer>();
		for(Object o : targets)
			target_hits.add(0);	// Populate with 0's for each possible target
		
		c.ComputerPlayers.get(0).lastLocation = 18; // Set last location to the doorway
		
		for(int i = 0; i < 100; i++) {// Select targets 100 times
			int index = c.selectTarget(c.ComputerPlayers.get(0).lastLocation, targets).index;
			
			for(int j = 0; j < targets.size(); j++)
				if (index == (targets).get(j).index)
					target_hits.set(j, target_hits.get(j)+1);
		}
		int y = 0;
		for(int x : target_hits) {
			Assert.assertTrue(5 < x);	// Make sure each target was selected at least 5 times out of 100
		}
	}	
}
