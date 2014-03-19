package test;

import java.util.LinkedList;
import java.util.Set;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.*;
import clueGame.Card.CardType;

public class testAccusation {
	public static ClueGame c;
	@BeforeClass
	public static void setup() {
		c = new ClueGame();
		c.loadSetupFiles();
		c.loadDeck();
		c.deal();
	}
	
	@Test
	public void Accuse() {
		Solution right = c.s;
		
		Solution wrongPerson = new Solution();
		if(right.person == "Miss Scarlet")
			wrongPerson.person = "Professor Plum";
		else
			wrongPerson.person = "Miss Scarlet";
		wrongPerson.weapon = right.weapon;
		wrongPerson.room = right.room;
		
		
		Solution wrongRoom = new Solution();
		if(right.room == "Ballroom")
			wrongRoom.room = "Kitchen";
		else
			wrongRoom.room = "Ballroom";
		wrongRoom.weapon = right.weapon;
		wrongRoom.person = right.person;
		
		
		Solution wrongWeapon = new Solution();
		if(right.weapon == "Shank")
			wrongWeapon.weapon = "AK47";
		else
			wrongWeapon.weapon = "Shank";
		wrongWeapon.room = right.room;
		wrongWeapon.person = right.person;
		
		Assert.assertEquals(true, c.makeAccusation(right));
		Assert.assertEquals(false, c.makeAccusation(wrongPerson));
		Assert.assertEquals(false, c.makeAccusation(wrongRoom));
		Assert.assertEquals(false, c.makeAccusation(wrongWeapon));
	}

}
