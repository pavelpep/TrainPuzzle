package com.trainpuzzle.ui.windows.loadedlevel;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import com.trainpuzzle.controller.GameController;
import com.trainpuzzle.infrastructure.Images;
import com.trainpuzzle.model.board.CompassHeading;
import com.trainpuzzle.model.board.Connection;
import com.trainpuzzle.model.board.Switch;
import com.trainpuzzle.model.board.Track;
import com.trainpuzzle.model.board.TrackType;
import com.trainpuzzle.model.level.Economy;
import com.trainpuzzle.observe.Observer;
import com.trainpuzzle.ui.windows.LoadedLevelScreen;
import com.trainpuzzle.ui.windows.RotatedImageIcon;

public class TrackSelection extends JPanel implements ActionListener, Observer{
	private static final long serialVersionUID = 1L;

	private LoadedLevelScreen loadedLevelScreen;
	private GameController gameController;
	private Economy economy;
	private TitledBorder sidePanelTitle;
	
	public TrackSelection(GameController gameController, LoadedLevelScreen loadedLevelScreen) {
		this.gameController = gameController;
		this.loadedLevelScreen = loadedLevelScreen;
		economy = this.gameController.getLevel().getEconomy();
		economy.register(this);
		this.setLayout(new GridLayout(0,1));
		
		setTrackSelectionBorder();
		initializeTrackList();
	}

	private void setTrackSelectionBorder() {
		Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		String totalTrackLimit = trackLimitToString(economy.getNumOfAvailableTrack(TrackType.TRACK));
		sidePanelTitle = BorderFactory.createTitledBorder(loweredetched, "Select Track " + totalTrackLimit);
		sidePanelTitle.setTitlePosition(TitledBorder.ABOVE_TOP);
		this.setBorder(sidePanelTitle);
	}

	private void initializeTrackList() {
		this.add(straightPanel());
		this.add(curvePanel());
		this.add(intersectionPanel());
		this.add(switchPanel());
	}

	private JPanel straightPanel() {
		JPanel straightPanel = new JPanel();
		straightPanel.add(track(TrackType.STRAIGHT_TRACK, Images.STRAIGHT_TRACK_IMAGE, "straightTrack"));
		straightPanel.add(track(TrackType.DIAGONAL_TRACK, Images.DIAGONAL_TRACK_IMAGE, "diagonalTrack"));
		String straightLimit = trackLimitToString(economy.getNumOfAvailableTrack(TrackType.STRAIGHT));
		straightPanel.add(new JLabel(straightLimit));
		return straightPanel;
	}
	
	private JPanel curvePanel() {
		JPanel curvePanel = new JPanel();
		curvePanel.add(track(TrackType.CURVELEFT_TRACK, Images.CURVELEFT_TRACK_IMAGE, "curveleftTrack"));
		curvePanel.add(track(TrackType.CURVERIGHT_TRACK, Images.CURVERIGHT_TRACK_IMAGE, "curverightTrack"));
		String curveLimit = trackLimitToString(economy.getNumOfAvailableTrack(TrackType.CURVE));
		curvePanel.add(new JLabel(curveLimit));
		return curvePanel;
	}

	private JPanel intersectionPanel() {
		JPanel intersectionPanel = new JPanel();
		intersectionPanel.add(track(TrackType.INTERSECTION_TRACK, Images.INTERSECTION_TRACK_IMAGE, "intersectionTrack"));
		intersectionPanel.add(track(TrackType.DIAGONAL_INTERSECTION_TRACK, Images.DIAGONAL_INTERSECTION_TRACK_IMAGE, "diagonalIntersectionTrack"));
		String intersectionLimit = trackLimitToString(economy.getNumOfAvailableTrack(TrackType.INTERSECTION));
		intersectionPanel.add(new JLabel(intersectionLimit));
		return intersectionPanel;
	}

	private JPanel switchPanel() {
		JPanel switchPanel = new JPanel();
		switchPanel.add(track(TrackType.CURVELEFT_STRAIGHT_SWITCH, Images.CURVELEFT_SWITCH_IMAGE, "curveleftStraightSwitch"));
		switchPanel.add(track(TrackType.CURVERIGHT_STRAIGHT_SWITCH, Images.CURVERIGHT_SWITCH_IMAGE, "curverightStraightSwitch"));
		String switchLimit = trackLimitToString(economy.getNumOfAvailableTrack(TrackType.SWITCH));
		switchPanel.add(new JLabel(switchLimit));
		return switchPanel;
	}
	
	private JPanel track(TrackType trackType, ImageIcon trackImage, String actionCommand) {
		JPanel trackPanel = new JPanel();
		trackPanel.setLayout(new BoxLayout(trackPanel, BoxLayout.Y_AXIS));
		JButton trackButton = initializeButton(trackImage, actionCommand);
		trackPanel.add(trackButton);
		String straightTrackLimit = trackLimitToString(economy.getNumOfAvailableTrack(trackType));
		trackPanel.add(new JLabel(straightTrackLimit));
		return trackPanel;
	}
	
	private String trackLimitToString(int trackLimit) {
		if(trackLimit != -1) {
			return "[" + trackLimit + "]";
		}
		else {
			return "[" + "\u221e" + "]";
		}
	}
	
	private JButton initializeButton(ImageIcon label, String actionCommand) {
		JButton button = new JButton(label);
		button.setActionCommand(actionCommand);
		button.addActionListener(this);
		return button;
	}
	
