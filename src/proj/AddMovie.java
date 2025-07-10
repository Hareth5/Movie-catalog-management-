package proj;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import static proj.Styling.setTitlesStyle;

public class AddMovie {
    protected Label mainTitle, lTitle, lDescription, lReleaseYear, lRating;
    protected TextField title, description, rating;
    protected DatePicker releaseYear; // Changed from TextField to DatePicker
    protected Button cancel, action;
    private Movie movie;

    public AddMovie(Movie movie) {
        this.movie = movie;
        initializeLabels();
        initializeTxts();
        initializeButtons();
        actions();
    }

    private void initializeLabels() { // initialize the scene labels
        mainTitle = new Label("Add new movie");
        lTitle = new Label("Movie title:");
        lDescription = new Label("Movie description:");
        lReleaseYear = new Label("Release year:");
        lRating = new Label("Rating:");

        setTitlesStyle(mainTitle);
    }

    private void initializeTxts() { // initialize the scene text fields
        title = new TextField();
        description = new TextField();
        releaseYear = new DatePicker(); // Changed from TextField to DatePicker
        rating = new TextField();
    }

    private void initializeButtons() { // initialize the scene button
        cancel = new Button("Cancel");
        action = new Button("Add");
    }

    protected GridPane left() { // a method to assemble the left components together
        GridPane data = new GridPane();
        data.setVgap(10);
        data.setHgap(10);
        data.setAlignment(Pos.TOP_LEFT);
        data.setPadding(new Insets(0, 20, 20, 20));

        data.addColumn(0, lTitle, lDescription, lReleaseYear, lRating);
        data.addColumn(1, title, description, releaseYear, rating);
        return data;
    }

    protected HBox bottom() { // a method to assemble the bottom components together
        HBox btns = new HBox(100, cancel, action);
        btns.setAlignment(Pos.CENTER);
        btns.setPadding(new Insets(20));
        return btns;
    }

    protected BorderPane main() {// a method to assemble all components together
        BorderPane main = new BorderPane();
        main.setTop(mainTitle);
        main.setLeft(left());
        main.setBottom(bottom());

        BorderPane.setAlignment(main.getTop(), Pos.CENTER);
        BorderPane.setMargin(main.getTop(), new Insets(20));
        return main;
    }

    private void actions() { // a method to handle all action
        cancel.setOnAction(e -> Main.setMain(new MoviesManagement().main()));

        action.setOnAction(e -> { // adding new product
            boolean action = new MovieHandler().handler(new String[]{title.getText().trim(), description.getText().trim(),
                    releaseYear.getValue() != null ? releaseYear.getValue().getYear() + "" : "", rating.getText().trim()}, movie, false);

            if (action) {
                title.setText("");
                description.setText("");
                releaseYear.setValue(null);
                rating.setText("");
            }
        });
    }
}