package Part2JDBCLambda.POJOs;

import Part2JDBCLambda.DAO;
public class OrderReceipt {

    DAO dao;
    int id;
    Customer customer;
    public OrderReceipt(int id, int customerID) {
        dao = DAO.getInstance();
        this.id = id;
        this.customer = dao.findCustomerObj(customerID);
    }

    public int getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }
}
