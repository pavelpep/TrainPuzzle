package com.trainpuzzle.victory_condition;

public class LeafVictoryCondition extends VictoryCondition{
	private Event event;
	
	public LeafVictoryCondition(Event event) {
		super();
		this.event = event;
	}
	public LeafVictoryCondition(Event event, final boolean not) {
		super(not);
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
		if(this.parent != this){
			this.parent.checkCoditionSatisfaction();
		}
	}
	public void checkCoditionSatisfaction() {
		
	}
	

}
