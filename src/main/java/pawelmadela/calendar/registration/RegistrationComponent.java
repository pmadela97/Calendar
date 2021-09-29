package pawelmadela.calendar.registration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import pawelmadela.calendar.enums.UserServiceResponseStatus;
import pawelmadela.calendar.model.User;
import pawelmadela.calendar.services.ServiceResponse;
import pawelmadela.calendar.services.UserServiceImp;

import javax.xml.bind.DatatypeConverter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static pawelmadela.calendar.enums.UserServiceResponseStatus.*;

@Component
public class RegistrationComponent {

    private final String secretKey = "secretKey2";
    private final long  jwtExpirationTime = System.currentTimeMillis()+ 60 * 20 * 1000;

    private Claims getClaimFromToken(String token){
        return Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
                .parseClaimsJws(token).getBody();

    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token).getExpiration();
    }

    private boolean isTokenExpired(String token){
        final Date expDate = getExpirationDateFromToken(token);
        return expDate.before(new Date());
    }

    public String generateToken(UserDetails userDetails){
        Map<String,Object> claims = new HashMap<>();
        return doGenerateToken(claims,userDetails.getUsername());
    }
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(jwtExpirationTime))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    //validate token
    public Boolean validateToken(String token, String userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails) && !isTokenExpired(token));
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token).getSubject();
    }

}
