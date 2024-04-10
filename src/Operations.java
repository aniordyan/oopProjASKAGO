import Exceptions.SongNotFoundException;

import javax.sound.sampled.*;
import java.io.*;
import java.util.*;


public class Operations {

    public static class SongPlayer {
        private String folderPath;
        private List<Song> songs;

        public SongPlayer(String folderPath) {
            this.folderPath = folderPath;
            this.songs = loadSongsFromDatabase();
        }

        private List<Song> loadSongsFromDatabase() {
            List<Song> songs = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader("database.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    String creator = parts[2];
                    int duration = Integer.parseInt(parts[3]);
                    String filePath = parts[4];
                    songs.add(new Song(id, name, creator, duration, filePath));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return songs;
        }

        public void listSongs() {
            System.out.println("Available songs:");
            for (Song song : songs) {
                System.out.println(song.getId() + ": " + song.getName() + " by " + song.getCreator());
            }
        }

        public void playSong(int id) throws SongNotFoundException {
            Song selectedSong = findSongById(id);
            if (selectedSong != null) {
                File songFile = new File(folderPath + File.separator + selectedSong.getFilePath());
                try {
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(songFile);
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioInputStream);

                    Scanner sc = new Scanner(System.in);
                    String response = "";


                    while(!response.equals("Q")){
                        System.out.println("P = play, S = stop, R = reset, Q = quit");
                        System.out.println("Enter your choice: ");
                        response = sc.next().toUpperCase();

                        switch(response){
                            case("P"): clip.start(); break;
                            case("S"): clip.stop(); break;
                            case("R"): clip.setMicrosecondPosition(0); break;
                            case("Q"): clip.close(); break;

                        }
                    }

                    audioInputStream.close();
                } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Song with ID " + id + " not found.");
            }
        }

        private Song findSongById(int id) {
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

        private void updateDatabase() {
            try (PrintWriter writer = new PrintWriter(new FileWriter("database.txt"))) {
                for (Song song : songs) {
                    writer.println(song.getId() + "," + song.getName() + "," + song.getCreator() + "," +
                            song.getDuration() + "," + song.getFilePath());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



    }
}