package pawelmadela.calendar.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pawelmadela.calendar.auth.jwt.JwtRequest;
import pawelmadela.calendar.auth.jwt.JwtResponse;
import pawelmadela.calendar.controllers.Request.UserRequest;
import pawelmadela.calendar.enums.AccountStatus;
import pawelmadela.calendar.enums.UserServiceResponseStatus;
import pawelmadela.calendar.model.User;
import pawelmadela.calendar.registration.RegistrationComponent;
import pawelmadela.calendar.registration.RegistrationService;
import pawelmadela.calendar.services.EmailServiceImp;
import pawelmadela.calendar.services.UserServiceImp;

import javax.imageio.spi.RegisterableService;
import java.net.URI;

import static pawelmadela.calendar.enums.UserServiceResponseStatus.*;

@RestController
@RequestMapping("/registration")
public class JwtRegistrationController {

RegistrationComponent registrationComponent;
UserServiceImp userServiceImp;
EmailServiceImp emailServiceImp;

@Autowired
JwtRegistrationController(RegistrationComponent registrationComponent,UserServiceImp userServiceImp,EmailServiceImp emailServiceImp){
    this.registrationComponent = registrationComponent;
    this.userServiceImp = userServiceImp;
    this.emailServiceImp = emailServiceImp;
}

    @PostMapping("")
    public ResponseEntity<?> createRegistrationToken(@RequestBody UserRequest request) throws Exception{
        if (request.isNull()) return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        UserServiceResponseStatus serviceResponse = userServiceImp.createUser(request,"USER",AccountStatus.NOT_CONFIRMED);
        if (serviceResponse == USER_CREATED) {
            String token = registrationComponent.generateToken(userServiceImp.showUserByUsername(request.getUsername()).getResponseObject());
            emailServiceImp.sendMessage(request.getEmailAddress(),"Rejestracja w serwisie Calendar","Kliknij w ten link, aby dokonać rejestracji:" +
                    "http://localhost:8080/registration/activate/"+token);
            return ResponseEntity.ok(token);
        }
        else {
            if (serviceResponse == SERVER_CONNECTION_FAILURE) return ResponseEntity.internalServerError().build();
            return ResponseEntity.internalServerError().body(serviceResponse);
        }

    }
    @GetMapping("/activate/{token}")
    public ResponseEntity<?> authenticateToken(@PathVariable(required = true) String token) throws Exception{
        String username = registrationComponent.getUsernameFromToken(token);
        if (token == null || token.equals("")) return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        if(registrationComponent.validateToken(token,username )
                && userServiceImp.showUserByUsername(username).getResponseObject().getAccountStatus() == AccountStatus.NOT_CONFIRMED){
            userServiceImp.editUserStatus(username,"ACTIVE");
         return    ResponseEntity.ok(REGISTRATION_SUCCESSFULL);
        }
        return ResponseEntity.badRequest().build();


    }


}
