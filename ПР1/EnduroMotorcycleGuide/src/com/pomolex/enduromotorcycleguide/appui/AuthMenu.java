package com.pomolex.enduromotorcycleguide.appui;

import com.pomolex.enduromotorcycleguide.domain.contract.AuthService;
import java.util.Scanner;

public class AuthMenu {
  private final AuthService authService;

  public AuthMenu(AuthService authService){
    this.authService = authService;
  }

  /**
   * Displays the authorization menu.
   */
  public void authorization() {
    while (true) {
      System.out.println("Введіть логін: ");
      Scanner userLoginInput = new Scanner(System.in);
      try {
        String userLogin = userLoginInput.nextLine();
        System.out.println("Введіть пароль: ");
        Scanner userPasswordInput = new Scanner(System.in);
        String userPassword = userPasswordInput.nextLine();
        if(authService.authenticate(userLogin, userPassword)){
            MainMenu mainMenu = new MainMenu();
            mainMenu.registeredUserMenu(userLogin);
        } else {
          System.out.println("Такого користувача не існує");
        }
        break;
      } catch (Exception e) {
        System.out.println("Помилка, спробуйте ще раз");
      }
    }
  }



}
