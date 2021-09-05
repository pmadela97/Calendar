
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

import static pawelmadela.calendar.enums.AccountType.*;

/**
 * Class that contains all information about user in app.
 */
@Entity
@Table(name = "USERS")
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

    public User() {}

    public User(String username, String firstname, String lastname, char[] password, String emailAddress, AccountStatus accountStatus, AccountType accountType) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.emailAddress = emailAddress;
        this.accountStatus = accountStatus;
        this.accountType = accountType;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        if(accountStatus == null) return false;
        return accountStatus != AccountStatus.EXPIRED;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        if(accountStatus == null) return false;
        return accountStatus != AccountStatus.BLOCKED;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    @JsonIgnore
    @Override
    public boolean isEnabled() {
        if(accountStatus == null) return false;
        return accountStatus == AccountStatus.ACTIVE;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(accountType == null) return null;
        if(accountType == USER){
            return List.of(Authority.USER_WRITE,Authority.USER_READ,Authority.USER_DELETE);
        }
        return List.of(Authority.ADMIN_WRITE,Authority.ADMIN_READ,Authority.ADMIN_DELETE);
    }
    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    @JsonIgnore
    public char[] getPasswordAsChar() {
        return password;
    }


    public String getPassword() {

        return String.valueOf(password);
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setUserId(Long id) {
        this.userId = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }


}
