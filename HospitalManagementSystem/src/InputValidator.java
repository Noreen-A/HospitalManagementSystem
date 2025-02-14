import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class InputValidator {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");


    public static String getValidString(Scanner scanner) {
        while (true) {
            String name = scanner.nextLine().trim();
            if (name.matches("[a-zA-Z-' ]+")) {
                return name;
            } else {
                System.out.println("Invalid name! Please enter only letters.");
            }
        }
    }
    public static int getValidAge(Scanner scanner) {
        int age;
        while (true) {
            String input = scanner.nextLine();
            try {
                age = Integer.parseInt(input);
                if (age < 0 || age > 120) {
                    System.out.println("Error: Age must be between 0 and 120.");
                } else {
                    return age;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid number.");
            }
        }
    }

      public static String getValidContactInfo(Scanner scanner) {
          while (true) {
              System.out.print("Enter contact number (9 digits): +20 1 ");
              String input = scanner.nextLine().trim();

              // Check if input is exactly 11 digits and contains only numbers
              if (input.matches("\\d{9}")) {
                  return input;  // Return valid contact number
              } else {
                  System.out.println("Invalid contact number! It must be exactly 11 digits.");
              }
          }
      }
    public static LocalDateTime getValidDateTime(Scanner scanner) {
        while (true) {
            System.out.print("Enter appointment date and time (yyyy-MM-dd HH:mm): ");
            String input = scanner.nextLine().trim();

            try {
                LocalDateTime dateTime = LocalDateTime.parse(input, formatter);
                if (dateTime.isBefore(LocalDateTime.now())) {
                    System.out.println("Error: Cannot schedule an appointment in the past.");
                } else {
                    return dateTime;
                }
            } catch (Exception e) {
                System.out.println("Invalid format! Please enter the date and time in 'yyyy-MM-dd HH:mm' format.");
            }
        }
    }
}
