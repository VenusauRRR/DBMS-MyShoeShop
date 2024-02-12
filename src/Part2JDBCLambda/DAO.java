package Part2JDBCLambda;

import Part2JDBCLambda.POJOs.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DAO {
    private static DAO instance = new DAO();
    private List<Customer> customerList;
    private List<Category> categoryList;
    private List<Size> sizeList;
    private List<Color> colorList;
    private List<Brand> brandList;
    private List<Model> modelList;
    private List<Product> productList;
    private List<Order> orderList;
    private List<OrderReceipt> orderReceiptList;


    private Map<Integer, Brand> brandMapList;
    private Map<Integer,Color> colorMapList;
    private Map<Integer,Size> sizeMapList;
    private Map<Integer,Model> modelMapList;
    private Map<Integer,Customer> customerMapList;
    private Map<Integer,Product> productMapList;
    private Map<Integer,OrderReceipt> orderReceiptMapList;


    private DAO() {
        customerList = new ArrayList<>();
        categoryList = new ArrayList<>();
        brandList = new ArrayList<>();
        sizeList = new ArrayList<>();
        colorList = new ArrayList<>();
        modelList = new ArrayList<>();
        productList = new ArrayList<>();
        orderList = new ArrayList<>();
        orderReceiptList = new ArrayList<>();
    }

    public static DAO getInstance(){
        return instance;
    }

    public List<Customer> getCustomerList() {
        return customerList;
    }

    public List<Size> getSizeList() {
        return sizeList;
    }

    public List<Color> getColorList() {
        return colorList;
    }

    public List<Brand> getBrandList() {
        return brandList;
    }


    public List<Category> getCategoryList() {
        return categoryList;
    }

    public List<Model> getModelList() {
        return modelList;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public List<OrderReceipt> getOrderReceiptList() {
        return orderReceiptList;
    }

    public void addCustomer(Customer customer){
        customerList.add(customer);
    }
    public void addCategory(Category category){
        categoryList.add(category);
    }
    public void addSize(Size size){
        sizeList.add(size);
    }

    public void addColor(Color color){
        colorList.add(color);
    }

    public void addBrand(Brand brand){
        brandList.add(brand);
    }

    public void addModel(Model model) {
        modelList.add(model);
    }

    public void addProduct(Product product){
        productList.add(product);
    }

    public void addOrder(Order order){
        orderList.add(order);
    }

    public void addOrderReceipt(OrderReceipt receipt){
        orderReceiptList.add(receipt);
    }

    public Customer verifyCustomerLogin(String firstname, String password){
        for (Customer c: getCustomerList()) {
            if (c.getFirstname().equals(firstname) && c.getPassword().equals(password)){
                return c;
            }
        }
        throw new NullPointerException("invalid password/name");
    }

    public Customer findCustomerObj(int customerID){
        return customerMapList.get(customerID);
    }

    public Brand findBrandObj(int brandID){
        return brandMapList.get(brandID);
    }

    public Color findColorObj(int colorID){
        return colorMapList.get(colorID);
    }

    public Size findSizeObj(int sizeID){
        return sizeMapList.get(sizeID);
    }

    public Model findModelObj(int modelID){
        return modelMapList.get(modelID);
    }

    public Product findProductObj(int productID){
        return productMapList.get(productID);
    }

    public OrderReceipt findOrderReceiptObj(int receiptID){
        return orderReceiptMapList.get(receiptID);
    }

    public void createCustomerMapList(){
        customerMapList = customerList.stream().collect(Collectors.toMap(l -> l.getCustomerID(),l -> l));
    }
    public void createBrandMapList(){
        brandMapList = brandList.stream().collect(Collectors.toMap(l -> l.getBrandID(),l -> l));
    }

    public void createColorMapList(){
        colorMapList = colorList.stream().collect(Collectors.toMap(l -> l.getColorID(),l -> l));
    }

    public void createSizeMapList(){
        sizeMapList = sizeList.stream().collect(Collectors.toMap(l -> l.getSizeID(), l -> l));
    }

    public void createModelMapList(){
        modelMapList = modelList.stream().collect(Collectors.toMap(l -> l.getId(), l -> l));
    }

    public void createProductMapList(){
        productMapList = productList.stream().collect(Collectors.toMap(l -> l.getID(), l -> l));
    }
    public void createOrderReceiptMapList(){
        orderReceiptMapList = orderReceiptList.stream().collect(Collectors.toMap(l -> l.getId(), l -> l));
    }


}
