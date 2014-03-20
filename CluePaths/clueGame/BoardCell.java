package clueGame;

public abstract class BoardCell {
  public boolean isWalkway() {
		return false;
	}

	public boolean isRoom() {
		return false;
	}

	public boolean isDoorway() {
		return false;
	}
	public int index;
	
	// public abstract void draw();
}
