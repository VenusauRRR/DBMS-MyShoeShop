package Part2JDBCLambda;

import java.io.IOException;

public class ShopMain {
    public static void main(String[] args) throws IOException {
        Repository repo = new Repository();
        repo.loadData();
        GUI g = new GUI();
    }
}
