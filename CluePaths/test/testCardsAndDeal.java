package test;

import java.util.LinkedList;
import java.util.Set;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.*;
import clueGame.Card.CardType;

public class testCardsAndDeal {
	public static ClueGame c;
	@BeforeClass
	public static void setup() {
		c = new ClueGame();
		c.loadSetupFiles();
		c.loadDeck();
		c.deal();
	}
	
	@Test
	public void testLoadCards() {
		// Make sure there are 21 cards
		Assert.assertEquals(21, c.Cards.size());
		int r=0,w=0,p=0;
		for(Card a : c.Cards) {
			if(a.type == CardType.PERSON)
				p=p+1;
			if(a.type == CardType.WEAPON)
				w=w+1;
			if(a.type == CardType.ROOM)
				r=r+1;
		}
		// Check for 6 player cards
		Assert.assertEquals(6, p);
		
		// Check for 6 weapon cards
		Assert.assertEquals(6, w);
		
		// Check for 9 room cards
		Assert.assertEquals(9, r);
	}
	
	@Test
	public void testDeal() {
		System.out.println(c.s.person + ", " + c.s.room + ", " + c.s.weapon);
		
		// Make sure each player has 3 cards ( ( 21 - 3 ) / 6 )
		for(Player a : c.ComputerPlayers) {
			Assert.assertEquals(3, a.myCards.size());
		}
		Assert.assertEquals(3, c.Human.myCards.size());
		
		
		
		
		// For each computer player check their card against all other dealt cards to ensure only 1 match
		// This will also assure that the Human doesnt have a card that matches a computer's card
		
		for(Player a : c.ComputerPlayers) {
			for(Card b : a.myCards) {  // For each card in each computer's hand
				int check = 0;
				for(Player a1 : c.ComputerPlayers) {
					for(Card b1 : a1.myCards) {
						if(b1.name == b.name)
							check = check + 1;	// Add one to check for each matched card
					}
				}
				for(Card b1 : c.Human.myCards) {
					if(b1.name == b.name)
						check = check + 1;	// Add one to check for each matched card
				}	
				Assert.assertEquals(1, check);
			}
		}
		
	}
	
	

}

