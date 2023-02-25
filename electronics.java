import javax.sound.sampled.spi.AudioFileReader;
import java.sql.*;
import java.time.Period;
import java.util.Scanner;

public class electronics {
    private static final String url="jdbc:postgresql://localhost:5432/electronics";
    private static final String username = "postgres";
    private static final String password = "180204";
    static Scanner scanner = new Scanner(System.in);
    static int uid=0;

    public static void add_user() throws Exception{
        Connection connection = DriverManager.getConnection(url,username,password);
        System.out.println("Enter your name:");
        String name = scanner.nextLine();
        System.out.println("Balance:");
        int balance = scanner.nextInt();

        String q = "insert into users(name,balance) values(?,?);";
        PreparedStatement prep = connection.prepareStatement(q);
        prep.setString(1,name);
        prep.setInt(2,balance);
        prep.executeUpdate();

        String getid = "select id from users where name = ?";
        PreparedStatement p = connection.prepareStatement(getid);
        p.setString(1,name);
        ResultSet res = p.executeQuery();
        if(res.next()){
            uid = res.getInt("id");
        }
        user_info();
        connection.close();
    }

    public static void show_catalog()throws Exception{
        int c;
        do{
            System.out.println("1. Phones");
            System.out.println("2. Headphones");
            System.out.println("3. Accessories");
            System.out.println("4. Exit");

            c = scanner.nextInt();

            switch (c){
                case 1:show_phones();break;
                case 2:show_headphones();break;
                case 3:show_accessories();break;
                default:break;
            }

        }while(c!=4);
        scanner.nextLine();
    }

    public static void show_phones() throws Exception{
        Connection Connection = DriverManager.getConnection(url,username,password);

        System.out.println("1. iPhone");
        System.out.println("2. Samsung");
        System.out.println("3. Oppo");
        System.out.println("4. Exit");
        System.out.println("Choose brand:");
        int c = scanner.nextInt();
        String q="";
        switch (c){
            case 1:  q = "select * from phones where brand='IPhone'";break;
            case 2:  q = "select * from phones where brand='Samsung'";break;
            case 3:  q = "select * from phones where brand='Xiaomi'";break;
        }

        Statement stmt = Connection.createStatement();
        ResultSet res = stmt.executeQuery(q);
        while (res.next()){
            System.out.println("ID: "+res.getInt("id")+" Brand: "+res.getString("brand")+" Model: "+res.getString("model")+" Memory: "+res.getInt("ROM")+" Color: "+res.getString("color")+" Price: "+res.getInt("price"));
        }
        Connection.close();
        scanner.nextLine();
    }

    public static void show_headphones() throws Exception{
        Connection Connection = DriverManager.getConnection(url,username,password);
        String q = "select * from headphones";
        Statement stmt = Connection.createStatement();
        ResultSet res = stmt.executeQuery(q);
        while (res.next()){
            System.out.println("ID: "+res.getInt("id")+" Brand: "+res.getString("brand")+" Model: "+res.getString("model")+" Colour: "+res.getString("colour")+" Price: "+res.getInt("price"));
        }
        Connection.close();
    }

    public static void show_accessories() throws Exception{
        Connection Connection = DriverManager.getConnection(url,username,password);
        int c;
        do {
            System.out.println("1. Chargers");
            System.out.println("2. Power-banks");
            System.out.println("3. Exit");
            c = scanner.nextInt();
            switch (c){
                case 1:
                    String charger = "select * from chargers";
                    Statement stmt = Connection.createStatement();
                    ResultSet res = stmt.executeQuery(charger);
                    while (res.next()){
                        System.out.println("ID: "+res.getInt("id")+" Type: "+res.getString("type")+" Length: "+res.getString("length")+" Colour: "+res.getString("colour")+ " Price: "+res.getInt("price"));
                    }
                    break;
                case 2:
                    String power = "select * from powerbank";
                    Statement stmt1 = Connection.createStatement();
                    ResultSet res1 = stmt1.executeQuery(power);
                    while(res1.next()){
                        System.out.println("ID: "+res1.getInt("id")+" Model: "+res1.getString("model")+" Colour: "+res1.getString("colour")+" Volume: "+res1.getInt("volume")+" Price: "+res1.getInt("price"));
                    }
                    break;
                default:break;
            }
        }while(c!=3);

        Connection.close();
        scanner.nextLine();
    }


