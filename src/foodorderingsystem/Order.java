package foodorderingsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Order extends Info{
    static Scanner input = new Scanner(System.in);
    
    static PreparedStatement prepareStatement;
    
    public static void main(String[] args) {
        Order order = new Order();
        
        int id;
        
        order.loadOrder();
        
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbfoodorderingsystem", "root", "");
            System.out.println("Please select a number from one of the options:"
                + "\n [1] - Pay for order"
                + "\n [2] - Remove from order"
                + "\n [3] - Clear all"
                + "\n [4] - Add more");
                System.out.print("Choose: ");
                char choice = input.next().charAt(0);
                
                switch (choice) {
                    case '1':
                        order.payOrder();
                        break;
                    case '2':
                        System.out.println("SYSTEM NOTICE: You chose [Remove from order].");
                        System.out.println("Please enter the product ID of the food that you want to remove from your order.");
                        System.out.print("Product ID: ");
                        id = input.nextInt();
                        
                        prepareStatement = con.prepareStatement("DELETE FROM tbltemp_order WHERE id='"+id+"'");
                        prepareStatement.execute();
                        
                        Order.main(args);
                        break;
                    case '3':
                        System.out.println("SYSTEM NOTICE: You chose [Clear all].");
                        prepareStatement = con.prepareStatement("DELETE FROM tbltemp_order");
                        prepareStatement.execute();
                        Order.main(args);
                        break;
                    case '4':
                        FoodMenu.main(args);
                        break;
                    default:
                        System.out.println("SYSTEM NOTICE: Invalid. Please select from the listed options.");
                        Order.main(args);
                        break;
                }
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }
    
    public void loadOrder() {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbfoodorderingsystem", "root", "");
            PreparedStatement selectStatement = con.prepareStatement("SELECT * FROM tbltemp_order");
            ResultSet resultSet = selectStatement.executeQuery();
            System.out.format("-------------------------------------YOUR ORDER--------------------------------------------------%n");
            String leftAlignFormat = "  %-10d | %-48s | ₱%-8.2f | %-4d | ₱%-9.2f  %n";
            System.out.format("-------------+--------------------------------------------------+-----------+------+-------------%n");
            System.out.format("  Product ID | Product Name                                     | Price     | Qty  | Subtotal    %n");
            System.out.format("-------------+--------------------------------------------------+-----------+------+-------------%n");
            
            while (resultSet.next()) {
                System.out.format(leftAlignFormat, resultSet.getInt(1), resultSet.getString(2),
                            resultSet.getDouble(3), resultSet.getInt(4),resultSet.getDouble(5));
            }
            
            String subtotal_price = "subtotal_price";
            setTotalPrice(sumSubtotalPrice(subtotal_price));
            
            System.out.format("-------------+--------------------------------------------------+-----------+------+-------------%n");
            System.out.println("                                                                       Total Price:  ₱" + getTotalPrice());
            System.out.format("-------------------------------------------------------------------------------------------------%n");
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }
    
    public static double sumSubtotalPrice(String subtotal) {
        double sum = 0;

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbfoodorderingsystem", "root", "")) {
            String selectQuery = "SELECT SUM(subtotal_price) AS total FROM tbltemp_order";
            try (PreparedStatement selectStatement = con.prepareStatement(selectQuery)) {
                try (ResultSet resultSet = selectStatement.executeQuery()) {
                    if (resultSet.next()) {
                        sum = resultSet.getDouble("total");
                    }
                }
            }
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        return sum;
    }
    
    public void payOrder() {
        System.out.println("SYSTEM NOTICE: You chose [Pay for order].");
        try {
            System.out.print("Enter your amount of payment: ");
            setPayment(input.nextDouble());

            if (getPayment() > getTotalPrice()) {
                setCashChange(getPayment() - getTotalPrice());
                
                loadOrderReceipt();
                System.out.println("Thank you for your order!");
                
                try {
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbfoodorderingsystem", "root", "");
                    prepareStatement = con.prepareStatement("DELETE FROM tbltemp_order");
                    prepareStatement.execute();
                } catch (SQLException err) {
                    System.out.println(err.getMessage());
                }
            } else {
                System.out.println("Insufficient payment. Please provide enough cash.");
                payOrder();
            }
        } catch (InputMismatchException err) {
            System.out.println(err.getMessage());
        }
    }
    
    public void loadOrderReceipt() {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbfoodorderingsystem", "root", "");
            PreparedStatement selectStatement = con.prepareStatement("SELECT * FROM tbltemp_order");
            ResultSet resultSet = selectStatement.executeQuery();
            System.out.format("-------------------------------------ORDER RECEIPT-----------------------------------------------%n");
            String leftAlignFormat = "  %-10d | %-48s | ₱%-8.2f | %-4d | ₱%-9.2f  %n";
            System.out.format("-------------+--------------------------------------------------+-----------+------+-------------%n");
            System.out.format("  Product ID | Product Name                                     | Price     | Qty  | Subtotal    %n");
            System.out.format("-------------+--------------------------------------------------+-----------+------+-------------%n");
            
            while (resultSet.next()) {
                System.out.format(leftAlignFormat, resultSet.getInt(1), resultSet.getString(2),
                         resultSet.getDouble(3), resultSet.getInt(4),resultSet.getDouble(5));
            }
            
            String subtotal_price = "subtotal_price";
            setTotalPrice(sumSubtotalPrice(subtotal_price));
            
            System.out.format("-------------+--------------------------------------------------+-----------+------+-------------%n");
            System.out.println("                                                                      Total Price:   ₱" + getTotalPrice());
            System.out.println("                                                                      Cash payment:  ₱" + getPayment());
            System.out.println("                                                                      Change:        ₱" + getCashChange());
            System.out.format("-------------------------------------------------------------------------------------------------%n");
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }
    
    
}
