package com.trainpuzzle.model.board;

import java.util.HashSet;
import java.util.Set;

import com.trainpuzzle.exception.TrainCrashException;

import static com.trainpuzzle.model.board.CompassHeading.*;


public class Track implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private boolean isStationTrack = false;
	private boolean removable = true;
	
	protected boolean isSwitch = false;
	protected Set<Connection> connections;
	
	
	/* Public Interface */
	
	public Track() {
		connections = new HashSet<Connection>();
	}
	
	public Track(Track trackToCopy) {
		connections = new HashSet<Connection>();
		removeConnections();
		for(Connection connection : trackToCopy.getConnections()) {
			addConnection(connection.getCompassHeadingPair()[0], connection.getCompassHeadingPair()[1]);
		}
		if(trackToCopy.isUnremovable()){
			setUnremoveable();
		}
	}
	
	public Track(Connection connection) {
		connections = new HashSet<Connection>();
		connections.add(connection);
	}
	
	public Track(Connection connection1, Connection connection2) {
		connections = new HashSet<Connection>();
		connections.add(connection1);
		connections.add(connection2);
	}
	
	public void rotateTrack() {		
		for(Connection connection : connections) {
			connection.rotate90Degrees();
		}
	}
	
	/* Getters and Setters */
	
	public void addConnection(Connection connection) {
		connections.add(connection);
		
	}
	
	public void addConnection(CompassHeading firstCompassHeading, CompassHeading secondCompassHeading) {
		connections.add(new Connection(firstCompassHeading, secondCompassHeading));
	}
	
	
	public Set<Connection> getConnections() {
		return connections;
	}
	
	private void removeConnections(){
		connections.removeAll(connections);
	}
	
	public void setUnremoveable(){
		removable = false;
	}
	
	public boolean isRemovable(){
		return removable;
	}
	
	public boolean isUnremovable(){
		return !removable;
	}
	
	public void setToBeStationTrack(){
		isStationTrack = true;
	}
	
	
	public boolean isStationTrack(){
		return isStationTrack;
	}
	
	public boolean isSwitch(){
		return isSwitch;
	}


	/**
	 * Get the outboundHeading
	 * 
	 * @param inboundHeading is direction in which the train is heading now
	 * @return outboundHeading  
	 */

	public CompassHeading getOutboundHeading(CompassHeading inboundHeading) throws TrainCrashException {
		for(Connection connection : connections) {
			if(connection.isInboundHeading(inboundHeading)) {
				return connection.outboundorInbound(inboundHeading);
			}
		}
		throw new TrainCrashException();
	}
}