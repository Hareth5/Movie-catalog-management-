package proj;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SideButtons { // a class for side buttons
    private Button home, add, load, save, info, clear, exit;
    private Stage stage;

    public SideButtons(Stage stage) {
        this.stage = stage;
        initializeSideButtons();
        actions();
    }

    private void initializeSideButtons() { // a method to initialize side buttons
        home = new Button();
        home.setGraphic(new ImageView("home.png"));

        add = new Button();
        add.setGraphic(new ImageView("add.png"));

        load = new Button();
        load.setGraphic(new ImageView("load.png"));

        save = new Button();
        save.setGraphic(new ImageView("save.png"));

        save = new Button();
        save.setGraphic(new ImageView("save.png"));

        info = new Button();
        info.setGraphic(new ImageView("info.png"));

        clear = new Button();
        clear.setGraphic(new ImageView("clear.png"));

        exit = new Button();
        exit.setGraphic(new ImageView("exit.png"));

        Styling.setSideButtonsStyle(home);
        Styling.setSideButtonsStyle(add);
        Styling.setSideButtonsStyle(load);
        Styling.setSideButtonsStyle(save);
        Styling.setSideButtonsStyle(info);
        Styling.setSideButtonsStyle(clear);
        Styling.setSideButtonsStyle(exit);
    }

    public VBox getSideButtons() { // a method to assemble side buttons together
        VBox btns = new VBox(10, home, add, load, save, info, clear, exit);
        btns.setAlignment(Pos.TOP_LEFT);
        btns.setPadding(new Insets(5));

        return btns;
    }

    private void actions() { // a method to handle all action
        home.setOnAction(e -> Main.setMain(new MoviesManagement().main()));
        add.setOnAction(e -> Main.setMain(new AddMovie(null).main()));

        load.setOnAction(e -> { // load data from files
            MovieCatalog.loadMoviesFromFile();
            Main.setMain(new MoviesManagement().main());
        });

        save.setOnAction(e -> { // sava data to file
            MovieCatalog.saveMoviesToFile();
            Main.setMain(new MoviesManagement().main());
        });

//        info.setOnAction(e -> new Information().main()));

        clear.setOnAction(e -> { // clear all data
            if (MyAlert.alert("Clear data", " Are you sure you want to clear all data ?", Alert.AlertType.CONFIRMATION)) {
                MovieCatalog.deallocate();
                Main.setMain(new MoviesManagement().main());
            }
        });

        exit.setOnAction(e -> stage.close());
    }
}
