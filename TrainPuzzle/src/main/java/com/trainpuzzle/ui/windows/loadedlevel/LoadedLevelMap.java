package com.trainpuzzle.ui.windows.loadedlevel;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.TransferHandler;

import com.trainpuzzle.controller.GameController;
import com.trainpuzzle.controller.TrackPlacer;
import com.trainpuzzle.infrastructure.Images;
import com.trainpuzzle.model.board.CompassHeading;
import com.trainpuzzle.model.board.Connection;
import com.trainpuzzle.model.board.Location;
import com.trainpuzzle.model.board.Tile;
import com.trainpuzzle.model.board.Train;
import com.trainpuzzle.model.level.Level;
import com.trainpuzzle.observe.Observer;
import com.trainpuzzle.ui.windows.RotatedImageIcon;
import com.trainpuzzle.ui.windows.TileMouseAdapter;

public class LoadedLevelMap extends JPanel implements Observer {
	private final int landscapeLayerIndex = 0;
	private final int trackLayerIndex = 1;
	private final int trainLayerIndex = 2;
	private final int obstacleLayerIndex = 3;
	private final int tileSizeInPixels = 40;
	

	private GameController gameController;
	private Level level;
	private Train train;
	TileMouseAdapter mouseAdapter;
	private JLayeredPane[][] mapTiles;
	
	
	Location previousTrainLocation = new Location(0,0);
	
	
	public LoadedLevelMap(GameController gameController, int numberOfRows, int numberOfColumns) {
		this.gameController = gameController;
		this.level = this.gameController.getLevel();
		this.train = gameController.getSimulator().getTrain();
		mouseAdapter = new TileMouseAdapter(new TrackPlacer(gameController.getLevel()));
		
		//mapPanel.setPreferredSize(new Dimension(40*numberOfRows, 40*numberOfColumns));
		this.setLayout(new GridLayout(numberOfRows, numberOfColumns));
		this.setSize(new Dimension(tileSizeInPixels * numberOfRows, tileSizeInPixels * numberOfColumns));
		mapTiles = new JLayeredPane[numberOfRows][numberOfColumns];
		
		// Initialize Map Panel
		for(int row = 0; row < numberOfRows; row++){
			for(int column = 0; column < numberOfColumns; column++){
				JLayeredPane mapTile = new JLayeredPane();
				mapTile.setMinimumSize(new Dimension(tileSizeInPixels, tileSizeInPixels));
				mapTile.setPreferredSize(new Dimension(tileSizeInPixels, tileSizeInPixels));
				mapTiles[row][column] = mapTile;
				level.getBoard().getTile(row, column).register(this);
				mapTile.addMouseListener(mouseAdapter); 
				
				this.add(mapTile);
			}
		}
		redrawTiles();
		initializeTrain();
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
		}
		
