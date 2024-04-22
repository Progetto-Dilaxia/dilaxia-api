package it.avbo.dilaxia.api.entities;

public class Tournament {
    public final int id;
    public final int sportId;
    public final int campId;
    public final String coachUsername;
    public final String creatorUsername;
    public final String description;

    public Tournament(int id, int sportId, int campId, String coachUsername, String creatorUsername, String description) {
        this.id = id;
        this.sportId = sportId;
        this.campId = campId;
        this.coachUsername = coachUsername;
        this.creatorUsername = creatorUsername;
        this.description = description;
    }
}
