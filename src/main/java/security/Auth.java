package security;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;


import brugerautorisation.data.Bruger;
import brugerautorisation.transport.rmi.Brugeradmin;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

import javax.ws.rs.core.Cookie;

public class Auth {

    //TODO: This needs to be changed so it changes once in a while.
    private final static Key jwtKey = MacProvider.generateKey();
    private static Brugeradmin ba;

    static{
        try {
            ba = (Brugeradmin) Naming.lookup("rmi://javabog.dk/brugeradmin");
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static String authenticate(String username, String password) throws InvalidCredentials {

        Bruger loggedInUser=null;

        try {
            loggedInUser = ba.hentBruger(username, password);
        }catch (Exception e){
            throw new InvalidCredentials();
        }

        String jwtJSON = generateJWT(loggedInUser);

        return jwtJSON;



    }

    public static AuthenticatedUser authorize(Cookie cookie) throws InvalidToken, NotAuthenticated {
        if(cookie==null) {
            throw new NotAuthenticated();
        }
        return authorize(cookie.getValue());

    }


    private static AuthenticatedUser authorize(String jwt) throws InvalidToken {

        Key sesionkey= jwtKey;

        Jws<Claims> myjwt;
        try {
            myjwt = Jwts.parser().setSigningKey(sesionkey).parseClaimsJws(jwt);
        }
        catch(Exception e) {
            throw new InvalidToken();
        }

        Claims body =myjwt.getBody();

        return new AuthenticatedUser(body.getSubject());
    }


    private static String generateJWT(Bruger loggedInUser) {

        Date currentDate = new Date();


        Date expDate = new Date(currentDate.getTime() + timeToMidnight());


        int jwtID = (int)(Math.random()*64000);


        String compactJws = Jwts.builder()
                .setHeaderParam("id", jwtID)
                .setSubject(loggedInUser.brugernavn)
                .claim("email", loggedInUser.email)
                .setIssuedAt(currentDate).setExpiration(expDate)
                .signWith(SignatureAlgorithm.HS512, jwtKey).compact();
        return compactJws;
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
