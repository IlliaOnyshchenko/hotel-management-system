package hotel.clients;

public class Client {
    private String name;
    private String contactNumber;
    private String email;
    private String gender;

    public Client(String name, String contactNumber, String email, String gender) {
        this.name = name;
        this.contactNumber = contactNumber;
        this.email = email;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }
}
