package am.aua.core;

import am.aua.exceptions.InvalidGenreException;
import am.aua.exceptions.SongNotFoundException;

import javax.sound.sampled.*;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class EpisodePlayer {
    private String folderPath;
    private static int highestId = 0;
    private ArrayList<Episode> episodes;

    private static final String databasePath = "podcastDatabase.txt";


    public EpisodePlayer(String folderPath) {
        this.folderPath = folderPath;
        this.episodes = loadPodcastsFromDatabase(databasePath);

    }

        public File getEpisodeLocation(Episode episode) throws SongNotFoundException{
        if (episode != null) {
            Path path = Paths.get(episode.getFilePath());
            if (path.isAbsolute()) {
                return new File(episode.getFilePath());
            } else {
                return new File(folderPath + File.separator + episode.getFilePath());
            }
        } else {
            throw new SongNotFoundException("Episode not found.");
        }
    }


    public ArrayList<Episode> loadPodcastsFromDatabase(String path){
        ArrayList<Episode> episodes = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(path))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                if (id > highestId) highestId = id;
                String name = parts[1];
                String creator = parts[2];
                Episode.GenrePodcast genre = Episode.GenrePodcast.valueOf(parts[3]);
                String filePath = parts[4];
                String date = parts[5];

                episodes.add(new Episode(id, name, creator, genre, filePath, date));
            }
            return episodes;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return episodes;
    }



    public void playFiles(int id) throws SongNotFoundException {

    }


    public void updateDatabase() {

    }


    public void addFile() throws InvalidGenreException {
/*
here addFile should mean that the user input the name of a podcast and an absolute path to it
and there would be a database with name... and path to the folder
 */
    }
}
