package controllers.security;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;


import brugerautorisation.data.Bruger;
import brugerautorisation.transport.rmi.Brugeradmin;
import com.sun.org.apache.xpath.internal.operations.Bool;
import controllers.security.exception.PermissionToLow;
import controllers.stub.StubAdminControls;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import controllers.security.exception.InvalidCredentials;
import controllers.security.exception.InvalidToken;
import controllers.security.exception.NotAuthenticated;

import javax.ws.rs.core.Cookie;

public class Auth {

    //TODO: This needs to be changed so it changes once in a while.
    private final static Key jwtKey = MacProvider.generateKey();
    private static Brugeradmin ba;

    private static StubAdminControls adminInfo;

    static{
        try {
            ba = (Brugeradmin) Naming.lookup("rmi://javabog.dk/brugeradmin");
        }catch (Exception e){
            e.printStackTrace();
        }
        adminInfo=StubAdminControls.getInstance();
    }


    public static String authenticate(String username, String password) throws InvalidCredentials {

        Bruger loggedInUser=null;

        try {
            loggedInUser = ba.hentBruger(username, password);
        }catch (RemoteException e){
            System.err.println("Error message: "+e.getMessage());
            e.printStackTrace();
            throw new InvalidCredentials();
        }

        return generateJWT(loggedInUser);
    }


    public static AuthenticatedUser authorizeAdmin(Cookie cookie) throws NotAuthenticated, InvalidToken, PermissionToLow {
        AuthenticatedUser user = authorize(cookie);
        if(!user.isAdmin()){
            throw new PermissionToLow();
        }
        return user;

    }

    public static AuthenticatedUser authorize(Cookie cookie) throws InvalidToken, NotAuthenticated {
        if(cookie==null) {
            throw new NotAuthenticated();
        }
        return authorize(cookie.getValue());

    }


    private static AuthenticatedUser authorize(String jwt) throws InvalidToken {

        Jws<Claims> myjwt;
        try {
            myjwt = Jwts.parser().setSigningKey(jwtKey).parseClaimsJws(jwt);
        }
        catch(Exception e) {
            throw new InvalidToken();
        }

        Claims body =myjwt.getBody();

        return new AuthenticatedUser(body.getSubject(),(Boolean)body.remove("isAdmin"),(int)body.remove("exp"));
    }


    private static String generateJWT(Bruger loggedInUser) {

        Date currentDate = new Date();


        Date expDate = new Date(currentDate.getTime() + timeToMidnight());


        int jwtID = (int)(Math.random()*64000);

        boolean isAdmin = adminInfo.isAdmin(loggedInUser.brugernavn);

        return Jwts.builder()
                .setHeaderParam("id", jwtID)
                .setSubject(loggedInUser.brugernavn)
                .claim("email", loggedInUser.email)
                .claim("isAdmin",isAdmin)
                .claim("exp",expDate)
                .setIssuedAt(currentDate).setExpiration(expDate)
                .signWith(SignatureAlgorithm.HS512, jwtKey).compact();
    }

    private static long  timeToMidnight() {
        long miliSecondsPrHour=3600000;
        long miliSecondsPrMinute=60000;
        long miliSecondsPrSecond=1000;

        Calendar myCalender = Calendar.getInstance();
        long hours =myCalender.get(Calendar.HOUR_OF_DAY);
        long minutes = myCalender.get(Calendar.MINUTE);
        long seconds = myCalender.get(Calendar.SECOND);


        return ((23-hours)*miliSecondsPrHour)+((59-minutes)*miliSecondsPrMinute)+((60-seconds)*miliSecondsPrSecond);
    }


}
