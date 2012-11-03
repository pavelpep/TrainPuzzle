package com.trainpuzzle.ui.windows.loadedlevel;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import com.trainpuzzle.controller.GameController;
import com.trainpuzzle.controller.TrackPlacer;
import com.trainpuzzle.model.board.Track;
import com.trainpuzzle.ui.windows.RotatedImageIcon;
import com.trainpuzzle.ui.windows.TileMouseAdapter;

public class SelectedTrack extends JPanel implements ActionListener {
	private GameController gameController;
	
	private JButton rotateButton = new JButton();
	private RotatedImageIcon selectedTrackImage;
	private Track selectedTrack;
	private TileMouseAdapter mouseAdapter;
	
	public SelectedTrack(GameController gameController) {
		this.gameController = gameController;
		mouseAdapter = new TileMouseAdapter(new TrackPlacer(gameController.getLevel()));
		
		Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		TitledBorder sidePanelTitle;
		sidePanelTitle = BorderFactory.createTitledBorder(loweredetched, "Click to rotate");
		sidePanelTitle.setTitlePosition(TitledBorder.ABOVE_TOP);
		this.setBorder(sidePanelTitle);
	
		this.add(rotateButton());
	}
	

	private JButton rotateButton() {
		JButton rotateButton = new JButton();
		rotateButton.setBounds(0, 0, 40, 40);
		rotateButton.setPreferredSize(new Dimension(100, 100));
		rotateButton.setMargin(new Insets(0, 15, 0, 0));
		rotateButton.setActionCommand("rotateTrack");
		rotateButton.addActionListener(this);
		return rotateButton;
	}
	
	public void redrawRotateButton() {
		rotateButton.removeAll();
		JLabel selectedTrackContainer = new JLabel(selectedTrackImage);
		rotateButton.add(selectedTrackContainer);
		rotateButton.repaint();
		this.setVisible(true);
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		/*
		if (event.getActionCommand() == "rotateTrack") {	
			selectedTrack = new Track(selectedTrack);
			selectedTrack.rotateTrack();
			mouseAdapter.setTrack(selectedTrack);
			selectedTrackImage.rotate90DegreesClockwise();
			redrawRotateButton();
		}*/
		
	}
}
