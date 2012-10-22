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
import com.trainpuzzle.model.board.Board;
import com.trainpuzzle.model.board.CompassHeading;
import com.trainpuzzle.model.board.Connection;
import com.trainpuzzle.model.board.Location;
import com.trainpuzzle.model.board.Obstacle;
import com.trainpuzzle.model.board.Tile;
import com.trainpuzzle.model.board.Track;
import com.trainpuzzle.model.board.Train;
import com.trainpuzzle.model.level.Level;
import com.trainpuzzle.infrastructure.TrackIcons;
import com.trainpuzzle.controller.TrackPlacer;



// Level selection for the campaign
public class LoadedLevelScreen extends Window implements ActionListener, Observer {
	
	
	private Logger logger = Logger.getLogger(LoadedLevelScreen.class);
	
	private GameController gameController;
	private Level level;
	private Train train;
	
	// Window elements
	

	
	//Map Panel	
	TitledBorder mapTitle;
	JPanel mapPanel = new JPanel();
	private JLayeredPane[][] mapTiles;
	TileMouseAdapter mouseAdapter;
	
	private final int landscapeLayerIndex = 0;
	private final int trackLayerIndex = 1;
	private final int trainLayerIndex = 2;
	private final int obstacleLayerIndex = 3;
	private final int stationLayerIndex = 4;
	
	Location previousTrainLocation = new Location(0,0);
	Location trainLocation = new Location(0,0);
	
	
	
	//Track Panel
	private JPanel trackPanel = new JPanel();
	
	
	//SelectedTrack Panel
	private JPanel selectedTrackPanel = new JPanel();
	private RotatedImageIcon selectedTrackImage;
    private Track selectedTrack;
    
	
	// Constructor
	public LoadedLevelScreen(GameController gameController) {
		//Connect to application
		this.gameController = gameController;
		this.level = this.gameController.getLevel();
		this.train = gameController.getSimulator().getTrain();
		mouseAdapter = new TileMouseAdapter(new TrackPlacer(gameController.getLevel()));
		
		//Window Layout
		setLayout(new GridBagLayout());
		setSize(new Dimension(1280,720));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);	
		