		if(level.getBoard().getTile(row, column).getLandscapeType().equals("water")) {
			landscapeLayer=new JLabel(Images.WATER_IMAGE);
		}
		landscapeLayer.setBounds(0,0,tileSizeInPixels,tileSizeInPixels);
		mapTile.add(landscapeLayer, new Integer(landscapeLayerIndex));
	}
	
	private void drawObstacle(int row, int column) {
		JLayeredPane mapTile = mapTiles[row][column];
		removeComponentsInGUILayer(mapTile,obstacleLayerIndex);
		if(level.getBoard().getTile(row, column).hasObstacle()) {
			JLabel obstacleLayer = new JLabel();
			switch(level.getBoard().getTile(row, column).getObstacleType()){
				case ROCK:
					obstacleLayer = new JLabel(Images.ROCK_IMAGE);
					break;
				case TREES:
					obstacleLayer = new JLabel(Images.TREES_IMAGE);
					break;
				case GREEN_STATION:
					obstacleLayer = new JLabel(Images.GREEN_STATION_IMAGE);
					break;
				case RED_STATION:
					obstacleLayer = new JLabel(Images.RED_STATION_IMAGE);
					break;				
			default:
				break;
			}
			obstacleLayer.setBounds(0,0,tileSizeInPixels,tileSizeInPixels);
			mapTile.add(obstacleLayer, new Integer(obstacleLayerIndex));
		}
	}
	
	private void drawTrack(int row, int column) {
		JLayeredPane mapTile = mapTiles[row][column];
		removeComponentsInGUILayer(mapTile,trackLayerIndex);
		if(level.getBoard().getTile(row, column).hasTrack()){

			for(Connection connection:level.getBoard().getTile(row, column).getTrack().getConnections()){
				Connection diagonal = new Connection(CompassHeading.NORTHWEST, CompassHeading.SOUTHEAST);
				Connection straight = new Connection(CompassHeading.NORTH, CompassHeading.SOUTH);
				Connection curveLeft = new Connection(CompassHeading.NORTHWEST, CompassHeading.SOUTH);
				Connection curveRight = new Connection(CompassHeading.NORTHEAST, CompassHeading.SOUTH);
				
				JLabel trackLayer = new JLabel();
				for(int i = 0; i < 2; i++){
					if(connection.equals(straight)){
						if(level.getBoard().getTile(row, column).getTrack().isUnremovable()){
							trackLayer=new JLabel(new RotatedImageIcon(Images.PERMANENT_STRAIGHT_TRACK, i * 2 + 2));
						}
						else{
							trackLayer=new JLabel(new RotatedImageIcon(Images.DIAGONAL_TRACK, i * 2 + 1));
						}
						trackLayer.setBounds(0,0,tileSizeInPixels,tileSizeInPixels);
						mapTile.add(trackLayer, new Integer(trackLayerIndex));
					}
					straight.rotate90Degrees();
				}
				for(int i = 0; i < 2; i++){	
					if(connection.equals(diagonal)){
						trackLayer=new JLabel(new RotatedImageIcon(Images.DIAGONAL_TRACK, i * 2));
						trackLayer.setBounds(0,0,tileSizeInPixels,tileSizeInPixels);
						mapTile.add(trackLayer, new Integer(trackLayerIndex));
					}
					diagonal.rotate90Degrees();
				}
				for(int i = 0; i < 4; i++){	
					if(connection.equals(curveLeft)){
						trackLayer=new JLabel(new RotatedImageIcon(Images.CURVELEFT_TRACK, i * 2));
						trackLayer.setBounds(0,0,tileSizeInPixels,tileSizeInPixels);
						mapTile.add(trackLayer, new Integer(trackLayerIndex));
					}
					curveLeft.rotate90Degrees();
					if(connection.equals(curveRight)){
						trackLayer=new JLabel(new RotatedImageIcon(Images.CURVERIGHT_TRACK, i * 2));
						trackLayer.setBounds(0,0,tileSizeInPixels,tileSizeInPixels);
						mapTile.add(trackLayer, new Integer(trackLayerIndex));
					}
					curveRight.rotate90Degrees();
				}
			}
		}
	}

	public void redrawTiles(){
		  for(int row = 0; row < level.getBoard().rows; row++){
				for(int column = 0; column < level.getBoard().columns; column++){
					redrawTile(row, column);
				}
			}
		  this.repaint();
	}
	
	private void redrawTile(int row, int column){
		   drawObstacle(row, column);
		   drawLandscape(row, column);
		   drawTrack(row, column);
	}
	
	private void initializeTrain() {
		train.register(this);
		redrawTrain(train);
	}
	
	private void redrawTrain(Train train) {
		JLayeredPane previousTile = mapTiles[previousTrainLocation.getRow()][previousTrainLocation.getColumn()];
		removeComponentsInGUILayer(previousTile,trainLayerIndex);
		previousTrainLocation = new Location(train.getLocation());
		
		int rotation = train.getHeading().ordinal() - 3;
		JLabel trainLayer = new JLabel(new RotatedImageIcon(Images.TRAIN, rotation));
		trainLayer.setBounds(0,0,tileSizeInPixels,tileSizeInPixels);
		int row = train.getLocation().getRow();
		int column = train.getLocation().getColumn();
		mapTiles[row][column].add(trainLayer, new Integer(trainLayerIndex));
		this.repaint();
	}
	
	private void removeComponentsInGUILayer(JLayeredPane mapTile, int layerIndex) {
		try {
			Component[] components = mapTile.getComponentsInLayer(layerIndex);
			for(Component component: components){
				mapTile.remove(component);
			}
		} catch(Exception e){
			//logger.error(e.getMessage(), e.fillInStackTrace());
		}
	}
	
	public TileMouseAdapter getMouseAdapter() {
		return mouseAdapter;
	}
	
	@Override
	public void notifyChange(Object object) {
		if(object instanceof Train){
			redrawTrain(train);
		}
		else if(object instanceof Tile){
			for(int row = 0; row < level.getBoard().rows; row++){
				for(int column = 0; column < level.getBoard().columns; column++){
					if(object.equals(level.getBoard().getTile(row, column)))
						redrawTile(row, column);
				}
			}
		}
	}

}
