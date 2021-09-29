package pawelmadela.calendar.model.dto;

import pawelmadela.calendar.model.User;

public class ObjcetMapper {
    public static UserDto map(User user){
        UserDto userDto = new UserDto();
        userDto.username = user.getUsername();
        userDto.firstname = user.getFirstname();
        userDto.lastname = user.getLastname();
        userDto.emailAddress = user.getEmailAddress();
        userDto.accountStatus = user.getAccountStatus().name;
        userDto.accountType = user.getAccountType().name();
    return userDto;
    }

}
