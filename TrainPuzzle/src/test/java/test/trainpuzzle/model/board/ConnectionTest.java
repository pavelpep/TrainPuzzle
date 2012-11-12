package test.trainpuzzle.model.board;

import com.trainpuzzle.model.board.CompassHeading;
import com.trainpuzzle.model.board.Connection;

import junit.framework.TestCase;

public class ConnectionTest extends TestCase {
	Connection connection;
	protected void setUp() throws Exception {
		connection = new Connection(CompassHeading.NORTH, CompassHeading.SOUTH);
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testConnection() {
		connection = new Connection(CompassHeading.NORTH, CompassHeading.SOUTH);
		assertNotNull(connection);
	}

	public void testGetCompassHeadingPair() {
		CompassHeading[] expectedPair = new CompassHeading[2];
		expectedPair[1] = CompassHeading.NORTH;
		expectedPair[0] = CompassHeading.SOUTH;
		CompassHeading[] actualPair = connection.getCompassHeadingPair();
		
		assertEquals(expectedPair, actualPair);
	}

	public void testIsInboundHeading() {
		assertTrue(connection.isInboundHeading(CompassHeading.NORTH));
		assertFalse(connection.isInboundHeading(CompassHeading.WEST));
	}

	public void testOutboundForInbound() {
		assertEquals(CompassHeading.SOUTH, connection.outboundForInbound(CompassHeading.NORTH));
	}

	public void testEqualsObject() {
		Connection newConnection = new Connection(CompassHeading.NORTH, CompassHeading.SOUTH);
		assertEquals(connection, newConnection);
	}

	public void testRotate45Degrees() {
		Connection newConnection = new Connection(CompassHeading.NORTHEAST, CompassHeading.SOUTHWEST);
		connection.rotate45Degrees();
		assertEquals(connection, newConnection);
	}

	public void testRotate90Degrees() {
		Connection newConnection = new Connection(CompassHeading.EAST, CompassHeading.WEST);
		connection.rotate90Degrees();
		assertEquals(connection, newConnection);
	}

/*
 	public void testHashCode() {
		fail("Not yet implemented"); // TODO
	}

	public void testNotify() {
		fail("Not yet implemented"); // TODO
	}

	public void testNotifyAll() {
		fail("Not yet implemented"); // TODO
	}
*/

}
