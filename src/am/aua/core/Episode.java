package am.aua.core;

import java.util.ArrayList;

public class Episode extends AudioFile{
    public enum GenrePodcast{COMEDY, EDUCATION, SPORT, NEWS};


    private String publishedDate;
    private GenrePodcast genre;

    public Episode(int id,
                   String name,
                   String creator,
                   GenrePodcast genre,
                   String filePath,
                   String publishedDate
                   ) {
        super(id, name, creator, filePath);
        this.publishedDate = publishedDate;
        this.genre = genre;

    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public GenrePodcast getGenre() {
        return genre;
    }

    public void setGenre(GenrePodcast genre) {
        this.genre = genre;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public String toString(){
        final int COLUMN_WIDTH = 40;

        String Title = String.format("%-" + COLUMN_WIDTH + "s",  getName());
        String Artist = String.format("%-" + COLUMN_WIDTH + "s",  getCreator());
        String Genre = String.format("%-" + COLUMN_WIDTH + "s",  getGenre());
        String Date = String.format("%-" + COLUMN_WIDTH + "s",  getPublishedDate());

        return Title + Artist + Genre + Date;
    }

}
