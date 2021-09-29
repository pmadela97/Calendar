package pawelmadela.calendar.enums;

public enum AccountStatus {
    BLOCKED("BLOCKED"),
    EXPIRED("EXPIRED"),
    ACTIVE("ACTIVE"),
    NOT_CONFIRMED("NOT_CONFIRMED");


    public String name;
    AccountStatus(String name){
        this.name = name;
    }

}
