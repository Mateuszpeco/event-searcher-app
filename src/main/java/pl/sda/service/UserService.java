package pl.sda.service;

import pl.sda.DTO.PasswordDto;
import pl.sda.DTO.UserDto;
import pl.sda.exception.EmptyUsernameException;
import pl.sda.exception.InvalidPasswordException;
import pl.sda.exception.WrongPasswordException;
import pl.sda.model.User;

import java.util.List;

public interface UserService {

    boolean existsByUsername(String username) throws EmptyUsernameException;

    void save(User user);

    List<User> findAll();

    void deleteByUserName(String username);

    void save(UserDto userDto);

    void changePassword(String username, PasswordDto passwordDto) throws WrongPasswordException, InvalidPasswordException;
}