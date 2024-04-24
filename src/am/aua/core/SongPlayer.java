package am.aua.core;

import am.aua.exceptions.InvalidGenreException;
import am.aua.exceptions.SongNotFoundException;

import javax.sound.sampled.*;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class SongPlayer implements Playable {

    private String folderPath;
    private ArrayList<Song> songs;
    private static int highestId = 0;
    private static final String databasePath = "songDatabase.txt";


    public SongPlayer() {
    }


    public SongPlayer(String folderPath) {
        this.folderPath = folderPath;
        this.songs = loadSongsFromDatabase(databasePath);

    }

    public File getSongLocation(Song s) throws SongNotFoundException {

        if (s != null) {
            Path path = Paths.get(s.getFilePath());
            if (path.isAbsolute()) {
                return new File(s.getFilePath());
            } else {
                return new File(folderPath + File.separator + s.getFilePath());
            }
        } else {
            throw new SongNotFoundException("Song not found.");
        }
    }


    public ArrayList<Song> loadSongsFromDatabase(String path) {
        ArrayList<Song> songs = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(path))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                if (id > highestId) highestId = id; // for adding song id
                String name = parts[1];
                String creator = parts[2];
                //  int duration = Integer.parseInt(parts[3]);
                Song.Genre genre = Song.Genre.valueOf(parts[3]);
                String filePath = parts[4];
                songs.add(new Song(id, name, creator, genre, filePath));
            }
            return songs;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return songs;

    }

    public void playSong(Song s) throws SongNotFoundException, UnsupportedAudioFileException, IOException {
        File songFile = new File(folderPath + File.separator + s.getFilePath());
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(songFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            audioInputStream.close();

        } catch (LineUnavailableException ex) {
            throw new RuntimeException(ex);
        }


    }



    public void pauseSong(Song s) throws SongNotFoundException, UnsupportedAudioFileException, IOException {
        File songFile = new File(folderPath + File.separator + s.getFilePath());
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(songFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            if (clip.isRunning()) {
                clip.stop(); // Stop playback
            }
            audioInputStream.close();
        } catch (LineUnavailableException ex) {
            throw new RuntimeException(ex);
        }
    }


    public void playFiles(int id) throws SongNotFoundException {

        Song selectedSong = findSongById(id);
        File songFile = getSongLocation(selectedSong);


            try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(songFile);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);

             /*       long duration = clip.getMicrosecondLength() / 1_000_000;
                    long seconds = duration % 60;
                    long minutes = (duration / 60) % 60;
                    long hours = duration / 3600;

                    System.out.println(hours + " : " + minutes + " : " + seconds);

              */
                Scanner sc = new Scanner(System.in);
                String response = "";


                while (!response.equals("Q")) {
                    System.out.println("P = play, S = stop, R = reset, Q = quit");
                    System.out.println("Enter your choice: ");
                    response = sc.next().toUpperCase();

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
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
                e.printStackTrace();
            }
    }


    public Song findSongById(int id) {
        for (Song song : songs) {
            if (song.getId() == id) {
                return song;
            }
        }
        return null;
    }

    public void deleteSong(int id) throws SongNotFoundException {
        Song songToDelete = findSongById(id);
        if (songToDelete != null) {
            songs.remove(songToDelete);
            updateDatabase();
            System.out.println("Song with ID " + id + " has been deleted.");
        } else {
            throw new SongNotFoundException("Song with ID " + id + " not found.");
        }
    }

    public void updateDatabase() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(databasePath))) {
            for (Song song : songs) {
                writer.println(song.getId() + "," + song.getName() + "," + song.getCreator() +
                        "," + song.getGenre() + "," + song.getFilePath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addFile() throws InvalidGenreException {
        Song.Genre genre = null;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the name of the song:");
        String name = scanner.nextLine();
        System.out.println("Enter the artist of the song:");
        String artist = scanner.nextLine();
        System.out.println("Enter the genre of the song:");
        String genreInput = scanner.nextLine();
        try {
            genre = Song.Genre.valueOf(genreInput.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidGenreException("Invalid genre.");
        }

        System.out.println("Enter the path to the audio file:");
        String filePath = scanner.nextLine();

        int id = highestId + 1;

        Song newSong = new Song(id, name, artist, genre, filePath);
        songs.add(newSong);
        updateDatabase();
        System.out.println("Song added successfully!");
    }

    public void createPlaylistByGenre() throws UnsupportedAudioFileException, LineUnavailableException, IOException {

        for (Song.Genre genre : Song.Genre.values()) {
            String genreFileName = genreFileName = genre.toString() + ".txt";
            try (PrintWriter writer = new PrintWriter(new FileWriter(genreFileName))) {
                for (Song song : songs) {
                    if (song.getGenre() == genre) {
                        writer.println(song.getId() + "," + song.getName() + "," + song.getCreator() +
                                "," + song.getGenre() + "," + song.getFilePath());
                    }
                }
                System.out.println(genre.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public ArrayList<Integer> getSongIdsFromGenreDatabase(String genreName) {
        String genreDatabasePath = genreName + ".txt";
        ArrayList<Integer> songIds = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(genreDatabasePath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                songIds.add(id);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return songIds;
    }

    public void playlistToPlayTest(ArrayList<Integer> songIds, boolean shuffle) throws UnsupportedAudioFileException, LineUnavailableException, IOException, SongNotFoundException {
        if (songIds.isEmpty()) {
            System.out.println("Playlist is empty.");
            return;
        }

        if (shuffle) {
            Collections.shuffle(songIds);
        }

        for (int songId : songIds) {
            Song song = findSongById(songId);
            if (song != null) {
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
            } else {
                System.out.println("Song with ID " + songId + " not found.");
            }
        }
    }


 /*   public void playlistToPlay(String respone, boolean shuffle) throws UnsupportedAudioFileException, LineUnavailableException, IOException, SongNotFoundException {
        listFiles(respone + ".txt");
        ArrayList<Song> playlistSongs = loadSongsFromDatabase(respone + ".txt");
        if (shuffle) Collections.shuffle(playlistSongs);

        for (Song songs : playlistSongs) {
            File songFile = getSongLocation(songs);

            try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(songFile);
                Clip clip = AudioSystem.getClip();

                clip.open(audioInputStream);
                clip.start();
                while (clip.getMicrosecondLength() != clip.getMicrosecondPosition()) {}

            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
                e.printStackTrace();
            }
        }

    }

  */


 /*   public String calculateDuration(Song s) {
        try {
            File songFile = new File(s.getFilePath());
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(songFile);
            AudioFormat format = audioInputStream.getFormat();
            long frames = audioInputStream.getFrameLength();
            double durationInSeconds = (frames + 0.0) / format.getFrameRate();

            long hours = (long) (durationInSeconds / 3600);
            long minutes = (long) ((durationInSeconds % 3600) / 60);
            long seconds = (long) (durationInSeconds % 60);

            return String.format("%02d:%02d:%02d", hours, minutes, seconds);
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
            return "Unknown"; // Handle error condition gracefully
        }
    }

  */







}








