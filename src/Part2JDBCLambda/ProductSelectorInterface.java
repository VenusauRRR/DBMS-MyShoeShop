package Part2JDBCLambda;

import Part2JDBCLambda.POJOs.Product;

import java.util.List;

public interface ProductSelectorInterface {
    boolean filterParameter(Product p, String s);
}
