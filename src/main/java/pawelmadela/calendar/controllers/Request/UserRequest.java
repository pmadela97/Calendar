package pawelmadela.calendar.controllers.Request;

import java.lang.reflect.Field;

public class UserRequest {
    private String username;
    private String firstname;
    private String lastname;
    private String password;
    private String emailAddress;

    public UserRequest() {
    }

    public UserRequest(String username, String firstname, String lastname, String password, String emailAddress) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.emailAddress = emailAddress;
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
