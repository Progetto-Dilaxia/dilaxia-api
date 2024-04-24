package it.avbo.dilaxia.api.models.sports;

import com.google.gson.annotations.SerializedName;

public class SportSubscriptionModel {
    @SerializedName("sport_id")
    private int sportId;

    @SerializedName("professionality_level")
    private String professionalLevel;

    public int getSportId() {
        return sportId;
    }

    public String getProfessionalLevel() {
        return professionalLevel;
    }
}
