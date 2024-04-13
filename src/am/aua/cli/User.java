package am.aua.cli;

import am.aua.core.Core;
import am.aua.exceptions.InvalidGenreException;
import am.aua.exceptions.SongNotFoundException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Scanner;

public class User {

    String folderPath = "src/am/aua/songs";
    String defaultDatabase = "database.txt";

    Core.SongPlayer songPlayer = new Core.SongPlayer(folderPath);

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
                            System.out.print("Enter the ID of the song to delete: ");
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
                    boolean shuffle;
                    boolean repeat = true;
                    while(repeat){
                        System.out.println("Do you want to enable shuffling?(y/n)");
                        if(sc.next().equalsIgnoreCase("y")) shuffle = true;
                        else shuffle = false;
                        songPlayer.playlistToPlay(respone, shuffle);
                        System.out.println("Do you want to repeat with playlist?(y/n)");
                        if(sc.next().equalsIgnoreCase("y")) repeat = true;
                        else repeat = false;

                    }

                    break;

                case 5:
                    songPlayer.addSong();
            }
        }
    }
}
