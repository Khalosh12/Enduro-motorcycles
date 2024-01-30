package com.pomolex.enduromotorcycleguide;

import com.github.javafaker.Faker;
import com.pomolex.enduromotorcycleguide.persistence.entity.impl.User;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.UUID;

public class Main {

  public static void main(String[] args) {
    Startup.init();
  }

  public static Set<User> generateUsers(int count) {
    Set<User> users = new HashSet<>();
    Faker faker = new Faker();

    for (int i = 0; i < count; i++) {
      UUID userId = UUID.randomUUID();
      String password = faker.internet().password();
      String email = faker.internet().emailAddress();
      String username = faker.name().username();

      User user = new User(userId, password, email, username);
      users.add(user);
    }

    return users;
  }

}
