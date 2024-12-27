package com.anju.demo.forMockUsingStub;

public class UserPreferenceServiceStub implements UserPreferenceService{

	@Override
	public String getUserPreferredFruits(Integer user) {
		if(user>0) return "Mango";
		else if(user==0) return "Papaya";
		else return null;
	}

}
