package com.trainpuzzle.model.map;

import java.util.HashSet;
import java.util.Set;
import static com.trainpuzzle.model.map.Heading.*;


public class Track {
	
	private Set<Connection> connections;
	
	/* Public Interface */
	
	public Track() {
		connections = new HashSet<Connection>();
		Connection connection = new Connection(EAST, WEST);
		connections.add(connection);
	}
	
	public void rotateTrack() {		
		for(Connection connection : connections) {
			connection.rotate90Degrees();
		}
	}
	
	/* Getters and Setters */
	
	public void addConnection(Heading firstCompassHeading, Heading secondCompassHeading) {
		connections.add(new Connection(firstCompassHeading, secondCompassHeading));
		return;
	}
	
	public Set<Connection> getConnections() {
		return connections;
	}
}

