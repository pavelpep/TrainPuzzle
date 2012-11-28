package com.trainpuzzle.factory;


import com.trainpuzzle.factory.level_strategy.*;
import com.trainpuzzle.model.level.Level;


//purposely created without the public tag because we only want this class accessible by the CampaignManager (which is in the same package)
/* 
 * Created without the public tag on purpose because we only want this class 
 * to be accessible by the CampaignManager (which is in the same package) 
*/

public class LevelFactory {    
	
	public Level createLevel(int levelNumber){
		switch(levelNumber) {
		case 1: 
			return (new LevelOne()).createLevel();
		case 2: 
			return (new LevelTwo()).createLevel();
		case 3: 
			return (new LevelThree()).createLevel();
		case 4:
			return (new LevelFour()).createLevel();
		case 5:
			return (new LevelFive()).createLevel();
		case 6:
			return (new LevelSix()).createLevel();
		case 7:
			return (new LevelSeven()).createLevel();
		case 8:
			return (new LevelEight()).createLevel();
		case 9:
			return (new LevelNine()).createLevel();
		case 10:
			return (new LevelTen()).createLevel();
		case 11:
			return (new LevelEleven()).createLevel();
		case 12:
			return (new LevelTwelve()).createLevel();
		default: 
			return (new LevelOne()).createLevel();
		}
	}
	    
	
}