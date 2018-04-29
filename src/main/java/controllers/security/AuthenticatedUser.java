package controllers.security;

public class AuthenticatedUser {

    private String username;
    private boolean isAdmin;

    public AuthenticatedUser(String username, boolean isAdmin) {
        this.username = username; this.isAdmin = isAdmin;
    }

    public String getUsername() {
        return username;
    }


    public boolean isAdmin(){
        return isAdmin;
    }
}
