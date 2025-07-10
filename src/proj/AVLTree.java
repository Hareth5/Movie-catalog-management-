package proj;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Stack;

public class AVLTree {
    private Node root;
    private int rootHeight;

    private class Node {
        private Movie data;
        private int height;
        private Node left, right;

        public Node(Movie element, Node left, Node right) {
            this.data = element;
            this.left = left;
            this.right = right;
            this.height = 0;
        }

        public Node(Movie element) {
            this(element, null, null);
        }

        public Movie getData() {
            return data;
        }

        public void setData(Movie data) {
            this.data = data;
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

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }
    }

    public AVLTree() {
        root = null;
        rootHeight = 0;
    }

    public int getRootHeight() {
        return rootHeight;
    }

    private int height(Node node) {
        if (node == null)
            return -1;

        return node.getHeight();
    }

    private Node rotateWithLeftChild(Node x) {
        Node y = x.getLeft();
        x.setLeft(y.getRight());
        y.setRight(x);
        x.setHeight(Math.max(height(x.getLeft()), height(x.getRight())) + 1);
        y.setHeight(Math.max(height(y.getLeft()), height(y.getRight())) + 1);
        return y;
    }

    private Node rotateWithRightChild(Node y) {
        Node x = y.getRight();
        y.setRight(x.getLeft());
        x.setLeft(y);
        y.setHeight(Math.max(height(y.getLeft()), height(y.getRight())) + 1);
        x.setHeight(Math.max(height(x.getLeft()), height(x.getRight())) + 1);
        return x;
    }

    private Node doubleWithLeftChild(Node x) {
        x.setLeft(rotateWithRightChild(x.getLeft()));
        return rotateWithLeftChild(x);
    }

    private Node doubleWithRightChild(Node y) {
        y.setRight(rotateWithLeftChild(y.getRight()));
        return rotateWithRightChild(y);
    }

    private Node findMax(Node root) {
        if (root == null)
            return null;

        else
            return findMaxHelper(root);
    }

    private Node findMaxHelper(Node root) {
        if (root.getRight() == null)
            return root;
        else
            return findMaxHelper(root.getRight());
    }

