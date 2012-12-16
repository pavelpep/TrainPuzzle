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
import java.util.List;

import com.trainpuzzle.controller.CargoRequestGenerator;
import com.trainpuzzle.infrastructure.Images;
import com.trainpuzzle.model.board.Station;
import com.trainpuzzle.model.board.Cargo;
import com.trainpuzzle.model.board.Cargo.CargoType;

import com.trainpuzzle.model.level.Level;

public class RequestersDrawer extends CargoLayerDrawerType{   
	public void displayCargoLayer(Station station, JPanel cargoLayer, Level level){
		LinkedList<Cargo> importCargoList = station.getImportCargo();
		HashMap<CargoType, Boolean> cargoTypeExist = station.getCanGenerateCargoTypes();
		
		JPanel cargoRow1 = new JPanel();
		initCargoRow(cargoRow1);		
		JPanel cargoRow2 = new JPanel();
		initCargoRow(cargoRow2);
		
		displayCargoTypesInStation(cargoRow1, station, cargoTypeExist);
		displayNumOfCargosInStation(cargoRow1, importCargoList);
		displayRequesterFrequency(cargoRow2,station, cargoTypeExist,level);
		
		cargoLayer.add(cargoRow1);
		cargoLayer.add(cargoRow2);	
	}
	
	private void displayCargoTypesInStation(JPanel cargoRow1, Station station,
			HashMap<CargoType,Boolean> canGenerateCargoTypes){
		JLabel cargoLabel = null;
		for (CargoType cargoType: CargoType.values()){
			if (canGenerateCargoTypes.get(cargoType)){
					cargoLabel = new JLabel(getImportCargoIcon(cargoType));
				cargoLabel.setBorder(new EmptyBorder(0, 1, 0, 1));
				cargoRow1.add(cargoLabel);
			}
		}
	}

	private void displayRequesterFrequency(JPanel cargoRow2,Station station,
			HashMap<CargoType,Boolean> cargoTypeExist, Level level){
		for (CargoType cargoType: CargoType.values()){
			if (cargoTypeExist.get(cargoType)){
				CargoRequestGenerator wantedRequester = getRequester(level.getCargorequestors(), station, cargoType);
				
				Integer generatingInterval = wantedRequester.getGeneratingInteval();
				JLabel cargoLabel = new JLabel(generatingInterval.toString()+ "T");
				cargoLabel.setForeground(Color.WHITE);
				Font font = new Font("Arial", Font.ROMAN_BASELINE, 9);
				cargoLabel.setFont(font);
				cargoRow2.add(cargoLabel);
			}
		}
	}
	
	private CargoRequestGenerator getRequester(List<CargoRequestGenerator> cargoRequesters, 
			Station station, CargoType cargoType){
		for (CargoRequestGenerator requester: cargoRequesters){
			if (requester.getStation().equals(station) && requester.getRequestType()==cargoType){
				return requester;
			}
		}
		return null;
	}
	
}
