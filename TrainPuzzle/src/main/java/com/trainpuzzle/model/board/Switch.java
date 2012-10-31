package com.trainpuzzle.model.board;
import java.util.Iterator;
import com.trainpuzzle.exception.TrainCrashException;

public class Switch extends Track {
	
	private Iterator<Connection> connectionsIterator;
	private Connection current;
	
	public Switch(Connection connection1, Connection connection2, TrackType trackType) {
		super(connection1, connection2, trackType);
		assert(!isValidSwitch(connection1, connection2)): "Invalid switch";
		goToFirstConnection();
	}
	
	private void goToFirstConnection() {
		connectionsIterator = connections.iterator();
		if(connectionsIterator.hasNext()) {
			current = connectionsIterator.next();
		} else {
			// TODO: throw an assertion or exception while there should be 2 connections (more than one connection) in the connection set
		}
	}
	
	private boolean isValidSwitch(Connection connection1, Connection connection2) {
		CompassHeading[] compassHeadingPair1 = connection1.getCompassHeadingPair();
		CompassHeading[] compassHeadingPair2 = connection2.getCompassHeadingPair();
		
		return hasOneSimilarHeading(compassHeadingPair1, compassHeadingPair2);
	}
	
	private boolean hasOneSimilarHeading(CompassHeading[] pair1, CompassHeading[] pair2) {
		int similarHeadingCounts = 0;
		for(CompassHeading pair1_heading: pair1) {
			for(CompassHeading pair2_heading: pair2) {
				if(pair1_heading == pair2_heading) {
					similarHeadingCounts++;
				}
			}
		}
		return (similarHeadingCounts == 1);
	}
	
	public Connection getCurrentConnection() {
		return current;
	}
	public CompassHeading getOutboundHeading(CompassHeading inboundHeading) throws TrainCrashException{
		CompassHeading outboundHeading;
		if(current.isInboundHeading(inboundHeading)) {
			outboundHeading = current.outboundorInbound(inboundHeading);
		} else {	// if the current connection does not connect to the inboundHeading, check for all other possible connections  
			outboundHeading = super.getOutboundHeading(inboundHeading);
		}
		switchConnection();
		return outboundHeading;		
	}
	
	public void switchConnection() {
		if(connectionsIterator.hasNext()) {
			current = connectionsIterator.next();
		} else {
			goToFirstConnection();
		}
	}
}
