package Part2JDBCLambda;

import Part2JDBCLambda.POJOs.Customer;
import Part2JDBCLambda.POJOs.Product;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class GUI extends JFrame {
    DAO dao;
    Repository repo;
    ArrayList<JRadioButton> categoryList;
    ArrayList<JRadioButton> brandBtnList;
    ArrayList<JRadioButton> sizeBtnList;
    ArrayList<JRadioButton> colorBtnList;
    ButtonGroup categoryBtnGp;
    ButtonGroup brandBtnGp;
    ButtonGroup sizeBtnGp;
    ButtonGroup colorBtnGp;
    JTextArea displayProduct;
    List<Product> prodcutListAfterSelection;

    static String chosenCategory;
    static String chosenBrand;
    static String chosenSize;
    static String chosenColor;
    static int receiptNr;

    static int orderCounter;
    Customer customer = null;
    JPanel loginPage;
    JPanel productPage;

    GUI() throws IOException {
        dao = DAO.getInstance();
        repo = new Repository();
        prodcutListAfterSelection = dao.getProductList();
        receiptNr=-1;
        orderCounter=0;
        LoginPage();
    }

    public void LoginPage(){
        loginPage = new JPanel(new GridLayout(3,2));
        JLabel loginName = new JLabel("Login Name: ");
        JTextField enterLoginName = new JTextField(10);
        JLabel loginPw = new JLabel("Login Password: ");
        JTextField enterPw = new JTextField(10);
        JButton enter = new JButton("Log In");
        JButton cancel = new JButton("Cancel");
        loginPage.add(loginName);
        loginPage.add(enterLoginName);
        loginPage.add(loginPw);
        loginPage.add(enterPw);
        loginPage.add(enter);
        loginPage.add(cancel);

        this.add(loginPage);
        PanelSetting();

        enter.addActionListener(e -> {

            try{
                System.out.println(dao.getBrandList().size());
                System.out.println(dao.getColorList().size());
                System.out.println(dao.getCustomerList().size());
                System.out.println(dao.getSizeList().size());
                customer = dao.verifyCustomerLogin(enterLoginName.getText(),enterPw.getText());
                if (customer != null) {
                    System.out.println(customer.getFirstname());
                    ProductPage(customer);
                }
            } catch (NullPointerException ex){
                JOptionPane.showMessageDialog(null,ex.getMessage());
                enterLoginName.setText("");
                enterPw.setText("");
            }
        });
        cancel.addActionListener(e -> System.exit(0));
        pack();
    }//loginpage

    public void ProductPage(Customer customer){
        productPage = new JPanel(new GridLayout(8,1));
        JTextField temp = new JTextField("Welcome! " + customer.getFirstname() + " " + customer.getLastname());
        JTextField displayProductTitle = new JTextField("Product id\tModel id\tBrand\tColor\tPrice\tSize\tStock");
        displayProduct = new JTextArea();
        displayProduct.setText(updateProductAfterSelection());
        JButton addToOrder = new JButton("Add To Order");
        productPage.add(temp);
        productPage.add(displayProductTitle);
        productPage.add(displayProduct);
//        productPage.add(loadCategory());
        productPage.add(loadBrand());
        productPage.add(loadColor());
        productPage.add(loadSize());
        productPage.add(addToOrder);

        addToOrder.addActionListener(e -> {
            System.out.println(chosenBrand + " " + chosenColor + " " + chosenSize);
            try {
                if (orderCounter>0){
                    repo.addToCartAddMoreItem(receiptNr,prodcutListAfterSelection.getFirst().getID());
                    System.out.println("orderCounter " + orderCounter);
                    System.out.println("receiptnr " + receiptNr);
                } else {
                    receiptNr = repo.addToCartNewOrder(customer.getCustomerID(),prodcutListAfterSelection.getFirst().getID());
                    System.out.println("receipt:" + receiptNr);
                }
                orderCounter++;
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (SQLException ex1){
                System.out.println("Not enough stock. Choose another.");
            }
        });
        this.setContentPane(productPage);
        PanelSetting();
    }//productpage


    public JPanel loadSize(){
        JPanel sizePanel = new JPanel(new BorderLayout());
        JPanel dataPanel = new JPanel();
        JTextField title = new JTextField("Size:");
        sizePanel.add(title,BorderLayout.NORTH);
        sizePanel.add(dataPanel,BorderLayout.SOUTH);
        sizeBtnList = new ArrayList<>();
        sizeBtnGp = new ButtonGroup();
        dao.getProductList().stream().map(l -> l.getSize().getSize()).distinct().forEach(l -> sizeBtnList.add(new JRadioButton(l)));
        sizeBtnList.forEach(l -> {
            sizeBtnGp.add(l);
            dataPanel.add(l);
        });

        sizeBtnList.forEach(s -> s.addActionListener(e -> {
            chosenSize = sizeBtnList.stream().filter(l -> l.isSelected()).findAny().get().getActionCommand();
            checkStockStatus();
            System.out.println(chosenSize);
                }));
        return sizePanel;
    }

    public JPanel loadColor(){
        JPanel colorPanel = new JPanel(new BorderLayout());
        JPanel dataPanel = new JPanel();
        JTextField title = new JTextField("Color:");
        colorPanel.add(title,BorderLayout.NORTH);
        colorPanel.add(dataPanel,BorderLayout.SOUTH);
        colorBtnList = new ArrayList<>();
        colorBtnGp = new ButtonGroup();
        dao.getProductList().stream().map(l -> l.getModel().getColor().getColor()).distinct().forEach(l -> colorBtnList.add(new JRadioButton(l)));
        colorBtnList.forEach(l -> {
            colorBtnGp.add(l);
            dataPanel.add(l);
        });

        colorBtnList.forEach(s -> s.addActionListener(e -> {
            chosenColor = colorBtnList.stream().filter(l -> l.isSelected()).findAny().get().getActionCommand();
            checkStockStatus();
            System.out.println(chosenColor);
                }));
        return colorPanel;
    }

    public JPanel loadCategory(){
        JPanel categoryPanel = new JPanel(new BorderLayout());
        JPanel dataPanel = new JPanel();
        JTextField title = new JTextField("Category:");
        categoryPanel.add(title,BorderLayout.NORTH);
        categoryPanel.add(dataPanel);
        categoryList = new ArrayList<>();
        categoryBtnGp = new ButtonGroup();
//        dao.getCategoryList().forEach(l -> categoryList.add(new JRadioButton(String.valueOf(l.getCategory()))));
//        categoryList.forEach(l -> {
//            categoryBtnGp.add(l);
//            dataPanel.add(l);
//        });
//
//        categoryList.forEach(s -> s.addActionListener(e -> {
//            chosenCategory = categoryList.stream().filter(l -> l.isSelected()).findAny().get().getActionCommand();
//            System.out.println(chosenCategory);
//        }));
        return categoryPanel;
    }
    public JPanel loadBrand(){
        JPanel brandPanel = new JPanel(new BorderLayout());
        JPanel dataPanel = new JPanel();
        JTextField title = new JTextField("Brand:");
        brandPanel.add(title,BorderLayout.NORTH);
        brandPanel.add(dataPanel, BorderLayout.SOUTH);
        brandBtnList = new ArrayList<>();
        brandBtnGp = new ButtonGroup();
        dao.getProductList().stream().map(l -> l.getModel().getBrand().getBrand()).distinct().forEach(l -> brandBtnList.add(new JRadioButton(l)));
        brandBtnList.forEach(l -> {
            brandBtnGp.add(l);
            dataPanel.add(l);
        });

        brandBtnList.forEach(s -> s.addActionListener(e -> {
            chosenBrand = brandBtnList.stream().filter(l -> l.isSelected()).findAny().get().getActionCommand();
            checkStockStatus();
            System.out.println(chosenBrand);
        }));
        return brandPanel;
    }

    public void PanelSetting(){
        pack();
        setResizable(true);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }//panelsetting

    //Högre ordnings funktionsgränsnitt!
    ProductSelectorInterface selectBrand = (p, s) -> p.getModel().getBrand().getBrand().equals(chosenBrand);
    ProductSelectorInterface selectColor = (p, s) -> p.getModel().getColor().getColor().equals(chosenColor);
    ProductSelectorInterface selectSize = (p, s) -> p.getSize().getSize().equals(chosenSize);

    public List<Product> selectProducts(ProductSelectorInterface psi, List<Product> list, String chosenPara){
        return list.stream()
                .filter(l -> psi.filterParameter(l,chosenPara))
                .toList();
    }

    public void checkStockStatus(){
        List<Product> temp = dao.getProductList();
        if (chosenBrand!=null){
            temp = selectProducts(selectBrand, temp, chosenBrand);
        }

        if (chosenColor!=null){
            temp = selectProducts(selectColor, temp, chosenColor);
        }

        if (chosenSize!=null){
            temp = selectProducts(selectSize, temp, chosenSize);
        }
        prodcutListAfterSelection = temp;
        displayProduct.setText(updateProductAfterSelection());
        System.out.println(customer.getFirstname());
        prodcutListAfterSelection.forEach(l -> System.out.println(l.toString()));
    }

    public String updateProductAfterSelection(){
        ArrayList<String> s = new ArrayList<>();
        prodcutListAfterSelection.stream().forEach(l -> s.add(l.toString()));
        String s1 = s.stream().reduce("",(sum,elem) -> sum.concat(elem).concat("\n"));
        s.stream().forEach(l -> System.out.println(l.toString()));
        return s1;
    }
}
