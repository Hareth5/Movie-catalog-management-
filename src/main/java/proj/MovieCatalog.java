package proj;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class MovieCatalog {
    private static AVLTree[][][] movies;
    private static int tableSize, rootHeights;
    private static ObservableList<Movie> moviesList;
    private static final Path MOVIES_FILE = Path.of(System.getProperty("user.home"), ".movie-catalog-management", "movies.txt");

    public MovieCatalog(int size) {
        this.tableSize = size;
        movies = new AVLTree[size][26][10];
        rootHeights = 0;
        moviesList = FXCollections.observableArrayList();

        for (int i = 0; i < size; i++)
            for (int j = 0; j < 26; j++) {
                for (int k = 0; k < 10; k++) {
                    movies[i][j][k] = new AVLTree();
                }
            }
    }

    public static AVLTree[][][] getMovies() {
        return movies;
    }

    public static int getTableSize() {
        return tableSize;
    }

    private static int hashFunction(String title) {
        int l = title.length();
        long sum = 0;

        for (int i = 0; i < l; i++) {
            sum += title.charAt(i) * Math.pow(32, i);
        }
        return (int) (sum % tableSize);
    }

    private static void reHash() {
        AVLTree[][][] oldMovies = movies;
        int oldSize = tableSize;

        tableSize = newTableSize();
        movies = new AVLTree[tableSize][26][10];
        for (int i = 0; i < tableSize; i++) {
            for (int j = 0; j < 26; j++) {
                for (int k = 0; k < 10; k++) {
                    movies[i][j][k] = new AVLTree();
                }
            }
        }

        rootHeights = 0;
        for (int i = 0; i < oldSize; i++) {
            for (int j = 0; j < 26; j++) {
                for (int k = 0; k < 10; k++) {
                    Stack<Movie> old = oldMovies[i][j][k].getMovies();
                    if (old != null)
                        while (!old.isEmpty())
                            add(old.pop());
                }
            }
        }
    }

    private static int newTableSize() {
        int p = tableSize * 2;

        while (!isPrime(p))
            p++;

        return p;
    }

    private static boolean isPrime(int n) {
        if (n <= 1)
            return false;

        double m = Math.sqrt(n);
        for (int i = 2; i < m; i++)
            if (n % i == 0)
                return false;

        return true;
    }

    public static void add(Movie movie) {
        int key = hashFunction(movie.getTitle());
        int c = movie.getTitle().charAt(0) - 65;
        int r = movie.getReleaseYear() % 10;
        rootHeights -= movies[key][c][r].getRootHeight();
        movies[key][c][r].insert(movie);
        rootHeights += movies[key][c][r].getRootHeight();

        moviesList.add(movie);

        if ((float) rootHeights / tableSize > 3)
            reHash();
    }

    public static void erase(String title) {
        int key = hashFunction(title);
        for (int i = 0; i < 26; i++) {
            for (int j = 0; j < 10; j++) {
                if (movies[key][i][j].contains(title)) {
                    rootHeights -= movies[key][i][j].getRootHeight();
                    movies[key][i][j].remove(title);
                    rootHeights += movies[key][i][j].getRootHeight();
                }
            }
        }
    }

    public static void erase(String title, int releaseYear) {
        int key = hashFunction(title);
        int c = title.charAt(0) - 65;
        int r = releaseYear % 10;
        if (movies[key][c][r].contains(title)) {
            rootHeights -= movies[key][c][r].getRootHeight();
            movies[key][c][r].remove(title);
            rootHeights += movies[key][c][r].getRootHeight();
        }
    }

    public static void update(String data[], Movie movie) {
        MovieCatalog.erase(movie.getTitle(), movie.getReleaseYear());
        moviesList.remove(movie);

        movie.setTitle(data[0]);
        movie.setDescription(data[1]);
        movie.setReleaseYear(Integer.parseInt(data[2]));
        movie.setRating(Double.parseDouble(data[3]));
        MovieCatalog.add(movie);
    }

    public static ObservableList<Movie> get(String title) {
        ObservableList<Movie> moviesList = FXCollections.observableArrayList();
        int key = hashFunction(title);
        int c = Character.toUpperCase(title.charAt(0)) - 65;
        if (c >= 0 && c < 26) {
            for (int k = 0; k < 10; k++) {
                moviesList.addAll(movies[key][c][k].get(title));
            }
        }
        return moviesList;
    }

    public static ObservableList<Movie> get(int releaseYear) {
        ObservableList<Movie> moviesList = FXCollections.observableArrayList();
        for (int i = 0; i < tableSize; i++) {
            for (int j = 0; j < 26; j++) {
                moviesList.addAll(movies[i][j][releaseYear % 10].getMoviesBasedReleaseYear(releaseYear));
            }
        }
        return moviesList;
    }

    public static boolean contains(Movie movie) {
        for (int i = 0; i < 10; i++) {
            if (movies[hashFunction(movie.getTitle())][movie.getTitle().charAt(0) - 65]
                    [movie.getReleaseYear() % 10].contains(movie.getTitle()))
                return true;
        }
        return false;
    }

    public static ObservableList<Movie> sortASC() {
        BST bst = new BST();
        for (int i = 0; i < tableSize; i++) {
            for (int j = 0; j < 26; j++) {
                for (int k = 0; k < 10; k++) {
                    Stack<Movie> stack = movies[i][j][k].getMovies();
                    if (stack != null)
                        while (!stack.isEmpty())
                            bst.insert(stack.pop());
                }
            }
        }

        ObservableList<Movie> moviesSorted = FXCollections.observableArrayList();
        Queue<Movie> queue = bst.postOrder();
        while (!queue.isEmpty())
            moviesSorted.add(queue.poll());

        return moviesSorted;
    }

    public static ObservableList<Movie> sortDESC() {
        BST bst = new BST();
        for (int i = 0; i < tableSize; i++) {
            for (int j = 0; j < 26; j++) {
                for (int k = 0; k < 10; k++) {
                    Stack<Movie> stack = movies[i][j][k].getMovies();
                    while (!stack.isEmpty())
                        bst.insert(stack.pop());
                }
            }
        }

        ObservableList<Movie> moviesSorted = FXCollections.observableArrayList();
        Stack<Movie> stack = bst.inOrder();
        while (!stack.isEmpty())
            moviesSorted.add(stack.pop());

        return moviesSorted;
    }

    public static void deallocate() {
        for (int i = 0; i < tableSize; i++)
            for (int j = 0; j < 26; j++)
                for (int k = 0; k < 10; k++)
                    if (!movies[i][j][k].isEmpty())
                        movies[i][j][k].clear();

        moviesList.clear();
        rootHeights = 0;
    }

    public static void loadMoviesFromFile() {
        loadMoviesFromFile(ensureDefaultMoviesFile());
    }

    public static boolean loadMoviesFromFile(Path moviesFile) {
        int loadedMovies = 0;
        int skippedMovies = 0;

        try (Scanner scanner = new Scanner(Files.newBufferedReader(moviesFile))) {
            while (scanner.hasNextLine()) {
                String[] arr = new String[4];

                int i = 0;
                while (scanner.hasNextLine()) {
                    String x = scanner.nextLine().trim();
                    if (x.isEmpty())
                        break;

                    String[] line = x.split(":", 2);
                    if (line.length != 2)
                        break;

                    if (i < arr.length)
                        arr[i++] = line[1].trim();
                }

                if (i != 4) {
                    skippedMovies++;
                    continue;
                }

                if (new MovieHandler().handler(arr, null, true))
                    loadedMovies++;
                else
                    skippedMovies++;
            }
            MyAlert.alert("Success", "Movies uploaded successfully.\nAdded: " + loadedMovies
                    + "\nSkipped: " + skippedMovies, Alert.AlertType.INFORMATION);
            return true;

        } catch (IOException e) {
            MyAlert.alert("Error", "File not found", Alert.AlertType.ERROR);
            return false;
        }
    }

    public static void saveMoviesToFile() {
        try {
            Files.createDirectories(MOVIES_FILE.getParent());
        } catch (IOException e) {
            MyAlert.alert("Error", "File not found", Alert.AlertType.ERROR);
            return;
        }

        try (PrintWriter pr = new PrintWriter(Files.newBufferedWriter(MOVIES_FILE))) {
            for (int i = 0; i < tableSize; i++) {
                for (int j = 0; j < 26; j++) {
                    for (int k = 0; k < 10; k++) {
                        Stack<Movie> stack = movies[i][j][k].getMovies();
                        if (stack != null)
                            while (!stack.isEmpty()) {
                                Movie movie = stack.pop();
                                pr.print("Title: " + movie.getTitle() + "\n");
                                pr.print("Description: " + movie.getDescription() + "\n");
                                pr.print("Release year: " + movie.getReleaseYear() + "\n");
                                pr.print("Rating: " + movie.getRating() + "\n\n\n");
                            }
                    }
                }
            }
            MyAlert.alert("Success", "Data saved successfully", Alert.AlertType.INFORMATION);

        } catch (IOException e) {
            MyAlert.alert("Error", "File not found", Alert.AlertType.ERROR);
        }
    }

    private static Path ensureDefaultMoviesFile() {
        try {
            return ensureMoviesFile();
        } catch (IOException e) {
            MyAlert.alert("Error", "File not found", Alert.AlertType.ERROR);
            return MOVIES_FILE;
        }
    }

    private static Path ensureMoviesFile() throws IOException {
        Files.createDirectories(MOVIES_FILE.getParent());
        if (Files.notExists(MOVIES_FILE)) {
            try (InputStream inputStream = MovieCatalog.class.getResourceAsStream("/files/movies.txt")) {
                if (inputStream != null)
                    Files.copy(inputStream, MOVIES_FILE);
                else
                    Files.createFile(MOVIES_FILE);
            }
        }
        return MOVIES_FILE;
    }

    public static ObservableList<Movie> getMoviesList() {
        return moviesList;
    }
}
