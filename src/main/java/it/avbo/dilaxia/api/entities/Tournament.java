package it.avbo.dilaxia.api.entities;

public class Tournament {
    private final int id;
    private final int sportId;
    private final int campId;
    private final String coachUsername;
    private final String creatorUsername;
    private final String description;

    public Tournament(int id, int sportId, int campId, String coachUsername, String creatorUsername, String description) {
        this.id = id;
        this.sportId = sportId;
        this.campId = campId;
        this.coachUsername = coachUsername;
        this.creatorUsername = creatorUsername;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public int getSportId() {
        return sportId;
    }

    public int getCampId() {
        return campId;
    }

    public String getCoachUsername() {
        return coachUsername;
    }

    public String getCreatorUsername() {
        return creatorUsername;
    }

    public String getDescription() {
        return description;
    }
}
