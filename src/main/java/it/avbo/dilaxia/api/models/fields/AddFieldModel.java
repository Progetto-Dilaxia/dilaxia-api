package it.avbo.dilaxia.api.models.fields;

import com.google.gson.annotations.SerializedName;

public class AddFieldModel {
    @SerializedName("name")
    private String name;

    @SerializedName("address")
    private String address;

    @SerializedName("sport")
    private int sportId;

    @SerializedName("type")
    private String type;

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getSportId() {
        return sportId;
    }

    public String getType() {
        return type;
    }
}
