package clueGame;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class RoomCell extends BoardCell {
	public enum DoorDirection {
		UP, DOWN, LEFT, RIGHT, NONE
	};

	private DoorDirection doorDirection;
	private char initial;

	public RoomCell(String cell) {
		this.initial = cell.charAt(0);
		if (cell.length() == 2) {
			switch (cell.charAt(1)) {
			case 'U':
				this.doorDirection = RoomCell.DoorDirection.UP;
				break;
			case 'D':
				this.doorDirection = RoomCell.DoorDirection.DOWN;
				break;
			case 'L':
				this.doorDirection = RoomCell.DoorDirection.LEFT;
				break;
			case 'R':
				this.doorDirection = RoomCell.DoorDirection.RIGHT;
				break;
			case 'N':
				this.doorDirection = RoomCell.DoorDirection.NONE;
				break;
			}
		} else
			this.doorDirection = RoomCell.DoorDirection.NONE;
	}

	public boolean isRoom() {
		return true;
	}

	public boolean isDoorway() {
		return (this.doorDirection != DoorDirection.NONE);
	}

	public DoorDirection getDoorDirection() {
		return doorDirection;
	}

	public char getInitial() {
		return initial;
	}

	@Override
	public void draw(Graphics g, Board t) {
		  int x = (this.index%t.getNumColumns())*35;
		  int y = ( this.index - this.index%t.getNumColumns() )/t.getNumColumns()*35;
		  g.setColor(Color.gray);
		  g.fillRect(x, y, 50, 50);
	}
}
