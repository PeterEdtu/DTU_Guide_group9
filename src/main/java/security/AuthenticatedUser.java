package security;

public class AuthenticatedUser {

    public AuthenticatedUser(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    private String username;

}
