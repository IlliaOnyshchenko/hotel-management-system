package hotel.rooms;

public class SingleRoom extends Room {
    public SingleRoom(int number, double price, boolean booked) {
        super(number, price, booked, "Одномісний");
    }
}