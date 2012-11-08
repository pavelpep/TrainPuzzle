package com.trainpuzzle.model.board;

import java.util.HashSet;
import java.util.Set;


import com.trainpuzzle.exception.TrainCrashException;

public class Track implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;	
	private boolean isStationTrack = false;
	private boolean removable = true;
	protected Set<Connection> connections = new HashSet<Connection>();
	protected TrackType trackType = TrackType.STRAIGHT_TRACK;
	
	/* Public Interface */
	
	public Track(Track trackToCopy) {
		removeConnections();
		for(Connection connection : trackToCopy.getConnections()) {
			addConnection(connection.getCompassHeadingPair()[0], connection.getCompassHeadingPair()[1]);
		}
		if(trackToCopy.isUnremovable()) {
			setUnremoveable();
		}
		trackType = trackToCopy.getTrackType();
	}
	
	public Track(Connection connection, TrackType trackType) {
		connections.add(connection);
		this.trackType = trackType;
	}
	
	public Track(Connection connection1, Connection connection2, TrackType trackType) {
		connections.add(connection1);
		connections.add(connection2);
		this.trackType = trackType;
	}
	
	public void rotateTrack() {		
		for(Connection connection : connections) {
			connection.rotate90Degrees();
		}
	}
	
	/* Getters and Setters */
	
	private void addConnection(Connection connection) {
		connections.add(connection);	
	}
	
	private void addConnection(CompassHeading firstCompassHeading, CompassHeading secondCompassHeading) {
		addConnection(new Connection(firstCompassHeading, secondCompassHeading));
	}
	
	public Set<Connection> getConnections() {
		return connections;
	}
	
	private void removeConnections() {
		connections.removeAll(connections);
	}
	
	public void setUnremoveable() {
		removable = false;
	}
	
	public boolean isRemovable() {
		return removable;
	}
	
	public boolean isUnremovable() {
		return !removable;
	}
	
	public void setToBeStationTrack() {
		isStationTrack = true;
	}
	
	public boolean isStationTrack() {
		return isStationTrack;
	}
	
	public boolean isSwitch() {
		return (this instanceof Switch);
	}

	public CompassHeading getOutboundHeading(CompassHeading inboundHeading) throws TrainCrashException {
		for(Connection connection : connections) {
			if(connection.isInboundHeading(inboundHeading)) {
				return connection.outboundForInbound(inboundHeading);
			}
		}
		throw new TrainCrashException();
	}
	
	public TrackType getTrackType() {
		return trackType;
	}

	public void setTrackType(TrackType trackType) {
		this.trackType = trackType;
	}
}