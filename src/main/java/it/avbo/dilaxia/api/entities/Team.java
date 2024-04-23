package it.avbo.dilaxia.api.entities;

public class Team {
	private final int id;
	private final String name;
	private final int sportId;
	private final String usernameCoach;
	public Team(int id, String name, int sportId, String usernameCoach) {
		super();
		this.id = id;
		this.name = name;
		this.sportId = sportId;
		this.usernameCoach = usernameCoach;
	}
	public int getId() {
		return id;
	}
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
