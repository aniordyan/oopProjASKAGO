package am.aua.core;

import java.util.ArrayList;

public class Podcast extends AudioFile{
    public enum GenrePodcast{COMEDY, EDUCATION, SPORT, NEWS};


    private String startYear;
    private ArrayList<Episode> episodes;
    private int numberOfEpisodes;
    private GenrePodcast genre;
    private String description;

    public Podcast(int id,String name, String creator, String filePath, GenrePodcast podcastGenre,
                    ArrayList<Episode> episodes, String startYear, String description){
        super(id,name,creator,filePath);

        this.startYear = startYear;
        this.episodes = episodes;
        this.description = description;
        this.genre = genre;
    }

    public String getStartYear() {
        return startYear;
    }

    public ArrayList<Episode> getEpisodes() {
        return episodes;
    }

    public int getNumberOfEpisodes() {
        return numberOfEpisodes;
    }

    public GenrePodcast getGenre() {
        return genre;
    }

    public String getDescription() {
        return description;
    }
}