    private Node balance(Node root) {
        if (root == null)
            return null;

        int balanceFactor = height(root.getLeft()) - height(root.getRight());

        if (balanceFactor > 1) {
            if (height(root.getLeft().getLeft()) >= height(root.getLeft().getRight()))
                root = rotateWithLeftChild(root);

            else
                root = doubleWithLeftChild(root);

        } else if (balanceFactor < -1) {
            if (height(root.getRight().getRight()) >= height(root.getRight().getLeft()))
                root = rotateWithRightChild(root);

            else
                root = doubleWithRightChild(root);
        }
        return root;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void insert(Movie data) {
        root = insert(data, root);
        rootHeight = height(root);
    }

    private Node insert(Movie movie, Node root) {
        if (root == null)
            return new Node(movie);

        if (movie.compareTo(root.getData()) < 0)
            root.setLeft(insert(movie, root.getLeft()));

        else if (movie.compareTo(root.getData()) > 0)
            root.setRight(insert(movie, root.getRight()));

        root.setHeight(Math.max(height(root.getLeft()), height(root.getRight())) + 1);
        return balance(root);
    }

    public void remove(String title) {
        root = remove(title, root);
        rootHeight = height(root);
    }

    private Node remove(String title, Node root) {
        if (root == null)
            return null;

        if (title.compareTo(root.getData().getTitle()) > 0)
            root.setRight(remove(title, root.getRight()));

        else if (title.compareTo(root.getData().getTitle()) < 0)
            root.setLeft(remove(title, root.getLeft()));

        else {
            if (root.getLeft() == null)
                return root.getRight();

            else if (root.getRight() == null)
                return root.getLeft();

            else {
                root.setData(findMax(root.getLeft()).getData());
                root.setLeft(remove(title, root.getLeft()));
            }
        }
        root.setHeight(Math.max(height(root.getLeft()), height(root.getRight())) + 1);
        return balance(root);
    }

    public ObservableList<Movie> get(String title) {
        return get(title, root);
    }

    private ObservableList<Movie> get(String title, Node root) {
        ObservableList<Movie> movies = FXCollections.observableArrayList();
        if (root == null)
            return movies;

        Stack<Node> nodes = new Stack<>();
        nodes.push(root);

        while (!nodes.isEmpty()) {
            Node current = nodes.pop();
            if (current.getData().getTitle().equalsIgnoreCase(title))
                movies.add(current.getData());

            if (current.getRight() != null)
                nodes.push(current.getRight());
            if (current.getLeft() != null)
                nodes.push(current.getLeft());
        }
        return movies;
    }

    public boolean contains(String title) {
        return contains(title, this.root);
    }

    private boolean contains(String title, Node root) {
        if (root == null)
            return false;

        if (title.compareTo(root.getData().getTitle()) > 0)
            return contains(title, root.getRight());

        else if (title.compareTo(root.getData().getTitle()) < 0)
            return contains(title, root.getLeft());

        return true;
    }

    public Stack<Movie> getMovies() {
        return preOrder(root);
    }

    private Stack<Movie> preOrder(Node root) {
        Stack<Movie> movies = new Stack<>();
        if (root == null)
            return movies;

        Stack<Node> nodes = new Stack<>();
        nodes.push(root);

        while (!nodes.isEmpty()) {
            Node current = nodes.pop();
            movies.push(current.getData());

            if (current.getRight() != null)
                nodes.push(current.getRight());
            if (current.getLeft() != null)
                nodes.push(current.getLeft());
        }
        return movies;
    }

    public ObservableList<Movie> getMoviesBasedReleaseYear(int year) {
        return getMoviesBasedReleaseYear(root, year);
    }

    private ObservableList<Movie> getMoviesBasedReleaseYear(Node root, int year) {
        ObservableList<Movie> movies = FXCollections.observableArrayList();
        if (root == null)
            return movies;

        Stack<Node> nodes = new Stack<>();
        nodes.push(root);

        while (!nodes.isEmpty()) {
            Node current = nodes.pop();
            if (current.getData().getReleaseYear() == year)
                movies.add(current.getData());

            if (current.getRight() != null)
                nodes.push(current.getRight());
            if (current.getLeft() != null)
                nodes.push(current.getLeft());
        }
        return movies;
    }

    public Movie getTopRating() {
        return getTopRating(root);
    }

    private Movie getTopRating(Node root) {
        if (root == null)
            return null;

        float top = 0;
        Movie movie = null;

        Stack<Node> nodes = new Stack<>();
        nodes.push(root);

        while (!nodes.isEmpty()) {
            Node current = nodes.pop();
            float rating = (float) current.getData().getRating();
            if (rating > top) {
                top = rating;
                movie = current.getData();
            }

            if (current.getRight() != null)
                nodes.push(current.getRight());
            if (current.getLeft() != null)
                nodes.push(current.getLeft());
        }
        return movie;
    }

    public Movie getLeastRating() {
        return getLeastRating(root);
    }

    private Movie getLeastRating(Node root) {
        if (root == null)
            return null;

        float top = 10;
        Movie movie = null;

        Stack<Node> nodes = new Stack<>();
        nodes.push(root);

        while (!nodes.isEmpty()) {
            Node current = nodes.pop();
            float rating = (float) current.getData().getRating();
            if (rating < top) {
                top = rating;
                movie = current.getData();
            }

            if (current.getRight() != null)
                nodes.push(current.getRight());
            if (current.getLeft() != null)
                nodes.push(current.getLeft());
        }
        return movie;
    }

    public void clear() {
        if (root != null)
            clear(root);
        rootHeight = 0;
    }

    private void clear(Node root) {
        root.setData(null);
        root.setLeft(null);
        root.setRight(null);
        root.setHeight(0);
    }
}