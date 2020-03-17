package play;

public class Matrix {
    private double x;
    private double y;

    Matrix(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Matrix{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
