package it.avbo.dilaxia.api.entities;

public class Team {
	private int id;
	private String name;
	private int idSport;
	private String usernameCoach;
	public Team(int id, String name, int idSport, String usernameCoach) {
		super();
		this.id = id;
		this.name = name;
		this.idSport = idSport;
		this.usernameCoach = usernameCoach;
	}
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public int getIdSport() {
		return idSport;
	}
	public String getUsernameCoach() {
		return usernameCoach;
	}

	
	
}
