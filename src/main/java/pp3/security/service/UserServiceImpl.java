package pp3.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pp3.security.dto.UserDtoRequest;
import pp3.security.module.User;
import pp3.security.repository.UserRepository;
import pp3.security.security.UserPrincipal;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder encoder;

    @Override
    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User getByIdThrowException(Long id) {
        return this.getById(id).orElseThrow(() -> new RuntimeException("Запись не найдена"));
    }

    @Override
    public Optional<User> getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User getByUsernameThrowException(String username) {
        return this.getByUsername(username).orElseThrow(() -> new RuntimeException("Запись не найдена"));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    private User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User createUser(UserDtoRequest dtoRequest) {
        User createdUser = new User();

        createdUser.setFirstName(dtoRequest.getFirstName());
        createdUser.setLastName(dtoRequest.getLastName());
        createdUser.setAge(dtoRequest.getAge());
        createdUser.setUsername(dtoRequest.getUsername());
        createdUser.setPassword(encoder.encode(dtoRequest.getPassword()));

        return this.save(createdUser);
    }

    @Override
    public User updateUser(UserDtoRequest dtoRequest,Long id) {
        User user = this.getByIdThrowException(id);

        if (dtoRequest.getFirstName() != null) {
            user.setFirstName(dtoRequest.getFirstName());
        }
        if (dtoRequest.getLastName() != null) {
            user.setLastName(dtoRequest.getLastName());
        }
        if (dtoRequest.getAge() != 0) {
            user.setAge(dtoRequest.getAge());
        }
        if (dtoRequest.getUsername() != null) {
            user.setUsername(dtoRequest.getUsername());
        }
        if (dtoRequest.getPassword() != null) {
            user.setPassword(encoder.encode(dtoRequest.getPassword()));
        }

        return this.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        User user = this.getByIdThrowException(id);
        userRepository.delete(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.getByUsernameThrowException(username);
        return new UserPrincipal(user);
    }
}
