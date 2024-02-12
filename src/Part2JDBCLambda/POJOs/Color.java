package Part2JDBCLambda.POJOs;

public class Color {
    int colorID;
    String color;

    public Color(int colorID, String color) {
        this.colorID = colorID;
        this.color = color;
    }

    public int getColorID() {
        return colorID;
    }

    public String getColor() {
        return color;
    }
}
