package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * TwoPlayerGameOfLife
 * <br>Rule:
 * <br> • Any live cell with fewer than two live neighbors of either player dies. 
 * <br> • Any live cell with two or three live neighbors of either player lives. 
 * <br> • Any live cell with more than three neighbors of either player dies. 
 * <br> • Any dead cell with exactly three neighbors becomes a live cell owned by the player who has the majority of live neighboring cells. 
 */
public class TwoPlayerGameOfLife {

	public static final char DEAD_CELL = '.';
	public static final char PLAYER_1 = '1';
	public static final char PLAYER_2 = '2';
	
	private char[][] cells;
	private char[][] state;
	private int currentGeneration = 0;
	
	public TwoPlayerGameOfLife() {
		
	}
	
	public TwoPlayerGameOfLife(String initialGenerationFileName) throws IOException {
		initGrid(initialGenerationFileName);
	}

	/**
	 * Initialize the grid
	 * @param initialGenerationFileName The configuration file name of the generation 0
	 * @throws IOException 
	 */
	protected void initGrid(String initialGenerationFileName) throws IOException {
		File file = new File(initialGenerationFileName);
		FileReader fileReader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String line;
		int lineNum = 0;
		int rowIndex = 0;
		boolean isGridHead = true;
		while ((line=bufferedReader.readLine())!=null) {
			lineNum++;
			//skip the blank lines
			if (line.trim().equals("")) {
				continue;
			}
			//remove blanks
			line = trim(line);
			//the first line
			if (isGridHead) {
				initGridSize(line);
				isGridHead = false;
			}else {
				//Initialize grid row by row
				initGridRow(line, rowIndex, lineNum);
				rowIndex++;
			}
		}
		bufferedReader.close();bufferedReader=null;
		fileReader.close();fileReader=null;
	}
	
	/**
	 * remove the blanks in the head or tail of a String
	 * @param s
	 * @return
	 */
	private static String trim(String s) {
		if(s.charAt(0)==' '){
			int i;
			for(i=0;i<s.length();i++){
				if(s.charAt(i)!=' ')
					break;
			}
			s = s.substring(i).trim();
		}else {
			s = s.trim();
		}
		return s;
	}
	
	/**
	 * Initialize grid size
	 * @param line The line that gives the definition of the grid size
	 */
	protected void initGridSize(String line) {
		String[] gridSize = line.split("\\s+");
		if (gridSize.length!=2) {
			throw new RuntimeException("Invalid definition of grid size '"+line+"' at line 1");
		}
		int rowCount = 0;
		int columnCount = 0;
		try {
			rowCount = Integer.parseInt(gridSize[0]);
		} catch (Exception e) {
			throw new RuntimeException("Invalid definition of grid row size '"+gridSize[0]+"' at line 1");
		}
		try {
			columnCount = Integer.parseInt(gridSize[1]);
		} catch (Exception e) {
			throw new RuntimeException("Invalid definition of grid column size '"+gridSize[1]+"' at line 1");
		}
		cells = new char[rowCount][columnCount];
		state = new char[rowCount][columnCount];
	}
	
	/**
	 * Initialize grid row by row
	 * @param line
	 * @param rowIndex
	 * @param lineNum
	 */
	protected void initGridRow(String line,int rowIndex,int lineNum) {
		if (line.length()!=cells[0].length) {
			throw new RuntimeException("Invalid row size '"+line.length()+"' at line "+lineNum);
		}
		char c;
		for (int i = 0; i < line.length(); i++) {
			c = line.charAt(i);
			if (validateCellChar(c,PLAYER_1,PLAYER_2,DEAD_CELL)) {
				cells[rowIndex][i] = c;
			}else {
				cells = null;
				state = null;
				throw new RuntimeException("Invalid cell character '"+c+"' at line "+lineNum+".Only "+PLAYER_1+","+PLAYER_2+" or "+DEAD_CELL+" is accepted.");
			}
		}
	}
	
