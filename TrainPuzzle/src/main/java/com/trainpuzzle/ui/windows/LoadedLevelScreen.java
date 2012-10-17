package com.trainpuzzle.ui.windows;
import com.trainpuzzle.observe.*;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import org.apache.log4j.Logger;

import com.trainpuzzle.controller.Application;
import com.trainpuzzle.controller.GameController;
import com.trainpuzzle.controller.Simulator;
import com.trainpuzzle.exception.TrainCrashException;
import com.trainpuzzle.model.level.Level;
import com.trainpuzzle.model.map.Heading;
import com.trainpuzzle.model.map.Tile;
import com.trainpuzzle.model.map.Train;
import com.trainpuzzle.model.map.Location;

import com.trainpuzzle.controller.TrackPlacer;



// Level selection for the campaign
public class LoadedLevelScreen extends Window implements ActionListener, Observer {
	
	// Window elements
	private JLabel titleLabel = new JLabel();
	private JButton runButton = new JButton();
	private JPanel mapPanel = new JPanel();
	private JPanel sidePanel = new JPanel();
	private JLabel landscapeLayer = new JLabel();
	private JLabel trackLayer = new JLabel();
	private JLabel tempTrack = new JLabel();
	private JLayeredPane draggableTile = new JLayeredPane();
	private Logger logger = Logger.getLogger(LoadedLevelScreen.class);
	
	MouseListener mouseListener;
	
	Location previousTrainLocation;
	
	int numberOfRows = 15;
	int numberOfColumns = 20;
	
	private final int landscapeLayerIndex = 0;
	private final int trackLayerIndex = 1;
	private final int trainLayerIndex = 2;
	
	private JLayeredPane mapTile;
	private JLayeredPane[][] mapTiles;
	
	private GameController gameController;
	private Level level;
	
	Border loweredbevel, loweredetched;
	TitledBorder mapTitle, sidePanelTitle;
	
	// Constructor
	public LoadedLevelScreen(GameController gameController) {
		this.gameController = gameController;
		loweredbevel = BorderFactory.createLoweredBevelBorder();
		loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);

