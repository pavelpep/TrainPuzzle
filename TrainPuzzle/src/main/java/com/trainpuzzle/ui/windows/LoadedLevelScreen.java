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
import com.trainpuzzle.model.board.Cargo;
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
	
	JPanel cargoPanelPointer = new JPanel();

	public LoadedLevelScreen(GameController gameController) {
		this.gameController = gameController;
		this.level = this.gameController.getLevel();

		gameController.getSimulator().register(this);
		gameController.getSimulator().getTrain().registerLoadedLevel(this);
		
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
		
		GridBagConstraints headerPanelConstraints = gbConstraints(new Point(0, 0), new Dimension(2, 1), 0, 0);
		loadedLevelScreenPanel.add(headerPanel(), headerPanelConstraints);
		
		GridBagConstraints mapPanelContsraints = gbConstraints(new Point(0, 1), new Dimension(1, 1), 1, 0);
		loadedLevelScreenPanel.add(mapPanel(), mapPanelContsraints);
		
		GridBagConstraints sidePanelConstraints = gbConstraints(new Point(1, 1), new Dimension(1, 1), 0, 0);
		loadedLevelScreenPanel.add(sidePanel(), sidePanelConstraints);
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
		
		GridBagConstraints messageBoxConstraints = gbConstraints(new Point(3, 0), new Dimension(1, 1), 1, 0);
		messageBoxConstraints.insets = new Insets(5,5,5,5);
		headerPanel.add(messageBox(), messageBoxConstraints);
		
		GridBagConstraints saveButtonConstraints = gbConstraints(new Point(4, 0), new Dimension(1, 1), 0, 0);
		saveButtonConstraints.insets = new Insets(5,5,5,5);
		headerPanel.add(saveButton(), saveButtonConstraints);
		
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

	
	private void setCargoPanel(JPanel cargoPanel) {
		cargoPanel.setLayout(new BoxLayout(cargoPanel, BoxLayout.X_AXIS));
		
		Train train = this.gameController.getSimulator().getTrain();
		cargoPanel.add(cargo("COTTON", Images.COTTON_IMAGE, train.getNumOfCargoes().get(Cargo.CargoType.COTTON)));
		cargoPanel.add(cargo("IRON", Images.IRON_IMAGE,train.getNumOfCargoes().get(Cargo.CargoType.IRON)));
		cargoPanel.add(cargo("WOOD", Images.WOOD_IMAGE,train.getNumOfCargoes().get(Cargo.CargoType.WOOD)));
	}
	
	private JPanel cargo(String cargoType, ImageIcon cargoImage, Integer numberOfCargo) {
		JPanel cargo = new JPanel();
		cargo.setLayout(new BoxLayout(cargo, BoxLayout.Y_AXIS));
		cargo.setBorder(new EmptyBorder(10, 10, 10, 10));
		
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
		sidePanel.setPreferredSize(new Dimension(200, 600));
		sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
		
		gameControlBox = new GameControlBox(gameController);
		sidePanel.add(gameControlBox);
		
		JPanel trackPanel = new TrackSelection(gameController, this);
		sidePanel.add(trackPanel);
		
		selectedTrackPanel = new SelectedTrack(this);
		sidePanel.add(selectedTrackPanel);
		
		JButton victButton = initializeButton("Objectives","objectives");
		initializeComponent(victButton, 15);
		sidePanel.add(victButton);
		
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
		else if (event.getActionCommand() == "objectives") {
			WindowManager.getManager().setActiveWindow(new VictoryConditionsWindow(gameController));
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
			if(((Simulator) object).isTrainCrashed()) {
				setMessageBoxMessage("TRAIN CRASHED");
			}
			if(((Simulator) object).isVictoryConditionsSatisfied()) {
				gameController.levelCompleted();
				setMessageBoxMessage("YOU COMPLETED THE LEVEL!");
			}
			gameControlBox.setRunButtonVisible();
		}
		else if (object instanceof Train) {
			cargoPanelPointer.removeAll();
			setCargoPanel(cargoPanelPointer);
			cargoPanelPointer.validate();
		}
	}
}