package com.trainpuzzle.model.board;


public enum TrackType {
	TRACK(null,0),
		STRAIGHT(TRACK,0),
			STRAIGHT_TRACK(STRAIGHT,10),
			DIAGONAL_TRACK(STRAIGHT,10),
		CURVE(TRACK,0),
			CURVELEFT_TRACK(CURVE,10),
			CURVERIGHT_TRACK(CURVE,10),
		INTERSECTION(TRACK,0),
			INTERSECTION_TRACK(INTERSECTION,10),
			DIAGONAL_INTERSECTION_TRACK(INTERSECTION,10),
		SWITCH(TRACK,0),
			CURVELEFT_STRAIGHT_SWITCH(SWITCH,10),
			CURVERIGHT_STRAIGHT_SWITCH(SWITCH,10);
	TrackType(TrackType parent,int price) {
		this.parent = parent;
		this.price = price;
	}
	private int price;
	private TrackType parent = null;
	
	public TrackType getParent() {
		return parent;
	}
	public int getPrice() {
		return this.price;
	}
		
	  
	 
	
	
}
