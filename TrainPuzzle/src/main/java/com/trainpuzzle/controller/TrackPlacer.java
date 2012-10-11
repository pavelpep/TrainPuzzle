package com.trainpuzzle.controller;
import org.apache.log4j.Logger;

import com.trainpuzzle.model.level.Level;
import com.trainpuzzle.model.map.*;

/**
 * 
 * @author $Author$
 * @version $Revision$
 * @since $Date$
 */
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
	 * @return 0 means fail to add a track on the tile, 1 means add a track successfully  
	 */
	public boolean placeTrack(Track track,int latitude,int longitude) {
		Tile tile = map.getTile(latitude, longitude);
		if(tile.hasTrack()||tile.hasObstacle()) {
			logger.info("Track failed to be added to tile because there was an obstacle");
			return false;
		}
		else{
			tile.setTrack(track);
			map.setTile(tile,latitude,longitude);
			return true;
		}
		
	}
	/**
	 * this  function remove a track on tile on location(latitude,longitude)
	 * 
	 * @param latitude
	 * @param longitude
	 * @return 0 means fall to remove a track on the tile, 1 means remove a track successfully
	 */
	public boolean removeTrack(int latitude, int longitude) {
		Tile tile = map.getTile(latitude, longitude);
		if(!tile.hasTrack()||tile.hasObstacle()) {
			logger.info("Track failed to be removed because there is no track at the tile to remove");
			return false;
		}
		else {
			tile.removeTrack();
			map.setTile(tile,latitude,longitude);
			return true;
		}
	}
	
	/* Getters and Setters */
	
	public Level getLevelWithTrack() {
		levelToAddTrack.setMap(map);
		return levelToAddTrack;
	}
}