	public void actionPerformed(ActionEvent event) {
		if (event.getActionCommand() == "straightTrack") {
			straightTrackEvent();
		}
		else if (event.getActionCommand() == "diagonalTrack") {
			diagonalTrackEvent();
		}
		else if (event.getActionCommand() == "curveleftTrack") {
			curveleftTrackEvent();
		}
		else if (event.getActionCommand() == "curverightTrack") {
			curverightTrackEvent();
		}
		else if (event.getActionCommand() == "intersectionTrack") {
			intersectionTrackEvent();
		}
		else if (event.getActionCommand() == "diagonalIntersectionTrack") {
			diagonalIntersectionTrackEvent();
		}
		else if (event.getActionCommand() == "curveleftStraightSwitch") {
			curveleftStraightSwitchEvent();
		}
		else if (event.getActionCommand() == "curverightStraightSwitch") {
			curverightStraightSwitchEvent();
		}
	}

	public void straightTrackEvent() {
		Connection connection = new Connection(CompassHeading.EAST,CompassHeading.WEST);
		Track selectedTrack = new Track(connection, TrackType.STRAIGHT_TRACK);
		RotatedImageIcon selectedTrackImage = new RotatedImageIcon(Images.STRAIGHT_TRACK);
		loadedLevelScreen.getSelectedTrackPanel().redrawRotateButton(selectedTrack, selectedTrackImage);
	}
	
	public void diagonalTrackEvent() {
		Connection connection = new Connection(CompassHeading.NORTHWEST,CompassHeading.SOUTHEAST);
		Track selectedTrack = new Track(connection, TrackType.DIAGONAL_TRACK);
		RotatedImageIcon selectedTrackImage = new RotatedImageIcon(Images.DIAGONAL_TRACK);
		loadedLevelScreen.getSelectedTrackPanel().redrawRotateButton(selectedTrack, selectedTrackImage);
	}
	
	public void curveleftTrackEvent() {
		Connection connection = new Connection(CompassHeading.NORTHWEST,CompassHeading.SOUTH);
		Track selectedTrack = new Track(connection, TrackType.CURVELEFT_TRACK);
		RotatedImageIcon selectedTrackImage = new RotatedImageIcon(Images.CURVELEFT_TRACK);
		loadedLevelScreen.getSelectedTrackPanel().redrawRotateButton(selectedTrack, selectedTrackImage);
	}
	
	public void curverightTrackEvent() {
		Connection connection = new Connection(CompassHeading.NORTHEAST,CompassHeading.SOUTH);
		Track selectedTrack = new Track(connection, TrackType.CURVERIGHT_TRACK);
		RotatedImageIcon selectedTrackImage = new RotatedImageIcon(Images.CURVERIGHT_TRACK);
		loadedLevelScreen.getSelectedTrackPanel().redrawRotateButton(selectedTrack, selectedTrackImage);
	}
	
	public void intersectionTrackEvent() {
		Connection connection1 = new Connection(CompassHeading.NORTH, CompassHeading.SOUTH);
		Connection connection2 = new Connection(CompassHeading.WEST, CompassHeading.EAST);
		Track selectedTrack = new Track(connection1, connection2, TrackType.INTERSECTION_TRACK);
		RotatedImageIcon selectedTrackImage = new RotatedImageIcon(Images.INTERSECTION_TRACK);
		loadedLevelScreen.getSelectedTrackPanel().redrawRotateButton(selectedTrack, selectedTrackImage);
	}
	
	public void diagonalIntersectionTrackEvent() {
		Connection connection1 = new Connection(CompassHeading.NORTHEAST, CompassHeading.SOUTHWEST);
		Connection connection2 = new Connection(CompassHeading.NORTHWEST, CompassHeading.SOUTHEAST);
		Track selectedTrack = new Track(connection1, connection2, TrackType.DIAGONAL_INTERSECTION_TRACK);
		RotatedImageIcon selectedTrackImage = new RotatedImageIcon(Images.DIAGONAL_INTERSECTION_TRACK);
		loadedLevelScreen.getSelectedTrackPanel().redrawRotateButton(selectedTrack, selectedTrackImage);
	}
	
	public void curveleftStraightSwitchEvent() {
		Connection connection1 = new Connection(CompassHeading.NORTH, CompassHeading.SOUTH);
		Connection connection2 = new Connection(CompassHeading.NORTHWEST, CompassHeading.SOUTH);
		Track selectedTrack = new Switch(connection1, connection2, TrackType.CURVELEFT_STRAIGHT_SWITCH);
		RotatedImageIcon selectedTrackImage = new RotatedImageIcon(Images.CURVELEFT_SWITCH);
		loadedLevelScreen.getSelectedTrackPanel().redrawRotateButton(selectedTrack, selectedTrackImage);
	}
	
	public void curverightStraightSwitchEvent() {
		Connection connection1 = new Connection(CompassHeading.NORTH, CompassHeading.SOUTH);
		Connection connection2 = new Connection(CompassHeading.NORTHEAST, CompassHeading.SOUTH);
		Track selectedTrack = new Switch(connection1, connection2, TrackType.CURVERIGHT_STRAIGHT_SWITCH);
		RotatedImageIcon selectedTrackImage = new RotatedImageIcon(Images.CURVERIGHT_SWITCH);
		loadedLevelScreen.getSelectedTrackPanel().redrawRotateButton(selectedTrack, selectedTrackImage);
	}
	
	public void notifyChange(Object object) {
		this.removeAll();
		setTrackSelectionBorder();
		initializeTrackList();
		this.validate();
	}
}