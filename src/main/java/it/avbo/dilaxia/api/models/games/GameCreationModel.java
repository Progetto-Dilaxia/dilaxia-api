package it.avbo.dilaxia.api.models.games;

import com.google.gson.annotations.SerializedName;

public class GameCreationModel {

    @SerializedName("id_campo")
    public int fieldId;
    @SerializedName("descrizione")
    public String gameDescription;
    @SerializedName("data_partita")
    public String gameDate;
    @SerializedName("anni_classe_partecipanti")
    public String classYears;
    @SerializedName("max_giocatori")
    public int maxPlayers;
    @SerializedName("min_giocatori")
    public int minPlayers;
    @SerializedName("numero_giocatori_per_squadra")
    public int playersPerTeam;
    @SerializedName("genere")
    public String sex;
    @SerializedName("id_squadra1")
    public int idTeam1;
    @SerializedName("id_squadra2")
    public int idTeam2;
    @SerializedName("id_torneo")
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
