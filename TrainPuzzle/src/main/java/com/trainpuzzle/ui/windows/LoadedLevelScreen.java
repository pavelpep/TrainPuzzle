package com.trainpuzzle.ui.windows;
import com.trainpuzzle.observe.Observer;
import com.trainpuzzle.ui.windows.loadedlevel.GameControlBox;
import com.trainpuzzle.ui.windows.loadedlevel.LoadedLevelMap;
import com.trainpuzzle.ui.windows.loadedlevel.SelectedTrack;
import com.trainpuzzle.ui.windows.loadedlevel.TrackSelection;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.log4j.Logger;

import test.trainpuzzle.observe.*;

import com.trainpuzzle.controller.GameController;
import com.trainpuzzle.model.board.Track;
import com.trainpuzzle.model.level.Level;
import com.trainpuzzle.controller.TrackPlacer;


// Level selection for the campaign
public class LoadedLevelScreen extends Window implements ActionListener, Observer {

	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger(LoadedLevelScreen.class);
	
	private GameController gameController;
	private Level level;

	// Window elements
	
	private JPanel headerPanel = new JPanel();
	private LoadedLevelMap loadedLevelMap;
	private JPanel sidePanel = new JPanel();
	private GameControlBox gameControlBox;
	private JPanel trackPanel = new JPanel();
	private SelectedTrack selectedTrackPanel;
	
	private JLabel messageBox =  new JLabel("");
	private Timer messageBoxDisplayTimer;
	private final int MESSAGE_BOX_DISPLAY_IN_MILLISECONDS = 3000;

	// Constructor
	public LoadedLevelScreen(GameController gameController) {
		//Connect to application
		this.gameController = gameController;
		this.level = this.gameController.getLevel();
		
		
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
		addComponent(headerPanel, messageBox(), Font.CENTER_BASELINE, 14, this.getBackground(), 2, 0, 1, 1, 1, 0, GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), true);
		
		addComponent(this, headerPanel, Font.CENTER_BASELINE, 28, this.getBackground(), 0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), true);
	}

	private JLabel messageBox() {
		messageBox =  new JLabel("");
		messageBox.setHorizontalAlignment(JLabel.RIGHT);
		//messageBox.setBorder(BorderFactory.createMatteBorder(1, 5, 1, 1, Color.red));
		Font font = messageBox.getFont();
		messageBox.setFont(font.deriveFont(font.getStyle() | Font.BOLD));
		messageBox.setForeground(Color.BLACK);
		

		
		
        messageBoxDisplayTimer = new Timer(MESSAGE_BOX_DISPLAY_IN_MILLISECONDS,
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                    	setMessageBoxMessage("");
                    	messageBoxDisplayTimer.stop();
                    }
        });
		
		return(messageBox);
	}
	
	public void setMessageBoxMessage(String message) {
		setMessageBoxMessage(message, Color.BLACK);
	}
	
	private void setMessageBoxMessage(String message, Color colour) {
		messageBox.setText(message);
		messageBox.setForeground(colour);
    	if(messageBoxDisplayTimer.isRunning()) {
    		messageBoxDisplayTimer.restart();
    	}
    	else {
    		messageBoxDisplayTimer.start();
    	}
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
		trackPanel = new TrackSelection(gameController, this);
		sidePanel.add(trackPanel);
		selectedTrackPanel = new SelectedTrack(gameController, this);
		sidePanel.add(selectedTrackPanel);
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
	
	public SelectedTrack getSelectedTrackPanel() {
		return selectedTrackPanel;
	}
	
	public LoadedLevelMap getMapPanel() {
		return loadedLevelMap;
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