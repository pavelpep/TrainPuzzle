package com.trainpuzzle.ui.windows;
import com.trainpuzzle.observe.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.swing.*;
import javax.swing.border.*;

import org.apache.log4j.Logger;

import com.trainpuzzle.controller.Application;
import com.trainpuzzle.controller.GameController;
import com.trainpuzzle.controller.Simulator;
import com.trainpuzzle.exception.TrainCrashException;
import com.trainpuzzle.model.level.Level;
import com.trainpuzzle.model.map.Board;
import com.trainpuzzle.model.map.CompassHeading;
import com.trainpuzzle.model.map.Connection;
import com.trainpuzzle.model.map.Obstacle;
import com.trainpuzzle.model.map.Tile;
import com.trainpuzzle.model.map.Train;
import com.trainpuzzle.model.map.Location;

import com.trainpuzzle.controller.TrackPlacer;



// Level selection for the campaign
public class LoadedLevelScreen extends Window implements ActionListener, Observer {
	
	// Window elements
	private JLabel titleLabel = new JLabel();
	private JLabel selectedTrackLabel = new JLabel();
	private JLabel selectedTrackImage = new JLabel();
	private JPanel mapPanel = new JPanel();
	private JPanel trackPanel = new JPanel();
	private JPanel selectedTrackPanel = new JPanel();
	private JButton runButton = new JButton();
	private JButton rotateLeftButton = new JButton();
	private JButton rotateRightButton = new JButton();

	private Logger logger = Logger.getLogger(LoadedLevelScreen.class);
	
	MouseListener mouseListener;
	
	Location previousTrainLocation;
	int previousRow;
	int previousColumn;
	Location trainLocation;
	
	private JLabel landscapeLayer = new JLabel();
	private JLabel trackLayer = new JLabel();
	private JLabel trainLayer = new JLabel();
	private JLabel obstacleLayer = new JLabel();
	private JLabel stationLayer = new JLabel();
	
	private final int landscapeLayerIndex = 0;
	private final int trackLayerIndex = 1;
	private final int trainLayerIndex = 2;
	private final int obstacleLayerIndex = 3;
	private final int stationLayerIndex = 4;

	private final ImageIcon GRASS_IMAGE = new ImageIcon("src/main/resources/images/grass.png");
	private final ImageIcon WATER_IMAGE = new ImageIcon("src/main/resources/images/water.png");
	private final ImageIcon ROCK_IMAGE = new ImageIcon("src/main/resources/images/rock.png");
	private final ImageIcon TREES_IMAGE = new ImageIcon("src/main/resources/images/trees.png");
	
	private final ImageIcon REDSTATION_FRONT_IMAGE = new ImageIcon("src/main/resources/images/redStation_Front.png");
	private final ImageIcon GREENSTATION_FRONT_IMAGE = new ImageIcon("src/main/resources/images/greenStation_Front.png");
	
	private final ImageIcon STRAIGHTTRACK_IMAGE = new ImageIcon("src/main/resources/images/straight_track.png");
	private final ImageIcon DIAGONALTRACK_IMAGE = new ImageIcon("src/main/resources/images/diagonal_track.png");
	private final ImageIcon CURVELEFTTRACK_IMAGE = new ImageIcon("src/main/resources/images/curve_left_track.png");
	private final ImageIcon CURVERIGHTTRACK_IMAGE = new ImageIcon("src/main/resources/images/curve_right_track.png");	
	
	private JLayeredPane mapTile;
	private JLayeredPane[][] mapTiles;
	
	private GameController gameController;
	private Level level;
	private Train train;
	
	Border loweredbevel, loweredetched;
	TitledBorder mapTitle, sidePanelTitle;
	
	// Constructor
	public LoadedLevelScreen(GameController gameController) {
		
		this.gameController = gameController;
		loweredbevel = BorderFactory.createLoweredBevelBorder();
		loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);

