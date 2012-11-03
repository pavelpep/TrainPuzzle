package com.trainpuzzle.ui.windows.loadedlevel;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import com.trainpuzzle.controller.GameController;
import com.trainpuzzle.infrastructure.Images;
import com.trainpuzzle.model.board.CompassHeading;
import com.trainpuzzle.model.board.Connection;
import com.trainpuzzle.model.board.Track;
import com.trainpuzzle.model.board.TrackType;
import com.trainpuzzle.ui.windows.LoadedLevelScreen;
import com.trainpuzzle.ui.windows.RotatedImageIcon;

public class TrackSelection extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1L;
	
	private GameController gameController;
	private LoadedLevelScreen loadedLevelScreen;
	
	public TrackSelection(GameController gameController, LoadedLevelScreen loadedLevelScreen) {
		this.gameController = gameController;
		this.loadedLevelScreen = loadedLevelScreen;
		
		this.setPreferredSize(new Dimension(200, 300));
		
		Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		TitledBorder sidePanelTitle;
		sidePanelTitle = BorderFactory.createTitledBorder(loweredetched, "Select Track");
		sidePanelTitle.setTitlePosition(TitledBorder.ABOVE_TOP);
		this.setBorder(sidePanelTitle);
	
		intializeTrackList();
		
	}
	
	private void intializeTrackList(){
		JButton straightTrack = initializeButton(Images.STRAIGHT_TRACK_IMAGE, "straightTrack");
		this.add(straightTrack);
		
		JButton diagonalTrack = initializeButton(Images.DIAGONAL_TRACK_IMAGE, "diagonalTrack");
		this.add(diagonalTrack);
		
		JButton curveleftTrack = initializeButton(Images.CURVELEFT_TRACK_IMAGE, "curveleftTrack");
		this.add(curveleftTrack);
		
		JButton curverightTrack = initializeButton(Images.CURVERIGHT_TRACK_IMAGE, "curverightTrack");
		this.add(curverightTrack);
		
		JButton intersectionTrack = initializeButton(Images.INTERSECTION_TRACK_IMAGE, "intersectionTrack");
		this.add(intersectionTrack);
		
		JButton diagonalIntersectionTrack = initializeButton(Images.DIAGONAL_INTERSECTION_TRACK_IMAGE, "diagonalIntersectionTrack");
		this.add(diagonalIntersectionTrack);
	}
	
	private JButton initializeButton(ImageIcon label, String actionCommand) {
		JButton button = new JButton(label);
		button.setActionCommand(actionCommand);
		button.addActionListener(this);
		return button;
	}

	public void actionPerformed(ActionEvent event) {

		if (event.getActionCommand() == "straightTrack") {
			Connection connection = new Connection(CompassHeading.EAST,CompassHeading.WEST);
			Track selectedTrack = new Track(connection, TrackType.STRAIGHT_TRACK);
			RotatedImageIcon selectedTrackImage = new RotatedImageIcon(Images.STRAIGHT_TRACK);
			loadedLevelScreen.getSelectedTrackPanel().redrawRotateButton(selectedTrack, selectedTrackImage);
		}
		
		if (event.getActionCommand() == "diagonalTrack") {
			Connection connection = new Connection(CompassHeading.NORTHWEST,CompassHeading.SOUTHEAST);
			Track selectedTrack = new Track(connection, TrackType.DIAGONAL_TRACK);
			RotatedImageIcon selectedTrackImage = new RotatedImageIcon(Images.DIAGONAL_TRACK);
			loadedLevelScreen.getSelectedTrackPanel().redrawRotateButton(selectedTrack, selectedTrackImage);
		}
		
		if (event.getActionCommand() == "curveleftTrack") {
			Connection connection = new Connection(CompassHeading.NORTHWEST,CompassHeading.SOUTH);
			Track selectedTrack = new Track(connection, TrackType.CURVELEFT_TRACK);
			RotatedImageIcon selectedTrackImage = new RotatedImageIcon(Images.CURVELEFT_TRACK);
			loadedLevelScreen.getSelectedTrackPanel().redrawRotateButton(selectedTrack, selectedTrackImage);
		}
		
		if (event.getActionCommand() == "curverightTrack") {
			Connection connection = new Connection(CompassHeading.NORTHEAST,CompassHeading.SOUTH);
			Track selectedTrack = new Track(connection, TrackType.CURVERIGHT_TRACK);
			RotatedImageIcon selectedTrackImage = new RotatedImageIcon(Images.CURVERIGHT_TRACK);
			loadedLevelScreen.getSelectedTrackPanel().redrawRotateButton(selectedTrack, selectedTrackImage);
		}
		
		if (event.getActionCommand() == "intersectionTrack") {
			Connection connection1 = new Connection(CompassHeading.NORTH, CompassHeading.SOUTH);
			Connection connection2 = new Connection(CompassHeading.WEST, CompassHeading.EAST);
			Track selectedTrack = new Track(connection1, connection2, TrackType.INTERSECTION_TRACK);
			RotatedImageIcon selectedTrackImage = new RotatedImageIcon(Images.INTERSECTION_TRACK);
			loadedLevelScreen.getSelectedTrackPanel().redrawRotateButton(selectedTrack, selectedTrackImage);
		}
		
		if (event.getActionCommand() == "diagonalIntersectionTrack") {
			Connection connection1 = new Connection(CompassHeading.NORTHEAST, CompassHeading.SOUTHWEST);
			Connection connection2 = new Connection(CompassHeading.NORTHWEST, CompassHeading.SOUTHEAST);
			Track selectedTrack = new Track(connection1, connection2, TrackType.DIAGONAL_INTERSECTION_TRACK);
			RotatedImageIcon selectedTrackImage = new RotatedImageIcon(Images.DIAGONAL_INTERSECTION_TRACK);
			loadedLevelScreen.getSelectedTrackPanel().redrawRotateButton(selectedTrack, selectedTrackImage);
		}
		
	}
	
}
