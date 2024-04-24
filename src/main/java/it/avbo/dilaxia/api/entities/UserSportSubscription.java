package it.avbo.dilaxia.api.entities;

import it.avbo.dilaxia.api.entities.enums.ProfessionalLevel;

public class UserSportSubscription {
    private int sportId;
    private String student;
    private ProfessionalLevel professionalLevel;

    public UserSportSubscription(int sportId, String student, ProfessionalLevel professionalLevel) {
        this.sportId = sportId;
        this.student = student;
        this.professionalLevel = professionalLevel;
    }

    public int getSportId() {
        return sportId;
    }

    public String getStudent() {
        return student;
    }

    public ProfessionalLevel getProfessionalLevel() {
        return professionalLevel;
    }
}
