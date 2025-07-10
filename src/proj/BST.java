package proj;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BST {
    private Node root;

    private class Node {
        private Movie data;
        private Node left, right;

        public Node(Movie data, Node left, Node right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }

        public Node(Movie movie) {
            this(movie, null, null);
        }

        public Node getLeft() {
            return left;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public Node getRight() {
            return right;
        }

        public void setRight(Node right) {
            this.right = right;
        }

        public Movie getData() {
            return data;
        }

        public void setData(Movie data) {
            this.data = data;
        }
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void insert(Movie data) {
        root = insert(data, this.root);
    }

    private Node insert(Movie movie, Node root) {
        if (root == null)
            return new Node(movie);

        if (movie.compareTo(root.getData()) > 0)
            root.setRight(insert(movie, root.getRight()));

        else
            root.setLeft(insert(movie, root.getLeft()));

        return root;
    }

    public Stack<Movie> inOrder() {
        return inOrder(root);
    }

    private Stack<Movie> inOrder(Node root) {
        Stack<Movie> movies = new Stack<>();
        if (root == null)
            return movies;

        Stack<Node> stack = new Stack<>();
        Node current = root;

        while (current != null || !stack.isEmpty()) {
            while (current != null) {
                stack.push(current);
                current = current.getLeft();
            }

            current = stack.pop();
            movies.push(current.getData());
            current = current.getRight();
        }
        return movies;
    }

    public Queue<Movie> postOrder() {
        return postOrder(root);
    }

    private Queue<Movie> postOrder(Node root) {
        Queue<Movie> movies = new LinkedList<>();
        if (root == null)
            return movies;

        Stack<Node> stack = new Stack<>();
        Node current = root;

        while (current != null || !stack.isEmpty()) {
            while (current != null) {
                stack.push(current);
                current = current.getLeft();
            }

            current = stack.pop();
            movies.add(current.getData());
            current = current.getRight();
        }
        return movies;
    }
}