package am.aua.core;

import am.aua.ui.AudioPlayerUi;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioFilePlayer {
    private String folderPath;
    private Clip clip;


    public AudioFilePlayer(String folderPath) {
        this.folderPath = folderPath;
    }



    public AudioFilePlayer(){}

    public void playAudioFile(AudioFile audioFile) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File file = new File(folderPath + File.separator + audioFile.getFilePath());
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



   /*STUFF FOR SLIDER THAT DIDNT WORK YET
    public int getCurrentPosition(AudioFile audioFile) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        if (clip != null && clip.isOpen()) {
            return (int) clip.getMicrosecondPosition() / 1000;
        }
        return 0;
    }

    public Clip getClip(AudioFile audioFile) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File file = new File(folderPath + File.separator + audioFile.getFilePath());
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);

        if (clip != null) {
            clip.close();
        }

        clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        audioInputStream.close();

        return clip;
    }

    */

}
