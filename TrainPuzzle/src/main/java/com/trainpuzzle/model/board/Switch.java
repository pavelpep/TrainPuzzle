package com.trainpuzzle.model.board;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.trainpuzzle.exception.InvalidCommonHeadingException;
import com.trainpuzzle.exception.TrainCrashException;
import com.trainpuzzle.observe.Observable;
import com.trainpuzzle.observe.Observer;

public class Switch extends Track implements Observable{

	private static final long serialVersionUID = 1L;
	
	private Iterator<Connection> connectionsIterator;
	private Connection current;
	private CompassHeading entrance;
	
	private Set<Observer> observerList = new HashSet<Observer>();
	
	public Switch(Connection connection1, Connection connection2, TrackType trackType) {
		super(connection1, connection2, trackType);
		assert(trackType == TrackType.CURVELEFT_STRAIGHT_SWITCH
			|| trackType == TrackType.CURVERIGHT_STRAIGHT_SWITCH): "Invalid trackType";
		
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
		CompassHeading validEntrance = null;
		try {
			validEntrance = connection1.findCommonHeading(connection2);
		} catch (InvalidCommonHeadingException e){
			assert(false): "Invalid Switch: " + e.getMessage();
		}
		return validEntrance;
	}
	
	public Connection getCurrentConnection() {
		return current;
	}
	
	public CompassHeading getEntrance() {
		return entrance;
	}
	
	public CompassHeading getOutboundHeading(CompassHeading inboundHeading) throws TrainCrashException {
		CompassHeading outboundHeading;
		
		if(isEntrance(inboundHeading)) {
			outboundHeading = current.outboundForInbound(inboundHeading);
			current = nextConnection();
			notifyAllObservers();
		} 
		else {	
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
	
	public void register(Observer observer) {
		if(observerList == null) {
    	  observerList = new HashSet<Observer>();
		}
		observerList.add(observer);
	}
		
	public void notifyAllObservers() {
		for(Observer observer : observerList) {
			observer.notifyChange(this);
		}
	} 
}