package pawelmadela.calendar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pawelmadela.calendar.controllers.Request.UserRequest;
import pawelmadela.calendar.enums.AccountType;
import pawelmadela.calendar.enums.UserServiceResponseStatus;
import pawelmadela.calendar.model.User;
import pawelmadela.calendar.repo.UserRepository;

import java.util.List;

import static pawelmadela.calendar.enums.AccountStatus.*;


/**
 * Service class that implements UserService interface to manage users informations
 */
@Service
public class UserServiceImp implements UserService, UserDetailsService {

    //Reference for repository, which provides access to database
    UserRepository userRepository;

    @Autowired
    UserServiceImp(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public ServiceResponse<UserServiceResponseStatus,User> showUserDataById(long userId){
        User user = userRepository.getUserByUserId(userId);
        if(user == null) return new ServiceResponse(UserServiceResponseStatus.USERID_NOT_FOUND,null);
        return new ServiceResponse(UserServiceResponseStatus.RESULT_FOUND,null);
    }

    @Override
    public ServiceResponse<UserServiceResponseStatus,User> showUserDataByUsername(String username) {
        User user = userRepository.getUserByUsername(username);
        if(user == null) return new ServiceResponse(UserServiceResponseStatus.USERNAME_NOT_FOUND,null);
        return new ServiceResponse(UserServiceResponseStatus.RESULT_FOUND,user);
    }

    @Override
    public ServiceResponse<UserServiceResponseStatus,User> showUserDataByEmail(String email) {
        User user = userRepository.getUserByEmailAddress(email);
        if(user == null) return new ServiceResponse(UserServiceResponseStatus.EMAIL_NOT_FOUND,null);
        return new ServiceResponse(UserServiceResponseStatus.RESULT_FOUND,user);
    }

    @Override
    public ServiceResponse<UserServiceResponseStatus,User> editUserPassword(String username, String password) {
        if(username == null || username.equals("") || password== null || password.equals(""))
            return new ServiceResponse<>(UserServiceResponseStatus.UNEXPECTED_ERROR,null);
        User user = userRepository.getUserByUsername(username);
        if(user==null)
            return new ServiceResponse<>(UserServiceResponseStatus.USERNAME_NOT_FOUND,null);
        char[] passwordEncrypted = password.toCharArray();
        if(passwordEncrypted.equals(userRepository.getUserByUsername(username).getPassword()))
            return new ServiceResponse<>(UserServiceResponseStatus.SAME_PASSWORD,null);
        //password validaion skiped
        user.setPassword(passwordEncrypted);
        try {
            userRepository.save(user);
        }catch (Exception e){
            return new ServiceResponse(UserServiceResponseStatus.UNEXPECTED_ERROR,null);
        }
        return new ServiceResponse(UserServiceResponseStatus.PASSWORD_CHANGED_SUCESSFULL,user.getUsername());

    }


    @Override
    public ServiceResponse deleteUserDataById(long userId) {
        return null;
    }

    @Override
    public ServiceResponse deleteUserDataByUsername(String username) {
        return null;
    }

    @Override
    public ServiceResponse deleteUserDataByEmail(String email) {
        return null;
    }
    @Override
    public ServiceResponse<UserServiceResponseStatus,User> createUser(UserRequest userRequest) {
        if (userRequest == null || userRequest.isNull())
            return new ServiceResponse(UserServiceResponseStatus.UNEXPECTED_ERROR, null);
        else if (userRepository.getUserByUsername(userRequest.getUsername()) != null)
            return new ServiceResponse(UserServiceResponseStatus.USERNAME_EXISTS, null);
        else if (userRepository.getUserByEmailAddress(userRequest.getEmailAddress()) != null)
            return new ServiceResponse(UserServiceResponseStatus.EMAIL_EXISTS, null);
        AccountType type = AccountType.valueOf(userRequest.getAccountType());
        if(type == null){
            return new ServiceResponse(UserServiceResponseStatus.INVALID_ACCOUNT_TYPE,null);
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        char[] encodedPassword = passwordEncoder.encode(userRequest.getPassword()).toCharArray();
        User user = new User(   userRequest.getUsername(),
                                userRequest.getFirstname(),
                                userRequest.getLastname(),
                                encodedPassword,
                                userRequest.getEmailAddress(),
                                ACTIVE,
                                type
        );
        try {
            userRepository.save(user);
        }catch (Exception e){
            return new ServiceResponse(UserServiceResponseStatus.UNEXPECTED_ERROR,null);
        }
        return new ServiceResponse(UserServiceResponseStatus.USER_CREATED,userRepository.getUserByUsername(user.getUsername()));
    }

    @Override
    public ServiceResponse<UserServiceResponseStatus, List<User>> showAllUsers() {
        List<User> list = userRepository.getAll();
        if(list== null) return new ServiceResponse(UserServiceResponseStatus.RESULT_NOT_FOUND,null);
        else return new ServiceResponse(UserServiceResponseStatus.RESULT_FOUND,list);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return (UserDetails) showUserDataByUsername(s).getResponseObject();
    }
}
