
package task;

public class Vertex<T> {
    private T name;

    public Vertex(T name) {
        this.name = name;
    }

    public T getName() {
        return name;
    }

    public void setName(T name) {
        this.name = name;
    }
}
