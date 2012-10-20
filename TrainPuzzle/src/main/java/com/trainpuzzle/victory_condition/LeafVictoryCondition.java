package com.trainpuzzle.victory_condition;

public class LeafVictoryCondition extends VictoryCondition{
	private Event event;
	
	public LeafVictoryCondition(Event event) {
		this.event = event;
	}
	public String getEventID() {
		return event.geteventID();
	}
	public void checkEvent(Event event) {
		if(this.event.equals(event)) {
			this.conditionSatisfied = true;
		}
	}
	

}