    public static void purchase() throws Exception{
        Connection Connection = DriverManager.getConnection(url,username,password);

        String ui = "select balance from users where id=?";
        PreparedStatement prep = Connection.prepareStatement(ui);
        prep.setInt(1,uid);
        ResultSet r = prep.executeQuery();
        int balance=0;
        if(r.next()){
            balance=r.getInt("balance");
        }


        System.out.println("Choose category");
        System.out.println("1.Phone");
        System.out.println("2.Headphones");
        System.out.println("3.Charger");
        System.out.println("4.Powerbank");



        int cat = scanner.nextInt();
        switch (cat){
            case 1:
                show_phones();
                String q="select price from phones where id =?";
                PreparedStatement p = Connection.prepareStatement(q);
                System.out.println("Enter product id: ");
                int pid= scanner.nextInt();
                p.setInt(1,pid);
                ResultSet res = p.executeQuery();
                int sum=0;
                if(res.next()) {sum =res.getInt("price");
                }
                int remainder = balance - sum;

                String q1 = "update users set balance=? where id=?";
                PreparedStatement p1 = Connection.prepareStatement(q1);
                p1.setInt(1,remainder);
                p1.setInt(2,uid);
                p1.executeUpdate();

                String q2 = "insert into purchases(userid,total) values(?,?);";
                PreparedStatement p2 = Connection.prepareStatement(q2);
                p2.setInt(1,uid);
                p2.setInt(2,sum);
                p2.executeUpdate();

                purchase_info(sum);
                break;
            case 2:
                show_headphones();
                String q3="select price from headphones where id =?";
                PreparedStatement p3 = Connection.prepareStatement(q3);
                System.out.println("Enter product id: ");
                int pid3= scanner.nextInt();
                p3.setInt(1,pid3);
                ResultSet res3 = p3.executeQuery();
                int sum3=0;
                if(res3.next()){sum3= res3.getInt("price");}
                int remainder3 = balance - sum3;

                String q4 = "update users set balance=? where id=?";
                PreparedStatement p4 = Connection.prepareStatement(q4);
                p4.setInt(1,remainder3);
                p4.setInt(2,uid);
                p4.executeUpdate();

                String q5 = "insert into purchases(userid,total) values(?,?);";
                PreparedStatement p5 = Connection.prepareStatement(q5);
                p5.setInt(1,uid);
                p5.setInt(2,sum3);
                p5.executeUpdate();
                purchase_info(sum3);
                break;

            case 3:

                String charger = "select * from chargers";
                Statement stmt = Connection.createStatement();
                ResultSet result = stmt.executeQuery(charger);
                while (result.next()){
                    System.out.println("ID: "+result.getInt("id")+" Type: "+result.getString("type")+" Length: "+result.getString("length")+" Colour: "+result.getString("colour")+ " Price: "+result.getInt("price"));
                }

                String q6="select price from chargers where id =?";
                PreparedStatement p6 = Connection.prepareStatement(q6);
                System.out.println("Enter product id: ");
                int pid4= scanner.nextInt();
                p6.setInt(1,pid4);
                ResultSet res4 = p6.executeQuery();
                int sum4=0;
                if(res4.next()){sum4= res4.getInt("price");}
                int remainder4 = balance - sum4;

                String q7 = "update users set balance=? where id=?";
                PreparedStatement p7 = Connection.prepareStatement(q7);
                p7.setInt(1,remainder4);
                p7.setInt(2,uid);
                p7.executeUpdate();


                String q8 = "insert into purchases(userid,total) values(?,?);";
                PreparedStatement p8 = Connection.prepareStatement(q8);
                p8.setInt(1,uid);
                p8.setInt(2,sum4);
                p8.executeUpdate();
                purchase_info(sum4);
                break;
            case 4:
                String power = "select * from powerbank";
                Statement stmt1 = Connection.createStatement();
                ResultSet res1 = stmt1.executeQuery(power);
                while(res1.next()){
                    System.out.println("ID: "+res1.getInt("id")+" Model: "+res1.getString("model")+" Colour: "+res1.getString("colour")+" Volume: "+res1.getInt("volume")+" Price: "+res1.getInt("price"));
                }

                String qu="select price from powerbank where id =?";
                PreparedStatement pr = Connection.prepareStatement(qu);
                System.out.println("Enter product id: ");
                int poid= scanner.nextInt();
                pr.setInt(1,poid);
                ResultSet re = pr.executeQuery();
                int summ=0;
                if(re.next()){summ= re.getInt("price");}
                int remain = balance - summ;

                String qu1 = "update users set balance=? where id=?";
                PreparedStatement pr1 = Connection.prepareStatement(qu1);
                pr1.setInt(1,remain);
                pr1.setInt(2,uid);
                pr1.executeUpdate();

                String qu2 = "insert into purchases(userid,total) values(?,?);";
                PreparedStatement pr2 = Connection.prepareStatement(qu2);
                pr2.setInt(1,uid);
                pr2.setInt(2,summ);
                pr2.executeUpdate();
                purchase_info(summ);
                break;
            default:break;
        }
        Connection.close();
        scanner.nextLine();
    }

