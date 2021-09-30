package pawelmadela.calendar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pawelmadela.calendar.controllers.Request.UserRequest;
import pawelmadela.calendar.enums.AccountStatus;
import pawelmadela.calendar.enums.AccountType;
import pawelmadela.calendar.enums.UserServiceResponseStatus;
import pawelmadela.calendar.model.User;
import pawelmadela.calendar.model.dto.ObjcetMapper;
import pawelmadela.calendar.model.dto.UserDto;
import pawelmadela.calendar.repo.UserRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static pawelmadela.calendar.enums.AccountStatus.*;
import static pawelmadela.calendar.enums.UserServiceResponseStatus.*;


/**
 * Service class that implements UserService interface to manage users' data.
 */
@Service
public class UserServiceImp implements UserService, UserDetailsService {

    //Reference for repository, which provides access to database
    UserRepository userRepository;

    @Autowired
    UserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    Optional<User> userOptional;

    @Override
    public ServiceResponse<UserServiceResponseStatus, User> getUserByUsername(String username) {
        try {
            if (username == null || username.equals("")) return new ServiceResponse<>(NULL_CREDENTIALS, null);
            userOptional = Optional.ofNullable(userRepository.getUserByUsername(username));
            if (userOptional.isPresent()) return new ServiceResponse<>(USERNAME_FOUND, userOptional.get());
            else return new ServiceResponse<>(USERNAME_NOT_FOUND, null);
        } catch (DataAccessResourceFailureException e) {
            return new ServiceResponse<>(SERVER_CONNECTION_FAILURE, null);
        }
    }

    @Override
    public ServiceResponse<UserServiceResponseStatus, UserDto> getUserDtoByUsername(String username) {
        ServiceResponse<UserServiceResponseStatus, User> response = getUserByUsername(username);
        return new ServiceResponse<>(response.getResponseStatus(), ObjcetMapper.map((User) response.getResponseObject()));
    }

    @Override
    @Transactional
    public UserServiceResponseStatus editUserPassword(String username, String password) {
        try {
            if (username == null || username.equals("") || password == null || password.equals(""))
                return NULL_CREDENTIALS;
            userOptional = Optional.ofNullable(userRepository.getUserByUsername(username));
            if (userOptional.isPresent()) {
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String encodedPasword = passwordEncoder.encode(password);
                if (encodedPasword.equals(userOptional.get().getPassword())) return SAME_PASSWORD;
                userOptional.get().setPassword(encodedPasword.toCharArray());
                userRepository.save(userOptional.get());
                return PASSWORD_CHANGED_SUCESSFULL;
            } else {
                return USERNAME_NOT_FOUND;
            }
        } catch (DataAccessResourceFailureException e) {
            return SERVER_CONNECTION_FAILURE;
        }
    }

    @Override
    public UserServiceResponseStatus editUserEmail(String username, String email) {
        try {
            if (username == null || username.equals("") || email == null || email.equals(""))
                return NULL_CREDENTIALS;
            userOptional = Optional.ofNullable(userRepository.getUserByUsername(username));
            if (userOptional.isPresent()) {

                userOptional.get().setEmailAddress(email);
                userRepository.save(userOptional.get());
                return EMAIL_CHANGED_SUCCESSFULL;
            } else {
                return USERNAME_NOT_FOUND;
            }
        } catch (DataAccessResourceFailureException e) {
            return SERVER_CONNECTION_FAILURE;
        }
    }

    @Override
    @Transactional
    public UserServiceResponseStatus deleteUserByUsername(String username) {
        try {
            if (username == null || username.equals("")) return NULL_CREDENTIALS;
            userOptional = Optional.ofNullable(userRepository.getUserByUsername(username));
            if (userOptional.isPresent()) {
                userRepository.deleteUserByUsername(username);
                return USER_DELETED;
            } else {
                return USERNAME_NOT_FOUND;
            }
        } catch (DataAccessResourceFailureException e) {
            return SERVER_CONNECTION_FAILURE;
        }
    }

