package com.pomolex.enduromotorcycleguide.appui;

import com.pomolex.enduromotorcycleguide.domain.contract.AuthService;
import com.pomolex.enduromotorcycleguide.domain.contract.SignUpService;
import com.pomolex.enduromotorcycleguide.domain.contract.UserService;
import com.pomolex.enduromotorcycleguide.domain.impl.ServiceFactory;
import com.pomolex.enduromotorcycleguide.persistence.repository.RepositoryFactory;
import java.util.Scanner;

public class BaseMenu {
  /**
   * Displays base menu.
   */
  public void baseMenu() {
    System.out.println("Ласкаво просимо до довідника про мотоцикли для Ендуро!");
    while (true) {
      System.out.println(
          "1. Авторизація \n2. Реєстрація \n3. Вихід");
      System.out.println("Оберіть потрібний пункт меню: ");
      Scanner userInput = new Scanner(System.in);
      try {
        RepositoryFactory jsonRepositoryFactory = RepositoryFactory
            .getRepositoryFactory(RepositoryFactory.JSON);
        ServiceFactory serviceFactory = ServiceFactory.getInstance(jsonRepositoryFactory);
        AuthService authService = serviceFactory.getAuthService();

        int userChoice = userInput.nextInt();
        switch (userChoice) {
          case 1:
            AuthMenu authMenu = new AuthMenu(authService);
            authMenu.authorization();
            break;
          case 2:
            UserService userService = serviceFactory.getUserService();
            SignUpService signUpService = serviceFactory.getSignUpService();
            RegistrationMenu registrationMenu = new RegistrationMenu(authService, userService, signUpService);
            registrationMenu.registration();
            break;
          case 3:
            return;
          default:
            System.out.println("Помилка, введіть число від 1 до 3");
            break;
        }
        System.out.println("Бажаєте продовжити?");
        System.out.println("1. Так");
        System.out.println("2. Ні");
        Scanner userInputChoice = new Scanner(System.in);
        int userChoiceContinue = userInputChoice.nextInt();
        if (userChoiceContinue == 2) {
          break;
        }
      } catch (Exception e) {
        System.out.println("Помилка, спробуйте ще раз");
      }
    }
  }
}
