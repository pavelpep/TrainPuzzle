package com.trainpuzzle.controller;

import org.apache.log4j.Logger;

import com.trainpuzzle.exception.CannotPlaceTrackException;
import com.trainpuzzle.exception.CannotRemoveTrackException;
import com.trainpuzzle.exception.CannotRotateException;
import com.trainpuzzle.model.board.Board;
import com.trainpuzzle.model.board.Tile;
import com.trainpuzzle.model.board.Track;
import com.trainpuzzle.model.board.Landscape.LandscapeType;
import com.trainpuzzle.model.level.Level;

import com.trainpuzzle.model.level.Economy;
import com.trainpuzzle.model.board.TrackType;

public class TrackPlacer {
	private Logger logger = Logger.getLogger(TrackPlacer.class);
	private Level levelToAddTrack;
	private Economy economy;
	private Board map;
	
	public TrackPlacer(Level levelToAddTrack) {
		this.levelToAddTrack = levelToAddTrack;
		this.economy = levelToAddTrack.getEconomy();
		this.map = levelToAddTrack.getBoard();
	}
	
	public void placeTrack(Track track, int row, int column) throws CannotPlaceTrackException {
		Tile tile = map.getTile(row, column);
		String errorMessage = getPlaceTrackErrorMessage(tile, track);
		if(hasNoError(errorMessage)) {
			tile.setTrack(track);
			TrackType trackType = track.getTrackType();
			economy.useOnePieceOfTrack(trackType);
			map.notifyAllObservers();
		}
		else {
			logger.warn("CannotPlaceTrackException was thrown");
			throw new CannotPlaceTrackException(errorMessage);			
		}
	}
	
	private String getPlaceTrackErrorMessage(Tile tile, Track track) {
		String errorMessage = "";
		String commonMessage = "Failed to place track on a tile because ";
		
		if (tile.hasTrack()) {
			errorMessage = commonMessage + "there was already a track";
		} 
		else if (tile.hasObstacle()) {
			errorMessage = commonMessage + "there was an obstacle";
		} 
		else if (tile.getLandscapeType() == LandscapeType.WATER) {
			errorMessage = commonMessage + "there was a water landscape";
		}
		else if (!economy.isAvailable(track.getTrackType())) {
			errorMessage = commonMessage + "the track is out of limit";
		}
		return errorMessage;
	}
	
	private boolean hasNoError(String errorMessage) {
		return errorMessage == "";
	}
	
	public void removeTrack(int row, int column) throws CannotRemoveTrackException {
		Tile tile = map.getTile(row, column);
		String errorMessage = getRemoveTrackErrorMessage(tile);
		if(hasNoError(errorMessage)) {
			TrackType trackType=tile.getTrack().getTrackType();
			tile.removeTrack();
			economy.returnOnePieceOfTrack(trackType);
			map.notifyAllObservers();
		}
		else {
			logger.warn("CannotRemoveTrackException was thrown");
			throw new CannotRemoveTrackException(errorMessage);
		}
	}
	
	private String getRemoveTrackErrorMessage(Tile tile) {
		String commonMessage = "Failed to be removed track from a tile because ";
		String errorMessage = "";
		if(!tile.hasTrack()) {
			errorMessage = commonMessage + "there was no track to be removed";
		}
		else if(!tile.getTrack().isRemovable()) {
			errorMessage = commonMessage + "it's an unremovable track";
		}
		return errorMessage;
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