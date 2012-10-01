package com.trainpuzzle.controller;

public class Application {
	private LevelLoader levelLoader;
	private TrackBuilder trackBuilder;
	private Simulator simulator;
	
	public Application(int levelNumber) {
		//todo change int to enum?
		levelLoader = new LevelLoader(levelNumber);
		trackBuilder = new TrackBuilder(levelLoader.getLevel());
	}
	
	
}
