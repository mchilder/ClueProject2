package clueGame;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class WalkwayCell extends BoardCell {
  public boolean isWalkway() {
    return true;
  }
  @Override
  public void draw(Graphics g, Board t) {
	  int x = (this.index%t.getNumColumns())*35;
	  int y = ( this.index - this.index%t.getNumColumns() )/t.getNumColumns()*35;
	  g.setColor(Color.BLACK);
	  g.fillRect(x, y, 50, 50);
	  g.setColor(Color.YELLOW);
	  g.fillRect(x+1, y+1, 48, 48);
  }
}
