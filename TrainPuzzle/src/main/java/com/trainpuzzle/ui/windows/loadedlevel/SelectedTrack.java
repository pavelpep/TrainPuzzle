package com.trainpuzzle.ui.windows.loadedlevel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import com.trainpuzzle.infrastructure.Images;
import com.trainpuzzle.model.board.CompassHeading;
import com.trainpuzzle.model.board.Connection;
import com.trainpuzzle.model.board.Switch;
import com.trainpuzzle.model.board.Track;
import com.trainpuzzle.model.board.TrackType;
import com.trainpuzzle.ui.windows.LoadedLevelScreen;
import com.trainpuzzle.ui.windows.RotatedImageIcon;

public class SelectedTrack extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private LoadedLevelScreen loadedLevelScreen;
	private JButton rotateButton = new JButton();
	private RotatedImageIcon selectedTrackImage;
	private Track selectedTrack;
	
	public SelectedTrack(LoadedLevelScreen loadedLevelScreen) {
		this.loadedLevelScreen = loadedLevelScreen;
		
		Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		TitledBorder sidePanelTitle;
		sidePanelTitle = BorderFactory.createTitledBorder(loweredetched, "Click to rotate");
		sidePanelTitle.setTitlePosition(TitledBorder.ABOVE_TOP);
		this.setBorder(sidePanelTitle);
		this.add(rotateButton());
		
		setDefaultTrack();
	}

	private void setDefaultTrack() {
		Connection connection = new Connection(CompassHeading.EAST,CompassHeading.WEST);
		Track selectedTrack = new Track(connection, TrackType.STRAIGHT_TRACK);
		RotatedImageIcon selectedTrackImage = new RotatedImageIcon(Images.STRAIGHT_TRACK);
		redrawRotateButton(selectedTrack, selectedTrackImage);
	}
	
	private JButton rotateButton() {
		rotateButton = new JButton();
		rotateButton.setActionCommand("rotateTrack");
		rotateButton.addActionListener(this);
		return rotateButton;
	}
	
	public void redrawRotateButton(Track track, RotatedImageIcon trackImage) {
		
		rotateButton.removeAll();
		selectedTrack = track;
		loadedLevelScreen.getMapPanel().getMouseAdapter().setTrack(selectedTrack);
		selectedTrackImage = trackImage;
		JLabel selectedTrackContainer = new JLabel(selectedTrackImage);
		rotateButton.add(selectedTrackContainer);
		loadedLevelScreen.repaint();
		loadedLevelScreen.setVisible(true);
		
	}

	private void rotate() {
		if(!selectedTrack.isSwitch()) {
			selectedTrack = new Track(selectedTrack);
		} 
		else {
			selectedTrack = new Switch((Switch)selectedTrack);
		}
		selectedTrack.rotateTrack();
		selectedTrackImage.rotate90DegreesClockwise();
		redrawRotateButton(selectedTrack, selectedTrackImage);
	}

	public void actionPerformed(ActionEvent event) {
		if (event.getActionCommand() == "rotateTrack") {	
			rotate();
		}
	}
}