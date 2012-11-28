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
        addCargoGenerator(stationToAdd, 12, CargoType.IRON);
               
        stationToAdd = new Station(StationType.REQUESTER, new Location(7,12), CompassHeading.EAST);
        stations.add(stationToAdd);
        /*CargoRequestGenerator requester = new CargoRequestGenerator(stationToAdd, root, 4, CargoType.IRON);
        cargoRequestGenerators.add(requester);*/
        addCargoRequester(stationToAdd, root, 12, CargoType.IRON);
        
        stationToAdd = new Station(StationType.GREEN, new Location(4, 7), CompassHeading.SOUTH);
        stations.add(stationToAdd);
        stationToAdd.addExportCargo(new Cargo(CargoType.IRON));
        stationToAdd.addExportCargo(new Cargo(CargoType.IRON));
        
        stationToAdd = new Station(StationType.RED, new Location(5, 11), CompassHeading.SOUTH);
        stations.add(stationToAdd);
        addImportCargo(stationToAdd, root, new Cargo(CargoType.IRON));
        
        stationToAdd = new Station(StationType.GREEN, new Location(5, 12), CompassHeading.SOUTH);
        stations.add(stationToAdd);
        addImportCargo(stationToAdd, root, new Cargo(CargoType.STEEL));
        stationToAdd.addExportCargo(new Cargo(CargoType.COAL));
        
        stationToAdd = new Station(StationType.RED, new Location(9, 12), CompassHeading.SOUTH);
        stations.add(stationToAdd);
        addImportCargo(stationToAdd, root, new Cargo(CargoType.IRON));
        addImportCargo(stationToAdd, root, new Cargo(CargoType.COAL));
        stationToAdd.addExportCargo(new Cargo(CargoType.STEEL));
        
        
        
        setPassStations(stations);
	}	
	

	private void setLandscape() {
              
        setLandscapeByColumn(2, 8, 17, LandscapeType.LIGHTDIRT);
        setLandscapeByColumn(1, 3, 18, LandscapeType.DIRT);
        setLandscapeByColumn(0, 2, 19, LandscapeType.ROUGHDIRT);
        setLandscapeByColumn(8, 12, 18, LandscapeType.DIRT);
        setLandscapeByColumn(9, 14, 19, LandscapeType.ROUGHDIRT);
        
        setLandscapeByColumn(4, 7, 18, LandscapeType.WATER);
        setLandscapeByColumn(3, 8, 19, LandscapeType.WATER);
		
        
	}

	private void setObstacles() {
		ArrayList<Location> rockLocations = new ArrayList<Location>();
        rockLocations.add(new Location(6, 3));
        rockLocations.add(new Location(3, 15));
        rockLocations.add(new Location(8, 17));
        setObstacles(rockLocations, ObstacleType.ROCK);
        
        setObstaclesByRow(0, 0, 5, ObstacleType.TREES);
        setObstaclesByRow(5, 9, 9, ObstacleType.TREES);
        setObstaclesByRow(10, 0, 3, ObstacleType.TREES);
        setObstaclesByRow(11, 3, 4, ObstacleType.TREES);
        setObstaclesByRow(12, 4, 5, ObstacleType.TREES);
        setObstaclesByRow(13, 5, 6, ObstacleType.TREES);
        setObstaclesByRow(14, 6, 6, ObstacleType.TREES);
        
        setObstaclesByRow(11, 12, 12, ObstacleType.MOUNTAINS);
        setObstaclesByRow(7, 4, 5, ObstacleType.MOUNTAINS);
        setObstaclesByRow(8, 5, 5, ObstacleType.MOUNTAINS);
        setObstaclesByRow(12, 7, 7, ObstacleType.MOUNTAINS);
		
	}


}
