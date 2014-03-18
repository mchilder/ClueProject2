package clueGame;

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

	// public void draw() {}
}
