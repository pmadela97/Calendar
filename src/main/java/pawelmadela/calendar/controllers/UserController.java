package pawelmadela.calendar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pawelmadela.calendar.enums.AccountType;
import pawelmadela.calendar.model.User;
import pawelmadela.calendar.services.ServiceResponse;
import pawelmadela.calendar.services.UserService;

import javax.persistence.NonUniqueResultException;

@RestController
@RequestMapping("/user")
public class UserController {


}
