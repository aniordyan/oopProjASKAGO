import Exceptions.SongNotFoundException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws SongNotFoundException, UnsupportedAudioFileException, LineUnavailableException, IOException {
            User user = new User();
            System.out.printf("------------------------------------------%n");
            System.out.printf("      Welcome to ASKAGO Music Player      %n");
            System.out.printf("------------------------------------------%n");
            user.EntryPoint();

    }
}