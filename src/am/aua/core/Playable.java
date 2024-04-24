package am.aua.core;

import am.aua.exceptions.InvalidGenreException;
import am.aua.exceptions.SongNotFoundException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface Playable { //maybe its a bad idea, maybe it should be just abstract class, maybe we will have other interfaces too

     public class Duration {

     }





     public void playFiles(int id) throws SongNotFoundException;

     public void updateDatabase();

     public void addFile() throws InvalidGenreException;

}


 /*  public class Duration{
        private long hours, minutes, seconds;


        public String getDuration(am.aua.core.AudioFile s) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
            File audioFile = new File("src/am.aua.songs" + File.separator + s.getFilePath());

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);

            long duration = clip.getMicrosecondLength() / 1_000_000;
            seconds = duration % 60;
            minutes = (duration / 60) % 60;
            hours = duration / 3600;

            return hours + ":" + minutes + ":" + seconds;

        }
    }


   */