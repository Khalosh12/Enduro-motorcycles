package com.pomolex.enduromotorcycleguide.domain.contract;

import com.pomolex.enduromotorcycleguide.persistence.entity.impl.User;

/**
 * An interface for user authentication and access control.
 */
public interface AuthService {

  /**
   * Authenticates a user with the given username and password.
   * @param username the username of the user
   * @param password the password of the user
   * @return true if the authentication is successful, false otherwise
   */
  boolean authenticate(String username, String password);

  /**
   * Checks if a user with the given username exists.
   * @param username the username to check
   * @return true if the user exists, false otherwise
   */
  boolean isUserExist(String username);

  /**
   * Checks if the current user is authenticated.
   * @return true if the user is authenticated, false otherwise
   */
  boolean isAuthenticated();

  /**
   * Retrieves the user associated with the current session.
   * @return the user associated with the current session
   */
  User getUser();

  /**
   * Logs out the current user.
   */
  void logout();
}
