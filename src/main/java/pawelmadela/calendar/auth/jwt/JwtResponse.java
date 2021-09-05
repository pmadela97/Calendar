package pawelmadela.calendar.auth.jwt;

import java.util.Date;

public class JwtResponse {

    private final String jwttoken;
   // private final String username;
   // private final Date expDate;
   // private final String firstname;
   // private final String lastname;


    public JwtResponse(String jwttoken, String username, Date expDate, String firstname, String lastname) {
        this.jwttoken = jwttoken;
        //this.username = username;
        //this.expDate = expDate;
        //this.firstname = firstname;
        //this.lastname = lastname;
    }

    public String getJwttoken() {
        return jwttoken;
    }
/*
    public String getUsername() {
        return username;
    }

    public Date getExpDate() {
        return expDate;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

 */
}