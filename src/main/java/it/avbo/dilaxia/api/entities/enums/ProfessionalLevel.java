package it.avbo.dilaxia.api.entities.enums;

public enum ProfessionalLevel {
    Amateur("P"),
    Intermediate("I"),
    Advanced("A");

    private String value;
    ProfessionalLevel (String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ProfessionalLevel  fromValue(String value) {
        for(ProfessionalLevel type: ProfessionalLevel .values()) {
            if(type.value.equals(value)) {
                return type;
            }
        }
        return null;
    }
}
