package hotel.hotelmanagement;

import hotel.clients.Client;
import hotel.rooms.Room;
import hotel.rooms.SingleRoom;
import hotel.rooms.DoubleRoom;
import hotel.rooms.LuxeRoom;
import hotel.services.FoodOrder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Hotel {
    private static final String DB_URL = "jdbc:mysql://<host>:<port>/hotel";
    private static final String DB_USER = "<username>";
    private static final String DB_PASSWORD = "<password>";

    public void showRooms() {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM rooms")) {

            System.out.println("------------------------------------------------------------");
            System.out.printf("| %10s | %10s | %15s | %12s |\n", "Номер", "Ціна, грн", "Тип", "Заброньовано");
            System.out.println("------------------------------------------------------------");

            while (resultSet.next()) {
                int number = resultSet.getInt("number");
                double price = resultSet.getDouble("price");
                boolean booked = resultSet.getBoolean("booked");
                String type = resultSet.getString("type");

                Room room = createRoom(number, price, booked, type);
                System.out.println(room);
            }

            System.out.println("------------------------------------------------------------");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showAvailableRooms() {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM rooms WHERE booked = 0")) {

            System.out.println("------------------------------------------------------------");
            System.out.printf("| %10s | %10s | %15s | %10s |\n", "Номер", "Ціна, грн", "Тип", "Заброньовано");
            System.out.println("------------------------------------------------------------");

            while (resultSet.next()) {
                int number = resultSet.getInt("number");
                double price = resultSet.getDouble("price");
                boolean booked = resultSet.getBoolean("booked");
                String type = resultSet.getString("type");

                Room room = createRoom(number, price, booked, type);
                System.out.println(room);
            }

            System.out.println("------------------------------------------------------------");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void filterRooms() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Виберіть критерій фільтрації:");
        System.out.println("1. Фільтрація за ціною (зростання)");
        System.out.println("2. Фільтрація за ціною (спадання)");
        System.out.println("3. Фільтрація за доступністю номерів");
        int filterChoice = scanner.nextInt();

        List<Room> rooms = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM rooms")) {
            System.out.println("------------------------------------------------------------");
            System.out.printf("| %10s | %10s | %15s | %10s |\n", "Номер", "Ціна, грн", "Тип", "Заброньовано");
            System.out.println("------------------------------------------------------------");
            while (resultSet.next()) {
                int number = resultSet.getInt("number");
                double price = resultSet.getDouble("price");
                boolean booked = resultSet.getBoolean("booked");
                String type = resultSet.getString("type");

                Room room = createRoom(number, price, booked, type);
                rooms.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        switch (filterChoice) {
            case 1:
                quicksort(rooms, 0, rooms.size() - 1, true);
                break;
            case 2:
                quicksort(rooms, 0, rooms.size() - 1, false);
                break;
            case 3:
                sortByAvailabilityAndPrice(rooms);
                break;
            default:
                System.out.println("Невідомий критерій фільтрації.");
                return;
        }

        for (Room room : rooms) {
            System.out.println(room);
        }
        System.out.println("------------------------------------------------------------");
    }

    private void quicksort(List<Room> rooms, int low, int high, boolean ascending) {
        if (low < high) {
            int partitionIndex = partition(rooms, low, high, ascending);
            quicksort(rooms, low, partitionIndex - 1, ascending);
            quicksort(rooms, partitionIndex + 1, high, ascending);
        }
    }

    private int partition(List<Room> rooms, int low, int high, boolean ascending) {
        double pivot = rooms.get(high).getPrice();
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (ascending ? rooms.get(j).getPrice() < pivot : rooms.get(j).getPrice() > pivot) {
                i++;
                swapRooms(rooms, i, j);
            }
        }
        swapRooms(rooms, i + 1, high);
        return i + 1;
    }

    private void sortByAvailabilityAndPrice(List<Room> rooms) {
        for (int i = 0; i < rooms.size() - 1; i++) {
            for (int j = 0; j < rooms.size() - i - 1; j++) {
                Room room1 = rooms.get(j);
                Room room2 = rooms.get(j + 1);
                if (room1.isBooked() && !room2.isBooked()) {
                    swapRooms(rooms, j, j + 1);
                } else if (room1.isBooked() == room2.isBooked() && room1.getPrice() > room2.getPrice()) {
                    swapRooms(rooms, j, j + 1);
                }
            }
        }
    }

    private void swapRooms(List<Room> rooms, int index1, int index2) {
        Room temp = rooms.get(index1);
        rooms.set(index1, rooms.get(index2));
        rooms.set(index2, temp);
    }

    public void bookRoom() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введіть номер кімнати для бронювання:");
        int roomNumber = scanner.nextInt();
        System.out.println("Введіть кількість днів для бронювання:");
        int days = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Введіть ім'я клієнта:");
        String name = scanner.nextLine();
        System.out.println("Введіть номер телефону:");
        String contactNumber = scanner.nextLine();
        System.out.println("Введіть емейл:");
        String email = scanner.nextLine();
        System.out.println("Введіть гендер:");
        String gender = scanner.nextLine();

        Client client = new Client(name, contactNumber, email, gender);

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM rooms WHERE number = " + roomNumber);
            if (!resultSet.next()) {
                System.out.println("Кімнати " + roomNumber + " не існує.");
                return;
            }

            if (resultSet.getBoolean("booked")) {
                System.out.println("Кімната " + roomNumber + " вже заброньована.");
                return;
            }

            String updateQuery = "UPDATE rooms SET booked = 1, days_booked = " + days + " WHERE number = " + roomNumber;
            statement.executeUpdate(updateQuery);

            String insertCustomerQuery = "INSERT INTO customers (name, contact_number, email, gender) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertCustomerQuery);
            preparedStatement.setString(1, client.getName());
            preparedStatement.setString(2, client.getContactNumber());
            preparedStatement.setString(3, client.getEmail());
            preparedStatement.setString(4, client.getGender());
            preparedStatement.executeUpdate();

            System.out.println("Кімнату " + roomNumber + " заброньовано на " + days + " днів для клієнта " + name + ".");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void orderFood() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введіть номер кімнати для замовлення їжі (з доступних):");
        int roomNumber = scanner.nextInt();
        scanner.nextLine();

        boolean roomExists = false;
        boolean roomBooked = false;
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet roomResultSet = statement.executeQuery("SELECT * FROM rooms WHERE number = " + roomNumber)) {

            if (roomResultSet.next()) {
                roomExists = true;
                roomBooked = roomResultSet.getBoolean("booked");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (!roomExists) {
            System.out.println("Кімната з номером " + roomNumber + " не існує.");
            return;
        }

        if (!roomBooked) {
            System.out.println("Кімната з номером " + roomNumber + " не заброньована. Неможливо замовити їжу.");
            return;
        }

        List<FoodOrder> foodOrders = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM food_orders")) {

            List<String> foodNames = new ArrayList<>();
            List<Double> foodPrices = new ArrayList<>();
            int index = 1;

            System.out.println("--------------------------------------------------");
            System.out.printf("| %5s | %25s | %10s |\n", "№", "Назва страви", "Ціна, грн");
            System.out.println("--------------------------------------------------");

            while (resultSet.next()) {
                String foodName = resultSet.getString("order_name");
                double foodPrice = resultSet.getDouble("price");
                foodNames.add(foodName);
                foodPrices.add(foodPrice);
                System.out.printf("| %5d | %25s | %10.2f |\n", index++, foodName, foodPrice);
            }

            System.out.println("--------------------------------------------------");

            boolean continueOrdering = true;
            while (continueOrdering) {
                System.out.println("Введіть номер страви для замовлення:");
                int foodChoiceIndex = scanner.nextInt() - 1;
                scanner.nextLine();

                if (foodChoiceIndex < 0 || foodChoiceIndex >= foodNames.size()) {
                    System.out.println("Невірний вибір. Будь ласка, спробуйте ще раз.");
                    continue;
                }

                String selectedFoodName = foodNames.get(foodChoiceIndex);
                double selectedFoodPrice = foodPrices.get(foodChoiceIndex);

                FoodOrder foodOrder = new FoodOrder(roomNumber, selectedFoodName, selectedFoodPrice);
                foodOrders.add(foodOrder);

                String insertQuery = "INSERT INTO room_food_orders (room_id, food_order_id) VALUES ((SELECT id FROM rooms WHERE number = " + roomNumber + "), (SELECT id FROM food_orders WHERE order_name = '" + selectedFoodName + "'))";
                statement.executeUpdate(insertQuery);

                System.out.println("Замовлення прийнято.");

                System.out.println("Чи бажаєте замовити щось ще? (так/ні)");
                String continueResponse = scanner.nextLine().trim().toLowerCase();
                continueOrdering = continueResponse.equals("так");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("------------------------------------------");
        System.out.printf("| %25s | %10s |\n", "Замовлена страва", "Ціна, грн");
        System.out.println("------------------------------------------");
        for (FoodOrder order : foodOrders) {
            System.out.printf("| %25s | %10.2f |\n", order.getFoodName(), order.getPrice());
        }
        System.out.println("------------------------------------------");
    }

    public void generateBill() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введіть номер кімнати для виставлення чеку:");
        int roomNumber = scanner.nextInt();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet roomResultSet = statement.executeQuery("SELECT * FROM rooms WHERE number = " + roomNumber)) {

            if (roomResultSet.next() && roomResultSet.getBoolean("booked")) {
                double roomPrice = roomResultSet.getDouble("price");
                int daysBooked = roomResultSet.getInt("days_booked");

                double totalRoomCost = roomPrice * daysBooked;
                double totalFoodCost = 0;

                String foodQuery = "SELECT f.price FROM room_food_orders rfo JOIN food_orders f ON rfo.food_order_id = f.id JOIN rooms r ON rfo.room_id = r.id WHERE r.number = " + roomNumber;
                try (ResultSet foodResultSet = statement.executeQuery(foodQuery)) {
                    while (foodResultSet.next()) {
                        totalFoodCost += foodResultSet.getDouble("price");
                    }
                }

                double totalBill = totalRoomCost + totalFoodCost;
                System.out.println("Загальна сума чеку для кімнати " + roomNumber + ": " + totalBill + " грн");
            } else {
                System.out.println("Кімната не заброньована. Неможливо виставити чек.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void resetTables() {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement()) {
            String resetBookingsQuery = "UPDATE rooms SET booked = 0, days_booked = 0";
            statement.executeUpdate(resetBookingsQuery);
            System.out.println("Стан номерів скинуто.");

            String resetFoodOrdersQuery = "DELETE FROM room_food_orders";
            statement.executeUpdate(resetFoodOrdersQuery);
            System.out.println("Таблицю замовлень їжі скинуто.");

            String resetCustomersQuery = "DELETE FROM customers";
            statement.executeUpdate(resetCustomersQuery);
            System.out.println("Таблицю клієнтів скинуто.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Room createRoom(int number, double price, boolean booked, String type) {
        switch (type) {
            case "Одномісний":
                return new SingleRoom(number, price, booked);
            case "Двомісний":
                return new DoubleRoom(number, price, booked);
            case "Люкс":
                return new LuxeRoom(number, price, booked);
            default:
                throw new IllegalArgumentException("Невідомий тип кімнати: " + type);
        }
    }
}