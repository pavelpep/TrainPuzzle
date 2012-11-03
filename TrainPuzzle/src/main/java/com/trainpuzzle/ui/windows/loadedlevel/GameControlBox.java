package com.trainpuzzle.ui.windows.loadedlevel;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.UIManager;
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
import com.trainpuzzle.ui.windows.WindowManager;

public class GameControlBox extends JPanel implements ActionListener{
	private GameController gameController;
	private JButton runButton = new JButton("Run");
	private JButton pauseButton = new JButton("Pause");
	
	public GameControlBox(GameController gameController) {
		this.gameController = gameController;
		
		this.setPreferredSize(new Dimension(200, 150));
		Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		TitledBorder gameControlBoxTitle;
		gameControlBoxTitle = BorderFactory.createTitledBorder(loweredetched, "Game Controls");
		gameControlBoxTitle.setTitlePosition(TitledBorder.ABOVE_TOP);
		this.setBorder(gameControlBoxTitle);
		this.setLayout(new GridBagLayout());

		
		runButton.setActionCommand("run");
		runButton.addActionListener(this);
		runButton.setVisible(true);
		GridBagConstraints runButtonContraints = buttonConstraints(new Point(0, 0), new Dimension(3, 1));
		this.add(runButton, runButtonContraints);
		
		pauseButton.setActionCommand("pause");
		pauseButton.addActionListener(this);
		pauseButton.setVisible(false);
		GridBagConstraints pauseButtonContraints = buttonConstraints(new Point(0, 0), new Dimension(3, 1));
		this.add(pauseButton, pauseButtonContraints);
		
		JButton speedDecreaseButton = new JButton("-");
		speedDecreaseButton.setActionCommand("tickDecrease");
		speedDecreaseButton.addActionListener(this);
		GridBagConstraints speedDecreaseButtonContraints = buttonConstraints(new Point(0, 1), new Dimension(1, 1));
		this.add(speedDecreaseButton, speedDecreaseButtonContraints);
		
		JButton singleStepButton = new JButton(">");
		singleStepButton.setActionCommand("tickOnce");
		singleStepButton.addActionListener(this);
		GridBagConstraints singleStepButtonContraints = buttonConstraints(new Point(1, 1), new Dimension(1, 1));
		this.add(singleStepButton, singleStepButtonContraints);
		
		JButton speedIncreaseButton = new JButton("+");
		speedIncreaseButton.setActionCommand("tickIncrease");
		speedIncreaseButton.addActionListener(this);
		GridBagConstraints speedIncreaseButtonContraints = buttonConstraints(new Point(2, 1), new Dimension(1, 1));
		this.add(speedIncreaseButton, speedIncreaseButtonContraints);
		
		JButton resetButton = new JButton("Reset Position");
		resetButton.setActionCommand("reset");
		resetButton.addActionListener(this);
		GridBagConstraints resetButtonContraints = buttonConstraints(new Point(0, 2), new Dimension(3, 1));
		this.add(resetButton, resetButtonContraints);
		
		JButton removeAllTracksButton = new JButton("Remove All Tracks");
		removeAllTracksButton.setActionCommand("removeAllTracks");
		removeAllTracksButton.addActionListener(this);
		GridBagConstraints removeAllTracksButtonContraints = buttonConstraints(new Point(0, 3), new Dimension(3, 1));
		this.add(removeAllTracksButton, removeAllTracksButtonContraints);
	}
	
	

	private GridBagConstraints buttonConstraints(Point location, Dimension size) {
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = (int) location.getX();
		gridBagConstraints.gridy = (int) location.getY();
		gridBagConstraints.gridwidth = (int) size.getWidth();
		gridBagConstraints.gridheight = (int) size.getHeight();
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1;
		return gridBagConstraints;
	}
	

	public void actionPerformed(ActionEvent event) {
		if (event.getActionCommand() == "backToLevelSelect") {
			WindowManager.getManager(gameController).showPreviousWindow();
		}
		if (event.getActionCommand() == "run") {
			gameController.getSimulator().run();
			runButton.setVisible(false);
			pauseButton.setVisible(true);
			
		}
		if (event.getActionCommand() == "pause") {
			gameController.getSimulator().stop();
			runButton.setVisible(true);
			pauseButton.setVisible(false);
		}	
		if (event.getActionCommand() == "reset") {
			gameController.getSimulator().reset();
			
		}	
		if (event.getActionCommand() == "tickDecrease") {
			int decreasedValue = gameController.getSimulator().getTickInterval() * 2;
			int upperBound = gameController.getSimulator().getTickIntervalUpperBound();
			if(decreasedValue <= upperBound){
				gameController.getSimulator().setTickInterval(decreasedValue);
			}
		}
		
		if (event.getActionCommand() == "tickOnce") {
			gameController.getSimulator().move();
		}
		
		if (event.getActionCommand() == "tickIncrease") {
			int increasedValue = gameController.getSimulator().getTickInterval() / 2;
			int lowerBound = gameController.getSimulator().getTickIntervalLowerBound();
			if(increasedValue >= lowerBound){
				gameController.getSimulator().setTickInterval(increasedValue);
			}
		}
		
		if (event.getActionCommand() == "removeAllTracks") {
			gameController.removeAllTracks();
		}
	}
	
}