	/**
	 * Validate cell character.
	 * @param c character
	 * @param cells cells
	 * @return
	 */
	protected boolean validateCellChar(char c,char...cells) {
		for (char cell : cells) {
			if (c==cell) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * count neighbors
	 * @param cell
	 * @param x
	 * @param y
	 * @return
	 */
	protected int countNeighbors(char cell,int x,int y) {
		int count = 0;
		for (int i = (x==0?0:x-1); i <= (x==cells.length-1?x:x+1); i++) {
			for (int j = (y==0?0:y-1); j <= (y==cells[0].length-1?y:y+1); j++) {
				if (cells[i][j]==cell) {
					count++;
				}
			}
		}
		return cells[x][y]==cell?count-1:count;
	}
	
	/**
	 * generate next state.
	 */
	protected void generateNextState() {
		for (int x = 0; x < cells.length; x++) {
			for (int y = 0;y <cells[0].length; y++) {
				int neighborsOfPlayer1 = countNeighbors(PLAYER_1, x, y);
				int neighborsOfPlayer2 = countNeighbors(PLAYER_2, x, y);
				int neighbors = neighborsOfPlayer1+neighborsOfPlayer2;
				if (cells[x][y]==DEAD_CELL) {
					//dead cell
					if (neighbors==3) {
						state[x][y] = neighborsOfPlayer1>neighborsOfPlayer2?PLAYER_1:PLAYER_2;
					}else {
						state[x][y] = DEAD_CELL;
					}
				} else {
					//live cell
					if (neighbors==2||neighbors==3) {
						state[x][y] = cells[x][y];
					}else {
						state[x][y] = DEAD_CELL;
					}
				}
			}
		}
	}
	
	/**
	 * Update generation
	 */
	protected void update() {
		for (int x = 0; x < cells.length; x++) {
			for (int y=0; y<cells[0].length; y++) {
				cells[x][y] = state[x][y];
			}
		}
		currentGeneration++;
	}
	
	/**
	 * Get cell count
	 * @param cell
	 */
	protected int getCellCount(char cell) {
		int count = 0;
		for (int x = 0; x < cells.length; x++) {
			for (int y=0; y<cells[0].length; y++) {
				if (cells[x][y]==cell) {
					count++;
				}
			}
		}
		return count;
	}
	
	public boolean isOver() {
		return getCellCount(PLAYER_1)==0||getCellCount(PLAYER_2)==0;
	}
	
	/**
	 * Iterate to next generation.
	 * @return True if current game has next generation.
	 */
	public boolean next() {
		if (isOver()) {
			return false;
		}
		generateNextState();
		update();
		return true;
	}
	
	/**
	 * get current generation
	 * @return
	 */
	public final int getCurrentGeneration() {
		return currentGeneration;
	}

	/**
	 * Output game state to String.
	 * @return State information
	 */
	public String optStateToString() {
		StringBuilder sb = new StringBuilder();
		int cell1Count = getCellCount(PLAYER_1);
		int cell2Count = getCellCount(PLAYER_2);
		sb.append("Generation #").append(currentGeneration).append("\r\n");
		sb.append("Player 1 Cells: ").append(cell1Count).append("\r\n");
		sb.append("Player 2 Cells: ").append(cell2Count).append("\r\n");
		for (int x=0;x<cells.length;x++) {
			for (int y = 0; y < cells[0].length; y++) {
				sb.append(cells[x][y]);
			}
			sb.append("\r\n");
		}
		sb.append("\r\n");
		if (cell2Count==0&&cell1Count>0) {
			sb.append("Player 1 wins with "+cell1Count+" cells alive.");
		}else if (cell1Count==0&&cell2Count>0) {
			sb.append("Player 2 wins with "+cell2Count+" cells alive.");
		}else if (cell1Count==0&&cell2Count==0){
			sb.append("There is a tie.");
		}
		return sb.toString();
	}
	
	/**
	 * Print state on console.
	 */
	public void print() {
		System.out.println(optStateToString());
	}
	
	/**
	 * main method
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		TwoPlayerGameOfLife gameOfLife = new TwoPlayerGameOfLife(args[0]);
		gameOfLife.print();
		while (gameOfLife.next()) {
			gameOfLife.print();
		}
	}
	
}
