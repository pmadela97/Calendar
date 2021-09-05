package pawelmadela.calendar.enums;

public enum AccountStatus {
    BLOCKED("BLOCKED"),
    EXPIRED("EXPIRED"),
    ACTIVE("ACTIVE");

    public String name;
    AccountStatus(String name){
        this.name = name;
    }

}
