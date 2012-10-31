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

import com.trainpuzzle.controller.GameController;
import com.trainpuzzle.exception.CannotPlaceTrackException;
import com.trainpuzzle.exception.CannotRemoveTrackException;
import com.trainpuzzle.exception.TrainCrashException;
import com.trainpuzzle.model.board.CompassHeading;
import com.trainpuzzle.model.board.Connection;
import com.trainpuzzle.model.board.Track;
import com.trainpuzzle.model.board.TrackType;
import com.trainpuzzle.model.level.Level;
import com.trainpuzzle.infrastructure.Images;
import com.trainpuzzle.controller.TrackPlacer;


// Level selection for the campaign
public class LoadedLevelScreen extends Window implements ActionListener, Observer {

	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger(LoadedLevelScreen.class);
	
	private GameController gameController;
	private Level level;

	TileMouseAdapter mouseAdapter;
	
	// Window elements
	
	private JPanel headerPanel = new JPanel();
	private LoadedLevelMap loadedLevelMap;
	private JPanel sidePanel = new JPanel();
	private JPanel gameControlBox = new JPanel();
	private JButton runButton = new JButton("Run");
	private JButton pauseButton = new JButton("Pause");
	private JPanel trackPanel = new JPanel();
	private JPanel selectedTrackPanel = new JPanel();
	private JButton rotateButton = new JButton("Run");
	private RotatedImageIcon selectedTrackImage;
	private Track selectedTrack;
	private JLabel messageBox =  new JLabel("<html></html>");

