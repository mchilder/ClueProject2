package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import clueGame.RoomCell.DoorDirection;

public class Board {
  public static final char WALKWAY_CHAR = 'W';
  private static String DEFAULT_BOARD_FILE = "clueGame/layout.csv";
  private static String DEFAULT_LEGEND_FILE = "clueGame/room_names.txt";
  
  private ArrayList<BoardCell> cells;
  private Map<Character, String> rooms;
  private Map<Integer, LinkedList<Integer>> adjList;
  private HashSet<BoardCell> current_targets;
  private boolean[] visited;
  
  private int numRows = -1;
  private int numColumns = -1;
  private String mapConfigFile;
  private String roomsConfigFile;
  
  
  public Board(){
	  this.mapConfigFile = DEFAULT_BOARD_FILE;
	  this.roomsConfigFile = DEFAULT_LEGEND_FILE;
	  this.adjList = new HashMap<Integer, LinkedList<Integer>>();
  }
 
  
  public Board(String mapConfigFile, String roomsConfigFile) {
    this.mapConfigFile = mapConfigFile;
    this.roomsConfigFile = roomsConfigFile;
    this.adjList = new HashMap<Integer, LinkedList<Integer>>();
  }
  
  public void loadConfigFiles() throws FileNotFoundException, BadConfigFormatException {
    this.cells = new ArrayList<BoardCell>();
    this.rooms = new HashMap<Character, String>();
    this.numRows = 0;

    loadRoomConfig();
    loadBoardConfig();
    
    this.visited = new boolean[this.numColumns * this.numRows];
  }
  
  public void loadRoomConfig() throws FileNotFoundException, BadConfigFormatException {
    FileReader reader = new FileReader(this.roomsConfigFile);
    Scanner in = new Scanner(reader);
    
    int lineNum = 1;
    while (in.hasNext()) {
      // Load in the data.
      String line = in.nextLine();
      String[] splitLine = line.split(",");
      splitLine[0] = splitLine[0].trim();
      
      
      // Check for improper formatting.
      if (splitLine.length != 2) {
        throw new BadConfigFormatException("Bad config file - line " + lineNum + " should have 2 items, but it has " + splitLine.length + ".");
      }
      
      // Add to the rooms definitions.
      if (splitLine[0].charAt(0) != WALKWAY_CHAR) {
        rooms.put(splitLine[0].charAt(0), splitLine[1].trim());
      }
      
      lineNum += 1;
    }
  }
  
  public void loadBoardConfig() throws FileNotFoundException, BadConfigFormatException {
    FileReader reader = new FileReader(this.mapConfigFile);
    Scanner in = new Scanner(reader);

    int numDoors = 0;
    int lineNum = 1;
    while (in.hasNext()) {
      // Load in the line and split it.
      String line = in.nextLine();
      String[] chars = line.split(",");
      
      // Check for row length data. If there is a mismatch, throw an exception.
      if (this.numColumns == -1) {
        this.numColumns = chars.length;
      } else if (this.numColumns != chars.length) {
        throw new BadConfigFormatException("Bad config file - row " + lineNum + " does not have the same number of cells as the other rows.");
      }
      this.numRows += 1;
      
      for (String s : chars) {
        if ((s.length() == 2)&&(s.charAt(1) != 'N')){
    		  numDoors++;
    	  }
    	  
    	  if (this.rooms.containsKey(s.charAt(0))) {
              this.cells.add(new RoomCell(s));
           } else if (s.charAt(0) == WALKWAY_CHAR){
              this.cells.add(new WalkwayCell());
           }else throw new BadConfigFormatException("Bad config file: an unrecognized room "+ s +
        		   "was attempted to load into rooms");
      }
      lineNum += 1;
    }
    in.close();
  }
  
  
  //The profs calcIndex is the same formula, but the inputs are opposite
  //like hers is calcIndex(int y, int x)
  public int calcIndex(int row, int col) {
    return row * this.numColumns + col;
  }
  
  public ArrayList<BoardCell> getCells() {
    return this.cells;
  }
  
  public Map<Character, String> getRooms() {
    return this.rooms;
  }
  
  public BoardCell getCellAt(int id) {
    return this.cells.get(id);
  }
  
  public RoomCell getRoomCellAt(int row, int col) {
	  if (this.cells.get(calcIndex(row,col)).isRoom()){
		  return (RoomCell) this.cells.get(calcIndex(row,col));
	  }else return new RoomCell(" ");
  }
  
  public RoomCell getRoomCellAt(int index) {
	  if (this.cells.get(index).isRoom()){
		  return (RoomCell) this.cells.get(index);
	  }else return new RoomCell(" ");
  }
  
  public int getNumRows() {
    return this.numRows;
  }
  
  public int getNumColumns() {
    return this.numColumns;
  }
  
