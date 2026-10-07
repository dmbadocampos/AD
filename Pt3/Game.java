import java.io.*;

public class Game implements Serializable {

    private static final long serialVersionUID = 1L;

    String name;
    String genre;
    int releaseYear;
    String platform;
    double cost;

    public Game(String name, String genre, int releaseYear, String platform, double cost) {

        this.name = name;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.platform = platform;
        this.cost = cost;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return """
            Name: %s
            Genre: %s
            Release year: %d
            Platform: %s
            Cost: %.2f
        """.formatted(name, genre, releaseYear, platform, cost);
    }

}