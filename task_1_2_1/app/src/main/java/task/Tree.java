
package task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Stack;

/**
 * The `Tree` class represents a node in a tree data structure. 
 * Each node can hold data of any type T and have a parent (except the root node).
 * Nodes can have children, which are also nodes.
 *
 * @param <T> The data type held in the tree nodes.
 */
public class Tree<T> implements Iterable<Tree<T>> {
    /**
     * The data stored in the current node.
     */
    public T data;
    /**
     * The parent of the current node (null if it is the root node).
     */
    private Tree<T> parent;
    /**
     * The set of child nodes for the current node.
     */
    private ArrayList<Tree<T>> childrens;
    /**
     * Enum for store tree traversal method.
     */
    TraversalMethod traversalMethod = TraversalMethod.BFS; 

    /**
     * Creates a new instance of the `Tree` class with the given data and sets its parent to null.
     *
     * @param data The data to be stored in the node.
     * @throws IllegalArgumentException If data is null.
     */
    Tree(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Node data cannot be null");
        }        
        this.data = data;
        this.parent = null;
        childrens = new ArrayList<>();
    }

    /**
     * Enumeration for the tree traversal method 
     * (DFS - depth-first search, BFS - breadth-first search).
     */
    public enum TraversalMethod {
        DFS,
        BFS
    }
    
    /**
     * Adds the given child node to the current node.
     *
     * @param child The child node to add.
     * @return The added child node.
     */
    public Tree<T> addChild(Tree<T> child) {
        child.parent = this;
        this.childrens.add(child);
        return child;
    }

    /**
     * Creates and adds a new node with the given data to the current node.
     *
     * @param child The data for the new node.
     * @return The added child node.
     */
    public Tree<T> addChild(T child) {
        Tree<T> newNode = new Tree<>(child);
        return this.addChild(newNode);
    }

    /**
     * Removes the current node from the tree while preserving its child nodes. 
     * If the current node is the root, it only removes its child nodes.
     */
    public void remove() {
        if (parent != null) {
            parent.childrens.remove(this);
            parent.childrens.addAll(this.childrens);
        }
        for (Tree<T> child : this.childrens) {
            child.parent = this.parent;
        }
        this.childrens.clear();
        this.parent = null;
        this.data = null;
    }

    /**
     * Checks if the current node is equal to another node.
     *
     * @param obj The other node for comparison.
     * @return true if the nodes are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        Tree<T> otherTree = (Tree<T>) obj;
    
        if (!data.equals(otherTree.data)) {
            return false;
        }
    
        if ((parent == null && otherTree.parent != null) || 
            (parent != null && otherTree.parent == null)) {
            return false;
        }

        if (parent != null && parent.data != otherTree.parent.data) {
            return false;
        }
        
        if (childrens.size() != otherTree.childrens.size()) {
            return false;
        }

        return Arrays.equals(this.getAllSubTree().toArray(), otherTree.getAllSubTree().toArray());
    }
    
    /**
     * Gets list of all descendants.
     *
     * @return ArrayList contains all descendants.
     */
    protected ArrayList<T> getAllSubTree() {
        ArrayList<T> result = new ArrayList<>();
        for (Tree<T> node : childrens) {
            result.add(node.data);
            if (!node.childrens.isEmpty()) {
                result.addAll(node.getAllSubTree());
            }
        }
        result.sort(null);
        return result;
    }

    /**
     * Returns the hash code of the current node based on its data, parent, and child nodes.
     *
     * @return The hash code of the node.
     */
    @Override
    public int hashCode() {
        var parentValue = parent == null ? 7 : parent.data.hashCode();
        var values = new int[] {data.hashCode(), parentValue, childrens.hashCode()};
        return Arrays.hashCode(values);
    }

    /**
     * Inner class representing an iterator for depth-first traversal (DFS) of the tree.
     */
    private class DepthFirstIterator implements Iterator<Tree<T>> {
        private Stack<Tree<T>> stack;

        /**
         * Creates a new iterator for depth-first traversal, starting from the root node.
         *
         * @param root The root node of the tree or subtree.
         */
        DepthFirstIterator(Tree<T> root) {
            if (root != null) {
                stack = new Stack<>();
                stack.push(root);
            }
        }

        /**
         * Checks if there is a next node to iterate.
         *
         * @return true if there is a next node, false otherwise.
         */
        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        /**
         * Returns the next node for iteration.
         *
         * @return The next node.
         * @throws NoSuchElementException If there are no nodes available for iteration.
         */
        @Override
        public Tree<T> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Tree<T> current = stack.pop();
            if (current.data == null) {
                throw new ConcurrentModificationException();
            }
            for (Tree<T> child : current.childrens) {
                stack.push(child);
            }
            return current;
        }
    }

    /**
     * Inner class representing an iterator for breadth-first traversal (BFS) of the tree.
     */
    private class BreadthFirstIterator implements Iterator<Tree<T>> {
        private Queue<Tree<T>> queue;

        /**
         * Creates a new iterator for breadth-first traversal, starting from the root node.
         *
         * @param root The root node of the tree or subtree.
         */
        BreadthFirstIterator(Tree<T> root) {
            if (root != null) {
                queue = new LinkedList<>();
                queue.add(root);
            }
        }

        /**
         * Checks if there is a next node to iterate.
         *
         * @return true if there is a next node, false otherwise.
         */
        @Override
        public boolean hasNext() {
            return !queue.isEmpty();
        }

        /**
         * Returns the next node for iteration.
         *
         * @return The next node.
         * @throws NoSuchElementException If there are no nodes available for iteration.
         */
        @Override
        public Tree<T> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Tree<T> current = queue.poll();
            if (current.data == null) {
                throw new ConcurrentModificationException();
            }
            queue.addAll(current.childrens);
            return current;
        }
    }

    /**
     * Returns an iterator for tree traversal
     * based on the currently set traversal method (DFS or BFS).
     *
     * @return The iterator for tree traversal.
     */
    @Override
    public Iterator<Tree<T>> iterator() {
        if (traversalMethod == TraversalMethod.DFS) {
            return new DepthFirstIterator(this);
        } else {
            return new BreadthFirstIterator(this);
        }
    }

    /**
     * Sets the tree traversal method (DFS or BFS) for the tree.
     *
     * @param method The traversal method to set.
     */
    public void setTraverseMethod(TraversalMethod method) {
        traversalMethod = method;
    }

    /**
     * Returns a string representation of the tree by traversing it.
     * According to the set traversal method.
     *
     * @return The string representation of the tree.
     */
    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (Tree<T> node : this) {
            string.append(node.data + " ");
        }
        return string.toString();
    }

    /**
     * Example usage and test cases for the `Tree` class.
     *
     * @param args The command-line arguments (not used).
     */
    @ExcludeFromJacocoGeneratedReport
    public static void main(String[] args) {

        Tree<String> tree = new Tree<>("R1");
        Tree<String> e = tree.addChild("Nope");
        Tree<String> a1 = tree.addChild("A");
        Tree<String> b1 = a1.addChild("B");
        Tree<String> subTree1 = e.addChild("R2");
        Tree<String> d1 = subTree1.addChild("D");
        Tree<String> c1 = subTree1.addChild("C");
        e.remove();

        Tree<String> tree2 = new Tree<>("R1");
        Tree<String> a2 = tree2.addChild("A");
        Tree<String> b2 = a2.addChild("B");
        Tree<String> subTree3 = new Tree<>("R2");
        Tree<String> d2 = subTree3.addChild("D");
        Tree<String> c2 = subTree3.addChild("C");
        tree2.addChild(subTree3);
        tree2.setTraverseMethod(TraversalMethod.DFS);
        System.out.println(tree2.toString());
        System.out.println(tree2.getAllSubTree().toString());
        
        

    }
}
