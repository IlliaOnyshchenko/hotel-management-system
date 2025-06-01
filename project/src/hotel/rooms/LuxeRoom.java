package hotel.rooms;

public class LuxeRoom extends Room {
    public LuxeRoom(int number, double price, boolean booked) {
        super(number, price, booked, "Люкс");
    }
}