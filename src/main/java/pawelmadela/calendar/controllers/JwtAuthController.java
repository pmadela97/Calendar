package pawelmadela.calendar.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import pawelmadela.calendar.auth.jwt.JwtRequest;
import pawelmadela.calendar.auth.jwt.JwtResponse;
import pawelmadela.calendar.auth.jwt.JwtTokenComponent;
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
        final User user = (User) userServiceImp.getUserByUsername(authRequest.getUsername()).getResponseObject();
        final String token = jwtTokenComponent.generateToken(user);
        final JwtResponse response = new JwtResponse(token,user.getFirstname(),user.getLastname(),user.getAccountType().name());
        System.out.println("SEND AUTH TOKEN");
        return ResponseEntity.ok(response);
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            System.out.println("USER_DISABLED");
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            System.out.println("INVALID_CREDENTIALS");
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}

