package it.avbo.dilaxia.api.entities;


import java.sql.Timestamp;

public class Game {
    private int id;
    private int sportId;
    private int fieldId;
    private String creatorUsername;
    private String gameDescription;
    private Timestamp gameDate;
    private String classYears;
    private int playersPerTeam;
    private int idTeam1;
    private int idTeam2;
    private int tournamentId;
    
	public Game(int id, int sportId, int fieldId, String creatorUsername, String gameDescription, Timestamp gameDate,
			String classYears, int playersPerTeam, int idTeam1, int idTeam2, int tournamentId) {
		super();
		this.id = id;
		this.sportId = sportId;
		this.fieldId = fieldId;
		this.creatorUsername = creatorUsername;
		this.gameDescription = gameDescription;
		this.gameDate = gameDate;
		this.classYears = classYears;
		this.playersPerTeam = playersPerTeam;
		this.idTeam1 = idTeam1;
		this.idTeam2 = idTeam2;
		this.tournamentId = tournamentId;
	}

	public int getId() {
		return id;
	}

	public int getSportId() {
		return sportId;
	}

	public int getFieldId() {
		return fieldId;
	}

	public String getCreatorUsername() {
		return creatorUsername;
	}

	public String getGameDescription() {
		return gameDescription;
	}

	public Timestamp getGameDate() {
		return gameDate;
	}

	public String getClassYears() {
		return classYears;
	}

	public int getPlayersPerTeam() {
		return playersPerTeam;
	}

	public int getIdTeam1() {
		return idTeam1;
	}

	public int getIdTeam2() {
		return idTeam2;
	}

	public int getTournamentId() {
		return tournamentId;
	}
    
    
}
