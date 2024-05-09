package am.aua.demo.ui;


import am.aua.demo.core.AudioFile;
import am.aua.demo.core.Song;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.*;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class MenuBarManager extends AudioPlayerUi{
    public static MenuBar createMenuBar(AudioPlayerUi parentFrame) {
        MenuBar menuBar = new MenuBar();

        Menu addMenu = new Menu("Add");

        MenuItem addSongItem = new MenuItem("Add Song");
        addSongItem.setOnAction((ActionEvent event) -> {
            addSong(parentFrame);
        });

        MenuItem addPodcastItem = new MenuItem("Add Podcast");
        addPodcastItem.setOnAction((ActionEvent event) -> {
            addPodcast();
        });

        addMenu.getItems().addAll(addSongItem, addPodcastItem);

        Menu deleteMenu = new Menu("Delete");
        MenuItem deleteMenuItem = new MenuItem("Delete audiofile");
        deleteMenuItem.setOnAction((ActionEvent event) -> {
            audioPlayer.stopAudioFile();
            if (selectedAudio != null) {
                if (selectedAudio instanceof Song) {
                    Song selectedSong = (Song) selectedAudio;
                    songPlayer.deleteSong(selectedSong);
                    loadSongs("songDatabase.txt");
                }
                selectedAudio = null;
            }
        });
        deleteMenu.getItems().add(deleteMenuItem);
        MenuItem deletePlaylist = new MenuItem("Delete Playlist");
        deletePlaylist.setOnAction((ActionEvent event) -> {

        });
        deleteMenu.getItems().add(deletePlaylist);



        Menu createPlaylistMenu = new Menu("Create new playlist");
        MenuItem createSongPlaylistMenuItem = new MenuItem("Create Song Playlist");
        createSongPlaylistMenuItem.setOnAction((ActionEvent event) -> {
            createNewPlaylist(parentFrame);
        });
        createPlaylistMenu.getItems().add(createSongPlaylistMenuItem);

        MenuItem createPodcastPlaylistMenuItem = new MenuItem("Create Podcast Playlist");
        createPodcastPlaylistMenuItem.setOnAction((ActionEvent event) -> {
            createNewPlaylist(parentFrame);
        });
        createPlaylistMenu.getItems().add(createPodcastPlaylistMenuItem);

        MenuItem createCustomPlaylistMenuItem = new MenuItem("Create Custom Playlist");
        createCustomPlaylistMenuItem.setOnAction((ActionEvent event) -> {
            createNewPlaylist(parentFrame);
        });
        createPlaylistMenu.getItems().add(createCustomPlaylistMenuItem);


        menuBar.getMenus().addAll(addMenu, deleteMenu, createPlaylistMenu);

        return menuBar;
    }

    private static void addSong(AudioPlayerUi parentFrame) {

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Song");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter song name:");

        // Get song name input
        dialog.showAndWait().ifPresent(name -> {
            // Dialog for entering artist
            TextInputDialog artistDialog = new TextInputDialog();
            artistDialog.setTitle("Add Song");
            artistDialog.setHeaderText(null);
            artistDialog.setContentText("Enter artist:");

            // Get artist input
            artistDialog.showAndWait().ifPresent(artist -> {
                // Dialog for entering genre
                TextInputDialog genreDialog = new TextInputDialog();
                genreDialog.setTitle("Add Song");
                genreDialog.setHeaderText(null);
                genreDialog.setContentText("Enter genre:");

                // Get genre input
                genreDialog.showAndWait().ifPresent(genreString -> {
                    try {
                        Song.Genre genre = Song.Genre.valueOf(genreString.toUpperCase());



                            Song song = new Song(name, artist, genre, chooseFile("Add song"));
                            songPlayer.addSong(song);
                            loadSongs(currentPlaylistPath);

                    } catch (Exception ex) {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Error");
                        errorAlert.setHeaderText(null);
                        errorAlert.setContentText("Error adding song: " + ex.getMessage());
                        errorAlert.showAndWait();
                    }
                });
            });
        });



    }

    private static void addPodcast() {
        // Your add podcast logic here
    }

    private static void createNewPlaylist(AudioPlayerUi parentFrame) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Create Playlist");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter playlist name:");

        dialog.showAndWait().ifPresent(name -> {
            Label playlistLabel = new Label(name);
            ((VBox) parentFrame.mainPanel.getLeft()).getChildren().add(playlistLabel);
        });
    }


    private static String chooseFile(String dialogTitle) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(dialogTitle);
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("WAV Audio Files", "*.wav"));

        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            return selectedFile.getAbsolutePath();
        } else {
            return null;
        }
    }

}
