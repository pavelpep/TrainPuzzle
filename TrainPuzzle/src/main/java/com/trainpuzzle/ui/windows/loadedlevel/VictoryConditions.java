package com.trainpuzzle.ui.windows.loadedlevel;


import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import com.trainpuzzle.controller.GameController;

public class VictoryConditions extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1L;
	
	private GameController gameController;
	
	public VictoryConditions(GameController gameController) {
		this.gameController = gameController;
		
		this.setPreferredSize(new Dimension(200, 150));
		Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		TitledBorder gameControlBoxTitle;
		gameControlBoxTitle = BorderFactory.createTitledBorder(loweredetched, "Game Controls");
		gameControlBoxTitle.setTitlePosition(TitledBorder.ABOVE_TOP);
		this.setBorder(gameControlBoxTitle);
		
	}
	

	public void actionPerformed(ActionEvent event) {
		
	}
	
}
