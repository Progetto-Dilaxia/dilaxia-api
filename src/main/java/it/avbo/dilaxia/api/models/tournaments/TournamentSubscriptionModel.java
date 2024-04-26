package it.avbo.dilaxia.api.models.tournaments;

import com.google.gson.annotations.SerializedName;

public class TournamentSubscriptionModel {
    @SerializedName("tournament")
    private int tournamentId;
    @SerializedName("team")
    private int teamId;

    public int getTournamentId() {
        return tournamentId;
    }

    public int getTeamId() {
        return teamId;
    }
}
