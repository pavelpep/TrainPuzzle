package com.trainpuzzle.model.board;
import java.util.Iterator;

public class Switch extends Track{
	private Iterator<Connection> connectionsIterator;
	private Connection currentConnection;
	
	Switch(Connection headConnection, Connection tailConnection) {
		super(headConnection, tailConnection);
		currentConnection = headConnection;
	}
	
	public Connection getCurrentConnection() {
		return currentConnection;
	}
	
	public void switchConnection() {
		// currentConnection = super.connections.iterator().next();
	}
}
