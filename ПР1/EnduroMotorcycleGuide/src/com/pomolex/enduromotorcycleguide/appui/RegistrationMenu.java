package com.pomolex.enduromotorcycleguide.appui;

import com.pomolex.enduromotorcycleguide.domain.contract.AuthService;
import com.pomolex.enduromotorcycleguide.domain.contract.SignUpService;
import com.pomolex.enduromotorcycleguide.domain.contract.UserService;
import com.pomolex.enduromotorcycleguide.domain.dto.UserAddDto;
import com.pomolex.enduromotorcycleguide.persistence.exception.EntityArgumentException;
import java.nio.charset.Charset;
import java.util.Scanner;
import java.util.UUID;

public class RegistrationMenu {
  private final AuthService authService;
  private final UserService userService;
  private final SignUpService signUpService;

  public RegistrationMenu(AuthService authService, UserService userService, SignUpService signUpService){
    this.authService = authService;
    this.userService = userService;
    this.signUpService = signUpService;
  }

  /**
   * Displays the registration menu.
   */
  public void registration() {
    while (true) {
      System.out.println("Введіть логін: ");
      try {
        Scanner userLoginInput = new Scanner(System.in, Charset.forName(
            System.getProperty("os.name").toLowerCase().startsWith("win") ? "Windows-1251"
                : "UTF-8"));
        String userLogin = userLoginInput.nextLine();
        System.out.println("Введіть пароль: ");
        Scanner userPasswordInput = new Scanner(System.in);
        String userPassword = userPasswordInput.nextLine();
        System.out.println("Введіть свою пошту: ");
        Scanner emailInput = new Scanner(System.in);
        String email = emailInput.nextLine();
        if (!authService.isUserExist(userLogin)) {
          if (isLoginPasswordEmailValid(userLogin, userPassword, email)) {
            UserAddDto user = new UserAddDto(UUID.randomUUID(), userLogin, userPassword, email);

            signUpService.signUp(user, () -> {
              System.out.print("Введіть код підтвердження: ");
              try {
                Scanner scanner = new Scanner(System.in);
                return scanner.nextLine();
              } catch (Exception e) {
                System.out.println("Помилка формату.");
                return "";
              }
            });
            MainMenu mainMenu = new MainMenu();
            mainMenu.registeredUserMenu(userLogin);
            break;
          } else {
            System.out.println(
                "Помилка, логін повинен бути не менше 5 символів, \nта складатись тільки з великих та маленьких латинських літер та цифр. \n"
                    + "Пароль повинен мати хоча б одну велику, маленьку літери та одну цифру. \n"
                    + "Також перевірте правильність пошти.");
          }
        } else {
          System.out.println("Помилка, такий користувач вже існує");
        }
      } catch (EntityArgumentException e){
        e.getErrors().forEach(System.out::println);
      } catch (Exception e) {
        System.out.println("Помилка, спробуйте ще раз " + e.getMessage());
      }
    }
  }

  private boolean isLoginPasswordEmailValid(String login, String password, String email){
    return login.matches("[A-Za-z0-9]{5,}")
        && password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$")
        && email.matches("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
  }

}
