package foodorderingsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class Main {
    
    public static void main(String[] args) {
        Account account = new Account();
        LogIn login = new LogIn();
        Scanner input = new Scanner(System.in);
        
        char choice;
        
        System.out.println("+-----------------WELCOME TO FOODACITY------------------+");
        System.out.println("Please select a number from one of the options:"
                + "\n [1] - Log in"
                + "\n [2] - Create account"
                + "\n [3] - Exit");
        System.out.print("Choose: ");
        choice = input.next().charAt(0);
        
        switch(choice) {
            case '1':
                System.out.println("SYSTEM NOTICE: You chose [Log in].");
                login.LogIn();
                break;
            case '2':
                System.out.println("SYSTEM NOTICE: You chose [Create account].");
                account.createAccount();
                break;
            case '3':
                System.out.println("SYSTEM NOTICE: System closing...");
                System.exit(0);
                break;
            default:
                System.out.println("SYSTEM NOTICE: Invalid input. Please select from the listed options.");
                Main.main(args);
                break;    
        }
        
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            deleteAllData();
        }));
    }
    
    private static void deleteAllData() {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbfoodorderingsystem", "root", "")) {
            String deleteQuery = "DELETE FROM tbltemp_order";
            PreparedStatement deleteStatement = con.prepareStatement(deleteQuery);
            deleteStatement.executeUpdate();
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

}
