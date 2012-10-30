package com.trainpuzzle.ui.windows;
import com.trainpuzzle.observe.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.log4j.Logger;

import com.trainpuzzle.controller.Application;
import com.trainpuzzle.controller.GameController;
import com.trainpuzzle.controller.Simulator;
import com.trainpuzzle.exception.CannotPlaceTrackException;
import com.trainpuzzle.exception.CannotRemoveTrackException;
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
import com.trainpuzzle.infrastructure.Images;
import com.trainpuzzle.controller.TrackPlacer;


// Level selection for the campaign
public class LoadedLevelScreen extends Window implements ActionListener, Observer {

	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger(LoadedLevelScreen.class);
	
	private GameController gameController;
	private Level level;
	private Train train;
	
	// Window elements
	
	private JPanel headerPanel = new JPanel();
	
	//Map Panel	
	TitledBorder mapTitle;
	JPanel map = new JPanel();
	private JLayeredPane[][] mapTiles;
	TileMouseAdapter mouseAdapter;
	
	private final int landscapeLayerIndex = 0;
	private final int trackLayerIndex = 1;
	private final int trainLayerIndex = 2;
	private final int obstacleLayerIndex = 3;
	
	Location previousTrainLocation = new Location(0,0);
	
	private JButton runButton = new JButton("Run");
	private JButton pauseButton = new JButton("Pause");
	
	//Track Panel
	private JPanel trackPanel = new JPanel();
	
	
	//SelectedTrack Panel
	private JPanel selectedTrackPanel = new JPanel();
	private RotatedImageIcon selectedTrackImage;
	private Track selectedTrack;
	
	private JPanel sidePanel = new JPanel();
	private JPanel gameControlBox = new JPanel();
	JLabel messageBox =  new JLabel("<html></html>");
	
	
	// Constructor
	public LoadedLevelScreen(GameController gameController) {
		//Connect to application
		this.gameController = gameController;
		this.level = this.gameController.getLevel();
		this.train = gameController.getSimulator().getTrain();
		mouseAdapter = new TileMouseAdapter(new TrackPlacer(gameController.getLevel()));
		
		//Window Layout
		setLayout(new GridBagLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setBackground(this.getBackground());
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setMinimumSize(new Dimension(1024, 700));

		
		// Game title
		
		initializeHeaderPanel();
		initializeMapPanel();
		initializeSidePanel();
		
		
	}

	private void initializeHeaderPanel() {
		headerPanel = new JPanel();
		addComponent(this, headerPanel, Font.CENTER_BASELINE, 28, this.getBackground(), 0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), true);
		headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		initializeBackButton();
		initializeTitle();
	}

	private void initializeTitle() {
		JLabel titleLabel = new JLabel();
		titleLabel.setText("Level " + level.getLevelNumber());
		Font titleFont = new Font("Arial", Font.BOLD, 28);
		titleLabel.setFont(titleFont);
		headerPanel.add(titleLabel);
	}
	
	private void initializeBackButton() {
		JButton backToLevelSelect = new JButton("Back to Level Select");
		headerPanel.add(backToLevelSelect);		
		backToLevelSelect.setActionCommand("backToLevelSelect");
		backToLevelSelect.addActionListener(this);
	}

