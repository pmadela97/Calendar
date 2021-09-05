package pawelmadela.calendar.enums;


/**
 * Represents type which provides information about response from services and store message to controller
 */
public enum UserServiceResponseStatus {

    USERNAME_EXISTS,
    EMAIL_EXISTS,
    USERID_EXISTS,
    PASSWORD_INCORRECT,
    USERNAME_NOT_FOUND,
    USERID_NOT_FOUND,
    EMAIL_NOT_FOUND,
    USER_FOUND,
    UNEXPECTED_ERROR,
    USER_CREATED,
    EMAIL_CHANGED_SUCESSFULL,
    PASSWORD_CHANGED_SUCESSFULL,
    RESULT_FOUND,
    RESULT_NOT_FOUND,
    SAME_PASSWORD,
    INVALID_ACCOUNT_TYPE;
}
