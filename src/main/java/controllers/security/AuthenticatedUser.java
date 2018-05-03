package controllers.security;

import java.util.Date;

public class AuthenticatedUser {

    private String username;
    private boolean isAdmin;
    private int expDate;

    public AuthenticatedUser(String username, boolean isAdmin, int expDate) {
        this.username = username; this.isAdmin = isAdmin; this.expDate=expDate;
    }

    public String getUsername() {
        return username;
    }


    public boolean isAdmin(){
        return isAdmin;
    }

    public int getExpDate() {
        return expDate;
    }
}
