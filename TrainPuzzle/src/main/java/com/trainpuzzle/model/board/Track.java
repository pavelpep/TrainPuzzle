package com.trainpuzzle.model.board;

import java.util.HashSet;
import java.util.Set;

import com.trainpuzzle.exception.TrainCrashException;

import static com.trainpuzzle.model.board.CompassHeading.*;


public class Track {
	
	private Set<Connection> connections;
	
	/* Public Interface */
	
	public Track() {
		connections = new HashSet<Connection>();
		Connection connection = new Connection(EAST, WEST);
		connections.add(connection);
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
	
	public void addConnection(CompassHeading firstCompassHeading, CompassHeading secondCompassHeading) {
		connections.add(new Connection(firstCompassHeading, secondCompassHeading));
		return;
	}
	
	public Set<Connection> getConnections() {
		return connections;
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
