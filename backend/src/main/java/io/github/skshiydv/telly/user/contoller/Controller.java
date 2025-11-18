package io.github.skshiydv.telly.user.contoller;

import io.github.skshiydv.telly.user.entity.UserEntity;
import io.github.skshiydv.telly.user.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class Controller {
    private final UserRepository userRepository;

    public Controller(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/get-user/{email}")
    public UserEntity getUser(@PathVariable String email) {
        UserEntity userEntity = new UserEntity();
        userEntity = userRepository.findByEmail(email);
        return userEntity;
    }

}
