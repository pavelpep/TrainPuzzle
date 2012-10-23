package com.trainpuzzle.model.board;

import java.util.HashSet;
import java.util.Set;

import com.trainpuzzle.observe.Observable;
import com.trainpuzzle.observe.Observer;


public class Tile implements Observable {

	private int elevation;
	private Track track;
	private Obstacle obstacle;
	private Landscape landscape;
	private Station station;
	
	private Set<Observer> observerList = new HashSet<Observer>();

  /* Public Interface */

	public Tile(){	
		elevation = 0;
		landscape = new Landscape(Landscape.LandscapeType.GRASS);
	}
	
	public void register(Observer observer){
		observerList.add(observer);
	}
	
	public void notifyAllObservers(){
		for(Observer observer : observerList) {
			observer.notifyChange(this);
		}
	}

	public void removeTrack() {
		track = null;
		notifyAllObservers();
	}
	
	public boolean hasTrack() {
		return (track != null);
	}	
	
	public boolean hasObstacle() {
		return (obstacle != null);
	}
	
	public boolean hasStationBuildingOrStationTrack() {
		return (station != null);
	}
	
	public boolean hasStation(Location location) {
		return (station != null) && !getStation().isStationTrack(location);
	}
	
	public boolean hasStationTrack(Location location) {
		return (station != null) && getStation().isStationTrack(location);
	}
	
	
/* Getters and Setters */
	
	public int getElevation() {
		return elevation;
	}

	public void setElevation(int elevation) {
		this.elevation = elevation;
		notifyAllObservers();
	}
	
	public Track getTrack() {
		return track;
	}
	
	public void setTrack(Track track) {
		this.track = track;
		notifyAllObservers();
	}
	
	public void rotateTrack() {
		this.track.rotateTrack();
		notifyAllObservers();
	}
	
	public Landscape.LandscapeType getLandscapeType() {
		return landscape.getType();
	}
	
	public void setLandscapeType(Landscape.LandscapeType type) {
		this.landscape.setType(type);
		notifyAllObservers();
	}
	
	public Station getStation() {
		return station;
	}
	
	public void setStationBuilding(Station station) {
		this.station = station;
		this.obstacle = station.getBuilding();
		notifyAllObservers();
	}
	
	public void setStationTrack(Station station) {
		this.station = station;
		this.track = station.getTrack();
		notifyAllObservers();
	}	
	
	public Station.StationType getStationType() {
		return station.getType();
	}

	public void setObstacle(Obstacle obstacle) {
		this.obstacle = obstacle;
		notifyAllObservers();
	}
	
	public Obstacle.ObstacleType getObstacleType() {
		return obstacle.getType();
	}
	
}