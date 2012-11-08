package com.trainpuzzle.model.board;
import java.util.Iterator;
import com.trainpuzzle.controller.TrackPlacer;
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
		current = switchConnection();
	}
	
	public Switch(Switch switchToCopy) {
		super(switchToCopy);
		entrance = switchToCopy.getEntrance();
		connectionsIterator = connections.iterator();
		current = switchConnection();
	}
	
	private CompassHeading findValidEntrance(Connection connection1, Connection connection2) {
		int similarHeadingCounts = 0;
		CompassHeading entrance = null;
		
		CompassHeading[] pair1 = connection1.getCompassHeadingPair();
		CompassHeading[] pair2 = connection2.getCompassHeadingPair();
		for(CompassHeading pair1_heading: pair1) {
			for(CompassHeading pair2_heading: pair2) {
				if(pair1_heading == pair2_heading) {
					similarHeadingCounts++;
					entrance = pair1_heading;
				}
			}
		}
		assert(similarHeadingCounts == 1): "Invalid switch (similar headings:" + similarHeadingCounts + ")";
		return entrance;
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
			outboundHeading = current.outboundorInbound(inboundHeading);
			current = switchConnection();
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
	
	private Connection switchConnection() {
		if(!connectionsIterator.hasNext()) {
			connectionsIterator = connections.iterator();
		}
		return connectionsIterator.next();
	}
}
