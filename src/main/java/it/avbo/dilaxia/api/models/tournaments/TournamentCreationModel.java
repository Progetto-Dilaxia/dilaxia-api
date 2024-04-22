package it.avbo.dilaxia.api.models.tournaments;

import com.google.gson.annotations.SerializedName;

public class TournamentCreationModel {
    @SerializedName("id_sport")
    private int sportId;
    @SerializedName("id_campo")
    private int fieldId;
    @SerializedName("coach")
    private String coachUsername;
    @SerializedName("descrizione")
    private String description;
    @SerializedName("squadre")
    private int[] teams;

    public int getSportId() {
        return sportId;
    }

    public int getFieldId() {
        return fieldId;
    }

    public String getCoachUsername() {
        return coachUsername;
    }

    public String getDescription() {
        return description;
    }

    public int[] getTeams() {
        return teams;
    }
}
