package pp3.security.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pp3.security.dto.UserDtoRequest;
import pp3.security.dto.UserDtoResponse;
import pp3.security.mapper.UserMapper;
import pp3.security.module.User;
import pp3.security.service.UserService;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public ResponseEntity<HttpStatus> loginUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/user")
    public ResponseEntity<UserDtoResponse> welcomeUser(Principal principal) {
        User user = userService.getByUsernameThrowException(principal.getName());
        UserDtoResponse dtoResponse = UserMapper.userToDto(user);
        return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDtoResponse>> showAllUsers() {
        List<UserDtoResponse> dtoResponseList = userService.getAllUsers().stream().map(UserMapper::userToDto).toList();

        return new ResponseEntity<>(dtoResponseList, HttpStatus.OK);
    }

    @PostMapping("/create-user")
    public ResponseEntity<UserDtoResponse> addNewUser(@RequestBody UserDtoRequest userDtoRequest) {
        User user = userService.createUser(userDtoRequest);
        UserDtoResponse userDtoResponse = UserMapper.userToDto(user);
        return new ResponseEntity<>(userDtoResponse, HttpStatus.OK);
    }

    @PutMapping("/update-user/{id}")
    public ResponseEntity<UserDtoResponse> updateUser(@RequestBody UserDtoRequest userDtoRequest,
                                                      @PathVariable("id") Long id) {
        User user = userService.updateUser(userDtoRequest, id);
        UserDtoResponse userDtoResponse = UserMapper.userToDto(user);

        return new ResponseEntity<>(userDtoResponse, HttpStatus.OK);
    }


    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
