package am.aua.demo.core;

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
        Set<String> creators = new HashSet<>();

        for (Episode episode : episodes) {
            creators.add(episode.getCreator());
        }

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



    public void deleteEpisode(Episode episodeToDelete ){
        if (episodeToDelete != null) {
            episodes.remove(episodeToDelete);
            updateDatabase();
        }
    }


    public void addEpisode(Episode episodeToAdd) {
        if (episodeToAdd != null) {
            episodes.add(episodeToAdd);
            updateDatabase();
        }
    }

    public void updateDatabase() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(databasePath))) {
            for (Episode e : episodes) {
                writer.println(e.getName() + "," + e.getCreator() +
                        "," + e.getGenre() + "," + e.getFilePath() + "," + e.getPublishedDate());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
