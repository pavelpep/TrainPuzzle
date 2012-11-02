package com.trainpuzzle.model.board;

import java.util.HashMap;

public enum TrackType {
	TRACK(null,60),
		STRAIGHT(TRACK, 20),
			STRAIGHT_TRACK(STRAIGHT, 15),
			DIAGONAL_TRACK(STRAIGHT,15),
		CURVE(TRACK, 20),
			CURVELEFT_TRACK(CURVE,15),
			CURVERIGHT_TRACK(CURVE,15),
		INTERSECTION(TRACK, 15),
			INTERSECTION_TRACK(INTERSECTION, 10),
			DIAGONAL_INTERSECTION_TRACK(INTERSECTION,10);

	private TrackType parent = null;
	private int currentTrackLimit = 0;
	private TrackType(TrackType parent, Integer trackLimit){
		this.parent = parent;
		this.currentTrackLimit = trackLimit;
		}
	
		
	 public int getTrackLimit(TrackType trackType){
		 return trackType.currentTrackLimit;
	 }
	 
	 public void incrementTrackLimit(TrackType trackType){
		 trackType.currentTrackLimit++;
		 TrackType parent = trackType.parent;
		 TrackType grandparent = parent.parent;
		 parent.currentTrackLimit++;
		 grandparent.currentTrackLimit++;
	 }
	 
	 public void decrementTrackLimit(TrackType trackType){
		 trackType.currentTrackLimit--;
		 TrackType parent = trackType.parent;
		 TrackType grandparent = parent.parent;
		 parent.currentTrackLimit--;
		 grandparent.currentTrackLimit--;
	 }	 
	
	
}
