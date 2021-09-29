package pawelmadela.calendar.auth.jwt;

import pawelmadela.calendar.enums.AccountType;

import java.util.Date;

public class JwtResponse {

    private final String jwttoken;
   // private final Date expDate;
   private final String firstname;
   private final String lastname;
   private final String accountType;


    public JwtResponse(String jwttoken, String firstname, String lastname, String accountType) {
        this.jwttoken = jwttoken;
        this.firstname = firstname;
        this.lastname = lastname;
        this.accountType = accountType;
    }

    public String getJwttoken() {
        return jwttoken;
    }
    public String getFirstname() {
        return firstname;
    }
    public String getLastname() {
        return lastname;
    }
    public String getAccountType() {
        return accountType;
    }
}