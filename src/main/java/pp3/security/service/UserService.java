package pp3.security.service;

import pp3.security.dto.UserDtoRequest;
import pp3.security.module.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> getById(Long id);
    User getByIdThrowException(Long id);

    Optional<User> getByUsername(String username);
    User getByUsernameThrowException(String username);

    List<User> getAllUsers();

    User createUser(UserDtoRequest dtoRequest);

    User updateUser(UserDtoRequest dtoRequest,Long id);

    void deleteUser(Long id);
}
