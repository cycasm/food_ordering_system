package foodorderingsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Scanner;

public class FoodMenu extends Info {

    static Scanner input = new Scanner(System.in);
    static FoodMenu foodMenu = new FoodMenu();
    static LogIn login = new LogIn();
    
    public static void main(String[] args) {
        String appetizer = "tblappetizer";
        String mainCourse = "tblmaincourse";
        String desserts = "tbldesserts";
        String drinks = "tbldrinks";

        System.out.println("+-------------------RESTAURANT MENU---------------------+");
        System.out.println("Please select a number from one of the options:"
                + "\n [1] - Appetizer"
                + "\n [2] - Main Course"
                + "\n [3] - Desserts"
                + "\n [4] - Drinks"
                + "\n [5] - Back");
        System.out.print("Choose: ");
        char choice = input.next().charAt(0);
        switch (choice) {
            case '1':
                System.out.println("SYSTEM NOTICE: You chose [Appetizer].");
                foodMenu.loadMenu(appetizer);
                break;
            case '2':
                System.out.println("SYSTEM NOTICE: You chose [Main Course].");
                foodMenu.loadMenu(mainCourse);
                break;
            case '3':
                System.out.println("SYSTEM NOTICE: You chose [Desserts].");
                foodMenu.loadMenu(desserts);
                break;
            case '4':
                System.out.println("SYSTEM NOTICE: You chose [Drinks].");
                foodMenu.loadMenu(drinks);
                break;
            case '5':
                login.finishLogIn();
                break;
            default:
                System.out.println("SYSTEM NOTICE: Invalid input. Please select from the listed options.");
                FoodMenu.main(args);
                break;
        }
    }
    
    public void loadMenu(String tblName) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbfoodorderingsystem", "root", "");
            PreparedStatement selectStatement = con.prepareStatement("SELECT * FROM " + tblName);
            ResultSet resultSet = selectStatement.executeQuery();
            String leftAlignFormat = "| %-10d | %-48s | ₱%-8.2f |%n";
            System.out.format("+------------+--------------------------------------------------+-----------+%n");
            System.out.format("| Product ID | Product Name                                     | Price     |%n");
            System.out.format("+------------+--------------------------------------------------+-----------+%n");
            while (resultSet.next()) {
                System.out.format(leftAlignFormat, resultSet.getInt(1), resultSet.getString(2), resultSet.getDouble(3));
            }
            System.out.format("+------------+--------------------------------------------------+-----------+%n");
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }

        System.out.println("Please select a number from one of the options:"
                + "\n [1] - Add to order"
                + "\n [2] - See your order"
                + "\n [3] - Back");
        System.out.print("Choose: ");
        char choice = input.next().charAt(0);
        switch (choice) {
            case '1':
                try {
                System.out.println("SYSTEM NOTICE: You chose [Add to order].");
                System.out.println("Please enter the product ID of the food that you want to add in your order.");
                System.out.print("Product ID: ");
                setID(input.nextInt());
                System.out.print("Quantity: ");
                setQuantity(input.nextInt());

                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbfoodorderingsystem", "root", "");

                String selectQuery = "SELECT * FROM " + tblName + " WHERE id = ?";
                PreparedStatement selectStatement = con.prepareStatement(selectQuery);
                selectStatement.setInt(1, getID());
                ResultSet resultSet = selectStatement.executeQuery();
                
                String insertQuery = "INSERT INTO tbltemp_order (product_name, price_each, quantity, subtotal_price) VALUES (?, ?, ?, ?)";
                PreparedStatement insertStatement = con.prepareStatement(insertQuery);
                
                while (resultSet.next()) {
                    insertStatement.setString(1, resultSet.getString("product_name"));
                    insertStatement.setDouble(2, resultSet.getDouble("price"));
                    insertStatement.setInt(3, getQuantity());
                    insertStatement.setDouble(4, resultSet.getDouble("price")*getQuantity());
                    insertStatement.executeUpdate();
                }
                
                } catch (SQLException err) {
                    System.out.println(err.getMessage());
                }
                Order.main(null);
                break;
            case '2':
                Order.main(null);
                break;
            case '3':
                FoodMenu.main(null);
                break;
            default:
                System.out.println("SYSTEM NOTICE: Invalid input. Please select from the listed options.");
                foodMenu.loadMenu(tblName);
                break;
        }
    }

    
}
