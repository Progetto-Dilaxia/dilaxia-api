package it.avbo.dilaxia.api.entities;

import it.avbo.dilaxia.api.entities.enums.FieldType;

public class Field {
    private final int id;
    private final int sportId;
    private final String address;
    private final FieldType fieldType;

    public Field(int id, int sportId, String address, FieldType fieldType) {
        this.id = id;
        this.sportId = sportId;
        this.address = address;
        this.fieldType = fieldType;
    }

    public int getId() {
        return id;
    }

    public int getSportId() {
        return sportId;
    }

    public String getAddress() {
        return address;
    }

    public FieldType getFieldType() {
        return fieldType;
    }
}
