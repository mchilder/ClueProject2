package clueGame;
public class Card {
	public Card() {
		name = "";
	}
	public enum CardType {
		ROOM, PERSON, WEAPON
	};
	public String name;
	public CardType type;
}
