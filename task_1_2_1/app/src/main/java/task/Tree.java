
package task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Stack;


public class Tree<T> implements Iterable<T> {
    public final T data;
    public Tree<T> parent;
    private ArrayList<Tree<T>> childrens = new ArrayList<>();
    private boolean isDfs = true;

    Tree(T data) {
        this.data = data;
    }

    public Tree<T> addChild(Tree<T> child) {
        child.parent = this;
        this.childrens.add(child);
        return child;
    }

    public Tree<T> addChild(T child) {
        Tree<T> newNode = new Tree<>(child);
        return this.addChild(newNode);
    }

    public void removeChild(Tree<T> child) {
        this.childrens.remove(child);
    }

    public T getData() {
        return data;
    }

    public Tree<T> getParent() {
        return parent;
    }

    public void setParent(Tree<T> parent) {
        this.parent = parent;
    }

    public List<Tree<T>> getChildren() {
        return childrens;
    }

    public void setDfs(boolean a) {
        if (a) {
            isDfs = true;
        } else {
            isDfs = true;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || (this.getClass() != other.getClass())) {
            return false;
        }
        if (this.getClass() != other.getClass()) {
            return false;
        }
        Tree<T> otherTree = (Tree<T>) other;
        if ((data != otherTree.data) || (childrens.size() != otherTree.childrens.size())) {
            return false;
        }
       for (int i = 0; i < this.childrens.size(); i++) {
            if (this.childrens.get(i) != otherTree.childrens.get(i)) {
                return false;
            }
       }
       return true;
    }

    @Override
    public int hashCode() {
        var values = new int[] {data.hashCode(), childrens.hashCode()};
        return Arrays.hashCode(values);
    }

    @Override
    public Iterator<T> iterator() {
        if (isDfs) {
            return new DepthFirstIterator(this);
        } else {
            return new BreadthFirstIterator(this);
        }
    }

    private class DepthFirstIterator implements Iterator<T> {
        private Stack<Tree<T>> stack = new Stack<>();

        DepthFirstIterator(Tree<T> root) {
            if (root != null) {
                stack.push(root);
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Tree<T> current = stack.pop();
            for (int i = current.childrens.size() - 1; i >= 0; i--) {
                stack.push(current.childrens.get(i));
            }
            return current.data;
        }
    }

    private class BreadthFirstIterator implements Iterator<T> {
        private Queue<Tree<T>> queue = new LinkedList<>();

        BreadthFirstIterator(Tree<T> root) {
            if (root != null) {
                queue.add(root);
            }
        }

        @Override
        public boolean hasNext() {
            return !queue.isEmpty();
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Tree<T> current = queue.poll();
            queue.addAll(current.childrens);
            return current.data;
        }
    }

    public String toStringDfs() {
        DepthFirstIterator iterator = new DepthFirstIterator(this);
        StringBuilder result = new StringBuilder();

        while (iterator.hasNext()) {
            result.append(iterator.next()).append(" ");
        }

        return result.toString();
    }

    public String toStringBfs() {
        BreadthFirstIterator iterator = new BreadthFirstIterator(this);
        StringBuilder result = new StringBuilder();

        while (iterator.hasNext()) {
            result.append(iterator.next()).append(" ");
        }

        return result.toString();
    }

    public static void main(String[] args) {
        Tree<String> tree = new Tree("R1");
        var a = tree.addChild("A");
        var b = a.addChild("B");
        Tree<String> subtree = new Tree("R2");
        subtree.addChild("C");
        subtree.addChild("D");
        tree.addChild(subtree);
        a.removeChild(b);
        System.out.println(tree.toStringDfs());
    }
}
