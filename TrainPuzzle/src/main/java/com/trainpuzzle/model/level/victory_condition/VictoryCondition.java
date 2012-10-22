package com.trainpuzzle.model.level.victory_condition;

/**
 * Represents 'component' in the composite programming pattern
 * 
 * @see <a href="http://en.wikipedia.org/wiki/Composite_pattern">Wikipedia/Composite_pattern</a>
 */
public interface VictoryCondition {
		
	public boolean isSatisfied();
	public void processEvent(Event event);
	public void resetEvents();
	
}
