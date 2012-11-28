package com.trainpuzzle.factory.level_strategy;

import java.util.ArrayList;


import com.trainpuzzle.model.board.Cargo;
import com.trainpuzzle.model.board.CompassHeading;
import com.trainpuzzle.model.board.Location;
import com.trainpuzzle.model.board.Station;
import com.trainpuzzle.model.board.TrackType;
import com.trainpuzzle.model.board.Cargo.CargoType;
import com.trainpuzzle.model.board.Landscape.LandscapeType;
import com.trainpuzzle.model.board.Obstacle.ObstacleType;
import com.trainpuzzle.model.board.Station.StationType;
import com.trainpuzzle.model.level.Economy;
import com.trainpuzzle.model.level.Level;
import com.trainpuzzle.model.level.victory_condition.AndVictoryCondition;


public class LevelFive extends LevelOutline{

	@Override
	public Level createLevel() {
		 this.root = new AndVictoryCondition();

	     setLandscape();
	     initStationsAndCargoGenerator();
	     setObstacles();
	        
	     Economy economy = createEconomy();
	        
	     Level level=new Level(5, this.board, createStartLocation(), this.root, economy);
	     level.setCargoGenerators(cargoGenerators);
	     level.setCargorequestors(cargoRequestGenerators);
	     level.setTimeLimit(60);
	        	        
	     return level;
	}	
	private Economy createEconomy() {

        Economy economy = new Economy();
		return economy;
	}

	private Location createStartLocation() {
		Location startLocation = new Location(1,1);
        setStartLocation(startLocation, CompassHeading.WEST, CompassHeading.EAST, TrackType.STRAIGHT_TRACK);
		return startLocation;
	}

	private void initStationsAndCargoGenerator() {
		ArrayList<Station> stations = new ArrayList<Station>();
        Station stationToAdd = new Station(StationType.FACTORY, new Location(1, 14), CompassHeading.SOUTH);
        stations.add(stationToAdd);
        addCargoGenerator(stationToAdd, 16, CargoType.IRON);
               
        stationToAdd = new Station(StationType.REQUESTER, new Location(7,12), CompassHeading.EAST);
        stations.add(stationToAdd);
        /*CargoRequestGenerator requester = new CargoRequestGenerator(stationToAdd, root, 4, CargoType.IRON);
        cargoRequestGenerators.add(requester);*/
        addCargoRequester(stationToAdd, 16, CargoType.IRON);
        
        stationToAdd = new Station(StationType.GREEN, new Location(4, 7), CompassHeading.SOUTH);
        stations.add(stationToAdd);
        stationToAdd.addExportCargo(new Cargo(CargoType.IRON));
        
        stationToAdd = new Station(StationType.RED, new Location(5, 11), CompassHeading.SOUTH);
        stations.add(stationToAdd);
        
        stationToAdd = new Station(StationType.GREEN, new Location(5, 12), CompassHeading.SOUTH);
        stations.add(stationToAdd);
        
        stationToAdd = new Station(StationType.RED, new Location(9, 12), CompassHeading.SOUTH);
        stations.add(stationToAdd);
        
        setPassStations(stations);
	}	
	

	private void setLandscape() {
		
        
	}

	private void setObstacles() {
		
	}


}