  public void calcAdjacencies(){
	  int[][] deltas = {{-1, 0, 0, 1}, {0, 1, -1, 0,}};
	    // Loop through every cell in the map.
	    for (int y = 0; y < this.numRows; y += 1) {
	    	for (int x = 0; x < this.numColumns; x += 1) {
	    		this.adjList.put(this.calcIndex(y, x), new LinkedList<Integer>());
	    		//check if cell in question is a doorway, if so, the only adjacency added is the 
	    		// walkway adjacency in the direction of doorDirection of cell in question
	    		if (this.getCellAt(this.calcIndex(y, x)).isDoorway()){
	    			DoorDirection dd = this.getRoomCellAt(y,x).getDoorDirection();
	    			switch(dd) {
		                case UP: this.adjList.get(this.calcIndex(y, x)).add(this.calcIndex(y-1, x)); break;
		                case RIGHT: this.adjList.get(this.calcIndex(y, x)).add(this.calcIndex(y, x+1)); break;
		                case DOWN: this.adjList.get(this.calcIndex(y, x)).add(this.calcIndex(y+1, x)); break;
		                case LEFT: this.adjList.get(this.calcIndex(y, x)).add(this.calcIndex(y, x-1)); break;
		                case NONE: break;
	              }
	    			//check if cell in question is a room, if not, then add all adjacencies
	    		} else if (!(this.getCellAt(this.calcIndex(y, x)).isRoom())){
	    			// Loop through all the possible adjacencies.
		    		for (int i = 0; i < deltas[0].length; i += 1) {
		    			int new_x = x + deltas[0][i];
		    			int new_y = y + deltas[1][i];
		    			
		    			// Check that the adjacency is in the map and is not a room.
		    			if (new_x >= 0 && new_x < this.numColumns && new_y >= 0 && new_y < this.numRows
		    			    && !this.getCellAt(this.calcIndex(new_y, new_x)).isRoom()) {
		    				this.adjList.get(this.calcIndex(y, x)).add(this.calcIndex(new_y, new_x));
	    				
		    			//if it finds a doorway, it checks the doorway direction
			    		//if the walkway cell is in the direction of the door, it moves on, otherwise,
			    		//the doorway cell is deleted from the linkedList
		    			
		    			} else if (new_x >= 0 && new_x < this.numColumns && new_y >= 0 && new_y < this.numRows
		    			    && this.getCellAt(this.calcIndex(new_y, new_x)).isDoorway()){
		    				DoorDirection dd = this.getRoomCellAt(new_y, new_x).getDoorDirection();
		    				
		    				//System.out.println("at location "+y+' '+x);
		    				//System.out.println("found a door at "+ new_y + ' ' + new_x + " with door located "+ dd);
		    				switch(dd) {
			                case UP: 
			                	if(new_y == (y+1)){
				    				this.adjList.get(this.calcIndex(y, x)).add(this.calcIndex(new_y, new_x));
				            	}
			                	break;
			                case RIGHT: 
			                	if(new_x == (x-1)){
				    				this.adjList.get(this.calcIndex(y, x)).add(this.calcIndex(new_y, new_x));
			                	}
			                	break;
			                case DOWN: 
			                	if(new_y == (y-1)){
				    				this.adjList.get(this.calcIndex(y, x)).add(this.calcIndex(new_y, new_x));
			                	}
			                	break;
			                case LEFT: 
			                	if(new_x == (x+1)){
				    				this.adjList.get(this.calcIndex(y, x)).add(this.calcIndex(new_y, new_x));
			                	}
			                	break;
			                case NONE: 
			                	break;
		    				}
		    			}
		    		}
	    		}
    		}
	    }
	    /*
	    System.out.println("("+this.adjList.get(this.calcIndex(1,5)).size()+") adjList for location 1,5 at index "+this.calcIndex(1,5));
	    for (int i =0; i< this.adjList.get(this.calcIndex(1,5)).size(); i++){
			System.out.println(this.adjList.get(this.calcIndex(1,5)).get(i));
		}*/
  }
  
  public LinkedList<Integer> getAdjList(int index) {
	  if (index >= 0 && index < this.numRows * this.numColumns) {
	    	return this.adjList.get(index);
	    } else {
	    	return new LinkedList<Integer>();
	    }
  }
  
  public void calcTargets(int y, int x, int length) {
    if (x >= 0 && x < this.numColumns && y >= 0 && y < this.numRows) { 
      // Clear the targets and set the starting point.
      this.current_targets = new HashSet<BoardCell>();
      int id = this.calcIndex(y, x);
      
      // Initialize the "visited" array.
      for (int i = 0; i < this.visited.length; i += 1) {
        this.visited[i] = false;
      }
      this.visited[id] = true;
      
      // Begin pathfinding.
      this.pathRecurse(id, length);
    }
  }
  
  private void pathRecurse(int id, int move_left) {
    // Iterate over each cell adjacent to this cell.
    LinkedList<Integer> curr_adj = this.adjList.get(id);
    for (int i = 0; i < curr_adj.size(); i += 1) {
      // Make sure it hasn't been visited - this prevents the player from
      // backtracking or uselessly rocking back and forth between two squares.
      if (!visited[curr_adj.get(i)]) {
        this.visited[curr_adj.get(i)] = true;
        if (move_left == 1) {
          // If there's only one move left then add this adjacent cell to the
          // move targets.
          this.current_targets.add(cells.get(curr_adj.get(i)));
        }
        // check and see if current cell has a door, if so, it adds it to target list
        else if (cells.get(curr_adj.get(i)).isDoorway()){
        	this.current_targets.add(cells.get(curr_adj.get(i)));
        } else {
          // If there are more moves to go, recurse further downward.
          
          this.pathRecurse(curr_adj.get(i), move_left - 1);
        }
        this.visited[curr_adj.get(i)] = false;
      }
    }
  }
  
  public Set<BoardCell> getTargets() {
    return this.current_targets;
  }
}
