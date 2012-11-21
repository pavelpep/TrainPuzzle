package com.trainpuzzle.controller;

import org.apache.log4j.Logger;

import com.trainpuzzle.exception.CannotRemoveTrackException;
import com.trainpuzzle.exception.CannotToggleSwitchException;
import com.trainpuzzle.model.board.Board;
import com.trainpuzzle.model.board.Switch;
import com.trainpuzzle.model.board.Tile;
import com.trainpuzzle.model.board.Track;
import com.trainpuzzle.model.board.TrackType;
import com.trainpuzzle.model.board.Landscape.LandscapeType;
import com.trainpuzzle.model.level.Level;

public class SwitchToggler {
	private Logger logger = Logger.getLogger(TrackPlacer.class);
	private Level levelToAddTrack;
	private Board map;
	
	public SwitchToggler(Level levelToToggleSwitch) {
		this.levelToAddTrack = levelToToggleSwitch;
		this.map = levelToAddTrack.getBoard();
	}
	
	public void toggleSwitch(int row, int column) throws CannotToggleSwitchException {
		Tile tile = map.getTile(row, column);
		Track track;
		
		String errorMessage = getToggleSwitchErrorMessage(tile);
		if(hasNoError(errorMessage)) {
			track = tile.getTrack();
			((Switch)track).toggle();
			map.notifyAllObservers();
		}
		else {
			logger.warn("CannotRemoveTrackException was thrown");
			throw new CannotToggleSwitchException(errorMessage);
		}
	}
	
	private String getToggleSwitchErrorMessage(Tile tile) {
		String errorMessage = "";
		String commonMessage = "Failed to toggle a switch because ";
		
		if (!tile.hasTrack()) {
			errorMessage = commonMessage + "there was no track";
		} 
		else if (!tile.getTrack().isSwitch()) {
			errorMessage = commonMessage + "the track is not a switch";
		}
		return errorMessage;
	}
	
	private boolean hasNoError(String errorMessage) {
		return errorMessage == "";
	}
}
