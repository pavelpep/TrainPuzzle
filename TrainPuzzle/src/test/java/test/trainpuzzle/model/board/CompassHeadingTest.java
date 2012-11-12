package test.trainpuzzle.model.board;

import com.trainpuzzle.model.board.CompassHeading;
import static com.trainpuzzle.model.board.CompassHeading.*;

import junit.framework.TestCase;

public class CompassHeadingTest extends TestCase {
	CompassHeading heading;
	
	protected void setUp() throws Exception {
		heading = NORTH;
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testGetHeading() {
		assertEquals(NORTH, CompassHeading.getHeading(0));
	}

	public void testOpposite() {
		assertEquals(SOUTH, heading.opposite());
	}

	public void testRotate90DegreesClockwise() {
		assertEquals(EAST, heading.rotate90DegreesClockwise());
	}

	public void testRotate90DegreesCounterClockwise() {
		assertEquals(WEST, heading.rotate90DegreesCounterClockwise());
	}

	public void testRotate45DegreesClockwise() {
		assertEquals(NORTHWEST, heading.rotate45DegreesClockwise());
	}

	public void testRotate45DegreesCounterClockwise() {
		assertEquals(NORTHEAST, heading.rotate45DegreesCounterClockwise());
	}

	public void testGetValue() {
		assertEquals(0, heading.getValue());
	}

}
