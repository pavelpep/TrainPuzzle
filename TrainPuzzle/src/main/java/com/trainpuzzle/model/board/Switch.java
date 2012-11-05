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
		assert(!isValidSwitch(connection1, connection2)): "Invalid switch";
		goToFirstConnection();
	}
	
	private boolean isValidSwitch(Connection connection1, Connection connection2) {
		CompassHeading[] compassHeadingPair1 = connection1.getCompassHeadingPair();
		CompassHeading[] compassHeadingPair2 = connection2.getCompassHeadingPair();
		
		return hasOnlyOneSimilarHeadingAndSetEntrance(compassHeadingPair1, compassHeadingPair2);
	}
	
	private boolean hasOnlyOneSimilarHeadingAndSetEntrance(CompassHeading[] pair1, CompassHeading[] pair2) {
		int similarHeadingCounts = 0;
		for(CompassHeading pair1_heading: pair1) {
			for(CompassHeading pair2_heading: pair2) {
				if(pair1_heading == pair2_heading) {
					similarHeadingCounts++;
					setEntrance(pair1_heading);
				}
			}
		}
		return (similarHeadingCounts == 1);
	}
	
	private void setEntrance(CompassHeading entrance) {
		this.entrance = entrance;
	}
	
	private void goToFirstConnection() {
		connectionsIterator = connections.iterator();
		if(connectionsIterator.hasNext()) {
			current = connectionsIterator.next();
		} else {
			// TODO: throw an assertion or exception while there should be 2 connections (more than one connection) in the connection set
		}
	}
	
	public Connection getCurrentConnection() {
		return current;
	}
	
	public CompassHeading getOutboundHeading(CompassHeading inboundHeading) throws TrainCrashException{
		CompassHeading outboundHeading;
		if(isEntrance(inboundHeading)) {
			outboundHeading = current.outboundorInbound(inboundHeading);
			switchConnection();
		} else {	// if the inbound heading is not connected to the "switch entrance", check if it is connected to any other "switch exits"
			outboundHeading = super.getOutboundHeading(inboundHeading);
		}
		
		return outboundHeading;		
	}
	
	private boolean isEntrance(CompassHeading inboundHeading) {
		return inboundHeading.opposite() == entrance;
	}
	
	private void switchConnection() {
		if(connectionsIterator.hasNext()) {
			current = connectionsIterator.next();
		} else {
			goToFirstConnection();
		}
		// TODO: notify UI to redraw the switch when current is changed
	}
}
