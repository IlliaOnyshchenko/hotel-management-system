package hotel.rooms;

public class Room {
    private int number;
    private double price;
    private boolean booked;
    private String type;

    public Room(int number, double price, boolean booked, String type) {
        this.number = number;
        this.price = price;
        this.booked = booked;
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public boolean isBooked() {
        return booked;
    }

    @Override
    public String toString() {
        return String.format("| %10d | %10.2f | %15s | %12s |", number, price, type, booked ? "так" : "ні");
    }
}