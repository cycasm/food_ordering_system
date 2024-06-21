package foodorderingsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Account extends Info{
    LogIn login = new LogIn();
    static Account account = new Account();
    
    char choice;
    private static Scanner input;
    Connection con;
    PreparedStatement updateStatement;
    String updateQuery;
    
    public Account() {
        input = new Scanner(System.in);
    }
    
    public void createAccount() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbfoodorderingsystem", "root", "");
            System.out.println("+-------------------CREATE ACCOUNT----------------------+");
            System.out.println("Please enter your details to create a user account.");
            System.out.print("Username: ");
            setUsername(input.nextLine());
            System.out.print("Password: ");
            setPassword(input.nextLine());
            System.out.print("First name: ");
            setFirstName(input.nextLine());
            System.out.print("Last name: ");
            setLastName(input.nextLine());
            System.out.println("[(Optional) Add your email address to stay updated with the latest Foodacity promos and offers!]"
                    + "\nPress Enter to decline.");
            System.out.print("Email address: ");
            setEmail(input.nextLine());

            if (!getEmail().isEmpty()) {
                account.createAccount(getUsername(), getPassword(), getFirstName(), getLastName(), getEmail());
            } else {
                account.createAccount(getUsername(), getPassword(), getFirstName(), getLastName());
            }
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }
    
    public void createAccount(String username, String password, String firstName, String lastName) {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbfoodorderingsystem", "root", "");
            String insertQuery = "INSERT INTO tbluser (username, password, first_name, last_name) VALUES (?, ?, ?, ?)";
            PreparedStatement insertStatement = con.prepareStatement(insertQuery);
            insertStatement.setString(1, username);
            insertStatement.setString(2, password);
            insertStatement.setString(3, firstName);
            insertStatement.setString(4, lastName);
            insertStatement.executeUpdate();
            
            System.out.println("SYSTEM NOTICE: Successfully created your user account!");
            System.out.println("Please proceed to log in with your user account.");
            login.LogIn();
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }
    
    public void createAccount(String username, String password, String firstName, String lastName, String email) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbfoodorderingsystem", "root", "");
            String insertQuery = "INSERT INTO tbluser (username, password, first_name, last_name, email) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement insertStatement = con.prepareStatement(insertQuery);
            insertStatement.setString(1, username);
            insertStatement.setString(2, password);
            insertStatement.setString(3, firstName);
            insertStatement.setString(4, lastName);
            insertStatement.setString(5, email);
            insertStatement.executeUpdate();
            
            System.out.println("SYSTEM NOTICE: Successfully created your user account!");
            System.out.println("Please proceed to log in with your user account.");
            login.LogIn();
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }
    
    public void showAccountDetails(String username1, String password1) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbfoodorderingsystem", "root", "")) {
            String selectQuery = "SELECT * FROM tbluser WHERE username = ? and password = ?";
            PreparedStatement selectStatement = con.prepareStatement(selectQuery);
            selectStatement.setString(1, username1);
            selectStatement.setString(2, password1);
            
            ResultSet resultSet = selectStatement.executeQuery();
            if (resultSet.next()) {
                System.out.println("+-----------------USER ACCOUNT DETAILS------------------+");
                System.out.println("User ID: " + resultSet.getInt(1)
                        + "\nUsername: " + resultSet.getString(2)
                        + "\nPassword: " + resultSet.getString(3)
                        + "\nFull Name: " + resultSet.getString(5) + " " + resultSet.getString(4)
                        + "\nEmail Address: " + resultSet.getString(6));
            }
            
            System.out.println("Please select from the available options:"
                        + "\n [1] - Edit account details"
                        + "\n [2] - Delete account"
                        + "\n [3] - Back");
                System.out.print("Choose: ");
                choice = input.next().charAt(0);
                switch (choice) {
                    case '1':
                        System.out.println("SYSTEM NOTICE: You chose [Edit account details].");
                        account.editAccountDetails(username1, password1);
                        break;
                    case '2':
                        deleteAccount(username1, password1);
                        break;
                    case '3':
                        login.finishLogIn(username1, password1);
                        break;
                    default:
                        System.out.println("SYSTEM NOTICE: Invalid input. Please select from the listed options.");
                        showAccountDetails(username1, password1);
                        break;
                }
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }
    
    public void deleteAccount(String username, String password) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbfoodorderingsystem", "root", "")) {
            System.out.println("Are you sure you want to delete your account?"
                        + "\n [1] - Yes"
                        + "\n [2] - No");
                System.out.print("Choose: ");
                choice = input.next().charAt(0);
                switch (choice) {
                    case '1':
                        System.out.println("SYSTEM NOTICE: You chose [Yes].");
                        String deleteQuery = "DELETE FROM tbluser WHERE username = ? and password = ?";
                        PreparedStatement deleteStatement = con.prepareStatement(deleteQuery);
                        deleteStatement.setString(1, username);
                        deleteStatement.setString(2, password);
                        deleteStatement.execute();
                        System.out.println("SYSTEM NOTICE: You deleted your account.");
                        Main.main(null);
                        break;
                    case '2':
                        System.out.println("SYSTEM NOTICE: You chose [No].");
                        showAccountDetails(username, password);
                        break;
                    default:
                        System.out.println("SYSTEM NOTICE: Invalid input. Please select from the listed options.");
                        deleteAccount(username, password);
                        break;
                }
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }
    
    public void editAccountDetails(String username, String password) {
        try {
            System.out.println("+-----------------EDIT ACCOUNT DETAILS------------------+");
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbfoodorderingsystem", "root", "")) {
                String selectQuery = "SELECT * FROM tbluser WHERE username = ? and password = ?";
                PreparedStatement selectStatement = con.prepareStatement(selectQuery);
                selectStatement.setString(1, username);
                selectStatement.setString(2, password);
                ResultSet resultSet = selectStatement.executeQuery();
                if (resultSet.next()) {
                    setUserID(resultSet.getInt(1));
                }

                System.out.println("Please select the account detail that you want to update:"
                        + "\n [1] - Username"
                        + "\n [2] - Password"
                        + "\n [3] - Full Name"
                        + "\n [4] - Email Address"
                        + "\n [5] - Back");
                System.out.print("Choose: ");
                choice = input.next().charAt(0);
                switch (choice) {
                    case '1':
                        System.out.println("SYSTEM NOTICE: You chose [Username].");
                        System.out.print("Change username to: ");
                        setUsername(input.next());

                        updateQuery = "UPDATE tbluser SET username = ? WHERE user_id = ?";
                        updateStatement = con.prepareStatement(updateQuery);
                        updateStatement.setString(1, getUsername());
                        updateStatement.setInt(2, getUserID());
                        updateStatement.executeUpdate();
                        
                        account.showAccountDetails(getUsername(), password);
                        break;
                    case '2':
                        System.out.println("SYSTEM NOTICE: You chose [Password].");
                        System.out.print("Change password to: ");
                        setPassword(input.next());

                        updateQuery = "UPDATE tbluser SET password = ? WHERE user_id = ?";
                        updateStatement = con.prepareStatement(updateQuery);
                        updateStatement.setString(1, getPassword());
                        updateStatement.setInt(2, getUserID());
                        updateStatement.executeUpdate();

                        System.out.println("SYSTEM NOTICE: Successfully updated your password!");

                        account.showAccountDetails(username, getPassword());
                        break;
                    case '3':
                        System.out.println("SYSTEM NOTICE: You chose [Full Name].");
                        System.out.println("Please select the name detail that you want to update:"
                                + "\n [1] - First Name"
                                + "\n [2] - Last Name"
                                + "\n [3] - Back");
                        System.out.print("Choose: ");
                        choice = input.next().charAt(0);
                        switch (choice) {
                            case '1':
                                System.out.print("Change first name to: ");
                                input.nextLine();
                                setFirstName(input.nextLine());

                                updateQuery = "UPDATE tbluser SET first_name = ? WHERE user_id = ?";
                                updateStatement = con.prepareStatement(updateQuery);
                                updateStatement.setString(1, getFirstName());
                                updateStatement.setInt(2, getUserID());
                                updateStatement.executeUpdate();

                                System.out.println("SYSTEM NOTICE: Successfully updated your first name!");

                                account.showAccountDetails(username, password);
                                break;
                            case '2':
                                System.out.print("Change last name to: ");
                                input.nextLine();
                                setLastName(input.nextLine());

                                updateQuery = "UPDATE tbluser SET last_name = ? WHERE user_id = ?";
                                updateStatement = con.prepareStatement(updateQuery);
                                updateStatement.setString(1, getLastName());
                                updateStatement.setInt(2, getUserID());
                                updateStatement.executeUpdate();

                                System.out.println("SYSTEM NOTICE: Successfully updated your last name!");

                                account.showAccountDetails(username, password);
                                break;
                            case '3':
                                account.editAccountDetails(username, password);
                                break;
                            default:
                                System.out.println("SYSTEM NOTICE: Invalid input. Please select from the listed options.");
                                editAccountDetails(username, password);
                                break;
                        }
                        break;
                    case '4':
                        System.out.println("SYSTEM NOTICE: You chose [Email Address].");
                        System.out.print("Change email address to: ");
                        setEmail(input.next());

                        updateQuery = "UPDATE tbluser SET email = ? WHERE user_id = ?";
                        updateStatement = con.prepareStatement(updateQuery);
                        updateStatement.setString(1, getEmail());
                        updateStatement.setInt(2, getUserID());
                        updateStatement.executeUpdate();

                        System.out.println("SYSTEM NOTICE: Successfully updated your email address!");

                        account.showAccountDetails(username, password);
                        break;
                    case '5':
                        login.finishLogIn(username, password);
                        break;
                    default:
                        System.out.println("SYSTEM NOTICE: Invalid input. Please select from the listed options.");
                        editAccountDetails(username, password);
                        break;
                }
            }
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

}
