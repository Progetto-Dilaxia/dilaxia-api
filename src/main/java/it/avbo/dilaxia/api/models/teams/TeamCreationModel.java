package it.avbo.dilaxia.api.models.teams;

import com.google.gson.annotations.SerializedName;

public class TeamCreationModel {

	@SerializedName("name")
	private String name;
	@SerializedName("sport")
	private int sportId;
	@SerializedName("username_coach")
	private String usernameCoach;
	
	public String getName() {
		return name;
	}
	public int getSportId() {
		return sportId;
	}
	public String getUsernameCoach() {
		return usernameCoach;
	}
	
}
