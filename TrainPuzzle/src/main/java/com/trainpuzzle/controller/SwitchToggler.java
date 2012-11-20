package com.trainpuzzle.controller;

import org.apache.log4j.Logger;

import com.trainpuzzle.model.board.Board;
import com.trainpuzzle.model.board.Switch;
import com.trainpuzzle.model.board.Tile;
import com.trainpuzzle.model.board.Track;
import com.trainpuzzle.model.level.Level;

public class SwitchToggler {
	private Logger logger = Logger.getLogger(TrackPlacer.class);
	private Level levelToAddTrack;
	private Board map;
	
	public SwitchToggler(Level levelToToggleSwitch) {
		this.levelToAddTrack = levelToToggleSwitch;
		this.map = levelToAddTrack.getBoard();
	}
	
	public void toggleSwitch(int row, int column) {
		Tile tile = map.getTile(row, column);
		Track track;
		
		if(tile.hasTrack()) {
			track = tile.getTrack();
			if(track.isSwitch()) {
				((Switch)track).toggle();
			}
		}
	}
}
