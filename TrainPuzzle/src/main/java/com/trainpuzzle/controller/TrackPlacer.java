package com.trainpuzzle.controller;
import org.apache.log4j.Logger;

import com.trainpuzzle.exception.CannotPlaceTrackException;
import com.trainpuzzle.exception.CannotRemoveTrackException;
import com.trainpuzzle.exception.CannotRotateException;
import com.trainpuzzle.model.board.*;
import com.trainpuzzle.model.level.Level;


public class TrackPlacer {
	private Logger logger = Logger.getLogger(TrackPlacer.class);
	private Level levelToAddTrack;
	private Board map;
	
	public TrackPlacer(Level levelToAddTrack) {
		this.levelToAddTrack = levelToAddTrack;
		this.map = levelToAddTrack.getMap();
	}

	public void placeTrack(Track track, int row, int column) throws CannotPlaceTrackException{
		Tile tile = map.getTile(row, column);
		if(tile.hasTrack()||tile.hasObstacle()) {
			throw new CannotPlaceTrackException("Track failed to be placed to tile because there was a track or an obstacle");
		}
		else{
			tile.setTrack(track);
			map.notifyAllObservers();
			// map.setTile(tile,row,column); // can be removed because of passing by reference
		}
	}

	public void removeTrack(int row, int column) throws CannotRemoveTrackException{
		Tile tile = map.getTile(row, column);
		if(tile.hasTrack()) {			
			tile.removeTrack();
			map.notifyAllObservers();
			//map.setTile(tile,row,column); // can be removed because of passing by reference
		}
		else {
			throw new CannotRemoveTrackException("Track failed to be removed because there is no track at the tile to remove");
		}
	}
	
	
	public void rotateTrack(int row, int column) throws CannotRotateException{
		Tile tile = map.getTile(row, column);
		
		if(tile.hasTrack()) {
			tile.rotateTrack(); // TODO
			map.notifyAllObservers();
			//map.setTile(tile,row,column); // can be removed because of passing by reference		
		}
		else {
			throw new CannotRotateException("No track to be rotated on this tile");
		}		
	}
	
	public Level getLevelWithTrack() {
		//levelToAddTrack.setMap(map); // can be removed because of passing by reference
		return levelToAddTrack;
	}
}
