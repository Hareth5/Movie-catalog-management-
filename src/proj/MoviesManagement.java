package proj;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import static proj.MovieCatalog.getMoviesList;
import static proj.Styling.*;
import static proj.Tableview.moviesTable;

public class MoviesManagement {
    private Button cancel, update, remove;
    private TextField search;
    private Movie movie;

    public MoviesManagement() {
        initialize();
        actions();
    }

    private void initialize() { // a method to initialize Main interface components
        cancel = new Button();
        update = new Button("Update");
        remove = new Button("Remove");

        cancel.setDisable(true);
        setButtonsStatus(true);

        search = new TextField();
        search.setPromptText("Search");
    }

    protected void setButtonsStatus(boolean disable) {
        update.setDisable(disable);
        remove.setDisable(disable);
    }

    protected HBox right() { // a method for search and cancel search button
        setSearchTxtStyle(search);

        cancel.setGraphic(new ImageView("cancel.png"));
        setSmallButtonsStyle(cancel);

        HBox searching = new HBox(7, cancel, search);
        searching.setPadding(new Insets(0, 0, 20, 0));
        searching.setAlignment(Pos.TOP_RIGHT);
        return searching;
    }

    protected TableView<Movie> center() { // a method to get the table view
        return Tableview.getMoviesTable();
    }

    protected HBox bottom() { // a method to assemble the bottom components together
        HBox btns = new HBox(20, update, remove);
        btns.setAlignment(Pos.BOTTOM_LEFT);
        btns.setPadding(new Insets(0, 0, 20, 20));
        return btns;
    }

    public BorderPane main() { // a method to assemble all components together
        BorderPane main = new BorderPane();
        main.setRight(right());
        main.setCenter(center());
        main.setBottom(bottom());
        BorderPane.setMargin(main.getCenter(), new Insets(20));
        BorderPane.setMargin(main.getRight(), new Insets(20));
        return main;
    }

    private void actions() { // a method to handle all action
        search.setOnKeyPressed(e -> { // searching
            if (e.getCode() == KeyCode.ENTER) {
                String searchBy = search.getText().trim();
                if (searchBy.equals("") || !searchBy.matches("^[a-zA-Z0-9 ]+$")) {
                    MyAlert.alert("Warning", "You should enter a valid name to search", Alert.AlertType.WARNING);
                    return;
                }

                ObservableList<Movie> temp;
                if (Character.isDigit(searchBy.charAt(0))) {
                    temp = MovieCatalog.get(Integer.parseInt(searchBy));

                } else
                    temp = MovieCatalog.get(searchBy);

                if (temp.isEmpty()) {
                    MyAlert.alert("Not found", "There are no movies matching this name or release year", Alert.AlertType.INFORMATION);
                    return;
                }

                movie = temp.getFirst();
                moviesTable.setItems(temp);
                cancel.setDisable(false);
                remove.setDisable(false);
                update.setDisable(false);
            }
        });

        cancel.setOnAction(e -> { // cancel search button
            search.setText("");
            moviesTable.setItems(getMoviesList());
            cancel.setDisable(true);
            remove.setDisable(true);
            update.setDisable(true);
        });

        update.setOnAction(e -> update());

        remove.setOnAction(e -> remove());
    }

    private void update() { // a method to update an existing movie info
        if (movie == null) {
            MyAlert.alert("Error", "You should select a movie to update", Alert.AlertType.ERROR);
            cancel.setDisable(true);
            remove.setDisable(true);
            update.setDisable(true);
            return;
        }

        Main.setMain(new UpdateMovie(movie).main());
    }

    private void remove() { // a method to remove an existing movie
        if (movie == null) {
            MyAlert.alert("Error", "You should select a movie to remove", Alert.AlertType.ERROR);
            cancel.setDisable(true);
            remove.setDisable(true);
            update.setDisable(true);
            return;
        }

        MovieCatalog.erase(movie.getTitle());
        MovieCatalog.getMoviesList().remove(movie);
        MyAlert.alert("Success", "Movie removed successfully", Alert.AlertType.INFORMATION);
        Main.setMain(new MoviesManagement().main());
    }
}