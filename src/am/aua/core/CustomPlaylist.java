package am.aua.core;

import am.aua.exceptions.SongNotFoundException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class CustomPlaylist extends SongCore{

    public static ArrayList<String> names;
    private boolean wasCreated;

    public CustomPlaylist(){}

    public CustomPlaylist(String folderPath) {
        super(folderPath);
        this.names = new ArrayList<>();
    }



    public boolean createCustomPlaylist() throws IOException, SongNotFoundException {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the playlist: ");
        String playlistName = scanner.nextLine().toUpperCase();

        names.add(playlistName);
        String playlistFileName = playlistName + ".txt";
        File playlistFile = new File(playlistFileName);
        playlistFile.createNewFile();

        System.out.print("Enter the IDs of the songs to add: ");
        String input = scanner.nextLine();

        //either comma or space
        String[] songIds = input.split("[,\\s]+");


        try (PrintWriter writer = new PrintWriter(new FileWriter(playlistFile, true))) {
            for (String idStr : songIds) {
                try {
                    int songId = Integer.parseInt(idStr);
                    Song songToAdd = findSongById(songId);
                    if (songToAdd != null) {
                        writer.println(songToAdd.getId() + "," + songToAdd.getName() + "," +
                                songToAdd.getCreator() + "," + songToAdd.getGenre() + "," +
                                songToAdd.getFilePath());
                        wasCreated = true;
                    } else {
                        System.out.println("Song with ID " + songId + " not found.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input: " + idStr + ". Please enter valid song IDs.");
                }
            }
        }


        return wasCreated;
    }

    public void listPlaylists(){
        for(String s : names)
            System.out.println(s);
    }
}
