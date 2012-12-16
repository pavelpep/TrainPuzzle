package com.trainpuzzle.ui.windows.loadedlevel;

import java.util.LinkedList;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.trainpuzzle.infrastructure.Images;
import com.trainpuzzle.model.board.Cargo;
import com.trainpuzzle.model.board.Station;
import com.trainpuzzle.model.board.Cargo.CargoType;
import com.trainpuzzle.model.level.Level;

public class CargosDrawer extends CargoLayerDrawerType{
    public static final float LEFT_ALIGNMENT = 0.0f;
    
	public void displayCargoLayer(Station station, JPanel cargoLayer, Level level){
		LinkedList<Cargo> exportCargoList = station.getExportCargo(); 
		LinkedList<Cargo> importCargoList = station.getImportCargo();
		
		JPanel cargoRow1 = new JPanel();
		initCargoRow(cargoRow1);		
		JPanel cargoRow2 = new JPanel();
		initCargoRow(cargoRow2);
		
		displayAllCargoesInStation(cargoRow1, exportCargoList);
		displayAllRequestsInStation(cargoRow1, importCargoList);
		
		cargoLayer.add(cargoRow1);
		cargoLayer.add(cargoRow2);	
	}
	
	
	private void initCargoRow(JPanel oneCargoRow){
		oneCargoRow.setOpaque(false);
		oneCargoRow.setLayout(new BoxLayout(oneCargoRow, BoxLayout.X_AXIS));
		oneCargoRow.setAlignmentX(LEFT_ALIGNMENT);		
	}
	
	private void displayAllCargoesInStation(JPanel cargoRow, LinkedList<Cargo> exportCargoList){
		for (Cargo cargo: exportCargoList) {
			JLabel cargoLabel = new JLabel(getExportCargoIcon(cargo.getType()));
			cargoLabel.setBorder(new EmptyBorder(0, 1, 0, 1));
			cargoRow.add(cargoLabel);
		}			
	}

	private void displayAllRequestsInStation(JPanel cargoRow, LinkedList<Cargo> importCargoList){
		for (Cargo cargo: importCargoList) {
			JLabel cargoLabel = new JLabel(getImportCargoIcon(cargo.getType()));
			cargoLabel.setBorder(new EmptyBorder(0, 1, 0, 1));
			cargoRow.add(cargoLabel);
		}			
	}
	
}
