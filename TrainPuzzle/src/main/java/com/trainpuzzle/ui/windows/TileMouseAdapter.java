package com.trainpuzzle.ui.windows;

import com.trainpuzzle.exception.CannotPlaceTrackException;
import com.trainpuzzle.exception.CannotRemoveTrackException;

import com.trainpuzzle.controller.TrackPlacer;

import com.trainpuzzle.model.board.CompassHeading;
import com.trainpuzzle.model.board.Connection;
import com.trainpuzzle.model.board.Track;
import com.trainpuzzle.model.board.TrackType;

import javax.swing.*;
import java.awt.event.*;

public class TileMouseAdapter extends MouseAdapter {
	private TrackPlacer trackPlacer;
	private Track track;
		
	public TileMouseAdapter(TrackPlacer trackPlacer) {
		super();
		this.trackPlacer = trackPlacer;
		track = new Track(new Connection(CompassHeading.EAST, CompassHeading.WEST), TrackType.STRAIGHT_TRACK);
	}
		
	public void  mousePressed(MouseEvent e) {
        JComponent c = (JComponent) e.getSource();
        
        int row = c.getY()/40;
        int column = c.getX()/40;
            
        if (e.getButton() == MouseEvent.BUTTON1) {
            try {
            	trackPlacer.placeTrack(track, row, column);
        	} 
            catch(CannotPlaceTrackException ex) {
            	LoadedLevelScreen loadedLevelScreen = (LoadedLevelScreen)WindowManager.getManager().getActiveWindow();
            	loadedLevelScreen.setMessageBoxMessage(ex.getMessage());
            }
        }
        else if(e.getButton() == MouseEvent.BUTTON3) {
            try {
            	trackPlacer.removeTrack(row, column);
            }
            catch(CannotRemoveTrackException ex) {
            	LoadedLevelScreen loadedLevelScreen = (LoadedLevelScreen)WindowManager.getManager().getActiveWindow();
            	loadedLevelScreen.setMessageBoxMessage(ex.getMessage());
            }
        }
    }
			
	public TrackPlacer getTrackPlacer() {
		return trackPlacer;
	}
	
	public void setTrack(Track track) {
		this.track = track;
	}
}