    private static void purchase_info(int total) throws SQLException {
        Connection connection = DriverManager.getConnection(url, username, password);
        String sql = "INSERT INTO purchases (userid, total) VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, uid);
        statement.setInt(2, total);
        statement.executeUpdate();
        connection.close();
    }

    public static void userinfo() throws Exception{
        Connection Connection = DriverManager.getConnection(url,username,password);
        String q = "select * from users where id=?";
        PreparedStatement prep = Connection.prepareStatement(q);
        prep.setInt(1,uid);
        ResultSet res = prep.executeQuery();
        if(res.next()){
            System.out.println("ID: "+res.getInt("id")+" Name: "+res.getString("name")+" Balance: "+res.getInt("balance"));
        }
        Connection.close();
    }

    public static void add_to_cart() throws Exception{
        Connection Connection = DriverManager.getConnection(url,username,password);
        System.out.println("Enter the product ID:");
        int pid = scanner.nextInt();

        System.out.println("Enter the quantity:");
        int qty = scanner.nextInt();

        String q = "insert into cart(uid,pid,qty) values(?,?,?);";
        PreparedStatement prep = Connection.prepareStatement(q);
        prep.setInt(1,uid);
        prep.setInt(2,pid);
        prep.setInt(3,qty);
        prep.executeUpdate();
        System.out.println("Added to cart successfully");
        Connection.close();
    }

    private static final String SELECT_CART_ITEMS = "select c.pid, p.brand, p.model, c.qty, p.price from cart c inner join phones p on c.pid=p.id where uid=?";

    public static void showCart() throws Exception {
        Connection connection = DriverManager.getConnection(url,username,password);
        PreparedStatement prep = connection.prepareStatement(SELECT_CART_ITEMS);
        prep.setInt(1,uid);
        ResultSet res = prep.executeQuery();
        int total = 0;
        while (res.next()) {
            int subtotal = res.getInt("qty") * res.getInt("price");
            System.out.println("Product ID: "+res.getInt("pid")+" Brand: "+res.getString("brand")+" Model: "+res.getString("model")+" Quantity: "+res.getInt("qty")+" Price: "+res.getInt("price")+" Subtotal: "+subtotal);
            total += subtotal;
        }
        System.out.println("Total amount: "+total);
        if (res != null) res.close();
        if (prep != null) prep.close();
        if (connection != null) connection.close();
    }

    public static void checkout(int uid) throws SQLException {
        Connection conn = null;
        PreparedStatement prep = null;
        ResultSet res = null;
        try {
            conn = DriverManager.getConnection(url,username,password);
            prep = conn.prepareStatement(SELECT_CART_ITEMS);
            prep.setInt(1, uid);
            res = prep.executeQuery();
            int total = 0;
            while (res.next()){
                int subtotal = res.getInt("qty") * res.getInt("price");
                total += subtotal;
            }
            prep = conn.prepareStatement("select balance from users where id=?");
            prep.setInt(1, uid);
            res = prep.executeQuery();
            if(res.next()){
                int balance = res.getInt("balance");
                if(total <= balance){
                    int newbalance = balance - total;
                    prep = conn.prepareStatement("update users set balance=? where id=?");
                    prep.setInt(1, newbalance);
                    prep.setInt(2, uid);
                    prep.executeUpdate();
                    System.out.println("Checkout Successful. New balance: " + newbalance);
                } else {
                    System.out.println("Not enough balance");
                }
            }
        } finally {
            if (res != null) res.close();
            if (prep != null) prep.close();
            if (conn != null) conn.close();
        }
    }


