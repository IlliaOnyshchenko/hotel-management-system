package hotel.services;

public class FoodOrder {
    private int roomNumber;
    private String foodName;
    private double price;

    public FoodOrder(int roomNumber, String foodName, double price) {
        this.roomNumber = roomNumber;
        this.foodName = foodName;
        this.price = price;
    }

    public String getFoodName() {
        return foodName;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return foodName + " - " + price;
    }
}