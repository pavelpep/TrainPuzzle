package com.trainpuzzle.factory;

import static com.trainpuzzle.model.board.TrackType.*;
import static com.trainpuzzle.model.board.CompassHeading.*;

import com.trainpuzzle.model.board.Connection;
import com.trainpuzzle.model.board.Track;
import com.trainpuzzle.model.board.TrackType;

public class TrackFactory {

	private static TrackFactory instance = null;
	
	protected TrackFactory() {
		
	}
	   
	public static TrackFactory getInstance() {
		if(instance == null) {
			instance = new TrackFactory();
	    }
		return instance;
	}
	
	public Track createTrack(TrackType trackType) {
		
		switch(trackType) {	
		case STRAIGHT_TRACK:
			return straightTrack();
		case DIAGONAL_TRACK:
			return diagonalTrack();
		case CURVELEFT_TRACK:
			return curveLeftTrack();
		case CURVERIGHT_TRACK:
			return curveRightTrack();
		case INTERSECTION_TRACK:
			return intersectionTrack();
		case DIAGONAL_INTERSECTION_TRACK:
			return diagonalIntersectionTrack();
		default:
			return straightTrack();
		}
	}
	
	private Track straightTrack() {
		Connection connection = new Connection(EAST, WEST);
		return (new Track(connection, STRAIGHT_TRACK));
	}
	
	private Track diagonalTrack() {
		Connection connection = new Connection(NORTHWEST, SOUTHEAST);
		return (new Track(connection, DIAGONAL_TRACK));
	}
	
	private Track curveLeftTrack() {
		Connection connection = new Connection(NORTHWEST, SOUTH);
		return (new Track(connection, CURVELEFT_TRACK));
	}
	
	private Track curveRightTrack() {
		Connection connection = new Connection(NORTHEAST, SOUTH);
		return (new Track(connection, CURVERIGHT_TRACK));
	}
	
	private Track intersectionTrack() {
		Connection connection1 = new Connection(NORTH, SOUTH);
		Connection connection2 = new Connection(WEST, EAST);
		return (new Track(connection1, connection2, INTERSECTION_TRACK));
	}
	
	private Track diagonalIntersectionTrack() {
		Connection connection1 = new Connection(NORTHEAST, SOUTHWEST);
		Connection connection2 = new Connection(NORTHWEST, SOUTHEAST);
		return (new Track(connection1, connection2, DIAGONAL_INTERSECTION_TRACK));
	}	
}