package foodorderingsystem;

public abstract interface OrderInfo {
    public void setSubtotalPrice(double subtotalPrice);
    public double getSubtotalPrice();
    public void setTotalPrice(double totalPrice);
    public double getTotalPrice();
    public void setPayment(double payment);
    public double getPayment();
    public void setCashChange(double cashChange);
    public double getCashChange();
}
