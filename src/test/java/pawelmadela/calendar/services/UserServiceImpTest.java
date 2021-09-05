package pawelmadela.calendar.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import pawelmadela.calendar.CalendarApplication;
import pawelmadela.calendar.controllers.Request.UserRequest;
import pawelmadela.calendar.enums.AccountType;
import pawelmadela.calendar.enums.UserServiceResponseStatus;
import pawelmadela.calendar.model.User;
import pawelmadela.calendar.repo.UserRepository;

import javax.transaction.Transactional;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;
import static pawelmadela.calendar.enums.UserServiceResponseStatus.*;

@SpringBootTest
class UserServiceImpTest {

    @Autowired
    UserServiceImp userServiceImp;

    @Test
    void showUserData() {

    }

    @Test
    @Transactional
    @Rollback
   void createUser(){
        UserRequest userRequest = new UserRequest("usernameTest","firstnameTest","lastnameTest","password","email@test.pl","USER");
        assertEquals(USER_CREATED,userServiceImp.createUser(userRequest).getResponseStatus());
        assertEquals(USERNAME_EXISTS,userServiceImp.createUser(userRequest).getResponseStatus());
        userRequest = new UserRequest("usernameTestAnother","firstnameTest","lastnameTest","password","email@test.pl","USER");
        assertEquals(EMAIL_EXISTS,userServiceImp.createUser(userRequest).getResponseStatus());
        userRequest = null;
        assertEquals(UNEXPECTED_ERROR,userServiceImp.createUser(userRequest).getResponseStatus());
    }

    @Test
    void showUserDataByUsername() {

    }
}