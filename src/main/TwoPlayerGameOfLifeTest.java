package main;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class TwoPlayerGameOfLifeTest {

	private String initialGenerationFileName = "G:/java/project/TwoPlayerGameOfLife/src/generation0.txt";
	private TwoPlayerGameOfLife twoPlayerGameOfLife;
	
	public TwoPlayerGameOfLifeTest() throws IOException {
		twoPlayerGameOfLife = new TwoPlayerGameOfLife();
	}

	@Before
	public void testInitGridData() throws IOException {
		twoPlayerGameOfLife.initGrid(initialGenerationFileName);
	}
	
	@Test
	public void testValidateCellChar() throws IOException {
		boolean flag = twoPlayerGameOfLife.validateCellChar('0',TwoPlayerGameOfLife.DEAD_CELL, TwoPlayerGameOfLife.PLAYER_1,TwoPlayerGameOfLife.PLAYER_2);
		System.out.println("Is '0' valid : "+flag);
		flag = twoPlayerGameOfLife.validateCellChar('.',TwoPlayerGameOfLife.DEAD_CELL, TwoPlayerGameOfLife.PLAYER_1,TwoPlayerGameOfLife.PLAYER_2);
		System.out.println("Is '.' valid : "+flag);
		flag = twoPlayerGameOfLife.validateCellChar('1',TwoPlayerGameOfLife.DEAD_CELL, TwoPlayerGameOfLife.PLAYER_1,TwoPlayerGameOfLife.PLAYER_2);
		System.out.println("Is '1' valid : "+flag);
		flag = twoPlayerGameOfLife.validateCellChar('2',TwoPlayerGameOfLife.DEAD_CELL, TwoPlayerGameOfLife.PLAYER_1,TwoPlayerGameOfLife.PLAYER_2);
		System.out.println("Is '2' valid : "+flag);
	}
	
	@Test
	public void testOptState() {
		System.out.println(twoPlayerGameOfLife.optStateToString());
	}
	
	@Test
	public void testGenerateNextState() {
		twoPlayerGameOfLife.generateNextState();
	}
	
	@Test
	public void testPrint() {
		twoPlayerGameOfLife.print();
	}
	
	@Test
	public void testTwoPlayerGameOfLifeWithExampleData() {
		while (twoPlayerGameOfLife.next()) {
			twoPlayerGameOfLife.print();
		}
	}

}
