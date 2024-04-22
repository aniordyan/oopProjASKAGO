import am.aua.cli.User;
import am.aua.exceptions.InvalidGenreException;
import am.aua.exceptions.SongNotFoundException;
import am.aua.ui.AudioPlayerUi;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws SongNotFoundException, UnsupportedAudioFileException, LineUnavailableException, IOException, InvalidGenreException {

        javax.swing.SwingUtilities.invokeLater(() -> {
            // Create an instance of the AudioPlayerUI class
            AudioPlayerUi audioPlayerUI = new AudioPlayerUi();
        });

            User user = new User();
            System.out.printf("------------------------------------------%n");
            System.out.printf("      Welcome to ASKAGO Music Player      %n");
            System.out.printf("------------------------------------------%n");
            user.EntryPoint();




    }
}