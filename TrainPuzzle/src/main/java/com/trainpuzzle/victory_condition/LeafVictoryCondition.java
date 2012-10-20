package com.trainpuzzle.victory_condition;

public class LeafVictoryCondition extends VictoryCondition{
	private Event event;
	
	public LeafVictoryCondition(Event event) {
		this.event = event;
	}
	
	public Event getEvent() {
		return event;
	}
	
	public boolean checkEvent(Event event) {
		return this.event.equals(event);
	}
	public void eventClear() {
		this.conditionSatisfied = true;
		if(this.Parent != this){
			this.Parent.checkCoditionSatisfaction();
		}
	}
	public void checkCoditionSatisfaction() {
		
	}
	

}
