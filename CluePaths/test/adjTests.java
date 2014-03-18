package test;

import java.util.LinkedList;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class adjTests {
  private Board board;
  private static final int ROOM_COUNT = 10;
  private static final int ROWS = 25;
  private static final int COLS = 15;
  
  @Before
  public void setup() {
    board = new Board();
    
    try {
      board.loadConfigFiles();
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    board.calcAdjacencies();
  }

  @Test
  public void testAdjacenciesInsideRooms()
  {
    LinkedList<Integer> testList = board.getAdjList(board.calcIndex(3,1));
    Assert.assertEquals(0, testList.size());
    testList = board.getAdjList(board.calcIndex(4, 8));
    Assert.assertEquals(0, testList.size());
    testList = board.getAdjList(board.calcIndex(14, 1));
    Assert.assertEquals(0, testList.size());
    testList = board.getAdjList(board.calcIndex(18, 4));
    Assert.assertEquals(0, testList.size());
    testList = board.getAdjList(board.calcIndex(19, 13));
    Assert.assertEquals(0, testList.size());
  }

  @Test
  public void testDoors() {
    LinkedList<Integer> testList = board.getAdjList(board.calcIndex(1, 3));
    Assert.assertTrue(testList.contains(board.calcIndex(0, 3)));
    
    testList = board.getAdjList(board.calcIndex(3, 12));
    Assert.assertTrue(testList.contains(board.calcIndex(3, 11)));
    
    testList = board.getAdjList(board.calcIndex(9, 0));
    Assert.assertTrue(testList.contains(board.calcIndex(10, 0)));
    
    testList = board.getAdjList(board.calcIndex(15, 3));
    Assert.assertTrue(testList.contains(board.calcIndex(15, 4)));
    
    testList = board.getAdjList(board.calcIndex(17, 12));
    Assert.assertTrue(testList.contains(board.calcIndex(18, 12)));
    
    testList = board.getAdjList(board.calcIndex(19, 4));
    Assert.assertTrue(testList.contains(board.calcIndex(20, 4)));
    
    testList = board.getAdjList(board.calcIndex(20, 11));
    Assert.assertTrue(testList.contains(board.calcIndex(19, 11)));
  }
  
  @Test
  public void testOutsideDoors() {
    LinkedList<Integer> testList = board.getAdjList(board.calcIndex(0, 3));
    Assert.assertEquals(2, testList.size());
    Assert.assertTrue(testList.contains(board.calcIndex(1, 3)));
    Assert.assertTrue(testList.contains(board.calcIndex(0, 4)));
    
    testList = board.getAdjList(board.calcIndex(3, 11));
    Assert.assertEquals(4, testList.size());
    Assert.assertTrue(testList.contains(board.calcIndex(2, 11)));
    Assert.assertTrue(testList.contains(board.calcIndex(4, 11)));
    Assert.assertTrue(testList.contains(board.calcIndex(3, 10)));
    Assert.assertTrue(testList.contains(board.calcIndex(3, 12)));
    
    testList = board.getAdjList(board.calcIndex(9, 6));
    Assert.assertEquals(4, testList.size());
    Assert.assertTrue(testList.contains(board.calcIndex(10, 6)));
    Assert.assertTrue(testList.contains(board.calcIndex(8, 6)));
    Assert.assertTrue(testList.contains(board.calcIndex(9, 5)));
    Assert.assertTrue(testList.contains(board.calcIndex(9, 7)));
    
    testList = board.getAdjList(board.calcIndex(20, 4));
    Assert.assertEquals(3, testList.size());
    Assert.assertTrue(testList.contains(board.calcIndex(20, 3)));
    Assert.assertTrue(testList.contains(board.calcIndex(19, 4)));
    Assert.assertTrue(testList.contains(board.calcIndex(21, 4)));
    
    testList = board.getAdjList(board.calcIndex(18, 12));
    Assert.assertEquals(3, testList.size());
    Assert.assertTrue(testList.contains(board.calcIndex(17, 12)));
    Assert.assertTrue(testList.contains(board.calcIndex(19, 12)));
    Assert.assertTrue(testList.contains(board.calcIndex(18, 11)));
  }
  
  @Test
  public void testEdges() {
    LinkedList<Integer> testList = board.getAdjList(board.calcIndex(0, 5));
    Assert.assertEquals(3, testList.size());
    Assert.assertTrue(testList.contains(board.calcIndex(0, 4)));
    Assert.assertTrue(testList.contains(board.calcIndex(0, 6)));
    Assert.assertTrue(testList.contains(board.calcIndex(1, 5)));
    
    testList = board.getAdjList(board.calcIndex(10, 0));
    Assert.assertEquals(2, testList.size());
    Assert.assertTrue(testList.contains(board.calcIndex(9, 0)));
    Assert.assertTrue(testList.contains(board.calcIndex(10, 1)));
    
    testList = board.getAdjList(board.calcIndex(11, 14));
    Assert.assertEquals(2, testList.size());
    Assert.assertTrue(testList.contains(board.calcIndex(11, 13)));
    Assert.assertTrue(testList.contains(board.calcIndex(12, 14)));
    
    testList = board.getAdjList(board.calcIndex(24, 6));
    Assert.assertEquals(1, testList.size());
    Assert.assertTrue(testList.contains(board.calcIndex(23, 6)));
  }
  
  @Test
  public void testBesideRoom() {
    LinkedList<Integer> testList = board.getAdjList(board.calcIndex(6, 10));
    Assert.assertEquals(3, testList.size());
    Assert.assertTrue(testList.contains(board.calcIndex(5, 10)));
    Assert.assertTrue(testList.contains(board.calcIndex(7, 10)));
    Assert.assertTrue(testList.contains(board.calcIndex(6, 11)));
    
    testList = board.getAdjList(board.calcIndex(1, 6));
    Assert.assertEquals(3, testList.size());
    Assert.assertTrue(testList.contains(board.calcIndex(0, 6)));
    Assert.assertTrue(testList.contains(board.calcIndex(1, 5)));
    Assert.assertTrue(testList.contains(board.calcIndex(2, 6)));
  }
  
  @Test
  public void testWalkwayTargets() {
    board.calcTargets(8, 5, 6);
    Set<BoardCell> targets = board.getTargets();
    Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(2, 5))));
    Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(10, 1))));
    Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(11, 8))));
    Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(13, 4))));

    board.calcTargets(12, 10, 6);
    targets = board.getTargets();
    Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(7, 11))));
    Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(10, 6))));
    Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(11, 13))));
    Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(17, 9))));

    board.calcTargets(17, 5, 6);
    targets = board.getTargets();
    Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(12, 4))));
    Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(16, 10))));
    Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(21, 3))));
    Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(22, 6))));

    board.calcTargets(20, 9, 6);
    targets = board.getTargets();
    Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(15, 10))));
    Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(17, 6))));
    Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(18, 11))));
  }
  
  @Test
  public void testTargetsIntoRooms() {
    board.calcTargets(1, 6, 6);
    Set<BoardCell> targets = board.getTargets();
    Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(1, 3))));

    board.calcTargets(6, 10, 6);
    targets = board.getTargets();
    Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(3, 12))));
  }
  
  @Test
  public void testTargetsOutOfRooms() {
    board.calcTargets(21, 2, 6);
    Set<BoardCell> targets = board.getTargets();
    Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(17, 2))));

    board.calcTargets(18, 8, 6);
    targets = board.getTargets();
    Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(18, 6))));
  }
}
