package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.RoomCell;

public class boardTests {
	private Board board;
	private static final int ROOM_COUNT = 10;
	private static final int ROWS = 25;
	private static final int COLS = 15;

	@Before
	public void setup() {
		board = new Board("clueGame/layout.csv", "clueGame/room_names.txt");

		try {
			board.loadConfigFiles();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testRooms() {
		Map<Character, String> rooms = board.getRooms();
		assertEquals(ROOM_COUNT, rooms.size());
		assertEquals("Rec room", rooms.get('R'));
		assertEquals("Hall", rooms.get('H'));
		assertEquals("Ballroom", rooms.get('B'));
		assertEquals("Lounge", rooms.get('O'));
	}

	@Test
	public void testBoardDimensions() {
		assertEquals(ROWS, board.getNumRows());
		assertEquals(COLS, board.getNumColumns());
	}

	@Test
	public void testCalcIndex() {
		// If the four corners are correct, I can be fairly certain that nothing
		// has gone wrong.
		// ryan changed all calcIndexs to be calcIndex(row,col)!!
		assertEquals(0, board.calcIndex(0, 0));
		assertEquals(14, board.calcIndex(0, 14));
		assertEquals(360, board.calcIndex(24, 0));
		assertEquals(374, board.calcIndex(24, 14));
	}

	@Test
	public void testRoomInitials() {
		RoomCell cell = board.getRoomCellAt(0, 0);
		assertEquals('K', cell.getInitial());

		cell = board.getRoomCellAt(0, 8);
		assertEquals('B', cell.getInitial());

		cell = board.getRoomCellAt(0, 14);
		assertEquals('O', cell.getInitial());

		cell = board.getRoomCellAt(13, 1);
		assertEquals('C', cell.getInitial());

		cell = board.getRoomCellAt(13, 7);
		assertEquals('X', cell.getInitial());

		cell = board.getRoomCellAt(13, 14);
		assertEquals('R', cell.getInitial());

		cell = board.getRoomCellAt(24, 0);
		assertEquals('H', cell.getInitial());

		cell = board.getRoomCellAt(24, 5);
		assertEquals('L', cell.getInitial());

		cell = board.getRoomCellAt(24, 7);
		assertEquals('S', cell.getInitial());

		cell = board.getRoomCellAt(24, 10);
		assertEquals('D', cell.getInitial());
	}

	@Test
	public void testDoors() {
		RoomCell cell = (RoomCell) board.getRoomCellAt(1, 3);
		assertTrue(cell.isRoom());
		assertTrue(cell.isDoorway());
		assertEquals(RoomCell.DoorDirection.UP, cell.getDoorDirection());

	}

	@Test(expected = FileNotFoundException.class)
	public void badFile() throws BadConfigFormatException,
			FileNotFoundException {
		Board other_board = new Board("clueGame/layout_noExist.csv",
				"clueGame/room_names.txt");
		other_board.loadConfigFiles();
	}

	@Test(expected = BadConfigFormatException.class)
	public void testWrongColumns() throws BadConfigFormatException,
			FileNotFoundException {
		Board other_board = new Board("clueGame/bad_layout.csv",
				"clueGame/room_names.txt");
		other_board.loadConfigFiles();
	}

	@Test(expected = BadConfigFormatException.class)
	public void testWrongRoom() throws BadConfigFormatException,
			FileNotFoundException {
		Board other_board = new Board("clueGame/bad_layout.csv",
				"clueGame/room_names.txt");
		other_board.loadConfigFiles();
	}

	@Test(expected = BadConfigFormatException.class)
	public void testWrongRoom2() throws BadConfigFormatException,
			FileNotFoundException {
		Board other_board = new Board("clueGame/bad_layout2.csv",
				"clueGame/room_names.txt");
		other_board.loadConfigFiles();
	}

	@Test(expected = BadConfigFormatException.class)
	public void testWrongNames() throws BadConfigFormatException,
			FileNotFoundException {
		Board other_board = new Board("clueGame/layout.csv",
				"clueGame/bad_room_names.txt");
		other_board.loadConfigFiles();
	}
}