        this.selectedTrackImage.setPreferredSize(new Dimension(100,100));
        this.selectedTrackLabel.setPreferredSize(new Dimension(100,100));
        this.rotateLeftButton.setPreferredSize(new Dimension(100, 50));
        this.rotateRightButton.setPreferredSize(new Dimension(100, 50));
		setLayout(new GridBagLayout());
		setSize(new Dimension(1280,720));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);	
		
		previousTrainLocation = new Location(0,0);
		mouseListener = new TileMouseAdapter(new TrackPlacer(gameController.getLevel()));
		
		gameController.getLevel().getMap().register(this);
	}
	
	public void notifyChange(){
		redrawTrain(train);
		redrawTiles();
	}
	
	public void redrawTiles(){
		  for(int row = 0; row < Board.NUMBER_OF_ROWS; row++){
	            for(int column = 0; column < Board.NUMBER_OF_COLUMNS; column++){
	        		mapTile = mapTiles[row][column];
	            	modifyObstacles(row, column);
					modifyLandscape(row, column);
	            	modifyTrack(row, column);
	            	modifyStations(row, column);
	            }
	        }
        mapPanel.repaint();
	}
	
	private void modifyLandscape(int row, int column) {
		try {
			mapTiles[row][column].remove(mapTiles[row][column].getComponentsInLayer(landscapeLayerIndex)[0]);
		} catch(Exception e){
			//logger.error(e.getMessage(), e.fillInStackTrace());
		}
		
		switch(level.getMap().getTile(row, column).getLandscapeType()) {
		case GRASS:
			landscapeLayer=new JLabel(GRASS_IMAGE);
			break;
		case WATER:
			landscapeLayer=new JLabel(WATER_IMAGE);
			break;
		}
		
		landscapeLayer.setTransferHandler(new TransferHandler("icon"));
		
		if(level.getMap().getTile(row, column).getLandscapeType().equals("water")) {
			landscapeLayer=new JLabel(WATER_IMAGE);
			landscapeLayer.setTransferHandler(new TransferHandler("icon"));
		}
		landscapeLayer.setBounds(0,0,40,40);
		mapTile.add(landscapeLayer, new Integer(landscapeLayerIndex));
	}
	
	private void modifyStations(int row, int column){
		try {
			mapTiles[row][column].remove(mapTiles[row][column].getComponentsInLayer(stationLayerIndex)[0]);
		} catch(Exception e){
			//logger.error(e.getMessage(), e.fillInStackTrace());
		}
		
		if(level.getMap().getTile(row, column).hasStation()) {
			//System.out.println(level.getMap().getTile(row, column).hasObstacle() + " " + row + " "+  column);
			switch(level.getMap().getTile(row, column).getStationType()){
				case RED_FRONT:
					stationLayer = new JLabel(REDSTATION_FRONT_IMAGE);
					break;
				case GREEN_FRONT:
					stationLayer = new JLabel(GREENSTATION_FRONT_IMAGE);
					break;
				case RED_BACK:
					
					break;
				case GREEN_BACK:
					
					break;
			}
			stationLayer.setTransferHandler(new TransferHandler("icon"));
			stationLayer.setBounds(0,0,40,40);
			mapTile.add(stationLayer, new Integer(stationLayerIndex));	
		}	
	}
	
	private void modifyObstacles(int row, int column) {
		try {
			mapTiles[row][column].remove(mapTiles[row][column].getComponentsInLayer(obstacleLayerIndex)[0]);
		} catch(Exception e){
			//logger.error(e.getMessage(), e.fillInStackTrace());
		}
		
		if(level.getMap().getTile(row, column).hasObstacle()) {
			//System.out.println(level.getMap().getTile(row, column).hasObstacle() + " " + row + " "+  column);
			switch(level.getMap().getTile(row, column).getObstacleType()){
				case ROCK:
					obstacleLayer = new JLabel(ROCK_IMAGE);
					break;
				case TREES:
					obstacleLayer = new JLabel(TREES_IMAGE);
					break;
			}
			obstacleLayer.setTransferHandler(new TransferHandler("icon"));
			obstacleLayer.setBounds(0,0,40,40);
			mapTile.add(obstacleLayer, new Integer(obstacleLayerIndex));
		}
	}
	
	private void modifyTrack(int row, int column) {
		try {
			mapTiles[row][column].remove(mapTiles[row][column].getComponentsInLayer(trackLayerIndex)[0]);
		} catch(Exception e){
			//logger.error(e.getMessage(), e.fillInStackTrace());
		}
		if(level.getMap().getTile(row, column).hasTrack()){

			for(Connection connection:level.getMap().getTile(row, column).getTrack().getConnections()){
				Connection diagonal = new Connection(CompassHeading.NORTHWEST, CompassHeading.SOUTHEAST);
				Connection straight = new Connection(CompassHeading.NORTH, CompassHeading.SOUTH);
				Connection curveLeft = new Connection(CompassHeading.NORTHWEST, CompassHeading.SOUTH);
				Connection curveRight = new Connection(CompassHeading.NORTHEAST, CompassHeading.SOUTH);
				
				
				for(int i = 0; i < 2; i++){
					if(connection.equals(straight)){
						trackLayer=new JLabel(new RotatedImageIcon(DIAGONALTRACK_IMAGE.getImage(), i * 2 + 1));
						trackLayer.setBounds(0,0,40,40);
						mapTile.add(trackLayer, new Integer(trackLayerIndex));
					}
					straight.rotate90Degrees();
					
					if(connection.equals(diagonal)){
						trackLayer=new JLabel(new RotatedImageIcon(DIAGONALTRACK_IMAGE.getImage(), i * 2));
						trackLayer.setBounds(0,0,40,40);
						mapTile.add(trackLayer, new Integer(trackLayerIndex));
					}
					diagonal.rotate90Degrees();
				}
					
				for(int i = 0; i < 4; i++){	
					if(connection.equals(curveLeft)){
						trackLayer=new JLabel(new RotatedImageIcon(CURVELEFTTRACK_IMAGE.getImage(), i * 2));
						trackLayer.setBounds(0,0,40,40);
						mapTile.add(trackLayer, new Integer(trackLayerIndex));
					}
					curveLeft.rotate90Degrees();
					if(connection.equals(curveRight)){
						trackLayer=new JLabel(new RotatedImageIcon(CURVERIGHTTRACK_IMAGE.getImage(), i * 2));
						trackLayer.setBounds(0,0,40,40);
						mapTile.add(trackLayer, new Integer(trackLayerIndex));
					}
					curveRight.rotate90Degrees();
				}
				
				
			}
			
		}
	}
	
	public void redrawTrain(Train train) {
		//previousTrainLocation = new Location(trainLocation.getRow(),trainLocation.getColumn());
		try {
			mapTiles[previousRow][previousColumn].remove(mapTiles[previousRow][previousColumn].getComponentsInLayer(trainLayerIndex)[0]);
        	
		} catch(Exception e){
			//logger.error(e.getMessage(), e.fillInStackTrace());
		}
		
		previousRow = trainLocation.getRow();
		previousColumn = trainLocation.getColumn();

		trainLocation = train.getLocation();
		int row = trainLocation.getRow();
		int column = trainLocation.getColumn();
		
		int rotation = train.getHeading().ordinal() - 3; //we should make train image point NORTHWEST to begin
		ImageIcon trainImage = new RotatedImageIcon("src/main/resources/images/train.png", rotation);
    	trainLayer = new JLabel(trainImage);
    	trainLayer.setBounds(0,0,40,40);
		mapTiles[row][column].add(trainLayer, new Integer(trainLayerIndex));
		
		mapPanel.repaint();
	}
	
	public void create() {
	
		// Game title
		initializeComponent(this.titleLabel, Font.CENTER_BASELINE, 28, Color.BLACK, 0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 10, 0, 10), true);
		this.titleLabel.setText("Level 1");
		this.add(this.titleLabel, gbConstraints);
		
		// Map Panel
		//level = new Level(1);
		level = this.gameController.getLevel();
		
		mapTiles = new JLayeredPane[Board.NUMBER_OF_ROWS][Board.NUMBER_OF_COLUMNS];
		
		mapPanel = new JPanel();	
		mapPanel.setLayout(new GridLayout(Board.NUMBER_OF_ROWS, Board.NUMBER_OF_COLUMNS));
	
		
        for(int row = 0; row < Board.NUMBER_OF_ROWS; row++){
            for(int column = 0; column < Board.NUMBER_OF_COLUMNS; column++){
        		mapTile = new JLayeredPane();
        		mapTile.setPreferredSize(new Dimension(40, 40));

            	modifyObstacles(row, column);
				modifyLandscape(row, column);
            	modifyTrack(row, column);
            	modifyStations(row, column);
        		
				mapTile.addMouseListener(mouseListener); 
            	mapPanel.add(mapTile);
            	mapTiles[row][column] = mapTile;
            	
            }
        }
        
		
		train = gameController.getSimulator().getTrain();
		train.register(this);
		trainLocation = train.getLocation();
		redrawTrain(train);
        
        initializeComponent(this.mapPanel, Font.CENTER_BASELINE, 0, Color.LIGHT_GRAY, 0, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), true);
        this.add(this.mapPanel, gbConstraints);
		
		//testSimulation(testLevel);
		
		// Track Panel 
		initializeComponent(this.trackPanel, Font.CENTER_BASELINE, 0, Color.GRAY, 100, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 300, 10), true);
		this.add(trackPanel, gbConstraints);
		
		trackPanel.setPreferredSize(new Dimension(250, 300));
        sidePanelTitle = BorderFactory.createTitledBorder(loweredetched, "Select Track");
        sidePanelTitle.setTitlePosition(TitledBorder.ABOVE_TOP);
		trackPanel.setBorder(sidePanelTitle);
	
		JButton straightTrack = new JButton(STRAIGHTTRACK_IMAGE);
		straightTrack.setPreferredSize(new Dimension(80,80));
		straightTrack.setBounds(0, 0, 40, 40);
		straightTrack.setActionCommand("straightTrack");
		straightTrack.addActionListener(this);
		trackPanel.add(straightTrack);
		
		JButton diagonalTrack = new JButton(DIAGONALTRACK_IMAGE);
		diagonalTrack.setBounds(0, 0, 40, 40);
		diagonalTrack.setPreferredSize(new Dimension(80,80));
		diagonalTrack.setActionCommand("diagonalTrack");
		diagonalTrack.addActionListener(this);
		trackPanel.add(diagonalTrack);
		
		JButton curveleftTrack = new JButton(CURVELEFTTRACK_IMAGE);
		curveleftTrack.setBounds(0, 0, 40, 40);
		curveleftTrack.setPreferredSize(new Dimension(80,80));
		curveleftTrack.setActionCommand("curveleftTrack");
		curveleftTrack.addActionListener(this);
		trackPanel.add(curveleftTrack);
		
		JButton curverightTrack = new JButton(CURVERIGHTTRACK_IMAGE);
		curverightTrack.setBounds(0, 0, 40, 40);
		curverightTrack.setPreferredSize(new Dimension(80,80));
		curverightTrack.setActionCommand("curverightTrack");
		curverightTrack.addActionListener(this);
		trackPanel.add(curverightTrack);
		
		runButton = new JButton("Simulate");
		trackPanel.add(runButton);		
		runButton.setActionCommand("run");
		runButton.addActionListener(this);
		
		// Selected Track Panel 
		initializeComponent(this.selectedTrackPanel, Font.CENTER_BASELINE, 0, Color.GRAY, 100, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(300, 0, 0, 10), true);
		this.add(selectedTrackPanel, gbConstraints);
		
		selectedTrackPanel.setPreferredSize(new Dimension(250, 300));
        sidePanelTitle = BorderFactory.createTitledBorder(loweredetched, "Currently Selected Track");
        sidePanelTitle.setTitlePosition(TitledBorder.ABOVE_TOP);
        selectedTrackPanel.setBorder(sidePanelTitle);

        this.setVisible(true);
	}

	
	/**
	 * Used for internal testing to see if a are simulating a level correctly. Avoid using Application and directly runs the simulator.
	 * 
	 * @param levelToSimulate
	 */
	/*
	private void testSimulation(Level levelToSimulate) {
		Simulator testSim = new Simulator(levelToSimulate);
		Location location = new Location(0, 4);
		testSim.getTrain().setLocation(location);
		
		Train testTrain = new Train();
		testTrain.setLocation(location);
		testTrain.setHeading(CompassHeading.EAST);
		for(int row = 0; row < numberOfRows; row++) {
			for(int column = 0; column < numberOfColumns; column++) {
				if(levelToSimulate.getMap().getTile(row,column).hasTrack()) {
					redrawTrain(testSim.getTrain());
		        		try {
							testSim.proceedNextTile();
						} catch (TrainCrashException e) {
							logger.error(e.getMessage(), e.fillInStackTrace());
						}
		        		logger.info("NEW location @ " + testSim.getTrain().getLocation().getRow() + ", " + testSim.getTrain().getLocation().getColumn());
		        }
			}
		}
	}
	*/
	
	public void actionPerformed(ActionEvent event) {
		if (event.getActionCommand() == "run") {
			gameController.runSimulation();
			
		}	
		
		if (event.getActionCommand() == "straightTrack") {
			selectedTrackPanel.removeAll();
			selectedTrackImage = new JLabel(STRAIGHTTRACK_IMAGE);
			selectedTrackLabel = new JLabel("Straight Track");
	        rotateLeftButton = new JButton("Rotate Left");
	        rotateRightButton = new JButton("Rotate Right");        

	        selectedTrackImage.setPreferredSize(new Dimension(100,100));
	        selectedTrackLabel.setPreferredSize(new Dimension(100,100));
	        rotateLeftButton.setPreferredSize(new Dimension(100, 50));
	        rotateRightButton.setPreferredSize(new Dimension(100, 50));
	        
	        selectedTrackPanel.add(selectedTrackImage);
	        selectedTrackPanel.add(selectedTrackLabel);
	        selectedTrackPanel.add(rotateLeftButton);
	        selectedTrackPanel.add(rotateRightButton);
			this.setVisible(true);
			selectedTrackPanel.repaint();
		}
		
		if (event.getActionCommand() == "diagonalTrack") {
			selectedTrackPanel.removeAll();
			selectedTrackImage = new JLabel(DIAGONALTRACK_IMAGE);
			selectedTrackLabel = new JLabel("Diagonal Track");
	        rotateLeftButton = new JButton("Rotate Left");
	        rotateRightButton = new JButton("Rotate Right");        

	        selectedTrackImage.setPreferredSize(new Dimension(100,100));
	        selectedTrackLabel.setPreferredSize(new Dimension(100,100));
	        rotateLeftButton.setPreferredSize(new Dimension(100, 50));
	        rotateRightButton.setPreferredSize(new Dimension(100, 50));
	        
	        selectedTrackPanel.add(selectedTrackImage);
	        selectedTrackPanel.add(selectedTrackLabel);
	        selectedTrackPanel.add(rotateLeftButton);
	        selectedTrackPanel.add(rotateRightButton);
			this.setVisible(true);
			selectedTrackPanel.repaint();
		}
		
		if (event.getActionCommand() == "curveleftTrack") {
			selectedTrackPanel.removeAll();
			selectedTrackImage = new JLabel(CURVELEFTTRACK_IMAGE);
			selectedTrackLabel = new JLabel("Curve Left Track");
	        rotateLeftButton = new JButton("Rotate Left");
	        rotateRightButton = new JButton("Rotate Right");        

	        selectedTrackImage.setPreferredSize(new Dimension(100,100));
	        selectedTrackLabel.setPreferredSize(new Dimension(100,100));
	        rotateLeftButton.setPreferredSize(new Dimension(100, 50));
	        rotateRightButton.setPreferredSize(new Dimension(100, 50));
	        
	        selectedTrackPanel.add(selectedTrackImage);
	        selectedTrackPanel.add(selectedTrackLabel);
	        selectedTrackPanel.add(rotateLeftButton);
	        selectedTrackPanel.add(rotateRightButton);
			this.setVisible(true);
			selectedTrackPanel.repaint();
		}
		
		if (event.getActionCommand() == "curverightTrack") {
			selectedTrackPanel.removeAll();
			selectedTrackImage = new JLabel(CURVERIGHTTRACK_IMAGE);
			selectedTrackLabel = new JLabel("Curve Right Track");
	        rotateLeftButton = new JButton("Rotate Left");
	        rotateRightButton = new JButton("Rotate Right");        

	        selectedTrackImage.setPreferredSize(new Dimension(100,100));
	        selectedTrackLabel.setPreferredSize(new Dimension(100,100));
	        rotateLeftButton.setPreferredSize(new Dimension(100, 50));
	        rotateRightButton.setPreferredSize(new Dimension(100, 50));
	        
	        selectedTrackPanel.add(selectedTrackImage);
	        selectedTrackPanel.add(selectedTrackLabel);
	        selectedTrackPanel.add(rotateLeftButton);
	        selectedTrackPanel.add(rotateRightButton);
			this.setVisible(true);
			selectedTrackPanel.repaint();
		}
		
		
		
		
	}
}