package com.trainpuzzle.model.board;

import java.util.HashMap;

public enum TrackType {
	TRACK(null),
		STRAIGHT(TRACK),
			STRAIGHT_TRACK(STRAIGHT),
			DIAGONAL_TRACK(STRAIGHT),
		CURVE(TRACK),
			CURVELEFT_TRACK(CURVE),
			CURVERIGHT_TRACK(CURVE),
		INTERSECTION(TRACK),
			INTERSECTION_TRACK(INTERSECTION),
			DIAGONAL_INTERSECTION_TRACK(INTERSECTION),
		SWITCH(TRACK),
			CURVELEFT_STRAIGHT_SWITCH(SWITCH),
			CURVERIGHT_STRAIGHT_SWITCH(SWITCH);
		
	TrackType(TrackType parent) {
		this.parent = parent;
	}
	private TrackType parent = null;
	public TrackType getParent() {
		return parent;
	}
		
	  
	 
	
	
}
