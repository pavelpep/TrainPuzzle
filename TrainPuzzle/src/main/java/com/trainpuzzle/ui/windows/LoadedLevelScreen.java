package com.trainpuzzle.ui.windows;
import com.trainpuzzle.observe.*;
import com.trainpuzzle.ui.windows.loadedlevel.GameControlBox;
import com.trainpuzzle.ui.windows.loadedlevel.LoadedLevelMap;
import com.trainpuzzle.ui.windows.loadedlevel.TrackSelection;

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
	private GameControlBox gameControlBox;
	private JPanel trackPanel = new JPanel();
	private JPanel selectedTrackPanel = new JPanel();
	private JButton rotateButton = new JButton("Run");
	private RotatedImageIcon selectedTrackImage;
	private Track selectedTrack;
	private JLabel messageBox =  new JLabel("");
	Timer makeItBlink;
	boolean blinkState = true;

	// Constructor
	public LoadedLevelScreen(GameController gameController) {
		//Connect to application
		this.gameController = gameController;
		this.level = this.gameController.getLevel();
		mouseAdapter = new TileMouseAdapter(new TrackPlacer(gameController.getLevel()));
		
		//Window Layout
		setLayout(new GridBagLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(this.getBackground());
		setMinimumSize(new Dimension(1024, 700));
		setLocationRelativeTo(null);
		
		//setExtendedState(Frame.MAXIMIZED_BOTH);

		// Game title
		initializeHeaderPanel();
		initializeMapPanel();
		initializeSidePanel();
	}

	private void initializeHeaderPanel() {
		headerPanel = new JPanel();
		headerPanel.setLayout(new GridBagLayout());
		addComponent(headerPanel, backButton(), Font.CENTER_BASELINE, 12, this.getBackground(), 0, 0, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), true);
		addComponent(headerPanel, titleLabel(), Font.CENTER_BASELINE, 28, this.getBackground(), 1, 0, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), true);
		addComponent(headerPanel, messageBox(), Font.CENTER_BASELINE, 16, this.getBackground(), 2, 0, 1, 1, 1, 0, GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), true);
		
		addComponent(this, headerPanel, Font.CENTER_BASELINE, 28, this.getBackground(), 0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), true);
	}

	private JLabel messageBox() {
		messageBox =  new JLabel("");
		messageBox.setHorizontalAlignment(JLabel.RIGHT);
		//messageBox.setBorder(BorderFactory.createMatteBorder(1, 5, 1, 1, Color.red));
		Font font = messageBox.getFont();
		messageBox.setFont(font.deriveFont(font.getStyle() | Font.BOLD));
		messageBox.setForeground(Color.BLACK);
		
		int blinkInterval = 2500;
		
		
        makeItBlink = new Timer(blinkInterval,
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                    	setMessageBoxMessage("");
                    	makeItBlink.stop();
                    	/*
                    	blinkState = !blinkState;
                        if (blinkState) {
                        	messageBox.setForeground(Color.RED);
                        }
                        else {
                        	messageBox.setForeground(Color.BLACK);
                        }
                        */
                    }
        });
		
		return(messageBox);
	}
	
	public void setMessageBoxMessage(String message) {
		setMessageBoxMessage(message, Color.BLACK);
		makeItBlink.start();
		
	}
	
	private void setMessageBoxMessage(String message, Color colour) {
		messageBox.setText(message);
		messageBox.setForeground(colour);
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
		gameControlBox = new GameControlBox(gameController);
		sidePanel.add(gameControlBox);
		trackPanel = new TrackSelection(gameController);
		sidePanel.add(trackPanel);
		sidePanel.add(selectedTrackPanel());
		addComponent(this, sidePanel, Font.CENTER_BASELINE, 28, this.getBackground(), 1, 0, 1, 2, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), true);
	}

	private JButton saveButton() {
		JButton saveButton = new JButton("Save Level");
		saveButton.setActionCommand("save");
		saveButton.addActionListener(this);
		return saveButton;
	}

	
	//Map Panel

	private void initializeMapPanel() {
		JPanel mapPanel = new JPanel();
		mapPanel.setPreferredSize(new Dimension(800, 600));
		loadedLevelMap = new LoadedLevelMap(gameController, level.getBoard().rows, level.getBoard().columns);
		mapPanel.add(loadedLevelMap);
		addComponent(this, mapPanel, Font.CENTER_BASELINE, 0, this.getBackground(), 0, 1, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), true);
	}

	
	private JPanel selectedTrackPanel() {
		
		

		return selectedTrackPanel;
	}

	
	public void actionPerformed(ActionEvent event) {
		if (event.getActionCommand() == "backToLevelSelect") {
			WindowManager.getManager(gameController).showPreviousWindow();
		}
		
		if (event.getActionCommand() == "save") {
			File saveLevelFile = saveFileDialog();
			if(saveLevelFile != null){
				gameController.saveCurrentLevel(saveLevelFile);
			}
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
	}

	
}