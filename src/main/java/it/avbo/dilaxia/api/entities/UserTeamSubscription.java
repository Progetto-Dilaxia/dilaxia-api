package it.avbo.dilaxia.api.entities;

public class UserTeamSubscription {
    private String username;
    private int teamId;

    public UserTeamSubscription(String username, int teamId) {
        this.username = username;
        this.teamId = teamId;
    }

    public String getUsername() {
        return username;
    }

    public int getTeamId() {
        return teamId;
    }
}
