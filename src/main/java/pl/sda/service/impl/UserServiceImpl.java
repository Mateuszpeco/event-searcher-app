package pl.sda.service.impl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.sda.DTO.PasswordDto;
import pl.sda.DTO.UserDto;
import pl.sda.exception.EmptyUsernameException;
import pl.sda.exception.InvalidPasswordException;
import pl.sda.exception.WrongPasswordException;
import pl.sda.model.Role;
import pl.sda.model.User;
import pl.sda.util.PasswordValidator;
import pl.sda.repository.RoleRepository;
import pl.sda.repository.UserRepository;
import pl.sda.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private static final String INITIAL_PASSWORD = "pass";
    private final PasswordValidator passwordValidator;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder, PasswordValidator passwordValidator) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.passwordValidator = passwordValidator;
    }

    @Override
    public boolean existsByUsername(String username) throws EmptyUsernameException {
        if (username == null || username.isBlank()) {
            throw new EmptyUsernameException("Username can not be empty!");
        }
        return userRepository.existsByUsername(username);
    }

    @Override
    public void save(User user) {
        user.setRoles(getUserRoles("USER"));
        user.setPassword(encodeRawPassword(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void deleteByUserName (String username) {
        User user = userRepository.findByUsername(username);
        userRepository.deleteById(user.getId());
    }

    @Override
    public void save(UserDto userDto) {
        User user = new User();
        user.setPassword(encodeRawPassword(INITIAL_PASSWORD));
        user.setUsername(userDto.getUsername());
        user.setRoles(getUserRoles(userDto.getRoles().toUpperCase()));
        user.setEmail(userDto.getEmail());
        userRepository.save(user);
    }

    @Override
    public void changePassword(String username, PasswordDto passwordDto) throws WrongPasswordException, InvalidPasswordException {

        User user = userRepository.findByUsername(username);

        if (!bCryptPasswordEncoder.matches(passwordDto.getActualPassword(), user.getPassword())) {
            throw new WrongPasswordException("Passwords are incorrect.");
        }
        if (!passwordDto.getNewPassword().equals(passwordDto.getNewRepeatedPassword())) {
            throw new WrongPasswordException("Passwords are incorrect.");
        }

        if (!passwordValidator.isPasswordValid(passwordDto.getNewPassword())) {
            throw new InvalidPasswordException("Password must contains A-Z, a-z, @#$%, minimum 8 characters.");
        }
        user.setPassword(encodeRawPassword(passwordDto.getNewPassword()));
        userRepository.save(user);
    }

    private List<Role> getUserRoles(String roles) {

        String[] roleArray = roles.split(",");

        List<Role> roleList = new ArrayList<>();

        for (String role : roleArray) {
            Role roleFromDB = roleRepository.findByName(role.trim());
            roleList.add(roleFromDB);
        }
        return roleList;
    }

    private String encodeRawPassword(String rawPassword) {
        return bCryptPasswordEncoder.encode(rawPassword);
    }

}
