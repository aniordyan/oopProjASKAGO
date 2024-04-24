package am.aua.core;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import am.aua.core.SongPlayer;
import am.aua.exceptions.SongNotFoundException;


public class Song extends AudioFile{
    public enum Genre { CLASSICAL, POP, ROCK, JAZZ, FUNK, RNB }

    private Genre genre;
    private String duration;


    public Song(int id, String name, String creator, Genre genre, String filePath) {
        super(id, name, creator, filePath);
        this.genre = genre;
        this.duration = calculateDuration(filePath);
    }

    public Genre getGenre() {
        return genre;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duartion){
        this.duration = duartion;
    }


    private String calculateDuration(String filePath) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/am/aua/songs" + File.separator + filePath));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            long frameLength = clip.getFrameLength();
            float frameRate = clip.getFormat().getFrameRate();
            clip.close();
            audioInputStream.close();


            long milliseconds = (long) (1000 * frameLength / frameRate);


            long seconds = milliseconds / 1000;
            long hours = seconds / 3600;
            seconds %= 3600;
            long minutes = seconds / 60;
            seconds %= 60;


            return String.format("%02d:%02d:%02d", hours, minutes, seconds);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
            return "00:00:00";
        }
    }

    @Override
    public String toString() {
        final int COLUMN_WIDTH = 40;

        String Title = String.format("%-" + COLUMN_WIDTH + "s",  getName());
        String Artist = String.format("%-" + COLUMN_WIDTH + "s",  getCreator());
        String Genre = String.format("%-" + COLUMN_WIDTH + "s",  getGenre());
        String Duration = String.format("%-" + COLUMN_WIDTH + "s", duration);

        return Title + Artist + Genre + Duration;
    }

}
