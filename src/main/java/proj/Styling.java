package proj;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

    public class Styling { // a class for all components style
    public static void setSmallButtonsStyle(Button btn) {
        btn.setStyle("-fx-pref-width: 20px;" +
                "-fx-pref-height: 20px;" + "-fx-font-size: 10px;");
    }

    public static void setNextPrevButtonsStyle(Button btn) {
        btn.setStyle("-fx-pref-width: 80px;" +
                "-fx-pref-height: 80px;" + "-fx-font-size: 10px;");
    }


    public static void setSearchTxtStyle(TextField txt) {
        txt.setStyle("-fx-pref-height: 25px; -fx-pref-width: 208px;");
    }


    public static void setTitlesStyle(Label lbl) {
        lbl.setStyle("-fx-font-weight: bold; -fx-font-size: 60px;");
    }

    public static void setAlertLabelsStyle(Label lbl) {
        lbl.setStyle("-fx-font-weight: bold; -fx-font-size: 20px;");
        lbl.setWrapText(true);
    }

    public static void setSideButtonsStyle(Button btn) { // a method to style side buttons
        btn.setStyle("-fx-background-color: #C8C8C8;" + "-fx-text-fill: #E9ECEF;" + "-fx-pref-width: 50px;" +
                "-fx-pref-height: 50px;");
    }
}
