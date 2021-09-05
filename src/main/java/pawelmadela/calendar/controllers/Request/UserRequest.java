package pawelmadela.calendar.controller.Request;

import pawelmadela.calendar.model.User;

import java.lang.reflect.Field;

public class UserRequest {
    private String username;
    private String firstname;
    private String lastname;
    private String password;
    private String emailAddress;
    private String accountType;

    public UserRequest() {
    }

    public UserRequest(String username, String firstname, String lastname, String password, String emailAddress, String accountType) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.emailAddress = emailAddress;
        this.accountType = accountType;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPassword() { return password; }

    public String getAccountType() { return accountType; }

    public String getEmailAddress() {
        return emailAddress;
    }

    public boolean isNull(){
        Field[] fields  = this.getClass().getDeclaredFields();
        for (Field f: fields) {
            try {
                if(f.get(this) == null){ return true;}
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
