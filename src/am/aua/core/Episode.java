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

    public String getPublishedDate() {
        return publishedDate;
    }

}
