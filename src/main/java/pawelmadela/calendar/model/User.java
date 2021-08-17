package pawelmadela.calendar.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pawelmadela.calendar.enums.AccountStatus;

import javax.persistence.*;
import java.util.Collection;

@Entity(name = "USER")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String username;
    String firstname;
    String lastname;
    char[] password;
    String emailAddress;
    AccountStatus accountStatus;



    public User() { };

    public User(Long id, String username, String firstname, String lastname, char[] password, String emailAddress) {
        this.id = id;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.emailAddress = emailAddress;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        if(accountStatus!=null && accountStatus == AccountStatus.EXPIERED){
            return false;
        }
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        if(accountStatus!=null && accountStatus == AccountStatus.BLOCKED){
            return false;
        }
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        if(accountStatus!=null && accountStatus == AccountStatus.ACTIVE){
            return true;
        }
        return false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public AccountStatus isAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public char[] getPasswordAsChar() {
        return password;
    }

    public String getPassword() {
        return password.toString();
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setId(Long id) {
        this.id = id;
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
