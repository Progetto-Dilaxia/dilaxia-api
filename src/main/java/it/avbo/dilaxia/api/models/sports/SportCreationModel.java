package it.avbo.dilaxia.api.models.sports;

import com.google.gson.annotations.SerializedName;

public class SportCreationModel {

	@SerializedName("name")
	private String name;
	@SerializedName("description")
	private String description;

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
}
