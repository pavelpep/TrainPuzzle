package com.trainpuzzle.model.board;
import java.util.Iterator;

import com.trainpuzzle.exception.TrainCrashException;

public class Switch extends Track{
	private Iterator<Connection> connectionsIterator;
	private Connection current;
	
	Switch(Connection connection1, Connection connection2) {
		super(connection1, connection2);
		isSwitch = true;
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
	
	private boolean isValidSwitch() {
		//CompassHeading[] compassHeadingPair
		
		for(Connection connection : connections) {
			
		}
		
		return true;
	}
	
	public Connection getCurrentConnection() {
		return current;
	}
	public CompassHeading getOutboundHeading(CompassHeading inboundHeading) throws TrainCrashException {
		if(current.isInboundHeading(inboundHeading)) {
			return current.outboundorInbound(inboundHeading);
		}
		throw new TrainCrashException();
	}
	
	public void switchConnection() {
		if(connectionsIterator.hasNext()) {
			current = connectionsIterator.next();
		} else {
			goToFirstConnection();
		}
	}
}
