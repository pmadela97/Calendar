package pawelmadela.calendar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pawelmadela.calendar.controller.Request.UserRequest;
import pawelmadela.calendar.enums.AccountStatus;
import pawelmadela.calendar.enums.UserServiceResponseStatus;
import pawelmadela.calendar.model.User;
import pawelmadela.calendar.services.ServiceResponse;
import pawelmadela.calendar.services.UserServiceImp;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

UserServiceImp userServiceImp;

@Autowired
AdminController(UserServiceImp userServiceImp){
this.userServiceImp = userServiceImp;
}


    // Show User data
    @GetMapping("/users/{username}")
    public ResponseEntity<?> showUserDetails(@PathVariable String username){
    ServiceResponse<UserServiceResponseStatus,User> serviceResponse = userServiceImp.showUserDataByUsername(username);
    UserServiceResponseStatus status = serviceResponse.getResponseStatus();
    if(status!= null){
        switch (serviceResponse.getResponseStatus()){
            case USER_FOUND:
                return ResponseEntity
                        .ok(new ServiceResponse(serviceResponse.getResponseStatus(),serviceResponse.getResponseObject().getUsername()));
            case USERNAME_NOT_FOUND:
                return ResponseEntity
                        .internalServerError()
                        .body(new ServiceResponse(serviceResponse.getResponseStatus(),username));
            default:
                return ResponseEntity.internalServerError().build();
        }
    }else return ResponseEntity.internalServerError().build();
    }

    @GetMapping("/users/")
    public ResponseEntity<?> showUsers(@RequestParam(required = false) boolean isActive,@RequestParam(required = false) boolean isBlocked, @RequestParam(required = false) boolean isExpired ){
        ServiceResponse<UserServiceResponseStatus, List<User>> serviceResponse = userServiceImp.showAllUsers();
        if(serviceResponse.getResponseStatus() == UserServiceResponseStatus.RESULT_NOT_FOUND)
            return ResponseEntity.internalServerError().body(serviceResponse);
        System.err.println(isBlocked);
        List<User> list = serviceResponse.getResponseObject();
                if(isActive){
                    list =list
                            .stream()
                            .filter((User u)-> u.getAccountStatus().equals(AccountStatus.ACTIVE))
                            .collect(Collectors.toList());
                }
                else if(isBlocked){
                    list =list
                            .stream()
                            .filter((User u)-> u.getAccountStatus()== AccountStatus.BLOCKED)
                            .collect(Collectors.toList());}
                else if(isExpired){
                     list= list
                            .stream()
                            .filter((User u)-> u.getAccountStatus()== AccountStatus.EXPIRED)
                            .collect(Collectors.toList());}
        return ResponseEntity.ok(new ServiceResponse(serviceResponse.getResponseStatus(),list));
}
    //Add new user
    @PostMapping("/users/new")
      public ResponseEntity<?> createNewUser(@RequestBody UserRequest request){
        if(request.isNull()) return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        ServiceResponse<UserServiceResponseStatus,User> serviceResponse = userServiceImp.createUser(request);
        UserServiceResponseStatus status = serviceResponse.getResponseStatus();
        switch (status){
            case USER_CREATED:
                return ResponseEntity
                        .ok(new ServiceResponse(serviceResponse.getResponseStatus(),serviceResponse.getResponseObject().getUsername()));
            case EMAIL_EXISTS:
                return ResponseEntity
                        .internalServerError()
                        .body(new ServiceResponse(serviceResponse.getResponseStatus(),request.getEmailAddress()));
            case USERNAME_EXISTS:
                return ResponseEntity
                        .internalServerError()
                        .body(new ServiceResponse(serviceResponse.getResponseStatus(),request.getUsername()));
            default:
                return ResponseEntity.internalServerError().build();

        }
        }


    //edit user password
    @PostMapping("/users/{username}/edit/password")
    public ResponseEntity<?> editUserPassword(@PathVariable String username,@RequestBody String password){
    if(username==null || username.equals("")) return ResponseEntity.notFound().build();
    if(userServiceImp.loadUserByUsername(username).getPassword().equals(password))
        return ResponseEntity.internalServerError()
                .body(new ServiceResponse(UserServiceResponseStatus.SAME_PASSWORD,null));
    return null;

}

    //edit user email

    //delete user

    //set user status

    //delete user task

    //show list of users

    //show admin data

    //edit admin password

    //edit admin email




}