	private void initializeMapPanel() {
		JPanel mapPanel = new JPanel();
		mapPanel.setPreferredSize(new Dimension(800, 600));
		addComponent(this, sidePanel, Font.CENTER_BASELINE, 0, this.getBackground(), 0, 1, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), true);
		initializeMap(level.getBoard().NUMBER_OF_ROWS, level.getBoard().NUMBER_OF_COLUMNS);
	}
	
	private void initializeMap(int numberOfRows, int numberOfColumns) {
		addComponent(this, this.map, Font.CENTER_BASELINE, 0, this.getBackground(), 0, 1, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), true);
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
	
	private void initializeSidePanel() {
		sidePanel.setPreferredSize(new Dimension(200, 600));
		addComponent(this, sidePanel, Font.CENTER_BASELINE, 28, this.getBackground(), 1, 0, 1, 2, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), true);
		
		
		initializeSaveButton();
		
		initializeGameControlBox();
		initializeTrackPanel();
		initializeSelectedTrackPanel();
	}

	private void initializeSaveButton() {
		//initialize Save button
		JButton saveButton = new JButton("Save Level");
		sidePanel.add(saveButton);
		saveButton.setActionCommand("save");
		saveButton.addActionListener(this);
	}

	private void initializeGameControlBox() {
		addComponent(sidePanel, this.gameControlBox, Font.CENTER_BASELINE, 0, this.getBackground(), 0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), true);
		gameControlBox.setPreferredSize(new Dimension(200, 150));
		Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		TitledBorder gameControlBoxTitle;
		gameControlBoxTitle = BorderFactory.createTitledBorder(loweredetched, "Game Controls");
		gameControlBoxTitle.setTitlePosition(TitledBorder.ABOVE_TOP);
		gameControlBox.setBorder(gameControlBoxTitle);
		gameControlBox.setLayout(new GridBagLayout());
		
		
		int defaultFontSize = UIManager.getDefaults().getFont("TextPane.font").getSize();
		
		//initialize run button
		runButton = new JButton("Run");
		//gameControlBox.add(runButton);
		addComponent(gameControlBox, runButton, Font.CENTER_BASELINE, defaultFontSize, this.getBackground(), 0, 0, 3, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), true);		
		runButton.setPreferredSize(new Dimension(200, 150));
		runButton.setActionCommand("run");
		runButton.addActionListener(this);
		
		//initialize pause button
		pauseButton = new JButton("Pause");
		addComponent(gameControlBox, pauseButton, Font.CENTER_BASELINE, defaultFontSize, this.getBackground(), 0, 0, 3, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), true);
		pauseButton.setActionCommand("pause");
		pauseButton.addActionListener(this);
		pauseButton.setVisible(false);	
		
		//initialize speed decrease button
		JButton speedDecreaseButton = new JButton("-");
		addComponent(gameControlBox, speedDecreaseButton, Font.PLAIN, defaultFontSize, this.getBackground(), 0, 1, 1, 1, (float) 0.33, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), true);
		speedDecreaseButton.setActionCommand("tickDecrease");
		speedDecreaseButton.addActionListener(this);
		
		//initialize single step button
		JButton singleStepButton = new JButton(">");
		addComponent(gameControlBox, singleStepButton, Font.PLAIN, defaultFontSize, this.getBackground(), 1, 1, 1, 1, (float) 0.33, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), true);
		singleStepButton.setActionCommand("tickOnce");
		singleStepButton.addActionListener(this);
		
		
		
		//initialize speed decrease button
		JButton speedIncreaseButton = new JButton("+");
		addComponent(gameControlBox, speedIncreaseButton, Font.PLAIN, defaultFontSize, this.getBackground(), 2, 1, 1, 1, (float) 0.33, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), true);
		speedIncreaseButton.setActionCommand("tickIncrease");
		speedIncreaseButton.addActionListener(this);
		
		//initialize reset button
		JButton resetButton = new JButton("Reset Position");
		addComponent(gameControlBox, resetButton, Font.PLAIN, defaultFontSize, this.getBackground(), 0, 2, 3, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), true);
		resetButton.setActionCommand("reset");
		resetButton.addActionListener(this);
		
		//initialize removeAllTracksButton button
		JButton removeAllTracksButton = new JButton("Remove All Tracks");
		addComponent(gameControlBox, removeAllTracksButton, Font.PLAIN, defaultFontSize, this.getBackground(), 0, 3, 3, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), true);
		removeAllTracksButton.setActionCommand("removeAllTracks");
		removeAllTracksButton.addActionListener(this);
		
		//messageBox =  new JLabel("<html>First line and maybe second line</html>");
		messageBox.setHorizontalAlignment(JLabel.CENTER);
		Font font = messageBox.getFont();
		messageBox.setFont(font.deriveFont(font.getStyle() | Font.BOLD));
		messageBox.setForeground(Color.BLACK);
		//gameControlBox.add(messageBox);
			  
	}	
	
	//Map Panel

	
	
	//Track Panel
	private void initializeTrackPanel() {
		// Track Panel 
		
		addComponent(sidePanel, this.trackPanel, Font.CENTER_BASELINE, 0, this.getBackground(), 0, 1, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), true);
		trackPanel.setPreferredSize(new Dimension(200, 300));
		
		Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		TitledBorder sidePanelTitle;
		sidePanelTitle = BorderFactory.createTitledBorder(loweredetched, "Select Track");
		sidePanelTitle.setTitlePosition(TitledBorder.ABOVE_TOP);
		trackPanel.setBorder(sidePanelTitle);
	
		redrawTrackPanel();
	}
	
	private void redrawTrackPanel(){
	
		JButton straightTrack = new JButton(Images.STRAIGHT_TRACK_IMAGE);
		straightTrack.setPreferredSize(new Dimension(80,80));
		straightTrack.setBounds(0, 0, 40, 40);
		straightTrack.setActionCommand("straightTrack");
		straightTrack.addActionListener(this);
		trackPanel.add(straightTrack);
		
		JButton diagonalTrack = new JButton(Images.DIAGONAL_TRACK_IMAGE);
		diagonalTrack.setBounds(0, 0, 40, 40);
		diagonalTrack.setPreferredSize(new Dimension(80,80));
		diagonalTrack.setActionCommand("diagonalTrack");
		diagonalTrack.addActionListener(this);
		trackPanel.add(diagonalTrack);
		
		JButton curveleftTrack = new JButton(Images.CURVELEFT_TRACK_IMAGE);
		curveleftTrack.setBounds(0, 0, 40, 40);
		curveleftTrack.setPreferredSize(new Dimension(80,80));
		curveleftTrack.setActionCommand("curveleftTrack");
		curveleftTrack.addActionListener(this);
		trackPanel.add(curveleftTrack);
		
		JButton curverightTrack = new JButton(Images.CURVERIGHT_TRACK_IMAGE);
		curverightTrack.setBounds(0, 0, 40, 40);
		curverightTrack.setPreferredSize(new Dimension(80,80));
		curverightTrack.setActionCommand("curverightTrack");
		curverightTrack.addActionListener(this);
		trackPanel.add(curverightTrack);
		
		JButton intersectionTrack = new JButton(Images.INTERSECTION_TRACK_IMAGE);
		intersectionTrack.setBounds(0, 0, 40, 40);
		intersectionTrack.setPreferredSize(new Dimension(80,80));
		intersectionTrack.setActionCommand("intersectionTrack");
		intersectionTrack.addActionListener(this);
		trackPanel.add(intersectionTrack);
		
		JButton diagonalIntersectionTrack = new JButton(Images.DIAGONAL_INTERSECTION_TRACK_IMAGE);
		diagonalIntersectionTrack.setBounds(0, 0, 40, 40);
		diagonalIntersectionTrack.setPreferredSize(new Dimension(80,80));
		diagonalIntersectionTrack.setActionCommand("diagonalIntersectionTrack");
		diagonalIntersectionTrack.addActionListener(this);
		trackPanel.add(diagonalIntersectionTrack);
				
	}
	
	
	//Selected Track Panel 
	private void initializeSelectedTrackPanel() {
		//Initialize Selected Track Panel 
		addComponent(sidePanel, this.selectedTrackPanel, Font.CENTER_BASELINE, 0, this.getBackground(), 0, 2, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), true);
		//selectedTrackPanel.setPreferredSize(new Dimension(150, 150));
		
		Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		TitledBorder sidePanelTitle;
		sidePanelTitle = BorderFactory.createTitledBorder(loweredetched, "Click to rotate");
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
		if (event.getActionCommand() == "backToLevelSelect") {
			WindowManager.getManager().setActiveWindow(WindowManager.getManager().getPreviousWindow()); 
			WindowManager.getManager().updateWindows();	
			WindowManager.getManager().setPreviousWindow(this);
		}
		if (event.getActionCommand() == "run") {
			gameController.getSimulator().run();
			runButton.setVisible(false);
			pauseButton.setVisible(true);
			
		}
		if (event.getActionCommand() == "pause") {
			gameController.getSimulator().stop();
			runButton.setVisible(true);
			pauseButton.setVisible(false);
		}	
		if (event.getActionCommand() == "reset") {
			gameController.getSimulator().reset();
			
		}	
		if (event.getActionCommand() == "tickDecrease") {
			int decreasedValue = gameController.getSimulator().getTickInterval() * 2;
			int upperBound = gameController.getSimulator().getTickIntervalUpperBound();
			if(decreasedValue <= upperBound){
				gameController.getSimulator().setTickInterval(decreasedValue);
			}
		}
		
		if (event.getActionCommand() == "tickOnce") {
			gameController.getSimulator().move();
		}
		
		if (event.getActionCommand() == "tickIncrease") {
			int increasedValue = gameController.getSimulator().getTickInterval() / 2;
			int lowerBound = gameController.getSimulator().getTickIntervalLowerBound();
			if(increasedValue >= lowerBound){
				gameController.getSimulator().setTickInterval(increasedValue);
			}
			
		}
		
		if (event.getActionCommand() == "save") {
			File saveLevelFile = saveFileDialog();
			if(saveLevelFile != null){
				gameController.saveCurrentLevel(saveLevelFile);
			}
		}
		if (event.getActionCommand() == "straightTrack") {
			//Create and set new connection on mouseAdapter
			Connection connection = new Connection(CompassHeading.EAST,CompassHeading.WEST);
			selectedTrack = new Track(connection);
			mouseAdapter.setTrack(selectedTrack);
			
			selectedTrackImage = new RotatedImageIcon(Images.STRAIGHT_TRACK);
			
			redrawSelectedTrackPanel();
		}
		
		if (event.getActionCommand() == "diagonalTrack") {
			//Create and set new connection on mouseAdapter
			Connection connection = new Connection(CompassHeading.NORTHWEST,CompassHeading.SOUTHEAST);
			selectedTrack = new Track(connection);
			mouseAdapter.setTrack(selectedTrack);
			
			selectedTrackImage = new RotatedImageIcon(Images.DIAGONAL_TRACK);
			
			redrawSelectedTrackPanel();
		}
		
		if (event.getActionCommand() == "curveleftTrack") {
			//Create and set new connection on mouseAdapter
			Connection connection = new Connection(CompassHeading.NORTHWEST,CompassHeading.SOUTH);
			selectedTrack = new Track(connection);
			mouseAdapter.setTrack(selectedTrack);
			
			selectedTrackImage = new RotatedImageIcon(Images.CURVELEFT_TRACK);
			
			redrawSelectedTrackPanel();
		}
		
		if (event.getActionCommand() == "curverightTrack") {
			
			//Create and set new connection on mouseAdapter
			Connection connection = new Connection(CompassHeading.NORTHEAST,CompassHeading.SOUTH);
			selectedTrack = new Track(connection);
			mouseAdapter.setTrack(selectedTrack);
			
			selectedTrackImage = new RotatedImageIcon(Images.CURVERIGHT_TRACK);
			
			redrawSelectedTrackPanel();
		}
		
		if (event.getActionCommand() == "intersectionTrack") {
			//Create and set new track on mouseAdapter
			selectedTrack = new Track();
			selectedTrack.addConnection(CompassHeading.NORTH,CompassHeading.SOUTH);
			selectedTrack.addConnection(CompassHeading.WEST,CompassHeading.EAST);
			mouseAdapter.setTrack(selectedTrack);
			
			selectedTrackImage = new RotatedImageIcon(Images.INTERSECTION_TRACK);
			
			redrawSelectedTrackPanel();
		}
		
		if (event.getActionCommand() == "diagonalIntersectionTrack") {
			//Create and set new track on mouseAdapter
			selectedTrack = new Track();
			selectedTrack.addConnection(CompassHeading.NORTHEAST,CompassHeading.SOUTHWEST);
			selectedTrack.addConnection(CompassHeading.NORTHWEST,CompassHeading.SOUTHEAST);
			mouseAdapter.setTrack(selectedTrack);
			
			selectedTrackImage = new RotatedImageIcon(Images.DIAGONAL_INTERSECTION_TRACK);
			
			redrawSelectedTrackPanel();
		}
		
		if (event.getActionCommand() == "rotateTrack") {	
			selectedTrack = new Track(selectedTrack);
			//rotate track and set on  mouseAdapter
			selectedTrack.rotateTrack();
			mouseAdapter.setTrack(selectedTrack);
			
			selectedTrackImage.rotate90DegreesClockwise();
			
			redrawSelectedTrackPanel();
		}
	}
	
	private File saveFileDialog(){
		JFileChooser chooser = new JFileChooser();
	    FileNameExtensionFilter filter = new FileNameExtensionFilter(
	        "XML Encoded Level", "xml");
	    chooser.setFileFilter(filter);
	    int returnVal = chooser.showSaveDialog(this);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	    	return chooser.getSelectedFile();
	    }
		return null;
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

		else if(object instanceof CannotRemoveTrackException){
			messageBox.setForeground(Color.RED);
			messageBox.setText("<html><p>Cannot remove track</p></html>");
		}
		else if(object instanceof CannotPlaceTrackException){
			messageBox.setForeground(Color.RED);
			messageBox.setText("<html><p>Cannot place track</p></html>");
		}
		else if(object instanceof TrainCrashException){
			messageBox.setForeground(Color.RED);
			messageBox.setText("<html><p>Train has crashed</p><p>Please reset and try again</p></html>");
		}
	}

	
}