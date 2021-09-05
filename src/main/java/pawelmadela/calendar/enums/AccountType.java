package pawelmadela.calendar.enums;

/**
 * Represents two types of account
 */
public enum AccountType {
    USER("USER"),
    ADMIN("ADMIN");

    String name;
    AccountType(String s){
        name = s;
    }
}