    public static void buy_accessory() throws Exception{
        Connection Connection = DriverManager.getConnection(url,username,password);
        System.out.println("Choose accessory ID:");
        int aid = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Choose quantity:");
        int quantity = scanner.nextInt();

        String q = "select * from accessories where id = ?";
        PreparedStatement prep = Connection.prepareStatement(q);
        prep.setInt(1,aid);
        ResultSet res = prep.executeQuery();
        int price=0;
        int stock=0;
        String aname="";
        if(res.next()){
            price = res.getInt("price");
            stock = res.getInt("stock");
            aname = res.getString("name");
        }
        if(stock>=quantity){
            String update_stock = "update accessories set stock = ? where id = ?";
            PreparedStatement ps = Connection.prepareStatement(update_stock);
            ps.setInt(1,stock-quantity);
            ps.setInt(2,aid);
            ps.executeUpdate();

            String add_to_cart = "insert into cart(uid,aname,price,quantity) values(?,?,?,?)";
            PreparedStatement pc = Connection.prepareStatement(add_to_cart);
            pc.setInt(1,uid);
            pc.setString(2,aname);
            pc.setInt(3,price);
            pc.setInt(4,quantity);
            pc.executeUpdate();

            System.out.println("Accessory added to cart successfully!");
        }else{
            System.out.println("Stock not enough!");
        }
        Connection.close();
    }

    public static void user_info() throws Exception{
        Connection Connection = DriverManager.getConnection(url,username,password);
        String q = "select * from users where id=?";
        PreparedStatement prep = Connection.prepareStatement(q);
        prep.setInt(1,uid);
        ResultSet res = prep.executeQuery();
        if(res.next()){
            System.out.println("Name: "+res.getString("name")+" Balance: "+res.getInt("balance"));
        }
        Connection.close();
    }

    public static void cart_info() throws Exception{
        Connection Connection = DriverManager.getConnection(url,username,password);
        String q = "select * from cart where uid = ?";
        PreparedStatement prep = Connection.prepareStatement(q);
        prep.setInt(1,uid);
        ResultSet res = prep.executeQuery();
        int total_price=0;
        while (res.next()){
            System.out.println("Accessory: "+res.getString("aname")+" Price: "+res.getInt("price")+" Quantity: "+res.getInt("quantity"));
            total_price += res.getInt("price")*res.getInt("quantity");
        }
        System.out.println("Total price: "+total_price);
        Connection.close();
    }

    public static void buy_cart() throws Exception {
        Connection connection = DriverManager.getConnection(url,username,password);

        String q = "select * from cart where uid = ?";
        PreparedStatement prep = connection.prepareStatement(q);
        prep.setInt(1, uid);
        ResultSet res = prep.executeQuery();
        int total_price = 0;
        boolean can_buy = true;
        while (res.next()) {
            int aid = res.getInt("aid");
            int quantity = res.getInt("quantity");
            String check_stock = "select stock from accessories where id = ?";
            PreparedStatement p1 = connection.prepareStatement(check_stock);
            p1.setInt(1, aid);
            ResultSet r1 = p1.executeQuery();
            if (r1.next()) {
                int stock = r1.getInt("stock");
                if (stock < quantity) {
                    can_buy = false;
                    break;
                }
            }
            total_price += res.getInt("price") * res.getInt("quantity");
        }

        if (can_buy) {
            String update_balance = "update users set balance = balance - ? where id = ?";
            PreparedStatement ps = connection.prepareStatement(update_balance);
            ps.setInt(1, total_price);
            ps.setInt(2, uid);
            ps.executeUpdate();

            String q2 = "select * from accessories";
            Statement stmt = connection.createStatement();
            ResultSet res2 = stmt.executeQuery(q2);
            while (res2.next()) {
                System.out.println("ID: " + res2.getInt("id") + " Type: " + res2.getString("type") + " Name: " + res2.getString("name") + " Colour: " + res2.getString("colour") + " Price: " + res2.getInt("price"));
            }
        }
        connection.close();
    }


        public static void buy () throws Exception {
            Connection connection = DriverManager.getConnection(url,username,password);
            System.out.println("Enter ID of product you want to buy:");
            int pid = scanner.nextInt();

            String q = "select * from products where id = ?";
            PreparedStatement p = connection.prepareStatement(q);
            p.setInt(1, pid);
            ResultSet resultSet = p.executeQuery();
            if (resultSet.next()) {
                int price = resultSet.getInt("price");
                String name = resultSet.getString("name");
                String checkq = "select balance from users where id = ?";
                PreparedStatement check = connection.prepareStatement(checkq);
                check.setInt(1, uid);
                ResultSet r = check.executeQuery();

                if (r.next()) {
                    int balance = r.getInt("balance");
                    if (balance >= price) {
                        String updateq = "update users set balance = ? where id = ?";
                        PreparedStatement update = connection.prepareStatement(updateq);
                        update.setInt(1, balance - price);
                        update.setInt(2, uid);
                        update.executeUpdate();
                        System.out.println("Congratulations! You have successfully bought " + name);
                    } else {
                        System.out.println("You don't have enough balance!");
                    }
                }
            }
            connection.close();
            scanner.nextLine();
        }
    }