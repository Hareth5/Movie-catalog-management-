package proj;

import java.time.LocalDate;

public class UpdateMovie extends AddMovie {
    private Movie movie;

    public UpdateMovie(Movie movie) { // initialize the scene components
        super(movie);
        this.movie = movie;
        title.setText("Update Movie");
        action.setText("Update");
        fill();
        action();
    }

    private void fill() { // fill the movie data automatically
        title.setText(movie.getTitle());
        description.setText(movie.getDescription());
        releaseYear.setValue(LocalDate.of(movie.getReleaseYear(), 1, 1)); // Set the year, default to Jan 1
        rating.setText(String.valueOf(movie.getRating()));
    }

    private void action() { // a method to handle all actions
        cancel.setOnAction(e -> Main.setMain(new MoviesManagement().main()));
    }
}
