package com.trainpuzzle.ui.windows;

import com.trainpuzzle.exception.CannotOperateTrackException;
import com.trainpuzzle.exception.CannotPlaceTrackException;
import com.trainpuzzle.exception.CannotRemoveTrackException;
import com.trainpuzzle.exception.CannotOperateSwitchException;

import com.trainpuzzle.controller.SwitchController;
import com.trainpuzzle.controller.TrackPlacer;

import com.trainpuzzle.model.board.CompassHeading;
import com.trainpuzzle.model.board.Connection;
import com.trainpuzzle.model.board.Switch;
import com.trainpuzzle.model.board.Track;
import com.trainpuzzle.model.board.TrackType;
import com.trainpuzzle.model.level.Level;

import javax.swing.*;
import java.awt.event.*;

public class TileMouseAdapter extends MouseAdapter {
	private TrackPlacer trackPlacer;
	private SwitchController switchController;
	private Track track;
	
	private Mode mode;
	
	public enum Mode {
		PlaceTrack,
		Toggle
	}
		
	public TileMouseAdapter(Level level) {
		super();
		trackPlacer = new TrackPlacer(level);
		switchController = new SwitchController(level);
		track = new Track(new Connection(CompassHeading.EAST, CompassHeading.WEST), TrackType.STRAIGHT_TRACK);
		mode = Mode.PlaceTrack;
	}
		
	public void  mousePressed(MouseEvent e) {
        JComponent c = (JComponent) e.getSource();
        
        int row = c.getY()/40;
        int column = c.getX()/40;
        
        try {
        	if (e.getButton() == MouseEvent.BUTTON1) {
	        	if(mode == Mode.PlaceTrack) { 
		            placeTrackAt(row, column);
	        	} else if(mode == Mode.Toggle) {
					switchController.operateSwitch(row, column);
	        	}
        	} else if(e.getButton() == MouseEvent.BUTTON3) {
            	trackPlacer.removeTrack(row, column);
        	}
		} catch(CannotOperateTrackException ex) {
			LoadedLevelScreen loadedLevelScreen = (LoadedLevelScreen)WindowManager.getManager().getActiveWindow();
			loadedLevelScreen.setMessageBoxMessage(ex.getMessage());
		}
    }

	private void placeTrackAt(int row, int column) throws CannotPlaceTrackException {
		Track copyOfTrack;
		if(!track.isSwitch()) {
			copyOfTrack = new Track(track);
		} else {
			copyOfTrack = new Switch((Switch)track);
		}
		trackPlacer.placeTrack(copyOfTrack, row, column);
	}
			
	public TrackPlacer getTrackPlacer() {
		return trackPlacer;
	}
	
	public void setTrack(Track track) {
		this.track = track;
		setPlaceTrackMode();
	}
	
	public void setPlaceTrackMode() {
		mode = Mode.PlaceTrack;
	}
	
	public void setToggleMode() {
		mode = Mode.Toggle;
	}
}