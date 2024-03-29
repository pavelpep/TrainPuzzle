package com.trainpuzzle.controller;

import org.apache.log4j.Logger;

import com.trainpuzzle.exception.CannotOperateSwitchException;
import com.trainpuzzle.model.board.Board;
import com.trainpuzzle.model.board.Switch;
import com.trainpuzzle.model.board.Tile;
import com.trainpuzzle.model.board.Track;
import com.trainpuzzle.model.level.Level;

public class SwitchController {
	private Logger logger = Logger.getLogger(TrackPlacer.class);
	private Level levelToAddTrack;
	private Board map;
	
	public SwitchController(Level levelToOperateSwitch) {
		this.levelToAddTrack = levelToOperateSwitch;
		this.map = levelToAddTrack.getBoard();
	}
	
	public void operateSwitch(int row, int column) throws CannotOperateSwitchException {
		Tile tile = map.getTile(row, column);
		Track track;
		
		String errorMessage = getOperateSwitchErrorMessage(tile);
		if(hasNoError(errorMessage)) {
			track = tile.getTrack();
			((Switch)track).toggle();
		}
		else {
			logger.warn("CannotRemoveTrackException was thrown");
			throw new CannotOperateSwitchException(errorMessage);
		}
	}
	
	private String getOperateSwitchErrorMessage(Tile tile) {
		String errorMessage = "";
		String commonMessage = "Failed to operate a switch because ";
		
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
