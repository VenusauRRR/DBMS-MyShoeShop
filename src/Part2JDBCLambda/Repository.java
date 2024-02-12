package Part2JDBCLambda;

import Part2JDBCLambda.POJOs.*;

import javax.xml.transform.Result;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class Repository {
    DAO dao;
    static Properties p = new Properties();
    static String propertyPath = "src/Part2JDBCLambda/Database.properties";
    static Connection con;
    Repository() throws IOException {
        dao = DAO.getInstance();
    }
    public void addPwdToKundTable() throws IOException {
        p.load(new FileInputStream(propertyPath));
        String addPwdToKundInDB = "alter table Kund add password varchar(100) not null";
        String updatePwd = "update Kund set password = ? where id = ?";


        try(Connection con = DriverManager.getConnection(
                p.getProperty("ConnectionString"),
                p.getProperty("name"),
                p.getProperty("password"));

            PreparedStatement addPwdCol = con.prepareStatement(addPwdToKundInDB);
            PreparedStatement updatePwdCol = con.prepareStatement(updatePwd);

        ) {
            addPwdCol.executeUpdate();
            updatePwdCol.setString(1,"a123");
            updatePwdCol.setString(2,String.valueOf(1));
            updatePwdCol.executeUpdate();
            updatePwdCol.setString(1,"b456");
            updatePwdCol.setString(2,String.valueOf(2));
            updatePwdCol.executeUpdate();
            updatePwdCol.setString(1,"c789");
            updatePwdCol.setString(2,String.valueOf(3));
            updatePwdCol.executeUpdate();
            updatePwdCol.setString(1,"d001");
            updatePwdCol.setString(2,String.valueOf(4));
            updatePwdCol.executeUpdate();
            updatePwdCol.setString(1,"e002");
            updatePwdCol.setString(2,String.valueOf(5));
            updatePwdCol.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void loadData() throws IOException {
        p.load(new FileInputStream(propertyPath));

        String loadCustomerQuery = "select kund.id, förnamn, efternamn, ortnamn, password from Kund inner join Ort where Kund.ortid = ort.id";
        String loadCategoryQuery = "select * from Kategori";
        String loadBrandQuery = "select * from Märke";
        String loadColorQuery = "select * from Färg";
        String loadSizeQuery = "select * from Storlek";
        String loadModelQuery = "select * from Model";
        String loadProductQuery = "select * from Produkt";
        String loadOrderReceiptQuery = "select * from Kvitto";
        String loadOrderQuery = "select * from Beställning";

        try(Connection con = DriverManager.getConnection(
                p.getProperty("ConnectionString"),
                p.getProperty("name"),
                p.getProperty("password"));

            PreparedStatement loadCustomerStmt = con.prepareStatement(loadCustomerQuery);
            PreparedStatement loadCategoryStmt = con.prepareStatement(loadCategoryQuery);
            PreparedStatement loadBrandStmt = con.prepareStatement(loadBrandQuery);
            PreparedStatement loadColorStmt = con.prepareStatement(loadColorQuery);
            PreparedStatement loadSizeStmt = con.prepareStatement(loadSizeQuery);
            PreparedStatement loadModelStmt = con.prepareStatement(loadModelQuery);
            PreparedStatement loadProductStmt = con.prepareStatement(loadProductQuery);
            PreparedStatement loadOrderReceiptStmt = con.prepareStatement(loadOrderReceiptQuery);
            PreparedStatement loadOrderStmt = con.prepareStatement(loadOrderQuery);

            ResultSet rsCustomer = loadCustomerStmt.executeQuery();
            ResultSet rsCategory = loadCategoryStmt.executeQuery();
            ResultSet rsBrand = loadBrandStmt.executeQuery();
            ResultSet rsColor = loadColorStmt.executeQuery();
            ResultSet rsSize = loadSizeStmt.executeQuery();
            ResultSet rsModel = loadModelStmt.executeQuery();
            ResultSet rsProduct = loadProductStmt.executeQuery();
            ResultSet rsOrderReceipt = loadOrderReceiptStmt.executeQuery();
            ResultSet rsOrder = loadOrderStmt.executeQuery();
        ) {
            while (rsCustomer.next()){
                int id = rsCustomer.getInt("id");
                String firstname = rsCustomer.getString("förnamn");
                String lastname = rsCustomer.getString("efternamn");
                String address = rsCustomer.getString("ortnamn");
                String password = rsCustomer.getString("password");
                dao.addCustomer(new Customer(id,firstname,lastname,address,password));
            }
            dao.createCustomerMapList();

            while (rsBrand.next()){
                int id = rsBrand.getInt("id");
                String brand = rsBrand.getString("namn");
                dao.addBrand(new Brand(id,brand));
            }
            dao.createBrandMapList();

            while (rsCategory.next()){
                int id = rsCategory.getInt("id");
                String namn = rsCategory.getString("namn");
                dao.addCategory(new Category(id, namn));
            }

            while (rsColor.next()){
                int id = rsColor.getInt("id");
                String color = rsColor.getString("namn");
                dao.addColor(new Color(id,color));
            }
            dao.createColorMapList();

            while (rsSize.next()){
                int id = rsSize.getInt("id");
                String size = rsSize.getString("size");
                dao.addSize(new Size(id,size));
            }
            dao.createSizeMapList();

            while (rsModel.next()){
                int id = rsModel.getInt("id");
                int brandid = rsModel.getInt("Märkeid");
                int colorid = rsModel.getInt("Färgid");
                int price = rsModel.getInt("pris");
                dao.addModel(new Model(id,brandid,colorid,price));
            }
            dao.createModelMapList();


            while (rsProduct.next()){
                int id = rsProduct.getInt("id");
                int modelid = rsProduct.getInt("modelid");
                int sizeid = rsProduct.getInt("storlekid");
                int stockSum = rsProduct.getInt("antalExample");
                dao.addProduct(new Product(id,modelid,sizeid,stockSum));
            }
            dao.createProductMapList();

            while (rsOrderReceipt.next()){
                int id = rsOrderReceipt.getInt("id");
                int customerid = rsOrderReceipt.getInt("kundid");
                dao.addOrderReceipt(new OrderReceipt(id,customerid));
            }
            dao.createOrderReceiptMapList();

            while (rsOrder.next()){
                int id = rsOrder.getInt("id");
                int orderReceiptId = rsOrder.getInt("kvittoid");
                int productId = rsOrder.getInt("produktid");
                int amount = rsOrder.getInt("beställantal");
                dao.addOrder(new Order(id, orderReceiptId, productId, amount));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public int addToCartNewOrder(int customerID, int productID) throws IOException, SQLException {
        p.load(new FileInputStream(propertyPath));

        String addToCartQuery = "call AddToCart(null,?,?,1)";
        String findNewOrderReceiptQuery = "select * from Kvitto where kundid=? order by id desc limit 1";

        try(Connection con = DriverManager.getConnection(
                p.getProperty("ConnectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
            CallableStatement stmt = con.prepareCall(addToCartQuery);
            PreparedStatement findReceiptStmt = con.prepareStatement(findNewOrderReceiptQuery);
        ) {
            stmt.setString(1,String.valueOf(customerID));
            stmt.setString(2,String.valueOf(productID));
            int row = stmt.executeUpdate();
//            System.out.println(row);

            findReceiptStmt.setString(1,String.valueOf(customerID));
            ResultSet rs = findReceiptStmt.executeQuery();
            int lastReceiptNr = 0;
            while (rs.next()){
                lastReceiptNr = rs.getInt("id");
            }
            return lastReceiptNr;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
    public void addToCartAddMoreItem(int receiptNr,int chosenProduct) throws IOException, SQLException {
        p.load(new FileInputStream(propertyPath));

        String addToCartQuery = "call AddToCart(?,null,?,1)";

        try(Connection con = DriverManager.getConnection(
                p.getProperty("ConnectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
            CallableStatement stmt = con.prepareCall(addToCartQuery);
        ) {
            stmt.setString(1,String.valueOf(receiptNr));
            stmt.setString(2,String.valueOf(chosenProduct));

            int row = stmt.executeUpdate();
            System.out.println(row);

        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
    public static void main(String[] args) throws IOException {
            Repository repo = new Repository();
            repo.addPwdToKundTable();
            repo.loadData();
    }


}
