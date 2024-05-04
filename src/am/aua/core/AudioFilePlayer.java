package am.aua.core;

import am.aua.ui.AudioPlayerUi;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioFilePlayer{
    private String folderPath;
    private Clip clip;


    public AudioFilePlayer(String folderPath) {
        this.folderPath = folderPath;
    }



    public AudioFilePlayer(){}

    public File getFile(String filePath) {
        if (new File(filePath).isAbsolute()) {
            return new File(filePath);
        } else {
            return new File("src/am/aua/audioFiles" + File.separator + filePath);
        }
    }

    public void playAudioFile(AudioFile audioFile) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File file = getFile(audioFile.getFilePath());
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();
        audioInputStream.close();
    }

    public void pauseAudioFile() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    public void resumeAudioFile() {
        if (clip != null && !clip.isRunning()) {
            clip.start();
        }
    }

    public void stopAudioFile() {
        if (clip != null) {
            clip.stop();
            clip.close();
        }
    }

    public long getCurrentPosition() {
        if (clip != null && clip.isRunning()) {
            return clip.getMicrosecondPosition() / 1000; // Convert microseconds to milliseconds
        }
        return 0;
    }


}
