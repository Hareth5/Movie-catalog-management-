package proj;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class Tableview { // a class for initializing all tableViews for showing data
    public static TableView<Movie> moviesTable = new TableView<>();

    public static TableView<Movie> getMoviesTable() { // a method to initialize movie table view
        TableColumn<Movie, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Movie, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Movie, Integer> releaseYearColumn = new TableColumn<>("Release Year");
        releaseYearColumn.setCellValueFactory(new PropertyValueFactory<>("releaseYear"));

        TableColumn<Movie, Double> ratingColumn = new TableColumn<>("Rating");
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));

        moviesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        moviesTable.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #DDDDDD;");

        moviesTable.getColumns().clear();
        moviesTable.getColumns().addAll(titleColumn, descriptionColumn, releaseYearColumn, ratingColumn);
        moviesTable.setItems(MovieCatalog.getMoviesList());
        return moviesTable;
    }
}
