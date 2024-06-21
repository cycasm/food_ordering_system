# Food Ordering System

## Description
This is a food ordering system Java console application that uses MySQL as database.

## Some notes
To everyone who will see this project, this is my first time using git and github so pardon my ignorance with a lot of things. I just wanted to save the projects I made during my time in college in cloud and share it with others. I immediately thought of using github.

### What is included in the system
This food ordering system includes the following features:
- Creating an account and logging in
- Viewing of account info and editing it
- Viewing of restaurant menus (appetizer, main course, desserts, drinks)
- Adding the selected product to order
- Able to delete products from your order (order is similar to cart)
- Paying and getting a receipt when you pay

This project is for compliance with our OOP subject. It tries to use the different concepts included in Object-Oriented Programming. I don't know though if I used them right but it's working anyways. Only Polymorphism is not used because I don't know how to apply it here.

If there are any professionals or anyone who is good at coding Java who comes across with this project, feel free to leave any suggestions on how to improve it.

### IDE and JDK
For this project, I used Apache NetBeans IDE 16 and  Java(TM) SE Development Kit 17.0.9.

### I lost the sql database for this project
I used xampp to manage the SQL database of this project. However, I uninstalled it to clear my storage and forgot to save all my databases.

To download xampp, you can go here: https://www.apachefriends.org/download.html 

I forgot exactly how I made the project (I made this around 6 months ago, today is June 21, 2024) but I remember trying to download a jdbc driver for this. You can also check the following tutorials I found in my YouTube history.

https://youtu.be/0fsdIYiU0fo?si=dN3IvFlbcQ36PUEh - this is my starting point to make the food ordering system. It originally has GUI but I converted it to console and made a lot of changes.

If you follow this video and the other parts (I believe there are part 2 and 3), you will know how they used xampp to create the database and the tables.

https://youtu.be/i0c1b_LK5WQ?si=sJ4qq7PFRKO48VVM - How to connect JDBC - Java App with MYSQL

### Tables and Columns in the database
The following table and column names is what I remember creating in the database.
Format: 
**table_name** : description of the table
 column_name1 (data_type , primary key, not null, unique, auto_increment)
 column_name2, ...
 ...
 ...

**tbluser** : table for all the users
user_id(int, primary key, not null, auto_increment)
username (varchar, not null, unique)
password (varchar, not null)
first_name (varchar, not null)
last_name (varchar, not null)
email (varchar)

**appetizer** : appetizer foods menu
product_id (int, primary key, not null, auto_increment)
product_name (varchar, not null)
price (double, not null)

**mainCourse** : main course foods menu
product_id (int, primary key, not null, auto_increment)
product_name (varchar, not null)
price (double, not null)

**desserts** : desserts foods menu
product_id (int, primary key, not null, auto_increment)
product_name (varchar, not null)
price (double, not null)

**drinks** : drinks foods menu
product_id (int, primary key, not null, auto_increment)
product_name (varchar, not null)
price (double, not null)

**tbltemp_order** : table where all foods added to the user's order are placed
product_name (varchar, not null)
price_each (double, not null)
quantity (int, not null)
subtotal_price (double, not null)

### Suggestions we got during presentation that I remember
- Separating the full_name into first_name and last_name (which I already changed right after the presentation)

### What I want to improve
- Subtraction of quantity ordered from the stocks (But I believe this is only applicable to groceries, hardwares, and similar stores. Since this is a restaurant, rather than stocks, we can have a status if the food is available for order or not.)
- Inclusion of an admin user and an admin menu (The admin user can change which foods are available for order, change the price of a food, and add foods on the menu. They can also set up a new menu or an limited-time menu)
- Preventing duplicates in the tbltemp_order. (I don't know if I already fixed this)



