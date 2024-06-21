package foodorderingsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class LogIn extends Info{
    
    static LogIn login = new LogIn();
    static Account account = new Account();
    static Scanner input = new Scanner(System.in);
    
    public void LogIn() {
        System.out.println("+-----------------FOOD ORDERING SYSTEM------------------+");
        System.out.println("Please log in with your account to order.");
        System.out.print("Enter your username: ");
        setUsername(input.next());
        System.out.print("Enter your password: ");
        setPassword(input.next());
        
        if (validateLogin(getUsername(), getPassword())) {
            System.out.println("SYSTEM NOTICE: You have successfully logged in.");
            System.out.println("WELCOME " + getUsername() + "!");
            login.finishLogIn(getUsername(), getPassword());
        } else {
            System.out.println("SYSTEM NOTICE: Invalid username or password. Please try again.");
            login.LogIn();
        }
    }
    
    public static boolean validateLogin(String username, String password) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbfoodorderingsystem", "root", "");) {
            String selectQuery = "SELECT * FROM tbluser WHERE username = ? AND password = ?";
            try (PreparedStatement preparedStatement = con.prepareStatement(selectQuery)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return resultSet.next();
                }
            }
        } catch (SQLException err) {
            System.out.println(err.getMessage());
            return false;
        }
    }
    
    public void finishLogIn() {
        finishLogIn(getUsername(), getPassword());
    }
    
    public void finishLogIn(String username, String password) {
        System.out.println("+--------------------USER DASHBOARD---------------------+");
        System.out.println("Please select a number from one of the options:"
                + "\n [1] - Food Menu"
                + "\n [2] - Edit User Account"
                + "\n [3] - Exit");
        System.out.print("Choose: ");
        char choice = input.next().charAt(0);
        
        switch(choice) {
            case '1':
                System.out.println("SYSTEM NOTICE: You chose [Food Menu].");
                FoodMenu.main(null);
                break;
            case '2':
                System.out.println("SYSTEM NOTICE: You chose [Edit User Account].");
                account.showAccountDetails(username, password);
                break;
            case '3':
                System.out.println("SYSTEM NOTICE: System closing...");
                System.exit(0);
                break;
            default:
                System.out.println("SYSTEM NOTICE: Invalid input. Please select from the listed options.");
                login.finishLogIn(username, password);
                break;
        }
    }
    
}
