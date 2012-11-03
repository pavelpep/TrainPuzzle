package com.trainpuzzle.ui.windows.loadedlevel;
import java.awt.Container;
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
import com.trainpuzzle.ui.windows.RotatedImageIcon;

public class TrackSelection extends JPanel implements ActionListener{
	private GameController gameController;
	
	public TrackSelection(GameController gameController) {
		this.gameController = gameController;
		
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
/*
		if (event.getActionCommand() == "straightTrack") {
			Connection connection = new Connection(CompassHeading.EAST,CompassHeading.WEST);
			selectedTrack = new Track(connection, TrackType.STRAIGHT_TRACK);
			mouseAdapter.setTrack(selectedTrack);
			selectedTrackImage = new RotatedImageIcon(Images.STRAIGHT_TRACK);
			redrawRotateButton();
		}
		
		if (event.getActionCommand() == "diagonalTrack") {
			Connection connection = new Connection(CompassHeading.NORTHWEST,CompassHeading.SOUTHEAST);
			selectedTrack = new Track(connection, TrackType.DIAGONAL_TRACK);
			mouseAdapter.setTrack(selectedTrack);
			selectedTrackImage = new RotatedImageIcon(Images.DIAGONAL_TRACK);
			redrawRotateButton();
		}
		
		if (event.getActionCommand() == "curveleftTrack") {
			Connection connection = new Connection(CompassHeading.NORTHWEST,CompassHeading.SOUTH);
			selectedTrack = new Track(connection, TrackType.CURVELEFT_TRACK);
			mouseAdapter.setTrack(selectedTrack);
			selectedTrackImage = new RotatedImageIcon(Images.CURVELEFT_TRACK);
			redrawRotateButton();
		}
		
		if (event.getActionCommand() == "curverightTrack") {
			Connection connection = new Connection(CompassHeading.NORTHEAST,CompassHeading.SOUTH);
			selectedTrack = new Track(connection, TrackType.CURVERIGHT_TRACK);
			mouseAdapter.setTrack(selectedTrack);
			selectedTrackImage = new RotatedImageIcon(Images.CURVERIGHT_TRACK);
			redrawRotateButton();
		}
		
		if (event.getActionCommand() == "intersectionTrack") {
			Connection connection1 = new Connection(CompassHeading.NORTH, CompassHeading.SOUTH);
			Connection connection2 = new Connection(CompassHeading.WEST, CompassHeading.EAST);
			selectedTrack = new Track(connection1, connection2, TrackType.INTERSECTION_TRACK);
			mouseAdapter.setTrack(selectedTrack);
			selectedTrackImage = new RotatedImageIcon(Images.INTERSECTION_TRACK);
			redrawRotateButton();
		}
		
		if (event.getActionCommand() == "diagonalIntersectionTrack") {
			Connection connection1 = new Connection(CompassHeading.NORTHEAST, CompassHeading.SOUTHWEST);
			Connection connection2 = new Connection(CompassHeading.NORTHWEST, CompassHeading.SOUTHEAST);
			selectedTrack = new Track(connection1, connection2, TrackType.DIAGONAL_INTERSECTION_TRACK);
			mouseAdapter.setTrack(selectedTrack);
			selectedTrackImage = new RotatedImageIcon(Images.DIAGONAL_INTERSECTION_TRACK);
			redrawRotateButton();
		}
		*/
	}
	
}
