package com.pomolex.enduromotorcycleguide.domain.impl;

import com.pomolex.enduromotorcycleguide.domain.contract.AuthService;
import com.pomolex.enduromotorcycleguide.domain.exception.UserAlreadyAuthException;
import com.pomolex.enduromotorcycleguide.persistence.entity.impl.User;
import com.pomolex.enduromotorcycleguide.persistence.repository.contracts.UserRepository;
import com.pomolex.enduromotorcycleguide.domain.exception.AuthException;
import org.mindrot.jbcrypt.BCrypt;

final class AuthServiceImpl implements AuthService {

  private final UserRepository userRepository;
  private User user;

  AuthServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /**
   * Authenticates a user with the given username and password.
   * @param username the username of the user
   * @param password the password of the user
   * @return true if the authentication is successful, false otherwise
   */
  public boolean authenticate(String username, String password) {
    // Перевіряємо, чи вже існує аутентифікований користувач
    if (user != null) {
      throw new UserAlreadyAuthException("Ви вже авторизувалися як: %s"
          .formatted(user.getUsername()));
    }

    User foundedUser = userRepository.findByUsername(username)
        .orElseThrow(AuthException::new);

    if (!BCrypt.checkpw(password, foundedUser.getPassword())) {
      return false;
    }

    user = foundedUser;
    return true;
  }

  /**
   * Checks if a user with the given username exists.
   * @param username the username to check
   * @return true if the user exists, false otherwise
   */
  public boolean isUserExist(String username){
    return userRepository.findByUsername(username).isPresent();
  }

  /**
   * Checks if the current user is authenticated.
   * @return true if the user is authenticated, false otherwise
   */
  public boolean isAuthenticated() {
    return user != null;
  }

  /**
   * Retrieves the user associated with the current session.
   * @return the user associated with the current session
   */
  public User getUser() {
    return user;
  }

  /**
   * Logs out the current user.
   */
  public void logout() {
    if (user == null) {
      throw new UserAlreadyAuthException("Ви ще не автентифіковані.");
    }
    user = null;
  }
}