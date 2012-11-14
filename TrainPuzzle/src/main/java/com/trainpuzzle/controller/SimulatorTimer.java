package com.trainpuzzle.controller;

import com.trainpuzzle.exception.TrainCrashException;

public class SimulatorTimer implements Runnable {
	private Simulator simulator; 
	
	public SimulatorTimer(Simulator simulator) {
		this.simulator = simulator;
	}
	
	public void run(){
		try {
			simulator.move();
		} catch (TrainCrashException e) {
			e.printStackTrace();
		}
	}
}