package pawelmadela.calendar.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pawelmadela.calendar.controllers.Request.UserRequest;
import pawelmadela.calendar.enums.AccountStatus;
import pawelmadela.calendar.enums.UserServiceResponseStatus;
import pawelmadela.calendar.registration.RegistrationComponent;
import pawelmadela.calendar.services.EmailServiceImp;
import pawelmadela.calendar.services.UserServiceImp;

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
        if (request.isNull()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        UserServiceResponseStatus serviceResponse = userServiceImp.createUser(request,"USER",AccountStatus.UNCONFIRMED);
        if (serviceResponse == USER_CREATED) {
            String token = registrationComponent.generateToken(userServiceImp.getUserByUsername(request.getUsername()).getResponseObject().getUsername());
            emailServiceImp.sendMessage(request.getEmailAddress(),"Rejestracja w serwisie Calendar","Kliknij w ten link, aby dokonać rejestracji:" +
                    "http://localhost:8080/registration/activate/"+token);
            return ResponseEntity.ok().build();
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
                && userServiceImp.getUserByUsername(username).getResponseObject().getAccountStatus() == (AccountStatus.UNCONFIRMED)){
            userServiceImp.editUserStatus(username,"ACTIVE");
         return    ResponseEntity.ok(REGISTRATION_SUCCESSFULL);
        }
        return ResponseEntity.badRequest().build();


    }


}
