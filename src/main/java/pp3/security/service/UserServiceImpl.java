package pp3.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
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

    @Override
    public void saveUser(User user) {
        String encoderPassword = encoder.encode(user.getPassword());
        user.setPassword(encoderPassword);
        userRepository.save(user);
    }

    @Override
    public void updateUser(Long id, User updatedUser) {
        User user = this.getByIdThrowException(id);
        if (updatedUser.getFirstName() != null) {
            user.setFirstName(updatedUser.getFirstName());
        }
        if (updatedUser.getLastName() != null) {
            user.setLastName(updatedUser.getLastName());
        }
        if (updatedUser.getAge() != 0) {
            user.setAge(updatedUser.getAge());
        }
        if (updatedUser.getUsername() != null) {
            user.setUsername(updatedUser.getUsername());
        }
        if (updatedUser.getPassword() != null) {
            user.setPassword(encoder.encode(updatedUser.getPassword()));
        }
        userRepository.save(user);
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
