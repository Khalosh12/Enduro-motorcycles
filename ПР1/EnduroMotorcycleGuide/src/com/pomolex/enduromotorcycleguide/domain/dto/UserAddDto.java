package com.pomolex.enduromotorcycleguide.domain.dto;

import com.pomolex.enduromotorcycleguide.persistence.entity.Entity;
import com.pomolex.enduromotorcycleguide.persistence.entity.ErrorTemplates;
import com.pomolex.enduromotorcycleguide.persistence.exception.EntityArgumentException;
import java.util.UUID;
import java.util.regex.Pattern;

public final class UserAddDto extends Entity {
  private final String username;
  private final String rawPassword;
  private final String email;

  public UserAddDto(UUID id,
      String username,
      String rawPassword,
      String email) {
    super(id);
    this.username = username;
    this.rawPassword = validatedPassword(rawPassword);
    this.email = email;
  }

  private String validatedPassword(String rawPassword) {
    final String templateName = "паролю";

    if (rawPassword.isBlank()) {
      errors.add(ErrorTemplates.REQUIRED.getTemplate().formatted(templateName));
    }
    if (rawPassword.length() < 4) {
      errors.add(ErrorTemplates.MIN_LENGTH.getTemplate().formatted(templateName, 4));
    }
    if (rawPassword.length() > 32) {
      errors.add(ErrorTemplates.MAX_LENGTH.getTemplate().formatted(templateName, 32));
    }
    var pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$");
    if (!pattern.matcher(rawPassword).matches()) {
      errors.add(ErrorTemplates.PASSWORD.getTemplate().formatted(templateName, 24));
    }

    if (!this.errors.isEmpty()) {
      throw new EntityArgumentException(errors);
    }

    return rawPassword;
  }

  public String username() {
    return username;
  }

  public String rawPassword() {
    return rawPassword;
  }

  public String email() {
    return email;
  }

}
