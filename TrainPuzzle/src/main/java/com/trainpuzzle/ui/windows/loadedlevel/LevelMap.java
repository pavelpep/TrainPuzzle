package com.trainpuzzle.ui.windows.loadedlevel;

import java.awt.Component;


import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;


import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.HashMap;

import com.trainpuzzle.controller.GameController;
import com.trainpuzzle.infrastructure.Images;
import com.trainpuzzle.model.board.CompassHeading;
import com.trainpuzzle.model.board.Connection;
import com.trainpuzzle.model.board.Location;
import com.trainpuzzle.model.board.Station.StationType;
import com.trainpuzzle.model.board.Switch;
import com.trainpuzzle.model.board.Tile;
import com.trainpuzzle.model.board.Station;
import com.trainpuzzle.model.board.Cargo;
import com.trainpuzzle.model.board.Cargo.CargoType;
import com.trainpuzzle.model.board.Track;
import com.trainpuzzle.model.board.Train;
import com.trainpuzzle.model.board.TrainCar;
import com.trainpuzzle.model.level.Level;
import com.trainpuzzle.observe.Observer;
import com.trainpuzzle.ui.windows.RotatedImageIcon;
import com.trainpuzzle.ui.windows.TileMouseAdapter;

public class LevelMap extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;
	
	private final Connection STRAIGHT = new Connection(CompassHeading.EAST, CompassHeading.WEST);
	private final Connection DIAGONAL = new Connection(CompassHeading.NORTHWEST, CompassHeading.SOUTHEAST);
	private final Connection CURVE_LEFT = new Connection(CompassHeading.NORTHWEST, CompassHeading.SOUTH);
	private final Connection CURVE_RIGHT = new Connection(CompassHeading.NORTHEAST, CompassHeading.SOUTH);
	private final List<Connection> CONNECTION_LIST = Arrays.asList(STRAIGHT, DIAGONAL, CURVE_LEFT, CURVE_RIGHT);
	
	private final int landscapeLayerIndex = 0;
	private final int trackLayerIndex = 1;
	private final int trainLayerIndex = 2;
	private final int obstacleLayerIndex = 3;
	private final int cargoLayerIndex = 4;
	private final int cargoTrainLayerIndex = 5;
	private final int tileSizeInPixels = 40;
	
	private GameController gameController;
	private Level level;
	private Train train;
	TileMouseAdapter mouseAdapter;
	private JLayeredPane[][] mapTiles;
	
	Location previousTrainLocation = new Location(0,0);
	
	public LevelMap(GameController gameController, int numberOfRows, int numberOfColumns) {
		this.gameController = gameController;
		this.level = this.gameController.getLevel();
		this.train = gameController.getSimulator().getTrain();
		mouseAdapter = new TileMouseAdapter(gameController.getLevel());
		
		this.setLayout(new GridLayout(numberOfRows, numberOfColumns));
		this.setSize(new Dimension(tileSizeInPixels * numberOfRows, tileSizeInPixels * numberOfColumns));
		mapTiles = new JLayeredPane[numberOfRows][numberOfColumns];

		initializeMapPanel(numberOfRows, numberOfColumns);
		redrawTiles();
		initializeTrain();
	}

	private void initializeMapPanel(int numberOfRows, int numberOfColumns) {
		for(int row = 0; row < numberOfRows; row++) {
			for(int column = 0; column < numberOfColumns; column++) {
				JLayeredPane mapTile = new JLayeredPane();
				mapTile.setMinimumSize(new Dimension(tileSizeInPixels, tileSizeInPixels));
				mapTile.setPreferredSize(new Dimension(tileSizeInPixels, tileSizeInPixels));
				mapTiles[row][column] = mapTile;
				level.getBoard().getTile(row, column).register(this);
				
				if(level.getBoard().getTile(row, column).hasStationBuilding()) {
					level.getBoard().getTile(row, column).getStation().register(this);
				}
				mapTile.addMouseListener(mouseAdapter); 
				
				this.add(mapTile);
			}
		}
	}

	private void drawLandscape(int row, int column) {
		JLayeredPane mapTile = mapTiles[row][column];
		removeComponentsInGUILayer(mapTile,landscapeLayerIndex);
		JLabel landscapeLayer = new JLabel();
		switch(level.getBoard().getTile(row, column).getLandscapeType()) {
			case GRASS:
				landscapeLayer=new JLabel(Images.GRASS_IMAGE);
				break;
			case WATER:
				landscapeLayer=new JLabel(Images.WATER_IMAGE);
				break;
			case ROUGHDIRT:
				landscapeLayer=new JLabel(Images.ROUGHDIRT_IMAGE);
				break;
			case DIRT:
				landscapeLayer=new JLabel(Images.DIRT_IMAGE);
				break;
			case LIGHTDIRT:
				landscapeLayer=new JLabel(Images.LIGHTDIRT_IMAGE);
				break;
			case DARKSWAMP:
				landscapeLayer=new JLabel(Images.DARKSWAMP_IMAGE);
				break;	
			case MEDSWAMP:
				landscapeLayer=new JLabel(Images.MEDSWAMP_IMAGE);
				break;	
			case LIGHTSWAMP:
				landscapeLayer=new JLabel(Images.LIGHTSWAMP_IMAGE);
				break;	
			default:
				break;
		}
		
		if(level.getBoard().getTile(row, column).getLandscapeType().equals("water")) {
			landscapeLayer=new JLabel(Images.WATER_IMAGE);
		}
		landscapeLayer.setBounds(0, 0, tileSizeInPixels, tileSizeInPixels);
		mapTile.add(landscapeLayer, new Integer(landscapeLayerIndex));
	}
	
	private void drawObstacle(int row, int column) {
		JLayeredPane mapTile = mapTiles[row][column];
		removeComponentsInGUILayer(mapTile,obstacleLayerIndex);
		
		if(level.getBoard().getTile(row, column).hasObstacle()) {
			JLabel obstacleLayer = new JLabel();
			switch(level.getBoard().getTile(row, column).getObstacleType()) {
				case ROCK:
					obstacleLayer = new JLabel(Images.ROCK_IMAGE);
					break;
				case TREES:
					obstacleLayer = new JLabel(Images.TREES_IMAGE);
					break;
				case MOUNTAINS:
					obstacleLayer = new JLabel(Images.MOUNTAINS_IMAGE);
					break;
				case GREEN_STATION:
					obstacleLayer = new JLabel(Images.GREEN_STATION_IMAGE);
					break;
				case RED_STATION:
					obstacleLayer = new JLabel(Images.RED_STATION_IMAGE);
					break;
				case IRON_FACTORY_STATION:
					obstacleLayer = new JLabel(Images.FACTORY_IMAGE);
				default:
					break;
			}
			obstacleLayer.setBounds(0, 0, tileSizeInPixels, tileSizeInPixels);
			mapTile.add(obstacleLayer, new Integer(obstacleLayerIndex));
		}
	}

	private void drawCargoes(JLayeredPane mapTile, LinkedList<Cargo> exportCargoList, 
			LinkedList<Cargo> importCargoList, HashMap<CargoType, Boolean> cargoTypeExist, StationType stationType) {
		JPanel cargoLayer = new JPanel();
		initCargoLayer(cargoLayer);
		
		if (stationType==StationType.GREEN || stationType==StationType.RED){
			displayAllCargoesInStation(cargoLayer, exportCargoList);
		}
		else{
			displayCargoTypesInStation(cargoLayer, cargoTypeExist);
			displayNumOfCargosInStation(cargoLayer, exportCargoList);
		}
		
		
		for (Cargo cargo: importCargoList) {
			JLabel cargoLabel = new JLabel(getImportCargoImag(cargo.getType()));
			cargoLayer.add(cargoLabel);
		}
		cargoLayer.setBounds(0, 0, tileSizeInPixels, tileSizeInPixels);
		mapTile.add(cargoLayer, new Integer(cargoLayerIndex));
	}

	private void initCargoLayer(JPanel cargoLayer){
		cargoLayer.setOpaque(false);
		((FlowLayout) cargoLayer.getLayout()).setVgap(2);
		((FlowLayout) cargoLayer.getLayout()).setHgap(2);
	}
	private void displayAllCargoesInStation(JPanel cargoLayer, LinkedList<Cargo> exportCargoList){
		for (Cargo cargo: exportCargoList) {
			JLabel cargoLabel = new JLabel(getExportCargoIcon(cargo.getType()));
			cargoLayer.add(cargoLabel);
		}			
	}
	
	private void displayCargoTypesInStation(JPanel cargoLayer, HashMap<CargoType,Boolean> cargoTypeExist){
		for (CargoType cargoType: CargoType.values()){
			if (cargoTypeExist.get(cargoType)){	
				System.out.println(cargoTypeExist.get(cargoType));	//test
				JLabel cargoLabel = new JLabel(getExportCargoIcon(cargoType));
				cargoLayer.add(cargoLabel);
			}
		}
	}
	
	private void displayNumOfCargosInStation(JPanel cargoLayer,LinkedList<Cargo> exportCargoList){
		Integer numCargo = exportCargoList.size();
		JLabel numCargoLabel = new JLabel(numCargo.toString());
		cargoLayer.add(numCargoLabel);
	}
	
	private ImageIcon getExportCargoIcon(CargoType cargoType){
		ImageIcon cargoIcon = new ImageIcon(Images.IRON);
		switch(cargoType){
		case IRON:
			cargoIcon = new ImageIcon(Images.IRON);
			break;
		case COTTON:
			cargoIcon = new ImageIcon(Images.COTTON);
			break;
		case WOOD:
			cargoIcon = new ImageIcon(Images.WOOD);
			break;
		case COAL:
			cargoIcon = new ImageIcon(Images.COAL);
			break;
		case STEEL:
			cargoIcon = new ImageIcon(Images.STEEL);
			break;			
		}
		return cargoIcon;
	}	
	
	private ImageIcon getImportCargoImag(CargoType cargoType) {
		switch(cargoType) {
			case COTTON:
				return Images.REQUIRED_COTTON_IMAGE;
			case WOOD:
				return Images.REQUIRED_WOOD_IMAGE;
			case IRON:
				return Images.REQUIRED_IRON_IMAGE;
			default:
				return Images.REQUIRED_COTTON_IMAGE;
		}
	}
	
	/*private ImageIcon getExportCargoImage(CargoType cargoType) {
		switch(cargoType) {
			case COTTON:
				return Images.COTTON_IMAGE;
			case WOOD:
				return Images.WOOD_IMAGE;
			case IRON:
				return Images.IRON_IMAGE;
			default:
				return Images.COTTON_IMAGE;
		}
	}*/
	
	private void drawCargoesAtStation(int row, int column) {
		JLayeredPane mapTile = mapTiles[row][column];
		
		if(!level.getBoard().getTile(row, column).hasStationBuilding()) {
			return;
		}
		removeComponentsInGUILayer(mapTile,cargoLayerIndex);
		Station stationOnTile = level.getBoard().getTile(row, column).getStation();
		HashMap<CargoType, Boolean> cargoTypeExist = stationOnTile.getCargoTypeExist();
		StationType stationType = stationOnTile.getType();
		drawCargoes(mapTile, stationOnTile.getExportCargo(), stationOnTile.getImportCargo(),
				cargoTypeExist, stationType);
	}
	
	private void drawTrack(int row, int column) {
		JLayeredPane mapTile = mapTiles[row][column];
		removeComponentsInGUILayer(mapTile,trackLayerIndex);
		
		if(level.getBoard().getTile(row, column).hasTrack()) {
			Track track = level.getBoard().getTile(row, column).getTrack();
			
			
			if (track.isSwitch()) {
				((Switch)track).register(this);
				addCurrentConnectionLayer(track, mapTile);
			}
			
			addAllOtherConnectionLayers(track, mapTile);
		}
	}

	private void addCurrentConnectionLayer(Track track, JLayeredPane mapTile) {
		Track copyOfTrack = new Track(track);
		for(Connection connection:copyOfTrack.getConnections()) {
			if(isCurrentConnection((Switch)track, connection)) {
				addConnectionLayer(track, connection, mapTile);
				break;
			}
		}
	}

	private boolean isCurrentConnection(Switch switchTrack, Connection connection) {
		return connection.equals(switchTrack.getCurrentConnection());
	}
	
	private void addConnectionLayer(Track trackToAdd, Connection connection, JLayeredPane mapTile) {
		for(Connection comparedConnection: CONNECTION_LIST) {
			int rotation = 0;
			while (rotation < 4) {
				if(connection.equals(comparedConnection)) {
					JLabel connectionLayer = new JLabel(getConnectionImage(trackToAdd, comparedConnection, 4 - rotation));
					connectionLayer.setBounds(0, 0, tileSizeInPixels, tileSizeInPixels);
					mapTile.add(connectionLayer, new Integer(trackLayerIndex));
					break;
				}
				connection.rotate90Degrees();
				rotation++;
			}
		}
	}
	
	private void addAllOtherConnectionLayers(Track track, JLayeredPane mapTile) {
		Track copyOfTrack = new Track(track);
		for(Connection connection:copyOfTrack.getConnections()) {
			if(!track.isSwitch() || !isCurrentConnection((Switch)track, connection)) {
				addConnectionLayer(track, connection, mapTile);	
			}
		}
	}
	
	private RotatedImageIcon getConnectionImage(Track track, Connection connection, int rotation) {
		if(track.isUnremovable()) {
			return new RotatedImageIcon(Images.PERMANENT_STRAIGHT_TRACK, rotation * 2);
		}
		else if(connection.equals(STRAIGHT)) {
			return new RotatedImageIcon(Images.STRAIGHT_TRACK, rotation * 2);
		}
		else if(connection.equals(DIAGONAL)) {
			return new RotatedImageIcon(Images.DIAGONAL_TRACK, rotation * 2);
		}
		else if(connection.equals(CURVE_LEFT)) {
			return new RotatedImageIcon(Images.CURVELEFT_TRACK, rotation * 2);
		}
		else if(connection.equals(CURVE_RIGHT)) {
			return new RotatedImageIcon(Images.CURVERIGHT_TRACK, rotation * 2);
		}
		return new RotatedImageIcon(Images.STRAIGHT_TRACK, rotation * 2);
	}

	public void redrawTiles() {
		int row = 0;
		int column = 0;
		
		while(row < level.getBoard().getRows()) {			
			while(column < level.getBoard().getColumns()) {
				redrawTile(row, column);
				column++;
			}
			column = 0;
			row++;
		}
		this.repaint();
	}
	
	private void redrawTile(int row, int column) {
		drawObstacle(row, column);
		drawLandscape(row, column);
		drawTrack(row, column);
		drawCargoesAtStation(row, column);
	}
	
	private void initializeTrain() {
		train.register(this);
		redrawTrain(train);
		redrawTrainCars(train);
		redrawTrainCargo(train);
	}
	
	private void redrawTrain(Train train) {
		for(JLayeredPane[] tileArr: mapTiles) {
			for(JLayeredPane tile: tileArr) {
				removeComponentsInGUILayer(tile,trainLayerIndex);
				removeComponentsInGUILayer(tile,cargoTrainLayerIndex);
			}
		}
		
		int rotation = train.getHeading().ordinal() - 3;
		JLabel trainLayer = new JLabel(new RotatedImageIcon(Images.TRAIN, rotation));
		trainLayer.setBounds(0, 0, tileSizeInPixels, tileSizeInPixels);
		int row = train.getLocation().getRow();
		int column = train.getLocation().getColumn();
		mapTiles[row][column].add(trainLayer, new Integer(trainLayerIndex));
		this.repaint();
	}
		
	private void redrawTrainCars(Train train) {

		TrainCar trainCars[] = train.getTrainCars();
		
		for(TrainCar trainCar: trainCars) {
			int rotation = trainCar.getHeading().ordinal() - 3;
			JLabel trainLayer = new JLabel(new RotatedImageIcon(Images.TRAIN_CAR, rotation));
			trainLayer.setBounds(0, 0, tileSizeInPixels, tileSizeInPixels);
			int row = trainCar.getLocation().getRow();
			int column = trainCar.getLocation().getColumn();
			mapTiles[row][column].add(trainLayer, new Integer(trainLayerIndex));
		}
		this.repaint();
	}
	
	private void redrawTrainCargo(Train train) {
		
		TrainCar trainCars[] = train.getTrainCars();
		
		for(TrainCar trainCar: trainCars) {
			int row = trainCar.getLocation().getRow();
			int column = trainCar.getLocation().getColumn();
			
			if(trainCar.hasCargo()) {
				JLabel cargoLayer = new JLabel(getExportCargoIcon(trainCar.getCargo().getType()));
				cargoLayer.setBounds(0, 0, tileSizeInPixels, tileSizeInPixels);
				mapTiles[row][column].add(cargoLayer, new Integer(cargoTrainLayerIndex));
			}
			this.repaint();
		}
	}
	
	private void removeComponentsInGUILayer(JLayeredPane mapTile, int layerIndex) {
		try {
			Component[] components = mapTile.getComponentsInLayer(layerIndex);
			for(Component component: components){
				mapTile.remove(component);
			}
		} catch(Exception e) {
			//logger.error(e.getMessage(), e.fillInStackTrace());
		}
	}
	
	public TileMouseAdapter getMouseAdapter() {
		return mouseAdapter;
	}
	
	@Override
	public void notifyChange(Object object) {
		if(object instanceof Train) {
			instanceOfTrain(object);
		}
		else if(object instanceof Tile) {
			instanceOfTile(object);
		}	
		else if(object instanceof Station) {
			instanceOfStation(object);
		}
		else if(object instanceof Switch) {
			instanceOfSwitch(object);
		}
	}
	
	

	public void instanceOfTrain(Object object) {
		redrawTrain(train);
		redrawTrainCars(train);
		redrawTrainCargo(train);
	}
	
	public void instanceOfTile(Object object) {
		for(int row = 0; row < level.getBoard().getRows(); row++) {
			for(int column = 0; column < level.getBoard().getColumns(); column++) {
				if(object.equals(level.getBoard().getTile(row, column))) {
					redrawTile(row, column);
				}
			}
		}
	}
	
	public void instanceOfStation(Object object) {
		for(int row = 0; row < level.getBoard().getRows(); row++) {
			for(int column = 0; column < level.getBoard().getColumns(); column++) {
				if(level.getBoard().getTile(row, column).hasStationBuilding()) {
					if(object.equals(level.getBoard().getTile(row, column).getStation())) {
						redrawTile(row, column);
					}
				}
			}
		}	
	}
	
	public void instanceOfSwitch(Object object) {
		for(int row = 0; row < level.getBoard().getRows(); row++) {
			for(int column = 0; column < level.getBoard().getColumns(); column++) {
				if(level.getBoard().getTile(row, column).hasTrack()) {
					if(object.equals(level.getBoard().getTile(row, column).getTrack())) {
						redrawTile(row, column);
					}
				}
			}
		}
	}
}