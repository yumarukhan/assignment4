import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class DatabaseConnection {

    private static final String url = "jdbc:postgresql://localhost:5432/electronics";
    private static final String username = "postgres";
    private static final String password = "180204";
    private static Scanner scanner = new Scanner(System.in);
    private static int uid = 0;

    public static void main(String[] args) {
        // main method logic
    }

    public static void add_user() throws Exception {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        System.out.println("Enter your name:");
        String name = scanner.nextLine();
        System.out.println("Balance:");
        int balance = scanner.nextInt();
        scanner.nextLine();
        String q = "insert into users(name,balance) values(?,?);";
        PreparedStatement prep = connection.prepareStatement(q);
        prep.setString(1, name);
        prep.setInt(2, balance);
        prep.executeUpdate();
        String getid = "select id from users where name = ?";
        PreparedStatement p = connection.prepareStatement(getid);
        p.setString(1, name);
        ResultSet res = p.executeQuery();
        if (res.next()) {
            uid = res.getInt("id");
        }
        user_info();
        connection.close();
    }

    public static void show_catalog() throws Exception {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String q = "select * from products;";
        PreparedStatement prep = connection.prepareStatement(q);
        ResultSet res = prep.executeQuery();
        while (res.next()) {
            System.out.println(res.getInt("id") + " " + res.getString("name") + " $" + res.getDouble("price"));
        }
        connection.close();
    }

    public static void show_phones() throws Exception {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String q = "select * from products where category = 'phone';";
        PreparedStatement prep = connection.prepareStatement(q);
        ResultSet res = prep.executeQuery();
        while (res.next()) {
            System.out.println(res.getInt("id") + " " + res.getString("name") + " $" + res.getDouble("price"));
        }
        connection.close();
    }

    public static void user_info() throws Exception {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String q = "select * from users where id = ?";
        PreparedStatement p = connection.prepareStatement(q);
        p.setInt(1, uid);
        ResultSet res = p.executeQuery();
        if (res.next()) {
            System.out.println("ID: " + res.getInt("id") + " Name: " + res.getString("name") + " Balance: " + res.getInt("balance"));
        }
        connection.close();
    }

    private static DatabaseConnection instance = null;
    private Connection connection;

    private DatabaseConnection() {
        try {
            connection = DriverManager.getConnection(DatabaseConnection.url, DatabaseConnection.username, DatabaseConnection.password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
