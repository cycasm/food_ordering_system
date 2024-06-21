package foodorderingsystem;

public class Info implements LoginInfo, ProductInfo, OrderInfo{
    //LoginInfo
    private int userID;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    
    //ProductInfo
    private int id;
    private String productName;
    private double price;
    private int quantity;
    private double subtotalPrice;
    
    //OrderInfo
    private double totalPrice;
    private double payment;
    private double cashChange;
    
    //LoginInfo
    @Override
    public void setUserID(int userID) {
        this.userID = userID;
    }
    @Override
    public int getUserID() {
        return userID;
    }
    @Override
    public void setUsername(String username) {
        this.username = username;
    }
    @Override
    public String getUsername() {
        return username;
    }
    @Override
    public void setPassword(String password) {
        this.password = password;
    }
    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    @Override
    public String getFirstName() {
        return firstName;
    }
    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    @Override
    public String getLastName() {
        return lastName;
    }
    @Override
    public void setEmail(String email) {
        this.email = email;
    }
    @Override
    public String getEmail() {
        return email;
    }
    
    //ProductInfo
    @Override
    public void setID (int id) {
        this.id = id;
    }
    @Override
    public int getID () {
        return id;
    }
    @Override
    public void setProductName (String productName) {
        this.productName = productName;
    }
    @Override
    public String getProductName () {
        return productName;
    }
    @Override
    public void setPrice (double price) {
        this.price = price;
    }
    @Override
    public double getPrice () {
        return price;
    }
    @Override
    public void setQuantity (int quantity) {
        this.quantity = quantity;
    }
    @Override
    public int getQuantity () {
        return quantity;
    }
    @Override
    public void setSubtotalPrice(double subtotalPrice) {
        this.subtotalPrice = subtotalPrice;
    }
    @Override
    public double getSubtotalPrice() {
        return subtotalPrice;
    }
    
    //OrderInfo
    @Override
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    @Override
    public double getTotalPrice() {
        return totalPrice;
    }
    @Override
    public void setPayment(double payment) {
        this.payment = payment;
    }
    @Override
    public double getPayment() {
        return payment;
    }
    @Override
    public void setCashChange(double cashChange) {
        this.cashChange = cashChange;
    }
    @Override
    public double getCashChange() {
        return cashChange;
    }
}
