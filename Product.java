import java.util.Scanner;

interface Product {
    void showInfo() throws Exception;
    int getPrice() throws Exception;
}

class Phone implements Product {
    private final int id;
    private final String brand;
    private final String model;
    private final int memory;
    private final String color;
    private final int price;

    public Phone(int id, String brand, String model, int memory, String color, int price) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.memory = memory;
        this.color = color;
        this.price = price;
    }

    public void showInfo() {
        System.out.println("ID: " + id + " Brand: " + brand + " Model: " + model + " Memory: " + memory + " Color: " + color + " Price: " + price);
    }

    public int getPrice() {
        return price;
    }
}

class Headphone implements Product {
    private final int id;
    private final String brand;
    private final String model;
    private final String color;
    private final int price;

    public Headphone(int id, String brand, String model, String color, int price) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.price = price;
    }

    public void showInfo() {
        System.out.println("ID: " + id + " Brand: " + brand + " Model: " + model + " Colour: " + color + " Price: " + price);
    }

    public int getPrice() {
        return price;
    }
}

class Charger implements Product {
    private final int id;
    private final String type;
    private final String length;
    private final String color;
    private final int price;

    public Charger(int id, String type, String length, String color, int price) {
        this.id = id;
        this.type = type;
        this.length = length;
        this.color = color;
        this.price = price;
    }

    public void showInfo() {
        System.out.println("ID: " + id + " Type: " + type + " Length: " + length + " Colour: " + color + " Price: " + price);
    }

    public int getPrice() {
        return price;
    }
}

class PowerBank implements Product {
    private final int id;
    private final String model;
    private final String color;
    private final int volume;
    private final int price;

    public PowerBank(int id, String model, String color, int volume, int price) {
        this.id = id;
        this.model = model;
        this.color = color;
        this.volume = volume;
        this.price = price;
    }

    public void showInfo() {
        System.out.println("ID: " + id + " Model: " + model + " Colour: " + color + " Volume: " + volume + " Price: " + price);
    }

    public int getPrice() {
        return price;
    }
}

class ProductFactory {
    private static final String url = "jdbc:postgresql://localhost:5432/electronics";
    private static final String username = "postgres";
    private static final String password = "180204";
    private static final Scanner scanner = new Scanner(System.in);
}
