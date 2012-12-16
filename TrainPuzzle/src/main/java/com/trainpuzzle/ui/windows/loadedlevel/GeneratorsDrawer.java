package com.trainpuzzle.ui.windows.loadedlevel;

import java.awt.Font;
import java.awt.Color;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.util.LinkedList;
import java.util.HashMap;

import com.trainpuzzle.controller.CargoGenerator;
import com.trainpuzzle.infrastructure.Images;
import com.trainpuzzle.model.board.Station;
import com.trainpuzzle.model.board.Cargo;
import com.trainpuzzle.model.board.Cargo.CargoType;

import com.trainpuzzle.model.level.Level;

public class GeneratorsDrawer extends CargoLayerDrawerType{
    public static final float LEFT_ALIGNMENT = 0.0f;
    
	public void displayCargoLayer(Station station, JPanel cargoLayer, Level level){
		LinkedList<Cargo> exportCargoList = station.getExportCargo(); 
		HashMap<CargoType, Boolean> cargoTypeExist = station.getCanGenerateCargoTypes();
		
		JPanel cargoRow1 = new JPanel();
		initCargoRow(cargoRow1);		
		JPanel cargoRow2 = new JPanel();
		initCargoRow(cargoRow2);
		
		displayCargoTypesInStation(cargoRow1, station, cargoTypeExist);
		displayNumOfCargosInStation(cargoRow1, exportCargoList);
		displayGeneratorFrequency(cargoRow2,station, cargoTypeExist,level);
		
		cargoLayer.add(cargoRow1);
		cargoLayer.add(cargoRow2);	
	}
	
	
	private void initCargoRow(JPanel oneCargoRow){
		oneCargoRow.setOpaque(false);
		oneCargoRow.setLayout(new BoxLayout(oneCargoRow, BoxLayout.X_AXIS));
		oneCargoRow.setAlignmentX(LEFT_ALIGNMENT);		
	}
	
	private void displayCargoTypesInStation(JPanel cargoRow1, Station station,
			HashMap<CargoType,Boolean> canGenerateCargoTypes){
		JLabel cargoLabel = null;
		for (CargoType cargoType: CargoType.values()){
			if (canGenerateCargoTypes.get(cargoType)){
					cargoLabel = new JLabel(getExportCargoIcon(cargoType));
				cargoLabel.setBorder(new EmptyBorder(0, 1, 0, 1));
				cargoRow1.add(cargoLabel);
			}
		}
	}
		
	private void displayGeneratorFrequency(JPanel cargoRow2,Station station,
			HashMap<CargoType,Boolean> cargoTypeExist, Level level){
		final int ANY_INTERVAL = 10;
		for (CargoType cargoType: CargoType.values()){
			if (cargoTypeExist.get(cargoType)){
				CargoGenerator wantedGenerator = new CargoGenerator(station,ANY_INTERVAL,cargoType);
				int indexOfCargoGenerator = level.getCargoGenerators().indexOf(wantedGenerator);
				wantedGenerator = level.getCargoGenerators().get(indexOfCargoGenerator);
				
				Integer generatingInterval = wantedGenerator.getGeneratingInterval();
				JLabel cargoLabel = new JLabel(generatingInterval.toString()+ "T");
				cargoLabel.setForeground(Color.WHITE);
				Font font = new Font("Arial", Font.ROMAN_BASELINE, 9);
				cargoLabel.setFont(font);
				cargoRow2.add(cargoLabel);
			}
		}
	}
	
}
