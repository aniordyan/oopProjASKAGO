package am.aua.core;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import am.aua.core.SongPlayer;


public class Song extends AudioFile{
    public enum Genre{CLASSICAL, POP, ROCK, JAZZ, FUNK, RNB};
    private Genre genre;
    private String duration;
    private SongPlayer sp;


    public Song(int id,String name, String creator, Genre genre, String filePath){
        super(id,name,creator,filePath);
        this.genre = genre;
        //this.duration = new SongPlayer().calculateDuration(this);

    }

    public Genre getGenre() {
        return this.genre;
    }


    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration){
        this.duration = duration;
    }





    public String toString() {
        final int COLUMN_WIDTH = 40;

        String Id = String.format("%-" + 10 + "s", "ID: " + this.getId());
        String Title = String.format("%-" + COLUMN_WIDTH + "s", "Title: " + this.getName());
        String Artist = String.format("%-" + COLUMN_WIDTH + "s", "Artist: " + this.getCreator());
        String Genre = String.format("%-" + COLUMN_WIDTH + "s", "Genre: " + this.getGenre());


        return Id + Title + Artist + Genre;
    }

}
