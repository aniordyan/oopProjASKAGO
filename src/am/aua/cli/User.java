package am.aua.cli;

import am.aua.core.Operations;
import am.aua.exceptions.InvalidGenreException;
import am.aua.exceptions.SongNotFoundException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Scanner;

public class User {

    String folderPath = "src/am/aua/songs";
    String defaultDatabase = "database.txt";

    Operations.SongPlayer songPlayer = new Operations.SongPlayer(folderPath);

    public void EntryPoint() throws SongNotFoundException, UnsupportedAudioFileException, LineUnavailableException, IOException, InvalidGenreException {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println();
            System.out.println("1. Display Songs");
            System.out.println("2. Display Podcasts"); //currently doesnt work
            System.out.println("3. Display existing playlists");
            System.out.println("4. Create new playlist"); // currently doesnt work
            System.out.println("5. Add new song");
            System.out.println();
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    songPlayer.listSongs(defaultDatabase);
                    System.out.println("Select operation");
                    System.out.println("1. Play song | 2. Delete song | 3. Exit");

                    int operation = sc.nextInt();
                    sc.nextLine();
                    switch(operation) {
                        case 1:
                            System.out.print("Enter the ID of the song to play: ");
                            int selectedId = sc.nextInt();
                            songPlayer.playSong(selectedId);

                            break;
                        case 2:
                            System.out.print("Enter the ID of the song to play: ");
                             selectedId = sc.nextInt();
                            songPlayer.deleteSong(selectedId);

                            break;
                        case 3:
                            break;
                    }
                break;

                case 3:
                    songPlayer.createPlaylistByGenre();
                    System.out.println("Choose playlist to play: ");
                    String respone = sc.next().toUpperCase();
                    songPlayer.playlistToPlay(respone);

                    break;

                case 5:
                    songPlayer.addSong();
            }
        }
    }
}
