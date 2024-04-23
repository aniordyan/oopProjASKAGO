package am.aua.core;

import am.aua.exceptions.InvalidGenreException;
import am.aua.exceptions.SongNotFoundException;

import javax.sound.sampled.*;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class PodcastPlayer implements Playable{
    private String folderPath;
    private ArrayList<Podcast> podcasts;

    private static final String databasePath = "podcastDatabase.txt";

        public File getPodcastLocation(Podcast podcast) throws SongNotFoundException{
        if (podcast != null) {
            Path path = Paths.get(podcast.getFilePath());
            if (path.isAbsolute()) {
                return new File(podcast.getFilePath());
            } else {
                return new File(folderPath + File.separator + podcast.getFilePath());
            }
        } else {
            throw new SongNotFoundException("Podcast not found.");
        }
    }

  /*  public List<Podcast> loadPodcastsFromDatabase(String path){
        List<Podcast> podcasts = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(path))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                if (id > highestId) highestId = id;
                String name = parts[1];
                String creator = parts[2];
                Podcast.Genre genre = Podcast.Genre.valueOf(parts[3]);
                String filePath = parts[4];
                String[] partisipants = new String[]{parts[5]};
                int date = Integer.parseInt(parts[6]);
                String episodes = parts[7];
                String describtion = parts[8];
                podcasts.add(new Podcast(id, name, creator, partisipants, genre, episodes, date, describtion, filePath));
            }
            return podcasts;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return podcasts;
    }



    @Override
    public void listFiles(String path) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        List<Podcast> podcasts = loadPodcastsFromDatabase(path);
        System.out.println("Podcasts:");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~");
        for (Podcast podcast: podcasts){
            System.out.println(podcasts);
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~");
    }
    public Podcast findPodcastById(int id){
        for (Podcast podcast : podcasts){
            if (podcast.getId() == id){
                return podcast;
            }
        }
        return null;
    }


    @Override
    public void playFiles(int id) throws SongNotFoundException {

    }
    public void deletePodcast(int id) throws PodcastNotFoundExcaption{
        Podcast deletePodcast = findPodcastById(id);
        if (deletePodcast != null){
            podcasts.remove(deletePodcast);
            updateDatabase();
            System.out.println("Podcast with ID: " + id + "has been deleted.");
        }
        else {
            throw new PodcastNotFoundExcaption("Podcast with ID: " + id + "not found.");
        }
    }

    @Override
    public void updateDatabase() {

    }

   */

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
/*
here addFile should mean that the user input the name of a podcast and an absolute path to it
and there would be a database with name... and path to the folder
 */
    }
}
