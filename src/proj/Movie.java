package proj;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Movie implements Comparable<Movie> {
    private SimpleStringProperty title, description;
    private SimpleIntegerProperty releaseYear;
    private SimpleDoubleProperty rating;

    public Movie(String title, String description, int releaseYear, double rating) throws IllegalArgumentException {
        this.title = new SimpleStringProperty();
        this.description = new SimpleStringProperty();
        this.releaseYear = new SimpleIntegerProperty();
        this.rating = new SimpleDoubleProperty();

        setTitle(title);
        setDescription(description);
        setReleaseYear(releaseYear);
        setRating(rating);
    }

    // Getters and Setters
    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) throws IllegalArgumentException {
        if (isNull(title))
            throw new IllegalArgumentException("Title cannot be empty");

        this.title.set(Character.toUpperCase(title.charAt(0)) + title.substring(1));
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        if (isNull(description))
            throw new IllegalArgumentException("Description cannot be empty");

        this.description.set(description);
    }

    public int getReleaseYear() {
        return releaseYear.get();
    }

    public void setReleaseYear(int releaseYear) throws IllegalArgumentException {
        if (!validation(String.valueOf(releaseYear), "^[0-9]+$"))
            throw new IllegalArgumentException("Title must contain only numbers");

        int year = new GregorianCalendar().get(Calendar.YEAR);

        if (releaseYear < 1800 || releaseYear > year)
            throw new IllegalArgumentException("Release year must be between 1800 and this year");

        this.releaseYear.set(releaseYear);
    }

    public double getRating() {
        return rating.get();
    }

    public void setRating(double rating) throws IllegalArgumentException {
        if (isNull(String.valueOf(rating)))
            throw new IllegalArgumentException("Rating cannot be empty");

        if (!validation(String.valueOf(rating), "^[0-9. ]+$"))
            throw new IllegalArgumentException("Rating must contain only numbers");

        if (rating < 0.0 || rating > 10.0) {
            throw new IllegalArgumentException("Rating must be between 0.0 and 10.0");
        }
        this.rating.set(rating);
    }

    private boolean isNull(String value) {
        return value == null || value.trim().isEmpty();
    }

    private boolean validation(String value, String regex) {
        return value.matches(regex);
    }

    @Override
    public int compareTo(Movie movie) {
        return this.getTitle().compareTo(movie.getTitle());
    }

    @Override
    public boolean equals(Object obj) {
        Movie movie = (Movie) obj;
        return this.getTitle().equals(movie.getTitle()) && this.getReleaseYear() == movie.getReleaseYear()
                && this.getRating() == movie.getRating() && this.getDescription().equals(movie.getDescription());
    }

    @Override
    public String toString() {
        return getTitle();
    }
}