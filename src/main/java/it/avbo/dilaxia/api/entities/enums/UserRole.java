package it.avbo.dilaxia.api.entities.enums;

public enum UserRole {
    Student("S"),
    Teacher("I"),
    External("E"),
    Admin("A");

    private final String value;
    UserRole(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static UserRole fromValue(String value) {
        for(UserRole role: UserRole.values())
            if(role.getValue().equals(value))
                return role;
        return null;
    }
}
