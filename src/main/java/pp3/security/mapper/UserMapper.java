package pp3.security.mapper;

import pp3.security.dto.UserDtoResponse;
import pp3.security.module.User;

public class UserMapper {

    public static UserDtoResponse userToDto(User user) {
        UserDtoResponse userDtoResponse = new UserDtoResponse();

        userDtoResponse.setId(user.getId());
        userDtoResponse.setFirstName(user.getFirstName());
        userDtoResponse.setLastName(user.getLastName());
        userDtoResponse.setAge(user.getAge());
        userDtoResponse.setUsername(user.getUsername());

        return userDtoResponse;
    }
}
