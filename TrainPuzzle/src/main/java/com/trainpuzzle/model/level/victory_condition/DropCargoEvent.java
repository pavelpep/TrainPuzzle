package com.trainpuzzle.model.level.victory_condition;

import com.trainpuzzle.model.board.Cargo;
import com.trainpuzzle.model.board.Location;
import com.trainpuzzle.model.board.Station;

public class DropCargoEvent extends Event {
	
	private static final long serialVersionUID = 1L;
	private Cargo cargo;
	public DropCargoEvent(int time, Station station,Cargo cargo) {
		super(time, station);
		this.cargo = cargo;
	}
	
	public DropCargoEvent(Station station,Cargo cargo) {
		super(station);
		this.cargo = cargo;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		DropCargoEvent other = (DropCargoEvent) obj;
		return compareDropCargoEvent(other);
	}
	
	private boolean compareDropCargoEvent(DropCargoEvent event) {
		Station station = this.getStation();
		Cargo cargo = event.getCargo();
		return station.equals(event.getStation()) && cargo.equals(this.cargo);
	}

	public Cargo getCargo() {
		return cargo;
	}
	
	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}
	
	public String toString() {
		Location location = this.getStation().getStationLocation();
		return "drop "+cargo.getType().getName()+" at Station ( "+location.getRow()+","+location.getColumn()+")" ;
	}

}
