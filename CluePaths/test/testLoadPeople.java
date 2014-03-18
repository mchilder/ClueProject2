package test;

import java.util.LinkedList;
import java.util.Set;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.*;

public class testLoadPeople {
	public static ClueGame c;
	@BeforeClass
	public static void setup() {
		c = new ClueGame();
		c.loadSetupFiles();
	}
	
	@Test
	public void checkPlayers() {
		// Human
		Assert.assertEquals("Miss Scarlett", c.Human.name);
		Assert.assertEquals("red", c.Human.color);
		Assert.assertEquals(20, c.Human.StartingLocation);
		
		// First Computer
		Assert.assertEquals("Colonel Mustard", c.ComputerPlayers.get(0).name);
		Assert.assertEquals("yellow", c.ComputerPlayers.get(0).color);
		Assert.assertEquals(25, c.ComputerPlayers.get(0).StartingLocation);
		
		// Last Computer
		Assert.assertEquals("Professor Plum", c.ComputerPlayers.get(4).name);
		Assert.assertEquals("purple", c.ComputerPlayers.get(4).color);
		Assert.assertEquals(324, c.ComputerPlayers.get(4).StartingLocation);
		
		// Computer Count
		Assert.assertEquals(5, c.ComputerPlayers.size());
	}

}
