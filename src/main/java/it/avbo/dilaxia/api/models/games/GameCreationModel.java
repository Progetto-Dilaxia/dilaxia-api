package it.avbo.dilaxia.api.models.games;

import com.google.gson.annotations.SerializedName;

public class GameCreationModel {

    @SerializedName("field")
    private int fieldId;
    @SerializedName("sport")
    private int sportId;
    @SerializedName("description")
    private String gameDescription;
    @SerializedName("game_date")
    private String gameDate;
    @SerializedName("participants_class_year")
    private String classYears;
    @SerializedName("total_players_per_team")
    private int playersPerTeam;
    @SerializedName("id_team_1")
    private int idTeam1;
    @SerializedName("id_team_2")
    private int idTeam2;
    @SerializedName("tournament")
    private int tournamentId;

    public int getFieldId() {
        return fieldId;
    }

    public int getSportId() {
        return sportId;
    }

    public String getGameDescription() {
        return gameDescription;
    }

    public String getGameDate() {
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
