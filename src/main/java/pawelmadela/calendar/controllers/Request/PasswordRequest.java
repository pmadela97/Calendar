package pawelmadela.calendar.controllers.Request;

import java.lang.reflect.Field;

public class PasswordRequest {
    private String oldPassword;
    private String newPassword;

PasswordRequest(){};


    public PasswordRequest(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;

    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }



    public boolean isNull(){
        Field[] fields  = this.getClass().getDeclaredFields();
        for (Field f: fields) {
            try {
                if(f.get(this) == null || f.get(this).equals("")){ return true;}
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
