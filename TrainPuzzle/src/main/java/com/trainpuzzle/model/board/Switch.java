package com.trainpuzzle.model.board;
import java.util.Iterator;

public class Switch extends Track{
	private Iterator<Connection> connectionsIterator;
	private Connection current;
	
	Switch(Connection connection1, Connection connection2) {
		super(connection1, connection2);
		goToFirstConnection();
	}
	
	private void goToFirstConnection() {
		connectionsIterator = super.connections.iterator();
		if (connectionsIterator.hasNext()) {
			current = connectionsIterator.next();
		} else {
			// TODO: throw an assertion or exception while there should be 2 connections in the set
		}
	}
	
	public Connection getCurrentConnection() {
		return current;
	}
	
	public void switchConnection() {
		if (connectionsIterator.hasNext()) {
			current = connectionsIterator.next();
		} else {
			goToFirstConnection();
		}
	}
}
