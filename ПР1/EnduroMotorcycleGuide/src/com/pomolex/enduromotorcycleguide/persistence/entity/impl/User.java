package com.pomolex.enduromotorcycleguide.persistence.entity.impl;

import com.pomolex.enduromotorcycleguide.persistence.entity.Entity;
import com.pomolex.enduromotorcycleguide.persistence.entity.ErrorTemplates;
import com.pomolex.enduromotorcycleguide.persistence.exception.EntityArgumentException;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;

public class User extends Entity {
  private final String password;
  private String email;
  private String username;


  public User(UUID id, String password, String email, String username) {
    super(id);
    //this.password = validatedPassword(password);

    this.password = password;
    // TODO: setEmail(email);
    this.email = email;
    // TODO: validatedBirthday(birthday);
    setUsername(username);
  }

  public String getPassword() {
    return password;
  }

  public String getEmail() {
    return email;
  }

  public String getUsername() {
    return username;
  }

  /**
   * Setter method for the username with validation.
   * <p>
   * This method sets the username with the specified validation criteria: - Must not be empty. -
   * Must be longer than 4 characters. - Must be shorter than 24 characters. - Must consist only
   * of Latin letters.
   *
   * @param username the username to be set, must meet the validation criteria
   * @throws IllegalArgumentException if the provided username does not meet the validation
   *                                  criteria
   */
  public void setUsername(String username) {
    final String templateName = "логіну";

    if (username.isBlank()) {
      errors.add(ErrorTemplates.REQUIRED.getTemplate().formatted(templateName));
    }
    if (username.length() < 4) {
      errors.add(ErrorTemplates.MIN_LENGTH.getTemplate().formatted(templateName, 4));
    }
    if (username.length() > 24) {
      errors.add(ErrorTemplates.MAX_LENGTH.getTemplate().formatted(templateName, 24));
    }
    var pattern = Pattern.compile("^[a-zA-Z0-9_]+$");
    if (!pattern.matcher(username).matches()) {
      errors.add(ErrorTemplates.ONLY_LATIN.getTemplate().formatted(templateName, 24));
    }

    if (!this.errors.isEmpty()) {
      throw new EntityArgumentException(errors);
    }

    this.username = username;
  }

  private String validatedPassword(String password) {
    final String templateName = "паролю";

    if (password.isBlank()) {
      errors.add(ErrorTemplates.REQUIRED.getTemplate().formatted(templateName));
    }
    if (password.length() < 8) {
      errors.add(ErrorTemplates.MIN_LENGTH.getTemplate().formatted(templateName, 4));
    }
    if (password.length() > 32) {
      errors.add(ErrorTemplates.MAX_LENGTH.getTemplate().formatted(templateName, 32));
    }
    var pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$");
    if (!pattern.matcher(password).matches()) {
      errors.add(ErrorTemplates.PASSWORD.getTemplate().formatted(templateName, 24));
    }

    if (!this.errors.isEmpty()) {
      throw new EntityArgumentException(errors);
    }

    return password;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return Objects.equals(email, user.email);
  }

  @Override
  public int hashCode() {
    return Objects.hash(email);
  }

  @Override
  public String toString() {
    return "User{" +
        "password='" + password + '\'' +
        ", email='" + email + '\'' +
        ", username='" + username + '\'' +
        ", id=" + id +
        '}';
  }
}
