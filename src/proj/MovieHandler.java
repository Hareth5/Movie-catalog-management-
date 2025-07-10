package proj;

import javafx.scene.control.Alert;

public class MovieHandler { // class for add and update movies

    public boolean handler(String data[], Movie movie, boolean fromFile) {
        try {
            int releaseYear;
            double rating;

            try {
                releaseYear = Integer.parseInt(data[2]);
            } catch (NumberFormatException e) {
                if (!fromFile)
                    MyAlert.alert("Error", "Release Year must be numeric value", Alert.AlertType.ERROR);
                return false;
            }

            try {
                rating = Double.parseDouble(data[3]);
            } catch (NumberFormatException e) {
                if (!fromFile)
                    MyAlert.alert("Error", "Rating must be numeric value", Alert.AlertType.ERROR);
                return false;
            }

            if (movie == null) {
                Movie newMovie = new Movie(data[0], data[1], releaseYear, rating);
                if (Character.isDigit(data[0].charAt(0))) {
                    if (!fromFile)
                        MyAlert.alert("Error!", "Movie title cannot start with a number", Alert.AlertType.ERROR);
                    return false;
                }

                if (MovieCatalog.contains(newMovie)) {
                    if (!fromFile)
                        MyAlert.alert("Error!", "Movie already exist", Alert.AlertType.ERROR);
                    return false;
                }

                MovieCatalog.add(newMovie);
                if (!fromFile)
                    MyAlert.alert("Success!", "Movie added successfully", Alert.AlertType.INFORMATION);
                return true;

            } else {
                MovieCatalog.update(data, movie);
                MyAlert.alert("Success!", "Movie updated successfully", Alert.AlertType.INFORMATION);
                Main.setMain(new MoviesManagement().main());
                return false;
            }

        } catch (IllegalArgumentException e) {
            if (!fromFile)
                MyAlert.alert("Error", e.getMessage(), Alert.AlertType.ERROR);
            return false;
        }
    }
}
