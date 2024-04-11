import Exceptions.SongNotFoundException;

import java.util.Scanner;

public abstract class People {

    String folderPath = "src/songs"; // Replace with the actual folder path

    Operations.SongPlayer songPlayer = new Operations.SongPlayer(folderPath);

    public void EntryPoint() throws SongNotFoundException {
        Scanner sc = new Scanner(System.in);

        while (true) {
            // Options for the user
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
                    System.out.println("1. Play song");
                    System.out.println("2. Delete song");

                    int operation = sc.nextInt();
                    sc.nextLine();
                    switch(operation) {
                        case 1:
                            // Play a selected song by ID
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
            }
        }
    }
}
