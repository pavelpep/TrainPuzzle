package com.trainpuzzle.ui.windows;

import com.trainpuzzle.exception.CannotPlaceTrackException;

import com.trainpuzzle.controller.TrackPlacer;

import com.trainpuzzle.model.map.Track;

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
			track = new Track();
		}
		
		public void  mousePressed(MouseEvent e) {
                JComponent c = (JComponent) e.getSource();
                int row = c.getY()/40;
                int column = c.getX()/40;
                
                try {
                	trackPlacer.placeTrack(track, row, column);
                }catch(CannotPlaceTrackException ex) {
                	System.out.println(ex.getMessage());
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
