package com.trainpuzzle.controller;
import org.apache.log4j.Logger;

import com.trainpuzzle.exception.CannotPlaceTrackException;
import com.trainpuzzle.exception.CannotRemoveTrackException;
import com.trainpuzzle.model.level.Level;
import com.trainpuzzle.model.map.*;


public class TrackPlacer {
	private Logger logger = Logger.getLogger(TrackPlacer.class);
	private Level levelToAddTrack;
	private Board map;
	
	/* Public Interface */
	
	TrackPlacer(Level levelToAddTrack) {
		this.levelToAddTrack = levelToAddTrack;
		this.map = levelToAddTrack.getMap();
	}
	
	/**
	 * This function add a track on tile on location (latitude,longitude)
	 * 
	 * @param track latitude longitude 
	 */
	public void placeTrack(Track track,int latitude,int longitude) throws CannotPlaceTrackException{
		Tile tile = map.getTile(latitude, longitude);
		if(tile.hasTrack()||tile.hasObstacle()) {
			throw new CannotPlaceTrackException("Track failed to be placed to tile because there was a track or an obstacle");
		}
		else{
			tile.setTrack(track);
			// map.setTile(tile,latitude,longitude); // can be removed because of passing by reference
		}
		
	}
	/**
	 * this  function remove a track on tile on location(latitude,longitude)
	 * 
	 * @param latitude
	 * @param longitude
	 */
	public void removeTrack(int latitude, int longitude) throws CannotRemoveTrackException{
		Tile tile = map.getTile(latitude, longitude);
		if(!tile.hasTrack()||tile.hasObstacle()) {
			throw new CannotRemoveTrackException("Track failed to be removed because there is no track at the tile to remove");
		}
		else {
			tile.removeTrack();
			//map.setTile(tile,latitude,longitude); // can be removed because of passing by reference
		}
	}
	
	/* Getters and Setters */
	
	public Level getLevelWithTrack() {
		//levelToAddTrack.setMap(map); // can be removed because of passing by reference
		return levelToAddTrack;
	}
}
