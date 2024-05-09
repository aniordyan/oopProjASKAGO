package am.aua.demo.core;

import am.aua.demo.exceptions.InvalidGenreException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class EpisodePlayer {
    private String folderPath;
    private static int highestId = 0;
    private ArrayList<Episode> episodes;

    private static final String databasePath = "podcastDatabase.txt";


    public EpisodePlayer(String folderPath) {
        this.folderPath = folderPath;
        this.episodes = loadPodcastsFromDatabase(databasePath);
        createPlaylistsByCreators();

    }

        public File getEpisodeLocation(Episode episode) throws FileNotFoundException{
        if (episode != null) {
            Path path = Paths.get(episode.getFilePath());
            if (path.isAbsolute()) {
                return new File(episode.getFilePath());
            } else {
                return new File(folderPath + File.separator + episode.getFilePath());
            }
        } else {
            throw new FileNotFoundException("Episode not found.");
        }
    }


    public ArrayList<Episode> loadPodcastsFromDatabase(String path){
        ArrayList<Episode> episodes = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(path))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                String name = parts[0];
                String creator = parts[1];
                Episode.GenrePodcast genre = Episode.GenrePodcast.valueOf(parts[2]);
                String filePath = parts[3];
                String date = parts[4];

                episodes.add(new Episode(name, creator, genre, filePath, date));
            }
            return episodes;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return episodes;
    }

    public void createPlaylistsByCreators() {
        // Create a set to store unique creator names
        Set<String> creators = new HashSet<>();

        // Extract unique creator names
        for (Episode episode : episodes) {
            creators.add(episode.getCreator());
        }

        // Create playlists for each unique creator
        for (String creator : creators) {
            String creatorFileName = creator + ".txt";
            try (PrintWriter writer = new PrintWriter(new FileWriter(creatorFileName))) {
                for (Episode episode : episodes) {
                    if (episode.getCreator().equals(creator)) {
                        writer.println(episode.getName() + "," + episode.getCreator() +
                                "," + episode.getGenre() + "," + episode.getFilePath() + "," + episode.getPublishedDate());
                    }
                }
                System.out.println("Playlist created for creator: " + creator);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }





    public void playFiles(int id) throws FileNotFoundException {

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
