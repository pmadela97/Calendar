package pawelmadela.calendar.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pawelmadela.calendar.auth.jwt.JwtRequest;
import pawelmadela.calendar.auth.jwt.JwtResponse;
import pawelmadela.calendar.auth.jwt.JwtTokenComponent;
import pawelmadela.calendar.enums.AccountType;
import pawelmadela.calendar.model.User;
import pawelmadela.calendar.services.UserServiceImp;

@RestController
@RequestMapping("/auth")
public class JwtAuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenComponent jwtTokenComponent;

    @Autowired
    private UserServiceImp userServiceImp;


    @PostMapping("")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) throws Exception{
        authenticate(authRequest.getUsername(), authRequest.getPassword());
        final User user = (User) userServiceImp.showUserDataByUsername(authRequest.getUsername()).getResponseObject();
        final String token = jwtTokenComponent.generateToken(user);
        final JwtResponse response = new JwtResponse(token,user.getUsername(),jwtTokenComponent.getExpirationDateFromToken(token),user.getFirstname(),user.getLastname());
        System.out.println("SEND AUTH TOKEN");
        System.err.println(response);
        return ResponseEntity.ok(response);
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
