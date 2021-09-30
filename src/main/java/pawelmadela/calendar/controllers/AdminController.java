package pawelmadela.calendar.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pawelmadela.calendar.controllers.Request.UserRequest;
import pawelmadela.calendar.enums.AccountStatus;
import pawelmadela.calendar.enums.UserServiceResponseStatus;
import pawelmadela.calendar.model.User;
import pawelmadela.calendar.model.dto.ObjcetMapper;
import pawelmadela.calendar.model.dto.UserDto;
import pawelmadela.calendar.services.ServiceResponse;
import pawelmadela.calendar.services.UserServiceImp;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static pawelmadela.calendar.enums.UserServiceResponseStatus.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    UserServiceImp userServiceImp;

    @Autowired
    AdminController(UserServiceImp userServiceImp) {
        this.userServiceImp = userServiceImp;
    }


    // Show User data
    @GetMapping("/users/{username}")
    public ResponseEntity<?> showUserDetails(@PathVariable String username) {
        ServiceResponse<UserServiceResponseStatus, UserDto> serviceResponse = userServiceImp.getUserDtoByUsername(username);
        UserServiceResponseStatus status = serviceResponse.getResponseStatus();
        if (status != null) {
            switch (serviceResponse.getResponseStatus()) {
                case USERNAME_FOUND:
                    return ResponseEntity
                            .ok(new ServiceResponse(serviceResponse.getResponseStatus(), serviceResponse.getResponseObject()));
                case USERNAME_NOT_FOUND:
                    return ResponseEntity
                            .internalServerError()
                            .body(new ServiceResponse(serviceResponse.getResponseStatus(), username));
                default:
                    return ResponseEntity.internalServerError().body(serviceResponse.getResponseStatus());
            }
        } else return ResponseEntity.internalServerError().build();
    }

    @GetMapping("/users")
    public ResponseEntity<?> showUsers(@RequestParam(required = false) boolean isActive, @RequestParam(required = false) boolean isBlocked, @RequestParam(required = false) boolean isExpired,@RequestParam(required = false) boolean isUnconfirmed) {
        ServiceResponse<UserServiceResponseStatus, List<User>> serviceResponse = userServiceImp.getAllUsers();
        List<User> responseList = new ArrayList<>();
        if (serviceResponse.getResponseStatus() == RESULT_NOT_FOUND)
            return ResponseEntity.badRequest().body(serviceResponse);
        if (serviceResponse.getResponseStatus() == SERVER_CONNECTION_FAILURE)
            return ResponseEntity.internalServerError().body(serviceResponse.getResponseStatus());
        List<User> list = serviceResponse.getResponseObject();
        if (isActive) {
            responseList.addAll(list
                    .stream()
                    .filter((User u) -> u.getAccountStatus().equals(AccountStatus.ACTIVE))
                    .collect(Collectors.toList()));
        }if (isBlocked) {
            responseList.addAll(list
                    .stream()
                    .filter((User u) -> u.getAccountStatus() == AccountStatus.BLOCKED)
                    .collect(Collectors.toList()));
        }if (isUnconfirmed) {
            responseList.addAll(list
                    .stream()
                    .filter((User u) -> u.getAccountStatus() == AccountStatus.UNCONFIRMED)
                    .collect(Collectors.toList()));
        }if (isExpired) {
            responseList.addAll(list
                    .stream()
                    .filter((User u) -> u.getAccountStatus() == AccountStatus.EXPIRED)
                    .collect(Collectors.toList()));
        }
        return ResponseEntity.ok(new ServiceResponse(serviceResponse.getResponseStatus(), ObjcetMapper.map(responseList)));
    }

    //Add new user
    @PostMapping("/users/new")
    public ResponseEntity<?> createNewUser(@RequestBody UserRequest request) {
        if (request.isNull()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        UserServiceResponseStatus serviceResponseStatus = userServiceImp.createUser(request,"USER",AccountStatus.ACTIVE);
        switch (serviceResponseStatus){
            case USER_CREATED:
                return ResponseEntity.ok().build();
            case SERVER_CONNECTION_FAILURE:
                return ResponseEntity.internalServerError().body(serviceResponseStatus);
            default:
                return  ResponseEntity.badRequest().body(serviceResponseStatus);
        }
    }
    @PostMapping("/users/newadmin")
    public ResponseEntity<?> createNewAdmin(@RequestBody UserRequest request) {
        if (request.isNull()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        UserServiceResponseStatus serviceResponseStatus = userServiceImp.createUser(request,"ADMIN",AccountStatus.ACTIVE);
        switch (serviceResponseStatus){
            case USER_CREATED:
                return ResponseEntity.ok().build();
            case SERVER_CONNECTION_FAILURE:
                return ResponseEntity.internalServerError().body(serviceResponseStatus);
            default:
                return  ResponseEntity.badRequest().body(serviceResponseStatus);

        }


    }


    //edit user password
    @PostMapping("/users/{username}/edit/password")
    public ResponseEntity<?> editUserPassword(@PathVariable String username, @RequestBody String password) {
        if (username == null || password == null || username.equals("") || password.equals(""))
            return ResponseEntity.notFound().build();
        UserServiceResponseStatus responseStatus = userServiceImp.editUserPassword(username, password);
        switch (responseStatus) {
            case NULL_CREDENTIALS:
                return ResponseEntity.badRequest().build();
            case SAME_PASSWORD:
            case USERNAME_NOT_FOUND:
                return ResponseEntity.badRequest().body(responseStatus);
            case SERVER_CONNECTION_FAILURE:
                return ResponseEntity.internalServerError().body(responseStatus);
            case PASSWORD_CHANGED_SUCESSFULL:
                return ResponseEntity.ok(responseStatus);
            default:
                return ResponseEntity.badRequest().body(UNEXPECTED_ERROR);
        }

    }

    @PostMapping("/users/{username}/edit/email")
    public ResponseEntity<?> editUserEmail(@PathVariable String username, @RequestBody String email) {
        if (username == null || email == null || username.equals("") || email.equals(""))
            return ResponseEntity.notFound().build();
        UserServiceResponseStatus responseStatus = userServiceImp.editUserEmail(username, email);
        switch (responseStatus) {
            case NULL_CREDENTIALS:
                return ResponseEntity.badRequest().build();
            case SAME_EMAIL:
            case USERNAME_NOT_FOUND:
                return ResponseEntity.badRequest().body(responseStatus);
            case SERVER_CONNECTION_FAILURE:
                return ResponseEntity.internalServerError().body(responseStatus);
            case EMAIL_CHANGED_SUCCESSFULL:
                return ResponseEntity.ok(responseStatus);
            default:
                return ResponseEntity.badRequest().body(UNEXPECTED_ERROR);
        }
    }

    @PostMapping("/users/{username}/edit/status/{status}")
    public ResponseEntity<?> editUserStatus(@PathVariable String username, @PathVariable String status) {
        if (username == null || status == null || username.equals("") || status.equals(""))
            return ResponseEntity.notFound().build();
        UserServiceResponseStatus responseStatus = userServiceImp.editUserStatus(username, status);
        switch (responseStatus) {
            case NULL_CREDENTIALS:
                return ResponseEntity.badRequest().build();
            case USERNAME_NOT_FOUND:
                return ResponseEntity.badRequest().body(responseStatus);
            case SERVER_CONNECTION_FAILURE:
                return ResponseEntity.internalServerError().body(responseStatus);
            case STATUS_CHANGED_SUCCESFULL:
                return ResponseEntity.ok(responseStatus);
            default:
                return ResponseEntity.badRequest().body(UNEXPECTED_ERROR);
        }
    }

    @PostMapping("/users/{username}/delete")
    public ResponseEntity<?> deleteUser(@PathVariable String username) {
        if (username == null || username.equals("")) return ResponseEntity.notFound().build();
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