    @Transactional
    @Override
    public UserServiceResponseStatus createUser(UserRequest userRequest, String accountType, AccountStatus accountStatus) {
        try {
            if (userRequest == null || accountType == null || accountType.equals("") || userRequest.isNull())
                return NULL_CREDENTIALS;
            if (Optional.ofNullable(userRepository.getUserByUsername(userRequest.getUsername())).isPresent())
                return USERNAME_EXISTS;
            else if (Optional.ofNullable(userRepository.getUserByEmailAddress(userRequest.getEmailAddress())).isPresent())
                return EMAIL_EXISTS;

            AccountType type;
            switch (accountType) {
                case "USER":
                    type = AccountType.USER;
                    break;
                case "ADMIN":
                    type = AccountType.ADMIN;
                    break;
                default:
                    return INVALID_ACCOUNT_TYPE;
            }
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            char[] encodedPassword = passwordEncoder.encode(userRequest.getPassword()).toCharArray();
            User user = new User(userRequest.getUsername(),
                    userRequest.getFirstname(),
                    userRequest.getLastname(),
                    encodedPassword,
                    userRequest.getEmailAddress(),
                    accountStatus,
                    type
            );

            userRepository.save(user);
            return USER_CREATED;
        } catch (DataAccessResourceFailureException e) {
            return SERVER_CONNECTION_FAILURE;
        }
    }

    @Override
    public ServiceResponse<UserServiceResponseStatus, List<User>> getAllUsers() {
        try {
            Optional<List<User>> listOptional = Optional.ofNullable(userRepository.getAll());
            if (listOptional.isPresent()) return new ServiceResponse(RESULT_FOUND, listOptional.get());
            else return new ServiceResponse(RESULT_NOT_FOUND, null);
        } catch (DataAccessResourceFailureException e) {
            return new ServiceResponse<>(SERVER_CONNECTION_FAILURE, null);
        }
    }

    @Override
    public ServiceResponse<UserServiceResponseStatus, List<UserDto>> getAllUserDto() {
        ServiceResponse<?, ?> response = getAllUsers();
        return new ServiceResponse<>((UserServiceResponseStatus) response.getResponseStatus(), ObjcetMapper.map((List<User>) response.getResponseObject()));
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return (UserDetails) getUserByUsername(s).getResponseObject();
    }

    @Override
    public UserServiceResponseStatus editUserStatus(String username, String status) {
        if (username == null || status == null || username.equals("") || status.equals(""))
            return NULL_CREDENTIALS;
        if ((!status.equals(ACTIVE.name())) && (!status.equals(BLOCKED.name())) && (!status.equals(EXPIRED.name())))
            return WRONG_ACCOUNT_STATUS;
        try {
            Optional<User> userOptional = Optional.ofNullable(userRepository.getUserByUsername(username));
            if (userOptional.isPresent()) {
                userOptional.get().setAccountStatus(AccountStatus.valueOf(status));
                userRepository.save(userOptional.get());
                return STATUS_CHANGED_SUCCESFULL;
            } else {
                return USERNAME_NOT_FOUND;
            }
        } catch (DataAccessResourceFailureException e) {
            return SERVER_CONNECTION_FAILURE;
        }
    }

    @Override
    public UserServiceResponseStatus editPassword(String username, String oldPassword, String newPassword) {
        User user = userRepository.getUserByUsername(username);
        if (user == null) return USERNAME_NOT_FOUND;
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String cryptNewPassword = passwordEncoder.encode(newPassword);
        if(!passwordEncoder.matches(oldPassword,user.getPassword())){return PASSWORD_INCORRECT;}
        if(passwordEncoder.matches(newPassword,user.getPassword())){return PASSWORD_INCORRECT;}
        user.setPassword(cryptNewPassword.toCharArray());
        userRepository.save(user);
        return PASSWORD_CHANGED_SUCESSFULL;
    }
}