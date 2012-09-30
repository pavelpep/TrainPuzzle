package com.trainpuzzle.controller;
import com.trainpuzzle.model.map.*;
/**
 * 
 * @author huachuandeng
 *
 */
public class Simulator {
	public static final int Latitude = 0;
	public static final int Longitude =1;
	private Map map;
	private Train train;
	Simulator(TrackBuilder trackBuilder){
		this.map = trackBuilder.getMap();
		this.train= new Train();
		int[] startPoint= map.getStartPoint();
		this.train.setLocation(startPoint[0],startPoint[1]);
		this.train.setHeading(Track.Heading.EAST);
	}
	
	/**
	 * A function to check whether the user win the game
	 * @return a boolean to tell whether the train finish all requirements 
	 */
	private boolean isVictory(){
		return true;
	}
	
	/**
	 * 
	 * @param location train's current location
	 * @param headingValue where train heading to
	 * @return
	 */
	private int[] getNextTrack(int[] location, int headingValue){
		switch(headingValue){
			case 0:
				location[Latitude] = location[Latitude]-1;
				location[Longitude] = location[Longitude]-1;
				break;
			case 1:
				location[Longitude] = location[Longitude]-1;
				return location;
			case 2:
				location[Latitude] = location[Latitude]+1;
				location[Longitude] = location[Longitude]-1;
				break;
			case 3:
				location[Latitude] = location[Latitude]+1;
				break;
			case 4:
				location[Latitude] = location[Latitude]+1;
				location[Longitude] = location[Longitude]+1;
				break;
			case 5:
				location[Longitude] = location[1]+1;
				break;
			case 6: 
				location[Latitude] = location[Latitude]-1;
				location[1] = location[1]+1;
				break;
			case 7:
				location [Latitude] = location[Latitude]-1;
				break;
		}
		return location;
	}
	//TODO change integer to static 
	
	/**
	 * move the train to next tile according to its heading 
	 * @return whether the train can go to next tile or not
	 */
	public boolean goNextTrack(){
		int[] location = train.getLocation();
		Track.Heading heading =train.getHeading();
		int headingValue =heading.getValue();
		location = getNextTrack(location,headingValue);
		Tile tile = map.getTile (location[0],location[1]);
		Track track = tile.getTrack;
		if(!tile.hasTrack()){
		return false;
		}
		
	}
	/*public void setTrain(Train train){
		this.train = train;
	}*/
	public Train getTrain(){
		return this.train;
	}

}