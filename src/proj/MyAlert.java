package proj;

import javafx.scene.control.*;

import java.util.Optional;


public class MyAlert { // a class for show different alerts
    private static RadioButton move, remove, cancel;
    private static ToggleGroup choices;

    public static boolean alert(String head, String message, Alert.AlertType type) { // a method to show normal alert
        Alert alert = new Alert(type);
        alert.setTitle(head);
        alert.setContentText(message);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

//    public static Object[] removeCategoryAlert() { // a method to show remove category alert
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle("Saving data");
//        alert.getDialogPane().setContent(saveAlertContent());
//        alert.showAndWait();
//        if (((RadioButton) choices.getSelectedToggle()).getText().equals("Reassign products to another existing category"))
//            return new Object[]{categories.getValue(), "update"};
//
//        else if (((RadioButton) choices.getSelectedToggle()).getText().equals("remove the category and all its products"))
//            return new Object[]{null, "remove"};
//
//        else
//            return new Object[]{null, "cancel"};
//    }
//
//    private static VBox saveAlertContent() {
//        Label warning = new Label("Cannot delete category until all products under it are removed or reassigned manually");
//        setAlertLabelsStyle(warning);
//
//        choices = new ToggleGroup();
//        move = new RadioButton("Reassign products to another existing category");
//        remove = new RadioButton("remove the category and all its products");
//        cancel = new RadioButton("Cancel removing");
//
//        move.setToggleGroup(choices);
//        remove.setToggleGroup(choices);
//        cancel.setToggleGroup(choices);
//        cancel.setSelected(true);
//
//        categories = Catalog.getCategories().getAllCategories();
//        categories.setValue(categories.getItems().getFirst());
//
//        HBox hBox = new HBox(20, move, categories);
//        hBox.setAlignment(Pos.CENTER);
//
//        VBox vBox = new VBox(20, warning, hBox, remove, cancel);
//        vBox.setAlignment(Pos.CENTER);
//
//        return vBox;
//    }
}
