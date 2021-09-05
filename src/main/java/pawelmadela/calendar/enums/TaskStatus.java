package pawelmadela.calendar.enums;


/**
 * Represents type for tasks status
 */
public enum TaskStatus {
    ACTIVE("ACTIVE"),
    EXPIRED("EXPIRED"),
    ARCHIVED("ARCHIVED");
    public String name;
    TaskStatus(String name){
        this.name = name;
    }
}
