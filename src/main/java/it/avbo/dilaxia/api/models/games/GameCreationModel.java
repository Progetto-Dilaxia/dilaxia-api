package it.avbo.dilaxia.api.models.games;

import com.google.gson.annotations.SerializedName;

public class GameCreationModel {

    @SerializedName("field")
    public int fieldId;
    @SerializedName("description")
    public String gameDescription;
    @SerializedName("game_date")
    public String gameDate;
    @SerializedName("participants_class_year")
    public String classYears;
    @SerializedName("max_players")
    public int maxPlayers;
    @SerializedName("min_players")
    public int minPlayers;
    @SerializedName("total_players_per_team")
    public int playersPerTeam;
    @SerializedName("sex")
    public String sex;
    @SerializedName("id_team_1")
    public int idTeam1;
    @SerializedName("id_team_2")
    public int idTeam2;
    @SerializedName("tournament")
    public int tournamentId;

    public int getFieldId() {
        return fieldId;
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
