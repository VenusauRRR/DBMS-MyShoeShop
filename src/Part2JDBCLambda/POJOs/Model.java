package Part2JDBCLambda.POJOs;

import Part2JDBCLambda.DAO;

import java.util.Map;

public class Model {
    int id;
    Brand brand;
    Color color;
    int price;

    private DAO dao;

    public Model(int id, int branID, int colorID, int price) {
        this.dao = DAO.getInstance();
        this.id = id;
        this.brand = dao.findBrandObj(branID);
        this.color = dao.findColorObj(colorID);
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public Brand getBrand() {
        return brand;
    }

    public Color getColor() {
        return color;
    }

    public int getPrice() {
        return price;
    }

    public String toString(){
        return getId() + "\t" + getBrand().getBrand() + "\t" + getColor().getColor() + "\t" + getPrice();
    }
}
