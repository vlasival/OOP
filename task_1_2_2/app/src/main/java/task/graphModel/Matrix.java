package task.graphModel;

/**
 * Сlass representing a matrix.
 */
public class Matrix<T extends Number> {
    private T[][] data;

    public Matrix(T[][] data) {
        this.data = data;
    }

    public void setElement(int row, int col, T value) {
        data[row][col] = value;
    }

    public T getElement(int row, int col) {
        return data[row][col];
    }

    public void setData(T[][] data) {
        this.data = data;
    }

    public T[][] getData() {
        return data;
    }

    public T[] getLine(int number) {
        return data[number];
    }
}