	// Constructor
	public LoadedLevelScreen(GameController gameController) {
		//Connect to application
		this.gameController = gameController;
		this.level = this.gameController.getLevel();
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
		headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		headerPanel.add(backButton());
		headerPanel.add(titleLabel());
		addComponent(this, headerPanel, Font.CENTER_BASELINE, 28, this.getBackground(), 0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), true);
	}

	private JButton backButton() {
		JButton backToLevelSelect = new JButton("Back to Level Select");
		backToLevelSelect.setActionCommand("backToLevelSelect");
		backToLevelSelect.addActionListener(this);
		return(backToLevelSelect);
	}
	
	private JLabel titleLabel() {
		JLabel titleLabel = new JLabel();
		titleLabel.setText("Level " + level.getLevelNumber());
		Font titleFont = new Font("Arial", Font.BOLD, 28);
		titleLabel.setFont(titleFont);
		return titleLabel;
	}
	

	private void initializeSidePanel() {
		sidePanel.setPreferredSize(new Dimension(200, 600));
		sidePanel.setLayout(new FlowLayout());
		sidePanel.add(saveButton());
		sidePanel.add(gameControlBox());
		sidePanel.add(trackPanel());
		sidePanel.add(selectedTrackPanel());
		addComponent(this, sidePanel, Font.CENTER_BASELINE, 28, this.getBackground(), 1, 0, 1, 2, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), true);
	}

	private JButton saveButton() {
		JButton saveButton = new JButton("Save Level");
		saveButton.setActionCommand("save");
		saveButton.addActionListener(this);
		return saveButton;
	}

	private JPanel gameControlBox() {
		gameControlBox.setPreferredSize(new Dimension(200, 150));
		Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		TitledBorder gameControlBoxTitle;
		gameControlBoxTitle = BorderFactory.createTitledBorder(loweredetched, "Game Controls");
		gameControlBoxTitle.setTitlePosition(TitledBorder.ABOVE_TOP);
		gameControlBox.setBorder(gameControlBoxTitle);
		gameControlBox.setLayout(new GridBagLayout());

		addButtonToGridBag(runButton, "run",  gameControlBox, 0, 0, 3, 1);	
		runButton.setVisible(true);
		
		addButtonToGridBag(pauseButton, "pause", gameControlBox, 0, 0, 3, 1);
		pauseButton.setVisible(false);
		
		JButton speedDecreaseButton = new JButton("-");
		addButtonToGridBag(speedDecreaseButton, "tickDecrease", gameControlBox, 0, 1, 1, 1);
		
		JButton singleStepButton = new JButton(">");
		addButtonToGridBag(singleStepButton, "tickOnce", gameControlBox, 1, 1, 1, 1);
		
		JButton speedIncreaseButton = new JButton("+");
		addButtonToGridBag(speedIncreaseButton, "tickIncrease", gameControlBox, 2, 1, 1, 1);
		
		JButton resetButton = new JButton("Reset Position");
		addButtonToGridBag(resetButton, "reset", gameControlBox, 0, 2, 3, 1);
		
		JButton removeAllTracksButton = new JButton("Remove All Tracks");
		addButtonToGridBag(removeAllTracksButton, "removeAllTracks", gameControlBox, 0, 3, 3, 1);
		
		//messageBox =  new JLabel("<html>First line and maybe second line</html>");
		messageBox.setHorizontalAlignment(JLabel.CENTER);
		Font font = messageBox.getFont();
		messageBox.setFont(font.deriveFont(font.getStyle() | Font.BOLD));
		messageBox.setForeground(Color.BLACK);
		//gameControlBox.add(messageBox);
		
		return gameControlBox;
			  
	}

	private void addButtonToGridBag(JButton button, String actionCommand, Container destinationContainer, int x, int y, int width, int height) {
		int defaultFontSize = UIManager.getDefaults().getFont("TextPane.font").getSize();
		button.setActionCommand(actionCommand);
		button.addActionListener(this);
		addComponent(destinationContainer, button, Font.CENTER_BASELINE, defaultFontSize, this.getBackground(), x, y, width, height, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), true);
	}
	
	//Map Panel

	private void initializeMapPanel() {
		JPanel mapPanel = new JPanel();
		mapPanel.setPreferredSize(new Dimension(800, 600));
		loadedLevelMap = new LoadedLevelMap(gameController, mouseAdapter, level.getBoard().rows, level.getBoard().columns);
		mapPanel.add(loadedLevelMap.getMap());
		addComponent(this, mapPanel, Font.CENTER_BASELINE, 0, this.getBackground(), 0, 1, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), true);
	}
	
	private JPanel trackPanel() {
		trackPanel.setPreferredSize(new Dimension(200, 300));
		
		Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		TitledBorder sidePanelTitle;
		sidePanelTitle = BorderFactory.createTitledBorder(loweredetched, "Select Track");
		sidePanelTitle.setTitlePosition(TitledBorder.ABOVE_TOP);
		trackPanel.setBorder(sidePanelTitle);
	
		intializeTrackList();
		
		return trackPanel;
	}
	
	private void intializeTrackList(){
		JButton straightTrack = new JButton(Images.STRAIGHT_TRACK_IMAGE);
		addTrackButton(straightTrack, "straightTrack", trackPanel);
		
		JButton diagonalTrack = new JButton(Images.DIAGONAL_TRACK_IMAGE);
		addTrackButton(diagonalTrack, "diagonalTrack", trackPanel);
		
		JButton curveleftTrack = new JButton(Images.CURVELEFT_TRACK_IMAGE);
		addTrackButton(curveleftTrack, "curveleftTrack", trackPanel);
		
		JButton curverightTrack = new JButton(Images.CURVERIGHT_TRACK_IMAGE);
		addTrackButton(curverightTrack, "curverightTrack", trackPanel);
		
		JButton intersectionTrack = new JButton(Images.INTERSECTION_TRACK_IMAGE);
		addTrackButton(intersectionTrack, "intersectionTrack", trackPanel);
		
		JButton diagonalIntersectionTrack = new JButton(Images.DIAGONAL_INTERSECTION_TRACK_IMAGE);
		addTrackButton(diagonalIntersectionTrack, "diagonalIntersectionTrack", trackPanel);		
	}
	

	private void addTrackButton(JButton button, String actionCommand, Container destinationContainer) {
		button.setBounds(0, 0, 40, 40);
		button.setPreferredSize(new Dimension(60,60));
		button.setActionCommand(actionCommand);
		button.addActionListener(this);
		destinationContainer.add(button);
	}
	
	
	//Selected Track Panel 
	private JPanel selectedTrackPanel() {
		
		Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		TitledBorder sidePanelTitle;
		sidePanelTitle = BorderFactory.createTitledBorder(loweredetched, "Click to rotate");
		sidePanelTitle.setTitlePosition(TitledBorder.ABOVE_TOP);
		selectedTrackPanel.setBorder(sidePanelTitle);
		
		rotateButton = new JButton();
		rotateButton.setBounds(0, 0, 40, 40);
		rotateButton.setPreferredSize(new Dimension(100, 100));
		rotateButton.setMargin(new Insets(0, 15, 0, 0));
		rotateButton.setActionCommand("rotateTrack");
		rotateButton.addActionListener(this);
		selectedTrackPanel.add(rotateButton);
	
		return selectedTrackPanel;
	}
	
	private void redrawSelectedTrackPanel(Track selectedTrack, RotatedImageIcon selectedTrackImage) {
		this.selectedTrack = selectedTrack;
		this.selectedTrackImage = selectedTrackImage;
		rotateButton.removeAll();
		JLabel selectedTrackContainer = new JLabel(this.selectedTrackImage);
		rotateButton.add(selectedTrackContainer);
		rotateButton.repaint();
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
		
		if (event.getActionCommand() == "removeAllTracks") {
			gameController.removeAllTracks();
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
			selectedTrack = new Track(connection, TrackType.STRAIGHT_TRACK);
			mouseAdapter.setTrack(selectedTrack);
			
			selectedTrackImage = new RotatedImageIcon(Images.STRAIGHT_TRACK);
			
			redrawSelectedTrackPanel(selectedTrack,selectedTrackImage);
		}
		
		if (event.getActionCommand() == "diagonalTrack") {
			//Create and set new connection on mouseAdapter
			Connection connection = new Connection(CompassHeading.NORTHWEST,CompassHeading.SOUTHEAST);
			selectedTrack = new Track(connection, TrackType.DIAGONAL_TRACK);
			mouseAdapter.setTrack(selectedTrack);
			
			selectedTrackImage = new RotatedImageIcon(Images.DIAGONAL_TRACK);
			
			redrawSelectedTrackPanel(selectedTrack,selectedTrackImage);
		}
		
		if (event.getActionCommand() == "curveleftTrack") {
			Connection connection = new Connection(CompassHeading.NORTHWEST,CompassHeading.SOUTH);
			selectedTrack = new Track(connection, TrackType.CURVELEFT_TRACK);
			mouseAdapter.setTrack(selectedTrack);
			redrawSelectedTrackPanel(selectedTrack,new RotatedImageIcon(Images.CURVELEFT_TRACK));
		}
		
		if (event.getActionCommand() == "curverightTrack") {
			Connection connection = new Connection(CompassHeading.NORTHEAST,CompassHeading.SOUTH);
			selectedTrack = new Track(connection, TrackType.CURVERIGHT_TRACK);
			mouseAdapter.setTrack(selectedTrack);
			
			selectedTrackImage = new RotatedImageIcon(Images.CURVERIGHT_TRACK);
			
			redrawSelectedTrackPanel(selectedTrack,selectedTrackImage);
		}
		
		if (event.getActionCommand() == "intersectionTrack") {
			Connection connection1 = new Connection(CompassHeading.NORTH, CompassHeading.SOUTH);
			Connection connection2 = new Connection(CompassHeading.WEST, CompassHeading.EAST);
			selectedTrack = new Track(connection1, connection2, TrackType.INTERSECTION_TRACK);
			mouseAdapter.setTrack(selectedTrack);
			
			selectedTrackImage = new RotatedImageIcon(Images.INTERSECTION_TRACK);
			
			redrawSelectedTrackPanel(selectedTrack,selectedTrackImage);
		}
		
		if (event.getActionCommand() == "diagonalIntersectionTrack") {
			Connection connection1 = new Connection(CompassHeading.NORTHEAST, CompassHeading.SOUTHWEST);
			Connection connection2 = new Connection(CompassHeading.NORTHWEST, CompassHeading.SOUTHEAST);
			selectedTrack = new Track(connection1, connection2, TrackType.DIAGONAL_INTERSECTION_TRACK);
			mouseAdapter.setTrack(selectedTrack);
	
			selectedTrackImage = new RotatedImageIcon(Images.DIAGONAL_INTERSECTION_TRACK);
			
			redrawSelectedTrackPanel(selectedTrack,selectedTrackImage);
		}
		
		if (event.getActionCommand() == "rotateTrack") {	
			selectedTrack = new Track(selectedTrack);
			//rotate track and set on  mouseAdapter
			selectedTrack.rotateTrack();
			mouseAdapter.setTrack(selectedTrack);
			
			selectedTrackImage.rotate90DegreesClockwise();
			
			redrawSelectedTrackPanel(selectedTrack,selectedTrackImage);
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


		if(object instanceof CannotRemoveTrackException){
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