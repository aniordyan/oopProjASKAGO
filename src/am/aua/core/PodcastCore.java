package am.aua.core;

import am.aua.exceptions.InvalidGenreException;
import am.aua.exceptions.SongNotFoundException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class PodcastCore implements Playable{
    @Override
    public void listFiles(String path) throws UnsupportedAudioFileException, LineUnavailableException, IOException {

    }

    @Override
    public void playFiles(int id) throws SongNotFoundException {

    }

    @Override
    public void updateDatabase() {

    }

    @Override
    public void addFile() throws InvalidGenreException {

    }
}
