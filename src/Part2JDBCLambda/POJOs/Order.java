package Part2JDBCLambda.POJOs;

import Part2JDBCLambda.DAO;

public class Order {
    DAO dao;
    int id;
    OrderReceipt receipt;
    Product product;
    int amount;
    public Order(int id, int receiptId, int productID, int amount) {
        dao = DAO.getInstance();
        this.id = id;
        this.receipt = dao.findOrderReceiptObj(receiptId);
        this.product = dao.findProductObj(productID);
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public OrderReceipt getReceipt() {
        return receipt;
    }

    public Product getProduct() {
        return product;
    }

    public int getAmount() {
        return amount;
    }
}
