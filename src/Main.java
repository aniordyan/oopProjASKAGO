import am.aua.exceptions.InvalidGenreException;
import am.aua.ui.AudioPlayerUi;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws UnsupportedAudioFileException, LineUnavailableException, IOException, InvalidGenreException {

        AudioPlayerUi audioPlayerUI = new AudioPlayerUi();

    }
}