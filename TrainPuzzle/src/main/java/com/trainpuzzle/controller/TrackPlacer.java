package com.trainpuzzle.controller;


import org.apache.log4j.Logger;
import com.trainpuzzle.exception.CannotPlaceTrackException;
import com.trainpuzzle.exception.CannotRemoveTrackException;
import com.trainpuzzle.exception.CannotRotateException;
import com.trainpuzzle.model.board.*;
import com.trainpuzzle.model.board.Landscape.LandscapeType;
import com.trainpuzzle.model.level.Level;

public class TrackPlacer {
	private Logger logger = Logger.getLogger(TrackPlacer.class);
	private Level levelToAddTrack;
	private Board map;
	
	public TrackPlacer(Level levelToAddTrack) {
		this.levelToAddTrack = levelToAddTrack;
		this.map = levelToAddTrack.getBoard();
	}

	public void placeTrack(Track track, int row, int column) throws CannotPlaceTrackException {
		Tile tile = map.getTile(row, column);
		String errorMessage = "Failed to place track on a tile because ";
		
		if (tile.hasTrack()) {
			errorMessage += "there was already a track";
		} 
		else if (tile.hasObstacle()) {
			errorMessage += "there was an obstacle";
		} 
		else if (tile.getLandscapeType() == LandscapeType.WATER) {
			errorMessage += "landscape type is water";
		}
		else {
			tile.setTrack(track);
			map.notifyAllObservers();
			return;
		}
		logger.warn("CannotPlaceTrackException was thrown");
		throw new CannotPlaceTrackException(errorMessage);
	}

	public void removeTrack(int row, int column) throws CannotRemoveTrackException {
		Tile tile = map.getTile(row, column);
		String errorMessage = "Track failed to be removed because ";
		
		if(tile.hasTrack()) {			
			if (tile.getTrack().isRemovable()) {
				tile.removeTrack();
				map.notifyAllObservers();
				return;
			} 
			else {
				errorMessage += "there was an unremovable track";
			}
		} 
		else {
			errorMessage += "there was no track at the tile to remove";
		}
		logger.warn("CannotRemoveTrackException was thrown");
		throw new CannotRemoveTrackException(errorMessage);
	}
	
	public void rotateTrack(int row, int column) throws CannotRotateException {
		Tile tile = map.getTile(row, column);
		
		if(tile.hasTrack()) {
			tile.rotateTrack();
			map.notifyAllObservers();
		}
		else {
			logger.warn("CannotRotateException was thrown");
			throw new CannotRotateException("No track to be rotated on this tile");
		}		
	}
	
	public Level getLevelWithTrack() {
		return levelToAddTrack;
	}	
}
