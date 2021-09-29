package pawelmadela.calendar.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import pawelmadela.calendar.controllers.Request.UserRequest;
import pawelmadela.calendar.enums.AccountStatus;
import pawelmadela.calendar.enums.UserServiceResponseStatus;
import pawelmadela.calendar.model.User;

import javax.transaction.Transactional;

import java.util.Optional;

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


    }

    @Test
    void showUserDataByUsername() {
        String s1 = "testName";
        String s2 = "pmadela97";
        String s3 = "";
        String s4 = null;
        assertEquals(NULL_CREDENTIALS,userServiceImp.showUserByUsername(s3).getResponseStatus());
        assertEquals(NULL_CREDENTIALS,userServiceImp.showUserByUsername(s4).getResponseStatus());
        assertEquals(USERNAME_FOUND,userServiceImp.showUserByUsername(s2).getResponseStatus());
        assertEquals("pmadela97",userServiceImp.showUserByUsername(s2).getResponseObject().getUsername());
        assertEquals(USERNAME_NOT_FOUND,userServiceImp.showUserByUsername(s1).getResponseStatus());
        assertEquals(null,userServiceImp.showUserByUsername(s1).getResponseObject());

    }

    @Test
    void deleteUserByUsername() {
            assertEquals(USERNAME_NOT_FOUND,userServiceImp.deleteUserByUsername("NOT"));
            UserRequest userRequest = new UserRequest("usernameTest","firstnameTest","lastnameTest","password","email@test.pl");
            userServiceImp.createUser(userRequest,"USER", AccountStatus.ACTIVE);
            assertEquals(USER_DELETED, userServiceImp.deleteUserByUsername(userRequest.getUsername()));
            assertEquals(NULL_CREDENTIALS,userServiceImp.deleteUserByUsername(null));
            assertEquals(NULL_CREDENTIALS,userServiceImp.deleteUserByUsername(""));
    }

    @Transactional
    @Rollback
    @Test
    void editUserPassword() {
        String s1 = "testName";
        String s2 = "pmadela97";
        String s3 = "";
        String s4 = null;
        String p1 = "pass";
        String p2 = "pass2";
        String p3 = "";
        String p4 = null;
        assertEquals(USERNAME_NOT_FOUND,userServiceImp.editUserPassword(s1,p1));
        assertEquals(USERNAME_NOT_FOUND,userServiceImp.editUserPassword(s1,p2));
        assertEquals(NULL_CREDENTIALS,userServiceImp.editUserPassword(s1,p3));
        assertEquals(NULL_CREDENTIALS,userServiceImp.editUserPassword(s1,p4));

        assertEquals(PASSWORD_CHANGED_SUCESSFULL,userServiceImp.editUserPassword(s2,p1));
        assertEquals(PASSWORD_CHANGED_SUCESSFULL,userServiceImp.editUserPassword(s2,p2));
        assertEquals(NULL_CREDENTIALS,userServiceImp.editUserPassword(s2,p3));
        assertEquals(NULL_CREDENTIALS,userServiceImp.editUserPassword(s2,p4));

        assertEquals(NULL_CREDENTIALS,userServiceImp.editUserPassword(s3,p1));
        assertEquals(NULL_CREDENTIALS,userServiceImp.editUserPassword(s3,p2));
        assertEquals(NULL_CREDENTIALS,userServiceImp.editUserPassword(s3,p3));
        assertEquals(NULL_CREDENTIALS,userServiceImp.editUserPassword(s3,p4));

        assertEquals(NULL_CREDENTIALS,userServiceImp.editUserPassword(s4,p1));
        assertEquals(NULL_CREDENTIALS,userServiceImp.editUserPassword(s4,p2));
        assertEquals(NULL_CREDENTIALS,userServiceImp.editUserPassword(s4,p3));
        assertEquals(NULL_CREDENTIALS,userServiceImp.editUserPassword(s4,p4));
        assertEquals(NULL_CREDENTIALS,userServiceImp.editUserPassword(s4,p1));

    }
    @Transactional
    @Rollback
    @Test
    void editUserEmail() {
        String s1 = "testName";
        String s2 = "pmadela97";
        String s3 = "";
        String s4 = null;
        String p1 = "email";
        String p2 = "email2";
        String p3 = "";
        String p4 = null;
        assertEquals(USERNAME_NOT_FOUND,userServiceImp.editUserEmail(s1,p1));
        assertEquals(USERNAME_NOT_FOUND,userServiceImp.editUserEmail(s1,p2));
        assertEquals(NULL_CREDENTIALS,userServiceImp.editUserEmail(s1,p3));
        assertEquals(NULL_CREDENTIALS,userServiceImp.editUserEmail(s1,p4));

        assertEquals(EMAIL_CHANGED_SUCCESSFULL,userServiceImp.editUserEmail(s2,p1));
        assertEquals(EMAIL_CHANGED_SUCCESSFULL,userServiceImp.editUserEmail(s2,p2));
        assertEquals(NULL_CREDENTIALS,userServiceImp.editUserEmail(s2,p3));
        assertEquals(NULL_CREDENTIALS,userServiceImp.editUserEmail(s2,p4));

        assertEquals(NULL_CREDENTIALS,userServiceImp.editUserEmail(s3,p1));
        assertEquals(NULL_CREDENTIALS,userServiceImp.editUserEmail(s3,p2));
        assertEquals(NULL_CREDENTIALS,userServiceImp.editUserEmail(s3,p3));
        assertEquals(NULL_CREDENTIALS,userServiceImp.editUserEmail(s3,p4));

        assertEquals(NULL_CREDENTIALS,userServiceImp.editUserEmail(s4,p1));
        assertEquals(NULL_CREDENTIALS,userServiceImp.editUserEmail(s4,p2));
        assertEquals(NULL_CREDENTIALS,userServiceImp.editUserEmail(s4,p3));
        assertEquals(NULL_CREDENTIALS,userServiceImp.editUserEmail(s4,p4));
        assertEquals(NULL_CREDENTIALS,userServiceImp.editUserEmail(s4,p1));
    }

    @Test
    void showAllUsers() {
    ServiceResponse<?,?> response = userServiceImp.showAllUsers();
    assertEquals(RESULT_FOUND,response.getResponseStatus());;
    }

    @Test
    void editUserStatus() {
        String s1 = "testName";
        String s2 = "pmadela97";
        String s3 = "";
        String s4 = null;
        String p1 = "BLOCKED";
        String p2 = "ACTIVE";
        String p3 = "EXPIRED";
        String p4 = "AAAA";
        String p5 = "";
        String p6 = null;

        assertEquals(USERNAME_NOT_FOUND,userServiceImp.editUserStatus(s1,p1));
        assertEquals(USERNAME_NOT_FOUND,userServiceImp.editUserStatus(s1,p2));
        assertEquals(USERNAME_NOT_FOUND,userServiceImp.editUserStatus(s1,p3));
        assertEquals(WRONG_ACCOUNT_STATUS,userServiceImp.editUserStatus(s1,p4));
        assertEquals(NULL_CREDENTIALS,userServiceImp.editUserStatus(s1,p5));
        assertEquals(NULL_CREDENTIALS,userServiceImp.editUserStatus(s1,p6));

        assertEquals(STATUS_CHANGED_SUCCESFULL,userServiceImp.editUserStatus(s2,p1));
        assertEquals(STATUS_CHANGED_SUCCESFULL,userServiceImp.editUserStatus(s2,p2));
        assertEquals(STATUS_CHANGED_SUCCESFULL,userServiceImp.editUserStatus(s2,p3));
        assertEquals(WRONG_ACCOUNT_STATUS,userServiceImp.editUserStatus(s2,p4));
        assertEquals(NULL_CREDENTIALS,userServiceImp.editUserStatus(s2,p5));
        assertEquals(NULL_CREDENTIALS,userServiceImp.editUserStatus(s2,p6));

        assertEquals(NULL_CREDENTIALS,userServiceImp.editUserStatus(s3,p1));
        assertEquals(NULL_CREDENTIALS,userServiceImp.editUserStatus(s3,p2));
        assertEquals(NULL_CREDENTIALS,userServiceImp.editUserStatus(s3,p3));
        assertEquals(NULL_CREDENTIALS,userServiceImp.editUserStatus(s3,p4));
        assertEquals(NULL_CREDENTIALS,userServiceImp.editUserStatus(s3,p5));
        assertEquals(NULL_CREDENTIALS,userServiceImp.editUserStatus(s3,p6));

        assertEquals(NULL_CREDENTIALS,userServiceImp.editUserStatus(s4,p1));
        assertEquals(NULL_CREDENTIALS,userServiceImp.editUserStatus(s4,p2));
        assertEquals(NULL_CREDENTIALS,userServiceImp.editUserStatus(s4,p3));
        assertEquals(NULL_CREDENTIALS,userServiceImp.editUserStatus(s4,p4));
        assertEquals(NULL_CREDENTIALS,userServiceImp.editUserStatus(s4,p5));
        assertEquals(NULL_CREDENTIALS,userServiceImp.editUserStatus(s4,p6));





    }
}