package examples.aaronhoskins.com.contentproviders;

public class Movies {
    private String title;
    private String rating;
    private String desciption;
    private String releaseDate;
    private String genre;

    public Movies() {
    }

    public Movies(String title, String rating, String desciption, String releaseDate, String genre) {
        this.title = title;
        this.rating = rating;
        this.desciption = desciption;
        this.releaseDate = releaseDate;
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "Movies{" +
                "title='" + title + '\'' +
                ", rating='" + rating + '\'' +
                ", desciption='" + desciption + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", genre='" + genre + '\'' +
                '}';
    }
}
