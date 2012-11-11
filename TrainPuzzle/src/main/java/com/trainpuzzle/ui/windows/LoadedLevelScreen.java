package com.trainpuzzle.ui.windows;
import com.trainpuzzle.observe.Observer;
import com.trainpuzzle.ui.windows.loadedlevel.GameControlBox;
import com.trainpuzzle.ui.windows.loadedlevel.LevelMap;
import com.trainpuzzle.ui.windows.loadedlevel.SelectedTrack;
import com.trainpuzzle.ui.windows.loadedlevel.TrackSelection;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import com.trainpuzzle.controller.GameController;
import com.trainpuzzle.controller.Simulator;
import com.trainpuzzle.model.level.Level;
import com.trainpuzzle.infrastructure.Images;

public class LoadedLevelScreen extends Window implements ActionListener, Observer {

	private static final long serialVersionUID = 1L;
	private GameController gameController;
	private Level level;

	private GameControlBox gameControlBox;
	private LevelMap loadedLevelMap;
	private SelectedTrack selectedTrackPanel;
	
	private JTextPane messageBox =  new JTextPane();
	private Timer messageBoxDisplayTimer;
	private final int MESSAGE_BOX_DISPLAY_IN_MILLISECONDS = 3000;

	public LoadedLevelScreen(GameController gameController) {
		this.gameController = gameController;
		this.level = this.gameController.getLevel();

		gameController.getSimulator().register(this);
		
		setLayout(new GridBagLayout());
		setBackground(this.getBackground());
		
		create();
		pack();
		setLocationRelativeTo(null);
	}

	private void create() {
		JPanel loadedLevelScreenPanel = new JPanel();
		loadedLevelScreenPanel.setLayout(new GridBagLayout());
		loadedLevelScreenPanel.setBorder(new EmptyBorder(10, 10, 10, 10) );
		this.add(loadedLevelScreenPanel);
		
		GridBagConstraints headerPanelContraints = gbConstraints(new Point(0, 0), new Dimension(2, 1), 0, 0);
		loadedLevelScreenPanel.add(headerPanel(), headerPanelContraints);
		
		GridBagConstraints mapPanelContraints = gbConstraints(new Point(0, 1), new Dimension(1, 1), 1, 0);
		loadedLevelScreenPanel.add(mapPanel(), mapPanelContraints);
		
		GridBagConstraints sidePanelContraints = gbConstraints(new Point(1, 1), new Dimension(1, 1), 0, 0);
		loadedLevelScreenPanel.add(sidePanel(), sidePanelContraints);
	}

	private JPanel headerPanel() {
		JPanel headerPanel = new JPanel();
		headerPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints backButtonContraints = gbConstraints(new Point(0, 0), new Dimension(1, 1), 0, 0);
		backButtonContraints.insets = new Insets(5,5,5,5);
		headerPanel.add(backButton(), backButtonContraints);
		
		GridBagConstraints titleLabelContraints = gbConstraints(new Point(1, 0), new Dimension(1, 1), 0, 0);
		titleLabelContraints.insets = new Insets(5,5,5,5);
		headerPanel.add(titleLabel(), titleLabelContraints);
		
		GridBagConstraints cargoPanelContraints = gbConstraints(new Point(2, 0), new Dimension(1, 1), 0, 0);
		cargoPanelContraints.insets = new Insets(5,5,5,5);
		headerPanel.add(cargoPanel(), cargoPanelContraints);
		
		GridBagConstraints messageBoxContraints = gbConstraints(new Point(3, 0), new Dimension(1, 1), 1, 0);
		messageBoxContraints.insets = new Insets(5,5,5,5);
		headerPanel.add(messageBox(), messageBoxContraints);
		
		GridBagConstraints saveButtonContraints = gbConstraints(new Point(4, 0), new Dimension(1, 1), 0, 0);
		saveButtonContraints.insets = new Insets(5,5,5,5);
		headerPanel.add(saveButton(), saveButtonContraints);
		
		return headerPanel;
	}
	


