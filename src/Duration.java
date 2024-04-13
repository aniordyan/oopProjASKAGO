import am.aua.core.Song;

import javax.sound.sampled.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Duration {
    private long hours;
    private long minutes;
    private long seconds;

    private final String databaseFilePath = "database.txt";


    public void getDuration(Song s) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        File audioFile = new File("src/am.aua.songs" + File.separator + s.getFilePath());

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);

            long duration = clip.getMicrosecondLength() / 1_000_000;
             seconds = duration % 60;
             minutes = (duration / 60) % 60;
             hours = duration / 3600;

    }

    public void appendDurationToDatabase() {
        try {
            File databaseFile = new File(databaseFilePath);
            FileWriter fileWriter = new FileWriter(databaseFile,true);
            fileWriter.write("\n" + hours + ":" + minutes + ":" + seconds);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return hours + ":" + minutes + ":" + seconds;
    }


}



