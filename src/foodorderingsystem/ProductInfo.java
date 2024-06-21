package foodorderingsystem;

public abstract interface ProductInfo {
    public abstract void setID (int id);
    public abstract int getID ();
    public abstract void setProductName (String productName);
    public abstract String getProductName ();
    public abstract void setPrice (double price);
    public abstract double getPrice ();
    public abstract void setQuantity (int quantity);
    public abstract int getQuantity ();
}