	private JTextPane messageBox() {
		messageBox =  new JTextPane();
		messageBox.setPreferredSize(new Dimension(300,20));
		
		StyledDocument doc = messageBox.getStyledDocument();
		SimpleAttributeSet alignment = new SimpleAttributeSet();
		StyleConstants.setAlignment(alignment, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), alignment, false);
		
		messageBox.setOpaque(false);
		messageBox.setBackground(new Color(255, 255, 255, 0));
		messageBox.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		messageBox.setCursor(null);
		messageBox.setFocusable(false);
		messageBox.setEditable(false);
		
		Font font = messageBox.getFont();
		messageBox.setFont(font.deriveFont(font.getStyle() | Font.BOLD));
		messageBox.setForeground(Color.BLACK);
		messageBoxDisplayTimer = new Timer(MESSAGE_BOX_DISPLAY_IN_MILLISECONDS,
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setMessageBoxMessage("");
					messageBoxDisplayTimer.stop();
				}
			}
		);
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
	
	private JPanel cargoPanel() {
		JPanel cargoPanel = new JPanel();
		cargoPanel.setLayout(new BoxLayout(cargoPanel, BoxLayout.X_AXIS));
		cargoPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		cargoPanel.add(cargo("COTTON", Images.COTTON_IMAGE));
		cargoPanel.add(cargo("IRON", Images.IRON_IMAGE));
		cargoPanel.add(cargo("WOOD", Images.WOOD_IMAGE));
		
		return cargoPanel;
	}
	
	private JPanel cargo(String cargoType, ImageIcon cargoImage) {
		JPanel cargo = new JPanel();
		cargo.setLayout(new BoxLayout(cargo, BoxLayout.Y_AXIS));
		cargo.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		JLabel cargoTypeLabel = new JLabel(cargoType);
		initializeComponent(cargoTypeLabel, 10);
		cargo.add(cargoTypeLabel);
		
		JLabel cargoImageLabel = new JLabel(cargoImage);
		initializeComponent(cargoImageLabel, 10);
		cargo.add(cargoImageLabel);
		
		return cargo;
	}

	private JPanel sidePanel() {
		JPanel sidePanel = new JPanel();
		sidePanel.setPreferredSize(new Dimension(200, 575));
		sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
		
		gameControlBox = new GameControlBox(gameController);
		sidePanel.add(gameControlBox);
		
		JPanel trackPanel = new TrackSelection(this);
		sidePanel.add(trackPanel);
		
		selectedTrackPanel = new SelectedTrack(this);
		sidePanel.add(selectedTrackPanel);
		
		return sidePanel;
	}

	private JButton saveButton() {
		JButton saveButton = new JButton("Save Level");
		saveButton.setActionCommand("save");
		saveButton.addActionListener(this);
		return saveButton;
	}

	private JPanel mapPanel() {
		JPanel mapPanel = new JPanel();
		((FlowLayout) mapPanel.getLayout()).setVgap(0);
		loadedLevelMap = new LevelMap(gameController, level.getBoard().rows, level.getBoard().columns);
		mapPanel.add(loadedLevelMap);
		
		return mapPanel;
	}
	
	public SelectedTrack getSelectedTrackPanel() {
		return selectedTrackPanel;
	}
	
	public LevelMap getMapPanel() {
		return loadedLevelMap;
	}
	
	public void actionPerformed(ActionEvent event) {
		if (event.getActionCommand() == "backToLevelSelect") {
			WindowManager.getManager().showPreviousWindow();
		}
		
		if (event.getActionCommand() == "save") {
			gameController.getLevelManager().saveCurrentLevel();
			//File saveLevelFile = saveFileDialog();
			//if(saveLevelFile != null){
			//	gameController.saveCurrentLevel(saveLevelFile);
			//}
		}
		if (event.getActionCommand() == "saveToFile") {
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
		if(object instanceof Simulator){
			if(((Simulator) object).isTrainCrashed()) {
				setMessageBoxMessage("TRAIN CRASHED");
			}
			if(((Simulator) object).isVictoryConditionsSatisfied()) {
				gameController.levelCompleted();
				setMessageBoxMessage("YOU COMPLETED THE LEVEL!");
			}
			gameControlBox.setRunButtonVisible();
		}
	}

	
}