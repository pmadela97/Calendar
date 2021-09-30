
package pawelmadela.calendar.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pawelmadela.calendar.enums.AccountStatus;
import pawelmadela.calendar.enums.AccountType;
import pawelmadela.calendar.enums.Authority;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

import static pawelmadela.calendar.enums.AccountStatus.*;
import static pawelmadela.calendar.enums.AccountType.*;

/**
 * A <code>User</code> class is object wrapper for a database table.
 * Class provides common getters and setters methods except setters for
 *<code>username</code> field and <code>accountType</code> because they are
 * immu. No one can't change this fields. Class implements also <code>UserDetails</code>
 * interface, to allow interface <code>UserDetailsService</code> fetch <code>username</code> and
 * <code>password</code>fields values.
 * @author Paweł Madeła
 * @version 1.0
 *
 *
 */
@Entity
@Table(name = "users", schema = "calendar")
public class User implements UserDetails {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long userId;
    String username;
    String firstname;
    String lastname;
    @JsonIgnore
    char[] password;
    @JsonIgnore
    String emailAddress;
    @Enumerated(EnumType.STRING)
    AccountStatus accountStatus;
    @Enumerated(EnumType.STRING)
    AccountType accountType;

    /**
     * Simple constructor.
     */
    public User() {}

    /**
         * Constructor with params, necessary to create object of <code>User</code> class;
     * @param username Name of user's account. It can consist of normal letters and numbers. Min length is 8 characters.
     * @param firstname User's firstname, consists from letters and numbers. Max length is 32 characters.
     * @param lastname User's lastname, consists from letters and numbers. Max length is 64 characters.
     * @param password User's password, encoded, max length 256 characters.
     * @param emailAddress User's email address, max length 32 characters, must to be valid with patter "-----@------.--".
     * @param accountStatus User's account stat, represents types of account status.
     *
     * @param accountType User's account type, enumeration type represents account authority, and accessibility to api. Consist of two values
     *                      <code>USER</code> and <code>ADMIN</code>.
     */
    public User(String username, String firstname, String lastname, char[] password, String emailAddress, AccountStatus accountStatus, AccountType accountType) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.emailAddress = emailAddress;
        this.accountStatus = accountStatus;
        this.accountType = accountType;
    }

    /**
     *Gets account type;
     * @return accountType as AccountType enumeration class
     */
    public AccountType getAccountType() {
        return accountType;
    }
    /**
     *Gets userid;
     * @return userId as long
     */
    public long getUserId() {
        return userId;
    }
    /**
     *Gets username;
     * @return username as String
     */
    public String getUsername() {
        return username;
    }

    /**
     *Indicates whether the user's account has expired.
     * Method is not taken into during <code>JSON</code>file creation
     * @return true if accountStatus != EXPIRED
     */
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        if(accountStatus == null) return false;
        return accountStatus != EXPIRED;
    }
    /**
     *Indicates whether the user's is locked or unlocked.
     * Method is not taken into during <code>JSON</code>file creation
     * @return true if accountStatus != BLOCKED
     */
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        if(accountStatus == null) return false;
        return accountStatus != BLOCKED;
    }
    /**
     *Indicates whether the user's credentials (password) has expired.
     * Method is not taken into during <code>JSON</code>file creation
     * @return true
     */
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     *Tests if account isn't locked;
     * Method is not taken into during <code>JSON</code>file creation
     * @return true if accountStatus != ACTIVE
     */
    @JsonIgnore
    @Override
    public boolean isEnabled() {
        if(accountStatus == null) return false;
        return accountStatus == ACTIVE;
    }

    /**
     *Returns user's authorities depends on accountType field value
     * Method is not taken into during <code>JSON</code>file creation
     * @return list of objects which extends GrantedAuthority class.
     */
    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        switch (accountType) {
            case USER:
                return List.of(Authority.USER_WRITE, Authority.USER_READ, Authority.USER_DELETE);
            case ADMIN:
                return List.of(Authority.ADMIN_WRITE, Authority.ADMIN_READ, Authority.ADMIN_DELETE);
            default:
                return null;
        }
    }
    /**
     *Gets firstname;
     * @return firstname as  String
     */
    public String getFirstname() {
        return firstname;
    }
    /**
     *Gets lastname;
     * @return lastname as  String
     */
    public String getLastname() {
        return lastname;
    }
    /**
     *Gets accountStatus;
     * @return accountStatus as AccountStatus, <code>ACTIVE, EXPIRED, BLOCKED</code>
     */
    public AccountStatus getAccountStatus() {
        return accountStatus;
    }
    /**
     *Sets accountStatus;
     * @param accountStatus Param witch sets <code>accountStatus</code> field
     */
    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    /**
     *Returns user's password as char table;
     * Method is not taken into during <code>JSON</code>file creation
     * @return return char[]
     */
    @JsonIgnore
    public char[] getPasswordAsChar() {
        return password;
    }

    /**
     *Returns user's password as String;
     * Method is not taken into during <code>JSON</code>file creation
     * @return return String
     */
    @JsonIgnore
    public String getPassword() {

        return String.valueOf(password);
    }

    /**
     *Gets email address;
     * @return return String
     */
    public String getEmailAddress() {
        return emailAddress;
    }
    /**
     *Sets firstname;
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     *Sets lastname;
     * @param lastname Param witch sets <code>lastname</code> field
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    /**
     *Sets password;
     * @param password Param witch sets <code>password</code> field
     */
    public void setPassword(char[] password) {
        this.password = password;
    }

    /**
     *Sets emailAddress;
     * @param emailAddress Param witch sets <code>emailAddress</code> field
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }


}
