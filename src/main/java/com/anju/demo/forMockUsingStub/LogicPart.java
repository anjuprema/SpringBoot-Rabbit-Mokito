package com.anju.demo.forMockUsingStub;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LogicPart {
	public Boolean methodThantNeedsToBeTested(UserPreferenceService service){
		ArrayList<String> seasonalFruits = new ArrayList(Arrays.asList("Apple","Mango"));
		String fruitsUserLikes = service.getUserPreferredFruits(12);
		if (seasonalFruits.contains(fruitsUserLikes)) return true;
		else return false;		
	}

}
