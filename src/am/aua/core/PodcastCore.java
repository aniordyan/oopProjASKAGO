package am.aua.core;

import am.aua.exceptions.InvalidGenreException;
import am.aua.exceptions.ParamNotFoundException;
import am.aua.exceptions.PodcastNotFoundExcaption;
import am.aua.exceptions.SongNotFoundException;

import javax.sound.sampled.*;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PodcastCore implements Playable{
    private String folderPath;
    private List<Podcast> podcasts;
    private List<String> episodes;
   // private List<Episodes> episodes;
    private static int highestId = 0;
    private static final String databasePath = "database.txt";

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

    public List<Podcast> loadPodcastsFromDatabase(String path){
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
//    public Podcast findPodcastByEpisodId(int episodId){
//        for (Episodes episodes : episodes){
//            if (episodes.getId() == episodId){
//                return episodes;
//            }
//        }
//        return null;
//    }

    @Override
    public void playFiles(int id) throws SongNotFoundException {
        Podcast selectedPodcast = findPodcastById(id);
        File podcastFile = getPodcastLocation(selectedPodcast);
        try{
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(podcastFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);

            Scanner scanner = new Scanner(System.in);
            String response = "";
            while(!response.equals("Q")){
                System.out.println("P = play, S = stop, R = reset, Q = quit");
                System.out.println("Enter your choice: ");
                response = scanner.next().toUpperCase();

                switch (response) {
                    case ("P"):
                        clip.start();
                        break;
                    case ("S"):
                        clip.stop();
                        break;
                    case ("R"):
                        clip.setMicrosecondPosition(0);
                        break;
                    case ("Q"):
                        clip.close();
                        break;

                }
            }
            audioInputStream.close();
        }
        catch(UnsupportedAudioFileException| LineUnavailableException | IOException e){
            e.printStackTrace();
        }
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
        try(PrintWriter printWriter = new PrintWriter(new FileWriter(databasePath))){
            for (Podcast podcast : podcasts){
                printWriter.println(podcast.getId() + ", " + podcast.getName() + ", " + podcast.getCreator() + ", "
                        + podcast.getPartisipants() + ", " + podcast.getGenre() + ", " + podcast.getEpisodes()
                        + ", " + podcast.getDate() + ", " + podcast.getDescribtion() + ", " + podcast.getFilePath());
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void addFile() throws InvalidGenreException {
        Podcast.Genre genre = null;
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Enter the name of the podcast: ");
        String name = scanner.nextLine();
        System.out.println("Enter the name of the podcast host: ");
        String creator = scanner.nextLine();
        System.out.println("How many partisipants does the podcast have?");
        int number = scanner.nextInt();
        String[] partisipants = new String[0];
        for (int i = 0; i < number; i++) {
            System.out.println("Enter the names of the partisipants: ");
            partisipants[i] = scanner.nextLine();
        }
        System.out.println("Enter the genre of the podcast: ");
        String genreInput = scanner.nextLine();
        System.out.println("Enter the number of episodes: ");
        String episodes = scanner.nextLine();
        System.out.println("Enter the date of the podcast: ");
        int date = scanner.nextInt();
        System.out.println("Add describtion to podcast: ");
        String describtion = scanner.nextLine();
        try{
            genre = Podcast.Genre.valueOf(genreInput.toUpperCase());
        }
        catch(IllegalArgumentException e){
            throw new InvalidGenreException("Invalid genre.");
        }
        System.out.println("Enter the path to the file: ");
        String filePath = scanner.nextLine();
        int id = highestId + 1;
        
        Podcast newPodcast = new Podcast(id, name, creator, partisipants, genre, episodes, date, describtion, filePath);
        podcasts.add(newPodcast);
        updateDatabase();
        System.out.println("Podcast has been added successfully.");
    }
}
