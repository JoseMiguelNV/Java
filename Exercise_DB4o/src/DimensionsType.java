public class DimensionsType {

    protected double width;
    protected double height;


    public DimensionsType(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "DimensionsType{" +
                "width=" + width +
                ", height=" + height +
                '}';
    }
}
