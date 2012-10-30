package com.trainpuzzle.ui.windows;

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
import com.trainpuzzle.infrastructure.Images;
import com.trainpuzzle.model.board.CompassHeading;
import com.trainpuzzle.model.board.Connection;
import com.trainpuzzle.model.board.Location;
import com.trainpuzzle.model.board.Tile;
import com.trainpuzzle.model.board.Train;
import com.trainpuzzle.model.level.Level;
import com.trainpuzzle.observe.Observer;

public class LoadedLevelMap extends Window implements ActionListener, Observer {
	private final int landscapeLayerIndex = 0;
	private final int trackLayerIndex = 1;
	private final int trainLayerIndex = 2;
	private final int obstacleLayerIndex = 3;
	

	private GameController gameController;
	JPanel map = new JPanel();
	private JLayeredPane[][] mapTiles;
	TileMouseAdapter mouseAdapter;
	private Level level;
	
	private Train train;
	Location previousTrainLocation = new Location(0,0);
	
	
	LoadedLevelMap(GameController gameController, TileMouseAdapter mouseAdapter, int numberOfRows, int numberOfColumns) {
		this.gameController = gameController;
		this.level = this.gameController.getLevel();
		this.train = gameController.getSimulator().getTrain();
		this.mouseAdapter = mouseAdapter;
		
		
		//mapPanel.setPreferredSize(new Dimension(40*numberOfRows, 40*numberOfColumns));
		map.setLayout(new GridLayout(numberOfRows, numberOfColumns));
		map.setSize(new Dimension(40*numberOfRows, 40*numberOfColumns));
		mapTiles = new JLayeredPane[numberOfRows][numberOfColumns];
		// Initialize Map Panel
		for(int row = 0; row < numberOfRows; row++){
			for(int column = 0; column < numberOfColumns; column++){
				//initializes tile
				JLayeredPane mapTile = new JLayeredPane();
				mapTile.setMinimumSize(new Dimension(40, 40));
				mapTile.setPreferredSize(new Dimension(40, 40));
				mapTiles[row][column] = mapTile;

				//add mouse clicky thing to tile
				level.getBoard().getTile(row, column).register(this);
				mapTile.addMouseListener(mouseAdapter); 
				
				//add tile to map panel
				map.add(mapTile);
			}
		}
		redrawTiles();
		initializeTrain();
	}

	private void drawLandscape(int row, int column) {
		try {
			mapTiles[row][column].remove(mapTiles[row][column].getComponentsInLayer(landscapeLayerIndex)[0]);
		} catch(Exception e){
			//logger.error(e.getMessage(), e.fillInStackTrace());
		}
		
		JLabel landscapeLayer = new JLabel();
		switch(level.getBoard().getTile(row, column).getLandscapeType()) {
		case GRASS:
			landscapeLayer=new JLabel(Images.GRASS_IMAGE);
			break;
		case WATER:
			landscapeLayer=new JLabel(Images.WATER_IMAGE);
			break;
		}
		
		landscapeLayer.setTransferHandler(new TransferHandler("icon"));
		
		if(level.getBoard().getTile(row, column).getLandscapeType().equals("water")) {
			landscapeLayer=new JLabel(Images.WATER_IMAGE);
			landscapeLayer.setTransferHandler(new TransferHandler("icon"));
		}
		landscapeLayer.setBounds(0,0,40,40);
		mapTiles[row][column].add(landscapeLayer, new Integer(landscapeLayerIndex));
	}
	
	private void drawObstacle(int row, int column) {
		try {
			mapTiles[row][column].remove(mapTiles[row][column].getComponentsInLayer(obstacleLayerIndex)[0]);
		} catch(Exception e){
			//logger.error(e.getMessage(), e.fillInStackTrace());
		}
		JLayeredPane mapTile = mapTiles[row][column];
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
			obstacleLayer.setTransferHandler(new TransferHandler("icon"));
			obstacleLayer.setBounds(0,0,40,40);
			mapTile.add(obstacleLayer, new Integer(obstacleLayerIndex));
		}
	}
	
	private void drawTrack(int row, int column) {
		try {
			Component[] components = mapTiles[row][column].getComponentsInLayer(trackLayerIndex);
			for(Component component: components){
			mapTiles[row][column].remove(component);
			}
		} catch(Exception e){
			//logger.error(e.getMessage(), e.fillInStackTrace());
		}
		JLayeredPane mapTile = mapTiles[row][column];
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
						trackLayer.setBounds(0,0,40,40);
						mapTile.add(trackLayer, new Integer(trackLayerIndex));
					}
					straight.rotate90Degrees();
					
					if(connection.equals(diagonal)){
						trackLayer=new JLabel(new RotatedImageIcon(Images.DIAGONAL_TRACK, i * 2));
						trackLayer.setBounds(0,0,40,40);
						mapTile.add(trackLayer, new Integer(trackLayerIndex));
					}
					diagonal.rotate90Degrees();
				}
				for(int i = 0; i < 4; i++){	
					if(connection.equals(curveLeft)){
						trackLayer=new JLabel(new RotatedImageIcon(Images.CURVELEFT_TRACK, i * 2));
						trackLayer.setBounds(0,0,40,40);
						mapTile.add(trackLayer, new Integer(trackLayerIndex));
					}
					curveLeft.rotate90Degrees();
					if(connection.equals(curveRight)){
						trackLayer=new JLabel(new RotatedImageIcon(Images.CURVERIGHT_TRACK, i * 2));
						trackLayer.setBounds(0,0,40,40);
						mapTile.add(trackLayer, new Integer(trackLayerIndex));
					}
					curveRight.rotate90Degrees();
				}
			}
		}
	}

	public void  redrawTiles(){
		  for(int row = 0; row < level.getBoard().NUMBER_OF_ROWS; row++){
				for(int column = 0; column < level.getBoard().NUMBER_OF_COLUMNS; column++){
					redrawTile(row, column);
				}
			}
		map.repaint();
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
		int previousRow = previousTrainLocation.getRow();
		int previousColumn = previousTrainLocation.getColumn();
		try {
			mapTiles[previousRow][previousColumn].remove(mapTiles[previousRow][previousColumn].getComponentsInLayer(trainLayerIndex)[0]);
			
		} catch(Exception e){
			//logger.error(e.getMessage(), e.fillInStackTrace());
		}
		previousTrainLocation = new Location(train.getLocation());
		
		int row = train.getLocation().getRow();
		int column = train.getLocation().getColumn();
		
		int rotation = train.getHeading().ordinal() - 3; //we should make train image point NORTHWEST to begin
		ImageIcon trainImage = new RotatedImageIcon(Images.TRAIN, rotation);
		JLabel trainLayer = new JLabel(trainImage);
		trainLayer.setBounds(0,0,40,40);
		mapTiles[row][column].add(trainLayer, new Integer(trainLayerIndex));
		
		map.repaint();
	}
	
	
	
	
	@Override
	public void notifyChange(Object object) {
		if(object instanceof Train){
			redrawTrain(train);
		}
		else if(object instanceof Tile){
			for(int row = 0; row < level.getBoard().NUMBER_OF_ROWS; row++){
				for(int column = 0; column < level.getBoard().NUMBER_OF_COLUMNS; column++){
					if(object.equals(level.getBoard().getTile(row, column)))
						redrawTile(row, column);
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void create() {
		// TODO Auto-generated method stub
		
	}
	
	public JPanel getMap() {
		return map;
	}

}
