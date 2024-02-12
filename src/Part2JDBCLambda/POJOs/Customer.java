package Part2JDBCLambda.POJOs;

public class Customer {
    int customerID;
    String firstname;
    String lastname;
    String address;
    String password;


    public Customer(int customerID, String firstname, String lastname, String address, String password) {
        this.customerID = customerID;
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.password = password;
    }

    public int getCustomerID() {
        return customerID;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }
}
