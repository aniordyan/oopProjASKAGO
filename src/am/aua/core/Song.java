package am.aua.core;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class Song extends AudioFile{
    public enum Genre{CLASSICAL, POP, ROCK, JAZZ, FUNK, RNB};
    private Genre genre;
    private String duration;


    public Song(int id,String name, String creator, Genre genre, String filePath){
        super(id,name,creator,filePath);
        this.genre = genre;
    }

    public Genre getGenre() {
        return this.genre;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        //d = new Duration();
        //this.duration = d.getDuration(this);
    }


    public String toString() {
        final int COLUMN_WIDTH = 40;

        String formattedId = String.format("%-" + 10 + "s", "ID: " + this.getId());
        String formattedTitle = String.format("%-" + COLUMN_WIDTH + "s", "Title: " + this.getName());
        String formattedArtist = String.format("%-" + COLUMN_WIDTH + "s", "Artist: " + this.getCreator());
        String formattedGenre = String.format("%-" + COLUMN_WIDTH + "s", "Genre: " + this.getGenre());

        return formattedId + formattedTitle + formattedArtist + formattedGenre;
    }

}
