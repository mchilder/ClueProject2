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

public class testDisproveSuggestion {
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
	public void queriedInOrder() {
		Solution s = new Solution(c.s);
		Card c1 = new Card();
		Card c2 = new Card();
		c1 = c.ComputerPlayers.get(0).myCards.get(0);
		for(int i = 2; i < 5; i++) {
			for(Card c3 : c.ComputerPlayers.get(i).myCards) {
				if(c3.type != c1.type) {
					c2 = c3;
					break;
				}
			}
		}
		if(c1.type == CardType.PERSON)
			s.person = c1.name;
		if(c1.type == CardType.ROOM)
			s.room = c1.name;
		if(c1.type == CardType.WEAPON)
			s.weapon = c1.name;
		if(c2.type == CardType.PERSON)
			s.person = c2.name;
		if(c2.type == CardType.ROOM)
			s.room = c2.name;
		if(c2.type == CardType.WEAPON)
			s.weapon = c2.name;
		
		Assert.assertTrue(c.disproveSuggestion(s, 5).name == c1.name);	// Player 5 sees Player 0's card
		Assert.assertTrue(c.disproveSuggestion(s, 1).name == c2.name);	// Player 1 sees other players card
		
		
	}
	
	
	
	
	
	@Test 
	public void multiplePossibilities() {
		Solution s = new Solution(c.s);
		// Find first player with atleast 2 types of cards
		List<Card> cards;
		Card c1 = new Card();
		Card c2 = new Card();
		int turn = 0;
		for(int i = 0; i < 5; i++) {
			cards = c.ComputerPlayers.get(i).myCards;
			turn = i;
			if(cards.get(0).type != cards.get(1).type) {
				c1 = cards.get(0);
				c2 = cards.get(1);
				break;
			}
			if(cards.get(1).type != cards.get(2).type) {
				c1 = cards.get(1);
				c2 = cards.get(2);
				break;
			}
			if(cards.get(0).type != cards.get(2).type) {
				c1 = cards.get(0);
				c2 = cards.get(2);
				break;
			}
		}
		if(c1.type == CardType.PERSON)
			s.person = c1.name;
		if(c1.type == CardType.ROOM)
			s.room = c1.name;
		if(c1.type == CardType.WEAPON)
			s.weapon = c1.name;
		if(c2.type == CardType.PERSON)
			s.person = c2.name;
		if(c2.type == CardType.ROOM)
			s.room = c2.name;
		if(c2.type == CardType.WEAPON)
			s.weapon = c2.name;
		
		int count1 = 0, count2 = 0;
		
		if(turn == 0)
			turn = 5;
		else
			turn = turn -1;
		
		for(int i = 0; i < 20; i++) { // Run test 20 times
			if(c.disproveSuggestion(s, turn).name == c1.name)
				count1 = count1+1;
			if(c.disproveSuggestion(s, turn).name == c2.name)
				count2 = count2+1;
		}
		Assert.assertTrue(count1 > 5);	// Both options selected more than 5 times
		Assert.assertTrue(count2 > 5);
	}
	
	
	
	
	@Test
	public void disproveSuggestion() {
		// Test that a returns only possible card ( assuming its not in accuser's hand )
		Solution s = new Solution(c.s);
		if(s.room.equals("Ballroom"))
			s.room = "Library";
		else
			s.room = "Ballroom";
		int index_with_answer = -1;
		for(int i = 0; i < c.ComputerPlayers.size(); i++) {
			for(Card x : c.ComputerPlayers.get(i).myCards) {
				if(x.name.equals(s.room)) {
					index_with_answer = i;
				}
			}
		}
		for(Card x : c.Human.myCards) {
			if(x.name.equals(s.room))
				index_with_answer = 5;
		}
		
		// Make sure the correct answer is returned unless the player whose turn it is has it
		for(int i = 0; i < 6; i++) {
			if(index_with_answer != i)
				Assert.assertEquals(s.room, c.disproveSuggestion(s, i).name);
			else
				Assert.assertEquals("", c.disproveSuggestion(s, i).name);
		}
	}
}
