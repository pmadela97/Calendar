package pawelmadela.calendar.services;

import pawelmadela.calendar.controllers.Request.UserRequest;
import pawelmadela.calendar.enums.AccountStatus;
import pawelmadela.calendar.enums.UserServiceResponseStatus;
import pawelmadela.calendar.model.User;

import java.util.List;

/**
 * Interface that provides all necessary methods to manage users information. It returns two types of results.
 * First is <code>UserServiceResponseStatus</code> that returns service method status.
 * Second is <code>ServiceResponse<E,T></code> that return status and service result object.
 */
public interface UserService{
    public ServiceResponse<UserServiceResponseStatus,User> showUserByUsername(String username);
    public UserServiceResponseStatus editUserPassword(String username, String password);
    public UserServiceResponseStatus editUserEmail(String username, String password);
    public UserServiceResponseStatus editUserStatus(String username,String status);
    public UserServiceResponseStatus deleteUserByUsername(String username);
    public UserServiceResponseStatus createUser(UserRequest userRequest, String accountType, AccountStatus accountStatus);
    public ServiceResponse<UserServiceResponseStatus, List<User>> showAllUsers();


}
