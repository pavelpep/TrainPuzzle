package com.trainpuzzle.ui.windows.loadedlevel;


import java.util.LinkedList;


import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.trainpuzzle.infrastructure.Images;
import com.trainpuzzle.model.board.Cargo;
import com.trainpuzzle.model.board.Station;
import com.trainpuzzle.model.board.Cargo.CargoType;
import com.trainpuzzle.model.level.Level;


public abstract class CargoLayerDrawerType {
    public static final float LEFT_ALIGNMENT = 0.0f;
    
	abstract void displayCargoLayer(Station station, JPanel cargoLayer, Level level);
	
	static CargoLayerDrawerType createNewType(Station station){
		switch (station.getType()){
		case FACTORY:
			return new GeneratorsDrawer();
		case REQUESTER:
			return new RequestersDrawer();
		default:
			return new CargosDrawer();
		}
	}
	
	protected void initCargoRow(JPanel oneCargoRow){
		oneCargoRow.setOpaque(false);
		oneCargoRow.setLayout(new BoxLayout(oneCargoRow, BoxLayout.X_AXIS));
		oneCargoRow.setAlignmentX(LEFT_ALIGNMENT);		
	}
	
	protected ImageIcon getExportCargoIcon(CargoType cargoType){
		ImageIcon cargoIcon = new ImageIcon(Images.IRON);
		switch(cargoType){
		case IRON:
			cargoIcon = new ImageIcon(Images.IRON);
			break;
		case COTTON:
			cargoIcon = new ImageIcon(Images.COTTON);
			break;
		case WOOD:
			cargoIcon = new ImageIcon(Images.WOOD);
			break;
		case COAL:
			cargoIcon = new ImageIcon(Images.COAL);
			break;
		case STEEL:
			cargoIcon = new ImageIcon(Images.STEEL);
			break;			
		}
		return cargoIcon;
	}	
	
	protected ImageIcon getImportCargoIcon(CargoType cargoType){
		ImageIcon cargoIcon = new ImageIcon(Images.IRON);
		switch(cargoType){
		case IRON:
			cargoIcon = new ImageIcon(Images.REQUIRED_IRON);
			break;
		case COTTON:
			cargoIcon = new ImageIcon(Images.REQUIRED_COTTON);
			break;
		case WOOD:
			cargoIcon = new ImageIcon(Images.REQUIRED_WOOD);
			break;
		case COAL:
			cargoIcon = new ImageIcon(Images.REQUIRED_COAL);
			break;
		case STEEL:
			cargoIcon = new ImageIcon(Images.REQUIRED_STEEL);
			break;			
		}
		return cargoIcon;
	}	
	
	protected void displayNumOfCargosInStation(JPanel cargoRow1,LinkedList<Cargo> CargoList){
		Integer numCargo = CargoList.size();
		JLabel numCargoLabel = new JLabel(numCargo.toString());
		cargoRow1.add(numCargoLabel);
	}
	
}
