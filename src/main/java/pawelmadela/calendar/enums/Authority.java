package pawelmadela.calendar.enums;

import org.springframework.security.core.GrantedAuthority;

/**
 * Represents type for authorities in app, and provides access to GrantedAuthority interface
 */
public enum Authority implements GrantedAuthority {
    USER_READ,
    USER_WRITE,
    USER_DELETE,
    ADMIN_READ,
    ADMIN_WRITE,
    ADMIN_DELETE;



    @Override
    public String getAuthority() {
        return "ROLE_" + this.name();
    }
}