		// Game title
		JLabel titleLabel = new JLabel();
		initializeComponent(titleLabel, Font.CENTER_BASELINE, 28, Color.BLACK, 0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 10, 0, 10), true);
		titleLabel.setText("Level " + level.getLevelNumber());
		this.add(titleLabel, gbConstraints);
		
	    initializeMapPanel(level.getBoard().NUMBER_OF_ROWS, level.getBoard().NUMBER_OF_COLUMNS);

		initializeTrackPanel();
        
        initializeSelectedTrackPanel();
		
	}
	
    public void notifyChange(Object object){
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

    //Map Panel
	private void initializeMapPanel(int numberOfRows, int numberOfColumns) {
		
		mapPanel = new JPanel();	
		mapPanel.setLayout(new GridLayout(numberOfRows, numberOfColumns));
		mapTiles = new JLayeredPane[numberOfRows][numberOfColumns];
		
		// Initialize Map Panel
        for(int row = 0; row < numberOfRows; row++){
            for(int column = 0; column < numberOfColumns; column++){
        		
            	//initializes tile
            	
            	JLayeredPane mapTile = new JLayeredPane();
        		mapTile.setPreferredSize(new Dimension(40, 40));
        		mapTiles[row][column] = mapTile;

            	//add mouse clicky thing to tile
            	level.getBoard().getTile(row, column).register(this);
				mapTile.addMouseListener(mouseAdapter); 
				
				//add tile to map panel
            	mapPanel.add(mapTile);
            }
        }
        

		
        initializeComponent(this.mapPanel, Font.CENTER_BASELINE, 0, Color.LIGHT_GRAY, 0, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), true);
        this.add(this.mapPanel, gbConstraints);
        
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
			landscapeLayer=new JLabel(TrackIcons.GRASS_IMAGE);
			break;
		case WATER:
			landscapeLayer=new JLabel(TrackIcons.WATER_IMAGE);
			break;
		}
		
		landscapeLayer.setTransferHandler(new TransferHandler("icon"));
		
		if(level.getBoard().getTile(row, column).getLandscapeType().equals("water")) {
			landscapeLayer=new JLabel(TrackIcons.WATER_IMAGE);
			landscapeLayer.setTransferHandler(new TransferHandler("icon"));
		}
		landscapeLayer.setBounds(0,0,40,40);
		mapTiles[row][column].add(landscapeLayer, new Integer(landscapeLayerIndex));
	}
	private void drawStation(int row, int column){
		try {
			mapTiles[row][column].remove(mapTiles[row][column].getComponentsInLayer(stationLayerIndex)[0]);
		} catch(Exception e){
			//logger.error(e.getMessage(), e.fillInStackTrace());
		}
		JLayeredPane mapTile = mapTiles[row][column];
		Location location = new Location(row, column);
		if(level.getBoard().getTile(row, column).hasStation(location)) {
			//System.out.println(level.getBoard().getTile(row, column).hasObstacle() + " " + row + " "+  column);
			JLabel stationLayer = new JLabel();
			switch(level.getBoard().getTile(row, column).getStationType()){
				case RED_FRONT:
					stationLayer = new JLabel(TrackIcons.REDSTATION_FRONT_IMAGE);
					break;
				case GREEN_FRONT:
					stationLayer = new JLabel(TrackIcons.GREENSTATION_FRONT_IMAGE);
					break;
				case RED_BACK:
					
					break;
				case GREEN_BACK:
					
					break;
			default:
				break;
			}
			stationLayer.setTransferHandler(new TransferHandler("icon"));
			stationLayer.setBounds(0,0,40,40);
			mapTile.add(stationLayer, new Integer(stationLayerIndex));	
		}	
	}
	private void drawObstacle(int row, int column) {
		try {
			mapTiles[row][column].remove(mapTiles[row][column].getComponentsInLayer(obstacleLayerIndex)[0]);
		} catch(Exception e){
			//logger.error(e.getMessage(), e.fillInStackTrace());
		}
		JLayeredPane mapTile = mapTiles[row][column];
		if(level.getBoard().getTile(row, column).hasObstacle()) {
			//System.out.println(level.getBoard().getTile(row, column).hasObstacle() + " " + row + " "+  column);
			JLabel obstacleLayer = new JLabel();
			switch(level.getBoard().getTile(row, column).getObstacleType()){
				case ROCK:
					obstacleLayer = new JLabel(TrackIcons.ROCK_IMAGE);
					break;
				case TREES:
					obstacleLayer = new JLabel(TrackIcons.TREES_IMAGE);
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
			mapTiles[row][column].remove(mapTiles[row][column].getComponentsInLayer(trackLayerIndex)[0]);
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
						trackLayer=new JLabel(new RotatedImageIcon(TrackIcons.DIAGONALTRACK_IMAGE.getImage(), i * 2 + 1));
						trackLayer.setBounds(0,0,40,40);
						mapTile.add(trackLayer, new Integer(trackLayerIndex));
					}
					straight.rotate90Degrees();
					
					if(connection.equals(diagonal)){
						trackLayer=new JLabel(new RotatedImageIcon(TrackIcons.DIAGONALTRACK_IMAGE.getImage(), i * 2));
						trackLayer.setBounds(0,0,40,40);
						mapTile.add(trackLayer, new Integer(trackLayerIndex));
					}
					diagonal.rotate90Degrees();
				}
					
				for(int i = 0; i < 4; i++){	
					if(connection.equals(curveLeft)){
						trackLayer=new JLabel(new RotatedImageIcon(TrackIcons.CURVELEFTTRACK_IMAGE.getImage(), i * 2));
						trackLayer.setBounds(0,0,40,40);
						mapTile.add(trackLayer, new Integer(trackLayerIndex));
					}
					curveLeft.rotate90Degrees();
					if(connection.equals(curveRight)){
						trackLayer=new JLabel(new RotatedImageIcon(TrackIcons.CURVERIGHTTRACK_IMAGE.getImage(), i * 2));
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
	            	drawObstacle(row, column);
					drawLandscape(row, column);
	            	drawTrack(row, column);
	            	drawStation(row, column);
	            }
	        }
        mapPanel.repaint();
	}
    private void redrawTile(int row, int column){
    	   drawObstacle(row, column);
		   drawLandscape(row, column);
	       drawTrack(row, column);
	       drawStation(row, column);		
	}
    
    
	private void initializeTrain() {
		train.register(this);
		trainLocation = train.getLocation();
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
		
		previousTrainLocation = new Location(trainLocation.getRow(),trainLocation.getColumn());


		trainLocation = train.getLocation();
		int row = trainLocation.getRow();
		int column = trainLocation.getColumn();
		
		int rotation = train.getHeading().ordinal() - 3; //we should make train image point NORTHWEST to begin
		ImageIcon trainImage = new RotatedImageIcon("src/main/resources/images/train.png", rotation);
    	JLabel trainLayer = new JLabel(trainImage);
    	trainLayer.setBounds(0,0,40,40);
		mapTiles[row][column].add(trainLayer, new Integer(trainLayerIndex));
		
		mapPanel.repaint();
	}
	
	//Track Panel
	private void initializeTrackPanel() {
		// Track Panel 
		initializeComponent(this.trackPanel, Font.CENTER_BASELINE, 0, Color.GRAY, 100, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 300, 10), true);
		this.add(trackPanel, gbConstraints);
		
		redrawTrackPanel();
		
		//initialize run button
		JButton runButton = new JButton();
		runButton = new JButton("Simulate");
		trackPanel.add(runButton);		
		runButton.setActionCommand("run");
		runButton.addActionListener(this);
		
	}
	private void redrawTrackPanel(){

		trackPanel.setPreferredSize(new Dimension(250, 300));
		Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		TitledBorder sidePanelTitle;
        sidePanelTitle = BorderFactory.createTitledBorder(loweredetched, "Select Track");
        sidePanelTitle.setTitlePosition(TitledBorder.ABOVE_TOP);
		trackPanel.setBorder(sidePanelTitle);
	
		JButton straightTrack = new JButton(TrackIcons.STRAIGHTTRACK_IMAGE);
		straightTrack.setPreferredSize(new Dimension(80,80));
		straightTrack.setBounds(0, 0, 40, 40);
		straightTrack.setActionCommand("straightTrack");
		straightTrack.addActionListener(this);
		trackPanel.add(straightTrack);
		
		JButton diagonalTrack = new JButton(TrackIcons.DIAGONALTRACK_IMAGE);
		diagonalTrack.setBounds(0, 0, 40, 40);
		diagonalTrack.setPreferredSize(new Dimension(80,80));
		diagonalTrack.setActionCommand("diagonalTrack");
		diagonalTrack.addActionListener(this);
		trackPanel.add(diagonalTrack);
		
		JButton curveleftTrack = new JButton(TrackIcons.CURVELEFTTRACK_IMAGE);
		curveleftTrack.setBounds(0, 0, 40, 40);
		curveleftTrack.setPreferredSize(new Dimension(80,80));
		curveleftTrack.setActionCommand("curveleftTrack");
		curveleftTrack.addActionListener(this);
		trackPanel.add(curveleftTrack);
		
		JButton curverightTrack = new JButton(TrackIcons.CURVERIGHTTRACK_IMAGE);
		curverightTrack.setBounds(0, 0, 40, 40);
		curverightTrack.setPreferredSize(new Dimension(80,80));
		curverightTrack.setActionCommand("curverightTrack");
		curverightTrack.addActionListener(this);
		trackPanel.add(curverightTrack);
				
    }
	
	//Selected Track Panel 
	private void initializeSelectedTrackPanel() {
		//Initialize Selected Track Panel 
		initializeComponent(this.selectedTrackPanel, Font.CENTER_BASELINE, 0, Color.GRAY, 100, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(300, 0, 0, 10), true);
		this.add(selectedTrackPanel, gbConstraints);
		
		selectedTrackPanel.setPreferredSize(new Dimension(250, 300));
		Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		TitledBorder sidePanelTitle;
        sidePanelTitle = BorderFactory.createTitledBorder(loweredetched, "Currently Selected Track");
        sidePanelTitle.setTitlePosition(TitledBorder.ABOVE_TOP);
        selectedTrackPanel.setBorder(sidePanelTitle);
              
        redrawSelectedTrackPanel();
	}
	private void redrawSelectedTrackPanel() {
		selectedTrackPanel.removeAll();
		JLabel selectedTrackContainer = new JLabel(selectedTrackImage);
		JButton rotateButton = new JButton();
		rotateButton.add(selectedTrackContainer);
		rotateButton.setBounds(0, 0, 40, 40);
		rotateButton.setPreferredSize(new Dimension(100, 100));
		rotateButton.setMargin(new Insets(0, 15, 0, 0));
		rotateButton.setActionCommand("rotateTrack");
		rotateButton.addActionListener(this);
		selectedTrackPanel.add(rotateButton);
		selectedTrackPanel.repaint();
		this.setVisible(true);
	}

 
  

	//TODO: this method seems pretty useless remove?
	public void create() {
        this.setVisible(true);
	}

	
	public void actionPerformed(ActionEvent event) {
		if (event.getActionCommand() == "run") {
			gameController.runSimulation();
			
		}	
		
		if (event.getActionCommand() == "straightTrack") {
			//Create and set new connection on mouseAdapter
			Connection connection = new Connection(CompassHeading.EAST,CompassHeading.WEST);
			selectedTrack = new Track(connection);
			mouseAdapter.setTrack(selectedTrack);
			
			selectedTrackImage = new RotatedImageIcon(TrackIcons.STRAIGHTTRACK_IMAGE.getImage());
			
			redrawSelectedTrackPanel();
		}
		
		if (event.getActionCommand() == "diagonalTrack") {
			//Create and set new connection on mouseAdapter
			Connection connection = new Connection(CompassHeading.NORTHWEST,CompassHeading.SOUTHEAST);
			selectedTrack = new Track(connection);
			mouseAdapter.setTrack(selectedTrack);
			
			selectedTrackImage = new RotatedImageIcon(TrackIcons.DIAGONALTRACK_IMAGE.getImage());
			
			redrawSelectedTrackPanel();
		}
		
		if (event.getActionCommand() == "curveleftTrack") {
			//Create and set new connection on mouseAdapter
			Connection connection = new Connection(CompassHeading.NORTHWEST,CompassHeading.SOUTH);
			selectedTrack = new Track(connection);
			mouseAdapter.setTrack(selectedTrack);
			
			selectedTrackImage = new RotatedImageIcon(TrackIcons.CURVELEFTTRACK_IMAGE.getImage());
			
			redrawSelectedTrackPanel();
		}
		
		if (event.getActionCommand() == "curverightTrack") {
			
			//Create and set new connection on mouseAdapter
			Connection connection = new Connection(CompassHeading.NORTHEAST,CompassHeading.SOUTH);
			selectedTrack = new Track(connection);
			mouseAdapter.setTrack(selectedTrack);
			
			selectedTrackImage = new RotatedImageIcon(TrackIcons.CURVERIGHTTRACK_IMAGE.getImage());
			
			redrawSelectedTrackPanel();
		}
		
		if (event.getActionCommand() == "rotateTrack") {	
			//rotate track and set on  mouseAdapter
			selectedTrack.rotateTrack();
			mouseAdapter.setTrack(selectedTrack);
			
			selectedTrackImage.rotate90DegreesClockwise();
			
			redrawSelectedTrackPanel();
		}
	}

}