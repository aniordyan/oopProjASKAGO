package am.aua.cli;



import am.aua.core.SongUtility;
import am.aua.core.Playlist;
import am.aua.exceptions.InvalidGenreException;
import am.aua.exceptions.SongNotFoundException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Scanner;

public class User {

    String folderPath = "src/am/aua/songs";
    String defaultDatabase = "database.txt";

    SongUtility songPlayer = new SongUtility(folderPath);
    private Playlist.PlaylistManager playlistManager = new Playlist.PlaylistManager();


    public void EntryPoint() throws SongNotFoundException, UnsupportedAudioFileException, LineUnavailableException, IOException, InvalidGenreException {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println();
            System.out.println("1. Display Songs");
            System.out.println("2. Display Podcasts");
            System.out.println("3. Display existing playlists");
            System.out.println("4. Create new playlist");
            System.out.println("5. Add new song");
            System.out.println();
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    songPlayer.listFiles(defaultDatabase);
                    System.out.println("Select operation");
                    System.out.println("1. Play song | 2. Delete song | 3. Exit");

                    int operation = sc.nextInt();
                    sc.nextLine();
                    switch(operation) {
                        case 1:
                            System.out.print("Enter the ID of the song to play: ");
                            int selectedId = sc.nextInt();
                            songPlayer.playFiles(selectedId);

                            break;
                        case 2:
                            System.out.print("Enter the ID of the song to delete: ");
                            selectedId = sc.nextInt();
                            songPlayer.deleteSong(selectedId);
                            break;
                        case 3:
                            break;
                    }
                break;

                case 3:
                    System.out.println("Local library: ");
                    songPlayer.createPlaylistByGenre();

                    System.out.println("Custom playlists:");
                    for (Playlist playlist : playlistManager.getAllPlaylists()) {
                        System.out.println(playlist.getName());
                        //songPlayer.playlistToPlayTest(playlist.getSongIds(), false);
                    }
                    System.out.println();
                    System.out.println("Choose playlist to play: ");
                    String respone = sc.next().toUpperCase();
                    boolean shuffle;
                    boolean repeat = true;
                    while(repeat){
                        System.out.println("Do you want to enable shuffle play?(y/n)");
                        if(sc.next().equalsIgnoreCase("y")) shuffle = true;
                        else shuffle = false;
                        //songPlayer.playlistToPlay(respone, shuffle);
                        songPlayer.playlistToPlayTest(songPlayer.getSongIdsFromGenreDatabase(respone), shuffle);

                        System.out.println("Do you want to repeat with playlist?(y/n)");
                        if(sc.next().equalsIgnoreCase("y")) repeat = true;
                        else repeat = false;

                    }

                    break;

                case 4:
                    System.out.println("Enter the name of the new playlist:");
                    String playlistName = sc.nextLine();
                    Playlist newPlaylist = new Playlist(playlistName, songPlayer);
                    playlistManager.addPlaylist(newPlaylist);
                    System.out.println("Playlist created successfully!");

                    System.out.println("Add songs to the playlist:");
                    System.out.println("Enter the IDs of the songs to add (separated by spaces):");
                    String input = sc.nextLine();
                    String[] songIds = input.split("\\s+");
                    for (String idStr : songIds) {
                        try {
                            int songId = Integer.parseInt(idStr);
                            newPlaylist.addSong(songId);
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input: " + idStr + ". Please enter valid song IDs.");
                        }
                    }
                    break;



                case 5:
                    songPlayer.addFile();
                break;
            }
        }
    }
}
