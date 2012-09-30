package com.trainpuzzle.controller;

import java.util.Set;

import org.apache.log4j.Logger;

import com.trainpuzzle.model.map.*;

/**
 * 
 * @author $Author$
 * @version $Revision$
 * @since $Date$
 *
 */
public class Simulator {
	private Logger logger = Logger.getLogger(Simulator.class);
	
	public static final int LATITUDE = 0;			// x axis 
	public static final int LONGITUDE = 1;		// y axis
	
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
	 * Get location that the train is heading
	 * @param location train's current location
	 * @param headingValue where train heading to
	 * @return an int array holdiing latitude and longitude for next tile the is going to
	 */
	private int[] getNextTile(int[] location, Track.Heading heading){
		switch(heading){
		case NORTHWEST:
			location[LATITUDE] = location[LATITUDE]-1;
			location[LONGITUDE] = location[LONGITUDE]-1;
			break;
		case NORTH:
			location[LONGITUDE] = location[LONGITUDE]-1;
			return location;
		case NORTHEAST:
			location[LATITUDE] = location[LATITUDE]+1;
			location[LONGITUDE] = location[LONGITUDE]-1;
			break;
		case EAST:
			location[LATITUDE] = location[LATITUDE]+1;
			break;
		case SOUTHEAST:
			location[LATITUDE] = location[LATITUDE]+1;
			location[LONGITUDE] = location[LONGITUDE]+1;
			break;
		case SOUTH:
			location[LONGITUDE] = location[1]+1;
			break;
		case SOUTHWEST: 
			location[LATITUDE] = location[LATITUDE]-1;
			location[1] = location[1]+1;
			break;
		case WEST:
			location [LATITUDE] = location[LATITUDE]-1;
			break;
	}
	return location;

	}
	//TODO change integer to static 
	/**
	 *  Get where the train heading in next track and change heading stored in train
	 * @param track the track lay on the tile the train heading to 
	 * @param heading direction the train heading now
	 * @return whether the train get into the next track successfully or not 
	 */
	private boolean getNextHeading(Track track, Track.Heading heading){
		heading = heading.opposite();
		Set<Connection> connections = track.getConnections();
		for(Connection connection : connections){
			int[] directions = connection.getHeadingValues();
			if(directions[0] == heading.getValue()){
				heading = Track.Heading.getHeading(directions[1]);
				this.train.setHeading(heading);
				return true;
			}
			else if(directions[1] == heading.getValue()){
				heading = Track.Heading.getHeading(directions[0]);
				this.train.setHeading(heading);
				return true;
			}
		}
		return false;
		
	}
	
	/**
	 * move the train to next tile according to its heading and change its heading, too
	 * @return whether the train goes to next tile successfully or not
	 */
	public boolean go(){
		int[] location = train.getLocation();
		Track.Heading heading = train.getHeading();
		location = getNextTile(location,heading);
		train.setLocation(location[0], location[1]);
		Tile tile = map.getTile (location[0],location[1]);
		Track track = tile.getTrack();
		if(!tile.hasTrack()||!getNextHeading(track,heading)){
			return false;
		} 
		return true;
	}
	
	/*public void setTrain(Train train){
		this.train = train;
	}*/
	public Train getTrain(){
		return this.train;
	}

}