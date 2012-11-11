package com.trainpuzzle.model.board;
import java.util.Iterator;
import com.trainpuzzle.exception.TrainCrashException;

public class Switch extends Track {
	private static final long serialVersionUID = 1L;
	
	private Iterator<Connection> connectionsIterator;
	private Connection current;
	private CompassHeading entrance;
	
	public Switch(Connection connection1, Connection connection2, TrackType trackType) {
		super(connection1, connection2, trackType);
		entrance = findValidEntrance(connection1, connection2);
		connectionsIterator = connections.iterator();
		current = nextConnection();
	}
	
	public Switch(Switch switchToCopy) {
		super(switchToCopy);
		entrance = switchToCopy.getEntrance();
		connectionsIterator = connections.iterator();
		current = nextConnection();
	}
	
	private CompassHeading findValidEntrance(Connection connection1, Connection connection2) {
		return connection1.findSimilarHeading(connection2);
	}
	
	public Connection getCurrentConnection() {
		return current;
	}
	
	public CompassHeading getEntrance() {
		return entrance;
	}
	
	public CompassHeading getOutboundHeading(CompassHeading inboundHeading) throws TrainCrashException{
		CompassHeading outboundHeading;
		if(isEntrance(inboundHeading)) {
			outboundHeading = current.outboundForInbound(inboundHeading);
			current = nextConnection();
			// TODO: notify UI to redraw the switch when current is changed
		} else {	// if the inbound heading is not connected to the "switch entrance", check if it is connected to any other "switch exits"
			outboundHeading = super.getOutboundHeading(inboundHeading);
		}
		
		return outboundHeading;		
	}
	
	public void rotateTrack() {		
		super.rotateTrack();
		entrance = entrance.rotate90DegreesClockwise();
	}
	
	private boolean isEntrance(CompassHeading inboundHeading) {
		return inboundHeading.opposite() == entrance;
	}
	
	private Connection nextConnection() {
		if(!connectionsIterator.hasNext()) {
			connectionsIterator = connections.iterator();
		}
		return connectionsIterator.next();
	}
}
