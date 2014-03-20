package test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.*;
import clueGame.Card.CardType;

public class ComputerMakeSuggestion {
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
	public void test1() {
		Solution s = new Solution(c.s);
		c.ComputerPlayers.get(0).seenCards=new ArrayList<Card>();
		for(Card c1 : c.Cards) {
			if(!c1.name.equals(s.person) && !c1.name.equals(s.weapon) && !c1.name.equals(s.room))
				c.ComputerPlayers.get(0).seenCards.add(c1);
		}
		// Everything but the correct solution has been seen
		Solution s2 = c.makeSuggestion(0);
		Assert.assertTrue(s.person.equals(s2.person) && s.weapon.equals(s2.weapon) && s.room.equals(s2.room));
	}
	
	@Test
	public void test2() {
		Solution s = new Solution(c.s);
		c.ComputerPlayers.get(0).seenCards=new ArrayList<Card>();
		String otherPerson = "";
		Boolean foundCard = false;
		for(Card c1 : c.Cards) {
			if(!c1.name.equals(s.person) && !c1.name.equals(s.weapon) && !c1.name.equals(s.room)) {
				if(c1.type == CardType.PERSON && !foundCard) {
					otherPerson = c1.name;
					foundCard = true;
				}
				else
					c.ComputerPlayers.get(0).seenCards.add(c1);
			}
		}
		// Everything but the correct solution and 1 other name has been seen
		int count1 = 0, count2 = 0;
		for(int i = 0; i < 20; i++) {
			Solution s2 = c.makeSuggestion(0);
			if(s2.person.equals(s.person))
				count1 = count1+1;
			else if(s2.person.equals(otherPerson))
				count2 = count2+1;
		}
		Assert.assertTrue(count1>5);
		Assert.assertTrue(count2>5);
	}
}
