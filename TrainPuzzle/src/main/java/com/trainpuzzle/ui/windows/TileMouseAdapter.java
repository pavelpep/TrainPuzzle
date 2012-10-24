package com.trainpuzzle.ui.windows;

import com.trainpuzzle.exception.CannotPlaceTrackException;
import com.trainpuzzle.exception.CannotRemoveTrackException;

import com.trainpuzzle.controller.TrackPlacer;

import com.trainpuzzle.model.board.CompassHeading;
import com.trainpuzzle.model.board.Connection;
import com.trainpuzzle.model.board.Track;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

class TileMouseAdapter extends MouseAdapter {
        private TrackPlacer trackPlacer;
		private Track track;
		
		TileMouseAdapter() {
			super();
		}
		
		TileMouseAdapter(TrackPlacer trackPlacer) {
			super();
			this.trackPlacer = trackPlacer;
			track = new Track(new Connection(CompassHeading.EAST, CompassHeading.WEST));
		}
		
		public void  mousePressed(MouseEvent e) {
                JComponent c = (JComponent) e.getSource();
                if (e.getButton() == MouseEvent.BUTTON1){
                	
                }
                int row = c.getY()/40;
                int column = c.getX()/40;
                
                if (e.getButton() == MouseEvent.BUTTON1){
	                try {
	                	trackPlacer.placeTrack(track, row, column);
	                }catch(CannotPlaceTrackException ex) {
	                	//TODO remove system.out and send to UI
	                	System.out.println(ex.getMessage());
	                }
                }else if(e.getButton() == MouseEvent.BUTTON3){
	                try {
	                	trackPlacer.removeTrack(row, column);
	                }catch(CannotRemoveTrackException ex) {
	                	//TODO remove system.out and send to UI
	                	System.out.println(ex.getMessage());
	                }
                }
              //TransferHandler handler = c.getTransferHandler();
                //handler.exportAsDrag(c, e, TransferHandler.COPY);
                //System.out.println(row+ " , " +column);
                //return position;
        }
		
		
		public TrackPlacer getTrackPlacer(){
			return trackPlacer;
		}
		
		public void setTrack(Track track){
			this.track = track;
		}
        
}
