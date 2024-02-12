package Part2JDBCLambda.POJOs;

public class Size {
    int sizeID;
    String size;

    public Size(int sizeID, String size) {
        this.sizeID = sizeID;
        this.size = size;
    }

    public int getSizeID() {
        return sizeID;
    }

    public String getSize() {
        return size;
    }
}
