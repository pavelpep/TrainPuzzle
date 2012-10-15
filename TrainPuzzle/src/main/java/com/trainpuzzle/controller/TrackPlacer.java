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
			//logger.info("Track failed to be added to tile because there was an obstacle");
			throw new CannotPlaceTrackException();
		}
		else{
			tile.setTrack(track);
			map.setTile(tile,latitude,longitude);
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
			//logger.info("Track failed to be removed because there is no track at the tile to remove");
			throw new CannotRemoveTrackException();
		}
		else {
			tile.removeTrack();
			map.setTile(tile,latitude,longitude);
		}
	}
	
	/* Getters and Setters */
	
	public Level getLevelWithTrack() {
		levelToAddTrack.setMap(map);
		return levelToAddTrack;
	}
}
