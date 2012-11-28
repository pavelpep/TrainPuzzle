package com.trainpuzzle.ui.windows;

import com.trainpuzzle.observe.Observer;

import com.trainpuzzle.ui.windows.loadedlevel.GameControlBox;
import com.trainpuzzle.ui.windows.loadedlevel.LevelMap;
import com.trainpuzzle.ui.windows.loadedlevel.SelectedTrack;
import com.trainpuzzle.ui.windows.loadedlevel.TrackSelection;
import com.trainpuzzle.ui.windows.loadedlevel.VictoryConditions;

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
import com.trainpuzzle.model.board.Cargo.CargoType;
import com.trainpuzzle.model.board.Train;
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
	
	private JPanel cargoPanelPointer = new JPanel();
	
	private JTextPane remainTimeBox = new JTextPane();

	public LoadedLevelScreen(GameController gameController) {
		this.gameController = gameController;
		this.level = this.gameController.getLevel();

		gameController.getSimulator().register(this);
		gameController.getSimulator().getTrain().registerLoadedLevel(this);
		
		setBackground(this.getBackground());
		
		create();
		pack();
		setLocationRelativeTo(null);
	}

	private void create() {
		JPanel loadedLevelScreenPanel = new JPanel();
		loadedLevelScreenPanel.setLayout(new GridBagLayout());
		this.add(loadedLevelScreenPanel);
		
		GridBagConstraints headerPanelConstraints = gbConstraints(new Point(0, 0), new Dimension(1, 1), 0, 0);
		loadedLevelScreenPanel.add(headerPanel(), headerPanelConstraints);
		
		GridBagConstraints bodyPanelConstraints = gbConstraints(new Point(0, 1), new Dimension(1, 1), 1, 1);
		loadedLevelScreenPanel.add(bodyPanel(), bodyPanelConstraints);
		
	}

	private JPanel bodyPanel() {
		JPanel bodyPanel = new JPanel();
		bodyPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints mapPanelConstraints = gbConstraints(new Point(0, 1), new Dimension(1, 1), 0, 1);
		bodyPanel.add(mapPanel(), mapPanelConstraints);
		
		
		GridBagConstraints sidePanelConstraints = gbConstraints(new Point(1, 1), new Dimension(1, 1), 0, 1);
		bodyPanel.add(sidePanel(), sidePanelConstraints);
		
		return bodyPanel;
	}

	private JPanel headerPanel() {
		JPanel headerPanel = new JPanel();
		headerPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints backButtonConstraints = gbConstraints(new Point(0, 0), new Dimension(1, 1), 0, 0);
		backButtonConstraints.insets = new Insets(5,5,5,5);
		headerPanel.add(backButton(), backButtonConstraints);
		
		GridBagConstraints titleLabelConstraints = gbConstraints(new Point(1, 0), new Dimension(1, 1), 0, 0);
		titleLabelConstraints.insets = new Insets(5,5,5,5);
		headerPanel.add(titleLabel(), titleLabelConstraints);
		
		GridBagConstraints cargoPanelConstraints = gbConstraints(new Point(2, 0), new Dimension(1, 1), 0, 0);
		cargoPanelConstraints.insets = new Insets(5,5,5,5);
		setCargoPanel(cargoPanelPointer);
		headerPanel.add(cargoPanelPointer, cargoPanelConstraints);
		
		GridBagConstraints remainTimeBoxConstraints = gbConstraints(new Point(3, 0), new Dimension(1, 1), 0, 0);
		remainTimeBoxConstraints.insets = new Insets(5,5,5,5);
		headerPanel.add(setRemainTimeBox(), remainTimeBoxConstraints);
		
		
		GridBagConstraints messageBoxConstraints = gbConstraints(new Point(4, 0), new Dimension(1, 1), 1, 0);
		messageBoxConstraints.insets = new Insets(5,5,5,5);
		headerPanel.add(messageBox(), messageBoxConstraints);
							
		GridBagConstraints saveButtonConstraints = gbConstraints(new Point(5, 0), new Dimension(1, 1), 0, 0);
		saveButtonConstraints.insets = new Insets(5,5,5,5);
		headerPanel.add(saveButton(), saveButtonConstraints);		
		
		
		return headerPanel;
	}
	
	private JTextPane messageBox() {
		messageBox =  new JTextPane();
		messageBox.setPreferredSize(new Dimension(250,20));
		
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
	
	private JTextPane setRemainTimeBox() {
		StyledDocument doc = remainTimeBox.getStyledDocument();
		SimpleAttributeSet alignment = new SimpleAttributeSet();
		StyleConstants.setAlignment(alignment, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), alignment, false);
		
		remainTimeBox.setOpaque(false);
		remainTimeBox.setBackground(new Color(255, 255, 255, 0));
		remainTimeBox.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		remainTimeBox.setCursor(null);
		remainTimeBox.setFocusable(false);
		remainTimeBox.setEditable(false);
		
		remainTimeBox.setForeground(Color.BLACK);
		int remainTime = this.level.getTimeLimit();
		if(remainTime == Simulator.NO_TIME_LIMIT) {
			remainTimeBox.setText("Remain Time: \u221e" );
		}
		else {
			remainTimeBox.setText("Remain Time: " +remainTime );
		}
		return remainTimeBox;
	}
	
	private void setRemainTime(int remainTime) {
		if(remainTime == Simulator.NO_TIME_LIMIT) {
			remainTimeBox.setText("Remain Time: \u221e" );
		}
		else {
			remainTimeBox.setText("Remain Time: " +remainTime );
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

	
	private void setCargoPanel(JPanel cargoPanel) {
		cargoPanel.setLayout(new BoxLayout(cargoPanel, BoxLayout.X_AXIS));
		
		Train train = this.gameController.getSimulator().getTrain();
		int numOfCargoOnTrain = 0; 
		for (CargoType cargoType: CargoType.values()){
			numOfCargoOnTrain = train.getNumOfCargoes().get(cargoType);
			cargoPanel.add(cargo(cargoType.getName(), getCargoIcon(cargoType), numOfCargoOnTrain));
		}
	}
		
	private ImageIcon getCargoIcon(CargoType cargoType){
		ImageIcon cargoIcon = new ImageIcon(Images.IRON);
		switch(cargoType){
		case IRON:
			cargoIcon = new ImageIcon(Images.IRON);
			break;
		case COTTON:
			cargoIcon = new ImageIcon(Images.COTTON);
			break;
		case WOOD:
			cargoIcon = new ImageIcon(Images.WOOD);
			break;
		case COAL:
			cargoIcon = new ImageIcon(Images.COAL);
			break;
		case STEEL:
			cargoIcon = new ImageIcon(Images.STEEL);
			break;			
		}
		return cargoIcon;
	}	
	
	private JPanel cargo(String cargoType, ImageIcon cargoImage, Integer numberOfCargo) {
		JPanel cargo = new JPanel();
		cargo.setLayout(new BoxLayout(cargo, BoxLayout.Y_AXIS));
		cargo.setBorder(new EmptyBorder(0, 1, 0, 1));
		
		JLabel cargoTypeLabel = new JLabel(cargoType);
		initializeComponent(cargoTypeLabel, 10);
		cargo.add(cargoTypeLabel);
		
		JLabel cargoImageLabel = new JLabel(cargoImage);
		initializeComponent(cargoImageLabel, 10);
		cargo.add(cargoImageLabel);
		
		JLabel cargoNumberLabel = new JLabel(numberOfCargo.toString());
		initializeComponent(cargoNumberLabel, 10);
		cargo.add(cargoNumberLabel);		
		return cargo;
	}

	private JPanel sidePanel() {
		JPanel sidePanel = new JPanel();
		sidePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		((FlowLayout) sidePanel.getLayout()).setVgap(0);
		
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
		
		gameControlBox = new GameControlBox(gameController);
		controlPanel.add(gameControlBox);
		
		JPanel trackPanel = new TrackSelection(gameController, this);
		controlPanel.add(trackPanel);
		
		JPanel victoryConditionsPanel = new VictoryConditions(gameController);
		controlPanel.add(victoryConditionsPanel);
		
		selectedTrackPanel = new SelectedTrack(this);
		controlPanel.add(selectedTrackPanel);
		
		JButton toggleButton = initializeButton("Control switches","toggle");
		initializeComponent(toggleButton, 15);
		controlPanel.add(toggleButton);
		
		JTabbedPane tabbedSidePane = new JTabbedPane();
		tabbedSidePane.setPreferredSize(new Dimension(200, 650));
		tabbedSidePane.addTab("Controls", null, controlPanel, null);
		tabbedSidePane.setMnemonicAt(0, KeyEvent.VK_1);
		
		tabbedSidePane.addTab("Victory Conditions", null, victoryConditionsPanel, null);
		tabbedSidePane.setMnemonicAt(1, KeyEvent.VK_2);
		
		sidePanel.add(tabbedSidePane);
		
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
		mapPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		((FlowLayout) mapPanel.getLayout()).setVgap(0);
		((FlowLayout) mapPanel.getLayout()).setVgap(0);
		loadedLevelMap = new LevelMap(gameController, level.getBoard().getRows(), level.getBoard().getColumns());
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
		
		else if (event.getActionCommand() == "save") {
			gameController.getLevelManager().saveCurrentLevel();
		}
		else if (event.getActionCommand() == "toggle") {
			loadedLevelMap.getMouseAdapter().setToggleMode();
		}
		else if (event.getActionCommand() == "objectives") {
			JFrame vcwindow = new VictoryConditionsWindow(gameController);
			vcwindow.setVisible(true);
		}
		else if (event.getActionCommand() == "saveToFile") {
			File saveLevelFile = saveFileDialog();
			if(saveLevelFile != null){
				gameController.saveCurrentLevel(saveLevelFile);
			}
		}
	}
	
	private File saveFileDialog() {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("XML Encoded Level", "xml");
		chooser.setFileFilter(filter);
		
		int returnVal = chooser.showSaveDialog(this);
		
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFile();
		}
		return null;
	}

	public void notifyChange(Object object) {
		if(object instanceof Simulator) {
			setRemainTime(((Simulator) object).getRestTime());
			if(((Simulator) object).isTrainCrashed()) {
				setMessageBoxMessage("TRAIN CRASHED");
				gameControlBox.setRunButtonVisible();
			}
			else if(((Simulator) object).isVictoryConditionsSatisfied()) {
				gameController.levelCompleted();
				setMessageBoxMessage("YOU COMPLETED THE LEVEL!");
				gameControlBox.setRunButtonVisible();
			}
			else if (((Simulator)object).checkTimeOut()) {
				setMessageBoxMessage("YOU RUN OUT OF TIME!");
				gameControlBox.setRunButtonVisible();
			}
			
		}
		else if (object instanceof Train) {
			cargoPanelPointer.removeAll();
			setCargoPanel(cargoPanelPointer);
			cargoPanelPointer.validate();
		}
	}
}