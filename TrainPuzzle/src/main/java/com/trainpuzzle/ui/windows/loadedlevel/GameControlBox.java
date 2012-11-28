package com.trainpuzzle.ui.windows.loadedlevel;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import com.trainpuzzle.controller.GameController;
import com.trainpuzzle.exception.TrainCrashException;

public class GameControlBox extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1L;
	
	private GameController gameController;
	private JButton runButton = new JButton("Run");
	private JButton pauseButton = new JButton("Pause");
	private int removeTracksOptionPane = JOptionPane.YES_OPTION;
	
	public GameControlBox(GameController gameController) {
		this.gameController = gameController;
		
		Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		TitledBorder gameControlBoxTitle;
		gameControlBoxTitle = BorderFactory.createTitledBorder(loweredetched, "Game Controls");
		gameControlBoxTitle.setTitlePosition(TitledBorder.ABOVE_TOP);
		
		this.setBorder(gameControlBoxTitle);
		this.setLayout(new GridBagLayout());
		
		addGameControlBoxComponents();
	}

	private void addGameControlBoxComponents() {
		runButton = initializeButton("Run", "run");
		runButton.setVisible(true);
		
		GridBagConstraints runButtonContraints = buttonConstraints(new Point(0, 0), new Dimension(3, 1));
		this.add(runButton, runButtonContraints);
		
		pauseButton = initializeButton("Pause","pause");
		pauseButton.setVisible(false);
		
		GridBagConstraints pauseButtonContraints = buttonConstraints(new Point(0, 0), new Dimension(3, 1));
		this.add(pauseButton, pauseButtonContraints);
		
		JButton speedDecreaseButton = initializeButton("-","tickDecrease");
		GridBagConstraints speedDecreaseButtonContraints = buttonConstraints(new Point(0, 1), new Dimension(1, 1));
		this.add(speedDecreaseButton, speedDecreaseButtonContraints);
		
		JButton singleStepButton = initializeButton(">", "tickOnce");
		GridBagConstraints singleStepButtonContraints = buttonConstraints(new Point(1, 1), new Dimension(1, 1));
		this.add(singleStepButton, singleStepButtonContraints);
		
		JButton speedIncreaseButton = initializeButton("+", "tickIncrease");
		GridBagConstraints speedIncreaseButtonContraints = buttonConstraints(new Point(2, 1), new Dimension(1, 1));
		this.add(speedIncreaseButton, speedIncreaseButtonContraints);
		
		JButton resetButton = initializeButton("Reset Position","reset");
		GridBagConstraints resetButtonContraints = buttonConstraints(new Point(0, 2), new Dimension(3, 1));
		this.add(resetButton, resetButtonContraints);
		
		JButton removeAllTracksButton = initializeButton("Remove All Placed Tracks", "removeAllTracks");
		GridBagConstraints removeAllTracksButtonContraints = buttonConstraints(new Point(0, 3), new Dimension(3, 1));
		this.add(removeAllTracksButton, removeAllTracksButtonContraints);
	}
	
	private JButton initializeButton(String label, String actionCommand) {
		JButton button = new JButton(label);
		button.setActionCommand(actionCommand);
		button.addActionListener(this);
		return button;
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
	
	public void setRunButtonVisible() {
		runButton.setVisible(true);
		pauseButton.setVisible(false);
	}
	
	public void setPauseButtonVisible() {
		runButton.setVisible(false);
		pauseButton.setVisible(true);
	}

	public void actionPerformed(ActionEvent event) {
		if (event.getActionCommand() == "run") {
			gameController.getSimulator().run();
			setPauseButtonVisible();
			
		}
		if (event.getActionCommand() == "pause") {
			gameController.getSimulator().stop();
			setRunButtonVisible();
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
			try {
				gameController.getSimulator().move();
			} catch (TrainCrashException e) {
				e.printStackTrace();
			}
		}
		if (event.getActionCommand() == "tickIncrease") {
			int increasedValue = gameController.getSimulator().getTickInterval() / 2;
			int lowerBound = gameController.getSimulator().getTickIntervalLowerBound();
			
			if(increasedValue >= lowerBound) {
				gameController.getSimulator().setTickInterval(increasedValue);
			}
		}
		if (event.getActionCommand() == "removeAllTracks") {
			removeTracksOptionPane = JOptionPane.showConfirmDialog(null,"This will remove all placed tracks and reset your train." +
					" Continue?");
			
			if (removeTracksOptionPane == JOptionPane.YES_OPTION) {
				gameController.removeAllTracks();
				gameController.getSimulator().reset();
				JOptionPane.showMessageDialog(null, "All placed tracks removed and position reset!");				
			} 
			else {
				// do nothing
			}


		}
	}
}