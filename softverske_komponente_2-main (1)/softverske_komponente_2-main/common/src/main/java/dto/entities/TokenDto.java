package dto.entities;

import models.Rank;

@SuppressWarnings("all")
public class TokenDto {

    private Long id;
    private String username;
    private Rank rank;
    private String email;
    private String userType;
    private boolean banned;

    public TokenDto(Long id, String username, Rank rank, String email, String userType, boolean isBanned) {
        this.id = id;
        this.email = email;
        this.userType = userType;
        this.banned = isBanned;
        this.username = username;
        this.rank = rank;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getUserType() {
        return userType;
    }

    public boolean isBanned() {
        return banned;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }
}
