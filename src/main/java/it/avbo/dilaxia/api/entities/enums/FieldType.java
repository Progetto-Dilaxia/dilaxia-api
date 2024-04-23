package it.avbo.dilaxia.api.entities.enums;

public enum FieldType {
    Dirt("T"),
    Synthetic("S"),
    Cement("C");

    private String value;
    FieldType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static FieldType fromValue(String value) {
        for(FieldType type: FieldType.values()) {
            if(type.value.equals(value)) {
                return type;
            }
        }
        return null;
    }
}
