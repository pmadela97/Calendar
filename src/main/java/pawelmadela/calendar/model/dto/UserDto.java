package pawelmadela.calendar.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserDto {

    public String userId;
    public String username;
    public String firstname;
    public String lastname;
    public String emailAddress;
    public String accountStatus;
    public String accountType;

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"").append("userId").append("\":").append("\"").append(userId).append("\",");
        sb.append("\"").append("username").append("\":").append("\"").append(username).append("\",");
        sb.append("\"").append("firstname").append("\":").append("\"").append(firstname).append("\",");
        sb.append("\"").append("lastname").append("\":").append("\"").append(lastname).append("\",");
        sb.append("\"").append("emailAddress").append("\":").append("\"").append(emailAddress).append("\",");
        sb.append("\"").append("accountStatus").append("\":").append("\"").append(accountStatus).append("\"");
        sb.append("}");
        return sb.toString();
    }

}

