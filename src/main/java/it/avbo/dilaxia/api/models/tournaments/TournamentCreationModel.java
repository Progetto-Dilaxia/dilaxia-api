package it.avbo.dilaxia.api.models.tournaments;

import com.google.gson.annotations.SerializedName;

public class TournamentCreationModel {
    @SerializedName("id_sport")
    private int sportId;
    @SerializedName("id_campo")
    private int campId;
    @SerializedName("coach")
    private int coachId;
    @SerializedName("descrizione")
    private String description;
    @SerializedName("squadre")
    private int[] teams;

}
