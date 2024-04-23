package it.avbo.dilaxia.api.models.fields;

import com.google.gson.annotations.SerializedName;

public class AddFieldModel {
    @SerializedName("name")
    public String name;

    @SerializedName("address")
    public String address;

    @SerializedName("sport")
    public int sportId;

    @SerializedName("type")
    public String type;

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
