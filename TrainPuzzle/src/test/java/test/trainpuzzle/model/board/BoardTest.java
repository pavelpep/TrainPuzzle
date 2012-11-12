package test.trainpuzzle.model.board;

import com.trainpuzzle.model.board.Board;

import junit.framework.TestCase;

public class BoardTest extends TestCase {
	Board board;
	
	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testBoard() {
		board = new Board();
		assertNotNull("Doesn't initialize tiles", board.getTile(0,0));
		
	}

	public void testGetTile() {
		board = new Board(10, 10);
		assertNotNull("Doesn't create expected board size", board.getTile(9,9));
	}

	public void testGetNumberOfColumns() {
		board = new Board(9, 10);
		assertEquals("wrong number of columns", 10, board.getColumns());
	}

	public void testGetNumberOfRows() {
		board = new Board(9, 10);
		assertEquals("wrong number of rows", 9, board.getRows());
	}
	
/*
	public void testRegister() {
		fail("Not yet implemented"); // TODO
	}

	public void testNotifyAllObservers() {
		fail("Not yet implemented"); // TODO
	}

	public void testNotify() {
		fail("Not yet implemented"); // TODO
	}

	public void testNotifyAll() {
		fail("Not yet implemented"); // TODO
	}

	public void testFinalize() {
		fail("Not yet implemented"); // TODO
	}
*/
}
