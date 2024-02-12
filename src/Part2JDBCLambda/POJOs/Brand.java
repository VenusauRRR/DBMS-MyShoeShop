package Part2JDBCLambda.POJOs;

public class Brand {
    int brandID;
    String brand;

    public Brand(int brandID, String brand) {
        this.brandID = brandID;
        this.brand = brand;
    }

    public int getBrandID() {
        return brandID;
    }

    public String getBrand() {
        return brand;
    }
}
