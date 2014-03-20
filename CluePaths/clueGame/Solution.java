package clueGame;

public class Solution {
	public Solution(Solution s) {
		this.room = s.room;
		this.weapon = s.weapon;
		this.person = s.person;
	}
	public Solution() {
		room = "";
		weapon = "";
		person = "";
	};
	public String room;
	public String weapon;
	public String person;
}
