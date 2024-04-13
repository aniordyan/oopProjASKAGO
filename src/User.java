import Exceptions.SongNotFoundException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Scanner;

public class User {

    String folderPath = "src/songs";

    Operations.SongPlayer songPlayer = new Operations.SongPlayer(folderPath);

    public void EntryPoint() throws SongNotFoundException, UnsupportedAudioFileException, LineUnavailableException, IOException {
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
                    // List all songs
                    songPlayer.listSongs();
                    System.out.println("Select operation");
                    System.out.println("1. Play song | 2. Delete song");

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

                    }
                break;

                case 3:
                    songPlayer.createPlaylistByGenre();
                    System.out.println("Choose playlist to play: ");
                    String respone = sc.next();

                    break;


            }
        }
    }
}
