package pawelmadela.calendar.model.dto;

import pawelmadela.calendar.controllers.Request.TaskRequest;
import pawelmadela.calendar.model.Task;
import pawelmadela.calendar.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ObjcetMapper {
    public static UserDto map(User user){
        UserDto userDto = new UserDto();
        userDto.userId = String.valueOf(user.getUserId());
        userDto.username = user.getUsername();
        userDto.firstname = user.getFirstname();
        userDto.lastname = user.getLastname();
        userDto.emailAddress = user.getEmailAddress();
        userDto.accountStatus = user.getAccountStatus().name;
        userDto.accountType = user.getAccountType().name();
    return userDto;
    }
    public static List<UserDto> map(List<User> users){
        List<UserDto> result = new ArrayList<>();
        for (User user:users) {
            result.add(map(user));
        }
        return result.stream().collect(Collectors.toList());
    }


}
