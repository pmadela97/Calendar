package pawelmadela.calendar.services;

import pawelmadela.calendar.controllers.Request.UserRequest;
import pawelmadela.calendar.enums.UserServiceResponseStatus;
import pawelmadela.calendar.model.User;

import java.util.List;

/**
 * Interface which provides all necessary methods to manage users information
 */
public interface UserService {
    public ServiceResponse<UserServiceResponseStatus,User> showUserDataById(long userId);
    public ServiceResponse<UserServiceResponseStatus,User> showUserDataByUsername(String username);
    public ServiceResponse showUserDataByEmail(String email);
    public ServiceResponse<UserServiceResponseStatus,User> editUserPassword(String username, String password);
    public ServiceResponse deleteUserDataById(long userId);
    public ServiceResponse deleteUserDataByUsername(String username);
    public ServiceResponse deleteUserDataByEmail(String email);
    public ServiceResponse<UserServiceResponseStatus,User> createUser(UserRequest userRequest);
    public ServiceResponse<UserServiceResponseStatus, List<User>> showAllUsers();


}
