package pawelmadela.calendar.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pawelmadela.calendar.controllers.Request.PasswordRequest;
import pawelmadela.calendar.enums.UserServiceResponseStatus;
import pawelmadela.calendar.model.dto.UserDto;
import pawelmadela.calendar.services.ServiceResponse;
import pawelmadela.calendar.services.UserServiceImp;

import java.security.Principal;

import static pawelmadela.calendar.enums.UserServiceResponseStatus.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    UserServiceImp userServiceImp;

    @Autowired
    UserController(UserServiceImp userServiceImp) {
        this.userServiceImp = userServiceImp;
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> showUserDetails(@PathVariable String username, Principal principal) {
        if(username.equals("")|| username == null || !principal.getName().equals(username)) return ResponseEntity.badRequest().build();
        ServiceResponse<UserServiceResponseStatus, UserDto> serviceResponse = userServiceImp.getUserDtoByUsername(username);
        UserServiceResponseStatus status = serviceResponse.getResponseStatus();
        if (status != null) {
            switch (serviceResponse.getResponseStatus()) {
                case USERNAME_FOUND:
                    return ResponseEntity
                            .ok(new ServiceResponse(serviceResponse.getResponseStatus(), serviceResponse.getResponseObject()));
                case USERNAME_NOT_FOUND:
                    return ResponseEntity
                            .badRequest().body(status);
                default:
                    return ResponseEntity.internalServerError().body(serviceResponse.getResponseStatus());
            }
        } else return ResponseEntity.internalServerError().build();
    }

    @PostMapping("/{username}/edit/password")
    public ResponseEntity<?> editUserPassword(@PathVariable String username, @RequestBody PasswordRequest request, Principal principal) {
        if (username == null || request == null || username.equals("") || !username.equals(principal.getName())) return ResponseEntity.badRequest().build();
        UserServiceResponseStatus responseStatus = userServiceImp.editPassword(username, request.getOldPassword(),request.getNewPassword());
        switch (responseStatus) {
            case PASSWORD_INCORRECT:
                return ResponseEntity.badRequest().build();
            case PASSWORD_CHANGED_SUCESSFULL:
                return ResponseEntity.ok().build();
            default:
                return ResponseEntity.badRequest().body(UNEXPECTED_ERROR);
        }

    }

    @PostMapping("/{username}/edit/email")
    public ResponseEntity<?> editUserEmail(@PathVariable String username, @RequestBody String email, Principal principal) {
        if (username == null || email == null || username.equals("") || email.equals("") || !principal.getName().equals(username))
            return ResponseEntity.badRequest().build();
        UserServiceResponseStatus responseStatus = userServiceImp.editUserEmail(username, email);
        switch (responseStatus) {
            case NULL_CREDENTIALS:
                return ResponseEntity.badRequest().build();
            case SAME_EMAIL:
            case USERNAME_NOT_FOUND:
                return ResponseEntity.badRequest().body(responseStatus);
            case SERVER_CONNECTION_FAILURE:
                return ResponseEntity.internalServerError().body(responseStatus);
            case EMAIL_CHANGED_SUCCESSFULL  :
                return ResponseEntity.ok(responseStatus);
            default:
                return ResponseEntity.badRequest().body(UNEXPECTED_ERROR);
        }
    }

    @PostMapping("/{username}/delete")
    public ResponseEntity<?> deleteUser(@PathVariable String username, Principal principal) {
        if (username == null || username.equals("")|| !principal.getName().equals(username)) return ResponseEntity.badRequest().build();
        UserServiceResponseStatus responseStatus = userServiceImp.deleteUserByUsername(username);
        switch (responseStatus) {
            case NULL_CREDENTIALS:
                return ResponseEntity.badRequest().build();
            case USERNAME_NOT_FOUND:
                return ResponseEntity.badRequest().body(responseStatus);
            case SERVER_CONNECTION_FAILURE:
                return ResponseEntity.internalServerError().body(responseStatus);
            case USER_DELETED:
                return ResponseEntity.ok(responseStatus);
            default:
                return ResponseEntity.badRequest().body(UNEXPECTED_ERROR);
        }
    }

}
