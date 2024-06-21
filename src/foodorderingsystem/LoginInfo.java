package foodorderingsystem;

public abstract interface LoginInfo {
    public void setUserID(int userID);
    public int getUserID();
    public void setUsername(String username);
    public String getUsername();
    public void setPassword(String password);
    public String getPassword();
    public void setFirstName(String firstName);
    public String getFirstName();
    public void setLastName(String lastName);
    public String getLastName();
    public void setEmail(String email);
    public String getEmail();
}
