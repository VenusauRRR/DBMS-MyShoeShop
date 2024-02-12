package Part2JDBCLambda.POJOs;

import Part2JDBCLambda.DAO;

import java.util.List;

public class Product{
    DAO dao;
    int id;
    Model model;
    Size size;
    int stockSum;

    public Product(int productID, int modelID, int sizeID, int stockSum) {
        dao = DAO.getInstance();
        this.id = productID;
        this.model = dao.findModelObj(modelID);
        this.size = dao.findSizeObj(sizeID);
        this.stockSum = stockSum;
    }

    public int getID() {
        return id;
    }

    public Model getModel() {
        return model;
    }

    public Size getSize() {
        return size;
    }

    public int getStockSum() {
        return stockSum;
    }

    public String toString(){
        return getID() + "\t" + getModel().toString() + "\t" + getSize().getSize() + "\t" + getStockSum();
    }
}
