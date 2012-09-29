package com.trainpuzzle.controller;
import com.trainpuzzle.model.map.*;
/**
 * 
 * @author huachuandeng
 *
 */
public class TrackBuilder {
	private Map map;
	
	TrackBuilder(Level level){
		this.map = level.getMap();
		
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}
	/**
	 * this function add a track on tile on location (latitude,longitude)
	 * @param track latitude longitude
	 * @return 0 means fall to add a track on the tile, 1 means add a track successfully  
	 */
	public boolean addTrack(Track track,int latitude,int longitude){
		Tile tile = map.getTile(latitude, longitude);
		if(tile.hasTrack()||tile.hasObstacle()){
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
	 * @param latitude
	 * @param longitude
	 * @return 0 means fall to remove a track on the tile, 1 means remove a track successfully
	 */
	public boolean deleteTrack(int latitude, int longitude){
		Tile tile = map.getTile(latitude, longitude);
		if(!tile.hasTrack()||tile.hasObstacle()){
		return false;
		}
		else{
			tile.removeTrack();
			map.setTile(tile,latitude,longitude);
			return true;
		}
	}
}
