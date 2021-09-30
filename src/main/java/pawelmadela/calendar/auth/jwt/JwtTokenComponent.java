package pawelmadela.calendar.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import pawelmadela.calendar.enums.Authority;
import pawelmadela.calendar.services.UserServiceImp;

import javax.xml.bind.DatatypeConverter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JwtTokenComponent {

    private final String secretKey = "secretKey";
    private final long  jwtExpirationTime = 60 * 20 * 1000;

    @Autowired
    private UserServiceImp userServiceImp;



    private Claims getClaimFromToken(String token){
        return Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
                .parseClaimsJws(token).getBody();

    }

    public String getUsernameFromToken(String token){
        return getClaimFromToken(token).getSubject();
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
         List userList = List.of(Authority.USER_WRITE,Authority.USER_READ,Authority.USER_DELETE);
         List adminList = List.of(Authority.ADMIN_WRITE,Authority.ADMIN_READ,Authority.ADMIN_DELETE);
         if(userDetails.getAuthorities().equals(userList) ){
             claims.put("type","USER");

         }else if(userDetails.getAuthorities().equals(adminList)){
             claims.put("type","ADMIN");

         }
         long userId= userServiceImp.getUserByUsername(userDetails.getUsername()).getResponseObject().getUserId();
         claims.put("userId",userId);
        return doGenerateToken(claims,userDetails.getUsername());
    }
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(jwtExpirationTime + System.currentTimeMillis()+20000))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    //validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
