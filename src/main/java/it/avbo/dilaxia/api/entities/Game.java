package it.avbo.dilaxia.api.entities;


import java.sql.Date;
import java.sql.Timestamp;

public class Game {
    private int id;
    private int fieldId;
    private String creatorUsername;
    private String gameDescription;
    private Timestamp gameDate;
    private String classYears;
    private int maxPlayers;
    private int minPlayers;
    private int playersPerTeam;
    private String sex;
    private int idTeam1;
    private int idTeam2;
    private int tournamentId;

    public Game(int id, int fieldId, String creatorUsername, String gameDescription, Timestamp gameDate, String classYears, int maxPlayers, int minPlayers, int playersPerTeam, String sex, int idTeam1, int idTeam2, int tournamentId) {
        this.id = id;
        this.fieldId = fieldId;
        this.creatorUsername = creatorUsername;
        this.gameDescription = gameDescription;
        this.gameDate = gameDate;
        this.classYears = classYears;
        this.maxPlayers = maxPlayers;
        this.minPlayers = minPlayers;
        this.playersPerTeam = playersPerTeam;
        this.sex = sex;
        this.idTeam1 = idTeam1;
        this.idTeam2 = idTeam2;
        this.tournamentId = tournamentId;
    }

    public String getCreatorUsername() {
        return creatorUsername;
    }

    public int getFieldId() {
        return fieldId;
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

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public int getPlayersPerTeam() {
        return playersPerTeam;
    }

    public String getSex() {
        return sex;
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
