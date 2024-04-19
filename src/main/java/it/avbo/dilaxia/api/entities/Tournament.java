package it.avbo.dilaxia.api.entities;

public class Tournament {
    public final int id;
    public final int sportId;
    public final int campId;
    public final String coach;
    public final String profCreator;
    public final String description;

    public Tournament(int id, int sportId, int campId, String coach, String profCreator, String description) {
        this.id = id;
        this.sportId = sportId;
        this.campId = campId;
        this.coach = coach;
        this.profCreator = profCreator;
        this.description = description;
    }
}