		//setBackground(Color.LIGHT_GRAY);
		setLayout(new GridBagLayout());
		setSize(new Dimension(1280,720));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);	
		
		previousTrainLocation = new Location(0,0);
	}
	
	public void notifyChange(){
		redrawTiles();
	}
	
	public void redrawTiles(){
        for(int row = 0; row < numberOfRows; row++){
            for(int column = 0; column < numberOfColumns; column++){
				try {
					modifyLandscape(row, column);
	            	modifyTrack(row, column);
					//logger.info("removing @ " + previousTrainLatitude + ", " + previousTrainLongitude);
		        	
				} catch(Exception e){
					logger.error(e.getMessage(), e.fillInStackTrace());
				}
            }
        }
        mapPanel.repaint();
	}
	
	private void modifyLandscape(int row, int column) {
		try {
			mapTiles[row][column].remove(mapTiles[row][column].getComponentsInLayer(landscapeLayerIndex)[0]);
		} catch(Exception e){
			logger.error(e.getMessage(), e.fillInStackTrace());
		}
		if(level.getMap().getTile(row, column).getLandscapeType().equals("grass")) {
			landscapeLayer=new JLabel(new ImageIcon("src/main/resources/images/grass.png"));
			landscapeLayer.setTransferHandler(new TransferHandler("icon"));
		}
		
		if(level.getMap().getTile(row, column).getLandscapeType().equals("water")) {
			landscapeLayer=new JLabel(new ImageIcon("src/main/resources/images/water.png"));
			landscapeLayer.setTransferHandler(new TransferHandler("icon"));
		}
		landscapeLayer.setBounds(0,0,40,40);
		mapTile.add(landscapeLayer, new Integer(landscapeLayerIndex));
	}
	
	private void modifyTrack(int row, int column) {
		try {
			mapTiles[row][column].remove(mapTiles[row][column].getComponentsInLayer(trackLayerIndex)[0]);
		} catch(Exception e){
			logger.error(e.getMessage(), e.fillInStackTrace());
		}
		if(level.getMap().getTile(row, column).hasTrack()){
			trackLayer=new JLabel(new ImageIcon("src/main/resources/images/track.png"));
			trackLayer.setBounds(0,0,40,40);
			mapTile.add(trackLayer, new Integer(trackLayerIndex));
		}
	}
	
	public void redrawTrain(Train train) {
		
		JLabel trainLayer;
    	trainLayer = new JLabel(new ImageIcon("src/main/resources/images/train.png"));
    	trainLayer.setBounds(0,0,40,40);
        
		Location trainLocation = train.getLocation();
		int row = trainLocation.getRow();
		int column = trainLocation.getColumn();
		int previousRow = previousTrainLocation.getRow();
		int previousColumn = previousTrainLocation.getColumn();
		
		mapTiles[row][column].add(trainLayer, new Integer(trainLayerIndex));
		
		try {
			mapTiles[previousRow][previousColumn].remove(mapTiles[previousRow][previousColumn].getComponentsInLayer(trainLayerIndex)[0]);
			//logger.info("removing @ " + previousTrainLatitude + ", " + previousTrainLongitude);
        	
		} catch(Exception e){
			logger.error(e.getMessage(), e.fillInStackTrace());
		}
		
		previousTrainLocation = trainLocation;
		
		mapPanel.repaint();
	}
	
	public void create() {
	
		// Game title
		initializeComponent(this.titleLabel, Font.CENTER_BASELINE, 28, Color.BLACK, 0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 10, 0, 10), true);
		this.titleLabel.setText("Level 1");
		this.add(this.titleLabel, gbConstraints);
		
		// Map Panel
		level = new Level(1);
		
		mapTiles = new JLayeredPane[numberOfRows][numberOfColumns];
		
		mapPanel = new JPanel();	
		mapPanel.setLayout(new GridLayout(numberOfRows, numberOfColumns));
		
		//System.out.println(gameController.toString());

		
        for(int row = 0; row < numberOfRows; row++){
            for(int column = 0; column < numberOfColumns; column++){
        		mapTile = new JLayeredPane();
        		mapTile.setPreferredSize(new Dimension(40, 40));
        		
				modifyLandscape(row, column);
            	modifyTrack(row, column);
        		
				mapTile.addMouseListener(mouseListener); 
            	mapPanel.add(mapTile);
            	mapTiles[row][column] = mapTile;
            	
            }
        }
        
		System.out.println(gameController.getSimulator().getTrain().getHeading());
		Train train = gameController.getSimulator().getTrain();
		redrawTrain(train);
        
        initializeComponent(this.mapPanel, Font.CENTER_BASELINE, 0, Color.LIGHT_GRAY, 0, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), true);
        this.add(this.mapPanel, gbConstraints);
		
		//testSimulation(testLevel);
		
		// Track Panel 
        sidePanel.setPreferredSize(new Dimension(250, 600));
        sidePanel.setLayout(new GridLayout(10, 2));
        sidePanelTitle = BorderFactory.createTitledBorder(loweredetched, "Action");
        sidePanelTitle.setTitlePosition(TitledBorder.ABOVE_TOP);
		sidePanel.setBorder(sidePanelTitle);	
		
    	draggableTile = new JLayeredPane();
    	draggableTile.setPreferredSize(new Dimension(40, 40));
		
		runButton = new JButton("Simulate");
		sidePanel.add(runButton);
		
		trackLayer=new JLabel(new ImageIcon("src/main/resources/images/track.png"));
		trackLayer.setBounds(0,0,40,40);
		trackLayer.addMouseListener(mouseListener);
		trackLayer.setTransferHandler(new TransferHandler("icon"));
		//draggableTile.add(trackTile, new Integer(1));
		
		tempTrack=new JLabel(new ImageIcon("src/main/resources/images/tempTrack.png"));
		tempTrack.setBounds(0,0,40,40);
		tempTrack.addMouseListener(mouseListener);
		tempTrack.setTransferHandler(new TransferHandler("icon"));
		//draggableTile.add(tempTrack, new Integer(1));
		
		sidePanel.add(trackLayer);
		sidePanel.add(tempTrack);
		
		runButton.setActionCommand("run");
		runButton.addActionListener(this);
		
		initializeComponent(this.sidePanel, Font.CENTER_BASELINE, 0, Color.LIGHT_GRAY, 200, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 10), true);
		this.add(sidePanel, gbConstraints);
		
		this.setVisible(true);
	}




	
	/**
	 * Used for internal testing to see if a are simulating a level correctly. Avoid using Application and directly runs the simulator.
	 * 
	 * @param levelToSimulate
	 */
	private void testSimulation(Level levelToSimulate) {
		Simulator testSim = new Simulator(levelToSimulate);
		testSim.getTrain().setLocation(0, 4);
		
		Train testTrain = new Train();
		testTrain.setLocation(0, 4);
		testTrain.setHeading(Heading.EAST);
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

	public void actionPerformed(ActionEvent event) {
		if (event.getActionCommand() == "run") {
			gameController.runSimulation();
		}	
	}
}