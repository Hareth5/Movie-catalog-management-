package proj;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
    private MovieCatalog movieCatalog;
    private static BorderPane main;

    public Main() {
        movieCatalog = new MovieCatalog(5);
        main = new BorderPane();
    }

    public static void setMain(Node node) {
        main.setCenter(node);
    }

    @Override
    public void start(Stage primaryStage) {
        SideButtons mat = new SideButtons(primaryStage);
        main.setLeft(mat.getSideButtons());
        main.setStyle("-fx-background-color: #C8C8C8;");
        main.getLeft().setStyle("-fx-background-color: #17A2B8; -fx-pref-width: 40;");

        setMain(new MoviesManagement().main());

        Scene scene = new Scene(main, 900, 700);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        primaryStage.setMaximized(true);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Bomber on fire");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
