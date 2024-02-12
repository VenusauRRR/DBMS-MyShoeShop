package Part2JDBCLambda.POJOs;

public class Category {
    int categoryID;
    String category;

    public Category(int categoryID, String category) {
        this.categoryID = categoryID;
        this.category = category;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public String getCategory() {
        return category;
    }
}
