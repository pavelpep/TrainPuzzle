package com.trainpuzzle.factory.level_strategy;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.trainpuzzle.controller.CargoGenerator;
import com.trainpuzzle.controller.CargoRequestGenerator;
import com.trainpuzzle.model.board.Board;
import com.trainpuzzle.model.board.Cargo;
import com.trainpuzzle.model.board.CompassHeading;
import com.trainpuzzle.model.board.Connection;
import com.trainpuzzle.model.board.Location;
import com.trainpuzzle.model.board.Obstacle;
import com.trainpuzzle.model.board.Station;
import com.trainpuzzle.model.board.Track;
import com.trainpuzzle.model.board.TrackType;
import com.trainpuzzle.model.board.Landscape.LandscapeType;
import com.trainpuzzle.model.board.Obstacle.ObstacleType;
import com.trainpuzzle.model.board.Station.StationType;
import com.trainpuzzle.model.level.Level;
import com.trainpuzzle.model.level.victory_condition.AndVictoryCondition;
import com.trainpuzzle.model.level.victory_condition.DropCargoEvent;
import com.trainpuzzle.model.level.victory_condition.Event;
import com.trainpuzzle.model.level.victory_condition.LeafVictoryCondition;
import com.trainpuzzle.model.level.victory_condition.LogicalVictoryCondition;
import com.trainpuzzle.model.level.victory_condition.VictoryCondition;

public abstract class LevelOutline {
	protected final int NO_ECONOMY_LIMIT = -1;
	
	protected Board board = new Board();
    protected List<CargoGenerator> cargoGenerators = new LinkedList<CargoGenerator>();
    protected List<CargoRequestGenerator> cargoRequestGenerators =  new LinkedList<CargoRequestGenerator>();
	protected LogicalVictoryCondition root;
	
	public abstract Level createLevel();
	
	protected void setStartLocation(Location location, CompassHeading compassHeading1, CompassHeading compassHeading2, TrackType trackType) {
		Track startingTrack = new Track(new Connection(compassHeading1, compassHeading2), trackType);
		startingTrack.setUnremoveable();
		this.board.getTile(location).setTrack(startingTrack);
	}
	
	protected void setStations(ArrayList<Station> stations) {
		for (Station station : stations) {
			board.getTile(station.getStationLocation()).setStationBuilding(station);
			board.getTile(station.getTrackLocation()).setStationTrack(station);
			
			// Adds victory condition for station
			Event event = new Event(100,station); 
			LeafVictoryCondition leaf = new LeafVictoryCondition(event);
			root.addChild(leaf);
			
			}
		}
 	
	protected void setLandscapes(ArrayList<Location> locations, LandscapeType landscapeType) {
		for (Location currentLocation : locations) {
			this.board.getTile(currentLocation).setLandscapeType(landscapeType);
		}
	}
	
	protected void setLandscapeByRow(int row, int columnStart, int columnEnd, LandscapeType landscapeType) {
		for (int i = columnStart; i <= columnEnd; i++){
			this.board.getTile(row, i).setLandscapeType(landscapeType);
		}
	}
	
	protected void setLandscapeByColumn(int rowStart, int rowEnd, int column, LandscapeType landscapeType) {
		for (int i = rowStart; i <= rowEnd; i++){
			this.board.getTile(i, column).setLandscapeType(landscapeType);
		}
	}
	
	protected void setObstaclesByRow(int row, int columnStart, int columnEnd, ObstacleType obstacleType) {
		for (int i = columnStart; i <= columnEnd; i++){
			this.board.getTile(row, i).setObstacle(new Obstacle(obstacleType));
		}
	}
	
	protected void setObstaclesByColumn(int rowStart, int rowEnd, int column, ObstacleType obstacleType) {
		for (int i = rowStart; i <= rowEnd; i++){
			this.board.getTile(i, column).setObstacle(new Obstacle(obstacleType));
		}
	}
	
	protected void setObstacles(ArrayList<Location> locations, ObstacleType obstacleType) {
		for (Location currentLocation : locations) {
			this.board.getTile(currentLocation).setObstacle(new Obstacle(obstacleType));
		}
	}
	
	protected void addImportCargo (Station station, LogicalVictoryCondition condiction, Cargo cargo) {
		station.addImportCargo(cargo);
		DropCargoEvent event = new DropCargoEvent(100,station, cargo);
		LeafVictoryCondition lcondition = new LeafVictoryCondition(event);
		condiction.addChild(lcondition);
		
	}
	
}
