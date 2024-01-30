package com.pomolex.enduromotorcycleguide.appui;

import com.pomolex.enduromotorcycleguide.domain.contract.SuspensionService;
import com.pomolex.enduromotorcycleguide.domain.dto.SuspensionAddDto;
import com.pomolex.enduromotorcycleguide.domain.exception.EntityNotFoundException;
import com.pomolex.enduromotorcycleguide.persistence.entity.impl.Suspension;
import com.pomolex.enduromotorcycleguide.persistence.repository.RepositoryFactory;
import java.nio.charset.Charset;
import java.util.Scanner;
import java.util.Set;
import java.util.UUID;

public class SuspensionMenu {

  private final SuspensionService suspensionService;

  public SuspensionMenu(SuspensionService suspensionService) {
    this.suspensionService = suspensionService;
  }

  /**
   * Displays suspension menu.
   */
  public void suspensionMenu() {
    System.out.println("Оберіть потрібний пункт");
    while (true) {
      System.out.println(
          "1. Переглянути \n2. Пошук \n3. Додати \n4. Видалити \n5. Редагувати \n6. Вихід");
      System.out.println("Оберіть потрібний пункт меню: ");
      Scanner userInput = new Scanner(System.in);
      try {
        RepositoryFactory jsonRepositoryFactory = RepositoryFactory
            .getRepositoryFactory(RepositoryFactory.JSON);

        int userChoice = userInput.nextInt();
        switch (userChoice) {
          case 1:
            viewSuspensions();
            break;
          case 2:
            searchSuspension();
            break;
          case 3:
            addSuspension();
            jsonRepositoryFactory.commit();
            break;
          case 4:
            deleteSuspension();
            jsonRepositoryFactory.commit();
            break;
          case 5:
            updateSuspension();
            jsonRepositoryFactory.commit();
            break;
          case 6:
            return;
          default:
            System.out.println("Помилка, введіть числа 1 або 6");
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

  private void viewSuspensions() {
    Set<Suspension> suspensions = suspensionService.getAll();
    if (!suspensions.isEmpty()) {
      int i = 1;
      System.out.println("Номер, тип, хід підвіски");
      for (Suspension suspension : suspensions) {
        System.out.printf("%d. %s, %f %n", i, suspension.getType(),
            suspension.getSuspensionTravel());
        i++;
      }
    } else {
      System.out.println("Список підвісок пустий");
    }
  }

  private void searchSuspension() {
    System.out.println("Введіть тип підвіски: ");
    Scanner typeInput = new Scanner(System.in, Charset.forName(
        System.getProperty("os.name").toLowerCase().startsWith("win") ? "Windows-1251"
            : "UTF-8"));
    try {
      String type = typeInput.nextLine();
      Suspension suspension = suspensionService.getByType(type);
      System.out.println("Тип, хід підвіски");
      System.out.printf("%s, %f%n", suspension.getType(), suspension.getSuspensionTravel());
    } catch (EntityNotFoundException e) {
      System.out.println(e.getMessage());
    } catch (Exception e) {
      System.out.println("Помилка, спробуйте ще раз");
    }
  }

  private void addSuspension() {
    System.out.print("Введіть тип: ");
    try {
      Scanner typeInput = new Scanner(System.in, Charset.forName(
          System.getProperty("os.name").toLowerCase().startsWith("win") ? "Windows-1251"
              : "UTF-8"));
      String type = typeInput.nextLine();

      System.out.print("Введіть хід підвіски: ");
      Scanner suspensionTravelInput = new Scanner(System.in);
      float suspensionTravel = suspensionTravelInput.nextFloat();

      SuspensionAddDto suspension = new SuspensionAddDto(UUID.randomUUID(), type, suspensionTravel);
      if (suspensionService.getAll(s -> s.getType().equals(type)).isEmpty()) {
        suspensionService.add(suspension);
        System.out.println("Дані успішно додано");
      } else {
        System.out.println("Така підвіска вже існує");
      }
    } catch (EntityNotFoundException e) {
      System.out.println(e.getMessage());
    } catch (Exception e) {
      System.out.println("Помилка, спробуйте ще раз");
    }
  }

  private void deleteSuspension() {
    viewSuspensions();
    System.out.print("Введіть тип підвіски: ");
    Scanner userInput = new Scanner(System.in, Charset.forName(
        System.getProperty("os.name").toLowerCase().startsWith("win") ? "Windows-1251"
            : "UTF-8"));
    try {
      String userChoice = userInput.nextLine();
      Suspension deletedSuspension = suspensionService.getByType(userChoice);
      suspensionService.remove(deletedSuspension);
      System.out.println("Дані успішно видалено");
    } catch (EntityNotFoundException e) {
      System.out.println(e.getMessage());
    } catch (Exception e) {
      System.out.println("Помилка, спробуйте ще раз");
    }
  }

  private void updateSuspension() {
    viewSuspensions();
    try {
      System.out.print("Введіть тип підвіски: ");
      Scanner userInput = new Scanner(System.in, Charset.forName(
          System.getProperty("os.name").toLowerCase().startsWith("win") ? "Windows-1251"
              : "UTF-8"));
      String userChoice = userInput.nextLine();
      if (suspensionService.getAll(s -> s.getType().equals(userChoice)).isEmpty()) {
        System.out.println("Такої підвіски не існує");
        return;
      }
      System.out.print("Введіть новий тип: ");
      Scanner typeInput = new Scanner(System.in, Charset.forName(
          System.getProperty("os.name").toLowerCase().startsWith("win") ? "Windows-1251"
              : "UTF-8"));
      String type = typeInput.nextLine();
      System.out.print("Введіть новий хід підвіски: ");
      Scanner suspensionTravelInput = new Scanner(System.in);
      int suspensionTravel = suspensionTravelInput.nextInt();

      Suspension suspensionToUpdate = suspensionService.getByType(userChoice);
      SuspensionAddDto suspension = new SuspensionAddDto(suspensionToUpdate.getId(), type,
          suspensionTravel);
      suspensionService.remove(suspensionToUpdate);
      suspensionService.add(suspension);
      System.out.println("Дані успішно змінено");
    } catch (EntityNotFoundException e) {
      System.out.println(e.getMessage());
    } catch (Exception e) {
      System.out.println("Помилка, спробуйте ще раз");
    }
  }

}
