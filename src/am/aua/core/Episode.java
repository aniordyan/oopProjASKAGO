package am.aua.core;

import java.util.ArrayList;

public class Episode extends AudioFile{
    private ArrayList<String>  partisipants;
    private String publishedDate;
    private String description;


    public Episode(int id,
                   String name,
                   String creator,
                   String filePath,
                   String publishedDate,
                   String description) {
        super(id, name, creator, filePath);
        this.partisipants = new ArrayList<>();
        this.publishedDate = publishedDate;
        this.description = description;
    }

    public ArrayList<String> getPartisipants() {
        return partisipants;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public String getDescription() {
        return description;
    }
}
