package am.aua.demo.core;

import am.aua.demo.exceptions.InvalidGenreException;

import javax.sound.sampled.*;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class SongPlayer extends AudioFilePlayer{

    private String folderPath;
    private long clipPosition;
    private Clip clip;
    private ArrayList<Song> songs;
    private static final String databasePath = "songDatabase.txt";


    public SongPlayer() {
    }


    public SongPlayer(String folderPath) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        this.folderPath = folderPath;
        this.songs = loadSongsFromDatabase(databasePath);
        createPlaylistByGenre();

    }

    public File getSongLocation(Song s) throws FileNotFoundException {

        if (s != null) {
            Path path = Paths.get(s.getFilePath());
            if (path.isAbsolute()) {
                return new File(s.getFilePath());
            } else {
                return new File(folderPath + File.separator + s.getFilePath());
            }
        } else {
            throw new FileNotFoundException("Song not found.");
        }
    }



    public ArrayList<Song> loadSongsFromDatabase(String path) {
        ArrayList<Song> songs = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(path))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                String name = parts[0];
                String creator = parts[1];
                Song.Genre genre = Song.Genre.valueOf(parts[2]);
                String filePath = parts[3];
                songs.add(new Song(name, creator, genre, filePath));
            }
            return songs;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return songs;

    }


    public void deleteSong(Song songToDelete ){
        if (songToDelete != null) {
            songs.remove(songToDelete);
            updateDatabase();
        }
    }


    public void addSong(Song songToAdd) {
        if (songToAdd != null) {
            songs.add(songToAdd);
            updateDatabase();
        }
    }

    public void updateDatabase() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(databasePath))) {
            for (Song song : songs) {
                writer.println(song.getName() + "," + song.getCreator() +
                        "," + song.getGenre() + "," + song.getFilePath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void createPlaylistByGenre(){
        for (Song.Genre genre : Song.Genre.values()) {
            String genreFileName = genre.toString() + ".txt";
            try (PrintWriter writer = new PrintWriter(new FileWriter(genreFileName))) {
                for (Song song : songs) {
                    if (song.getGenre() == genre) {
                        writer.println( song.getName() + "," + song.getCreator() +
                                "," + song.getGenre() + "," + song.getFilePath());
                    }
                }
                System.out.println(genre.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    


    public ArrayList<Song> getSongsFromDatabase(String databasePath) {
        ArrayList<Song> songs = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(databasePath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                String name = parts[0];
                String creator = parts[1];
                Song.Genre genre = Song.Genre.valueOf(parts[2]);
                String filePath = parts[3];
                songs.add(new Song(name, creator, genre, filePath));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return songs;
    }


    public void playlistToPlayTest(ArrayList<Song> songs, boolean shuffle) throws UnsupportedAudioFileException, LineUnavailableException, IOException, FileNotFoundException {
        if (songs.isEmpty()) {
            System.out.println("Playlist is empty.");
            return;
        }

        if (shuffle) {
            Collections.shuffle(songs);
        }

        for (Song song : songs) {
            File songFile = getSongLocation(song);
            try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(songFile);
                Clip clip = AudioSystem.getClip();

                clip.open(audioInputStream);
                clip.start();
                while (clip.getMicrosecondLength() != clip.getMicrosecondPosition()) {
                }

            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
                e.printStackTrace();
            }
        }
    }


}








