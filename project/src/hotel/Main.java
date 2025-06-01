package hotel;

import hotel.hotelmanagement.Hotel;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Hotel hotel = new Hotel();
        Scanner scanner = new Scanner(System.in);
        int choice;

        Runtime.getRuntime().addShutdownHook(new Thread(Hotel::resetTables));

        while (true) {
            System.out.println("Виберіть опцію:");
            System.out.println("1. Показати номери");
            System.out.println("2. Показати доступні номери");
            System.out.println("3. Фільтрація номерів");
            System.out.println("4. Забронювати");
            System.out.println("5. Замовити їжу");
            System.out.println("6. Виставити чек");
            System.out.println("0. Вийти");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Будь ласка, введіть числове значення.");
                continue;
            }

            switch (choice) {
                case 1:
                    hotel.showRooms();
                    break;
                case 2:
                    hotel.showAvailableRooms();
                    break;
                case 3:
                    hotel.filterRooms();
                    break;
                case 4:
                    hotel.bookRoom();
                    break;
                case 5:
                    hotel.orderFood();
                    break;
                case 6:
                    hotel.generateBill();
                    break;
                case 0:
                    System.out.println("Вихід...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Невідома опція. Спробуйте ще раз.");
            }
        }
    }
}