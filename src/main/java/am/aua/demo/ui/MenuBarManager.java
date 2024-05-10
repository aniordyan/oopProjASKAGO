package am.aua.demo.ui;


import am.aua.demo.core.AudioFile;
import am.aua.demo.core.Episode;
import am.aua.demo.core.Song;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.*;

import javax.swing.*;
import java.io.*;
import java.util.Scanner;

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
            addEpisode(parentFrame);
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
                } else if (selectedAudio instanceof Episode) {
                    Episode selectedEpisode = (Episode) selectedAudio;
                    episodePlayer.deleteEpisode(selectedEpisode);
                    loadEpisodes("podcastDatabase.txt");
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
            createNewSongPlaylist(parentFrame);
        });
        createPlaylistMenu.getItems().add(createSongPlaylistMenuItem);

        MenuItem createPodcastPlaylistMenuItem = new MenuItem("Create Podcast Playlist");
        createPodcastPlaylistMenuItem.setOnAction((ActionEvent event) -> {
            createNewPodcastPlaylist(parentFrame);
        });
        createPlaylistMenu.getItems().add(createPodcastPlaylistMenuItem);

        MenuItem createCustomPlaylistMenuItem = new MenuItem("Create Custom Playlist");
        createCustomPlaylistMenuItem.setOnAction((ActionEvent event) -> {
            createNewCustomPlaylist(parentFrame);
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
                            songPlayer.createPlaylistByGenre();

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

    private static void addEpisode(AudioPlayerUi parentFrame) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Episode");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter episode name:");

        // Get podcast name input
        dialog.showAndWait().ifPresent(name -> {
            // Dialog for entering creator
            TextInputDialog creatorDialog = new TextInputDialog();
            creatorDialog.setTitle("Add episode");
            creatorDialog.setHeaderText(null);
            creatorDialog.setContentText("Enter creator:");

            // Get creator input
            creatorDialog.showAndWait().ifPresent(creator -> {
                // Dialog for entering genre
                TextInputDialog genreDialog = new TextInputDialog();
                genreDialog.setTitle("Add episode");
                genreDialog.setHeaderText(null);
                genreDialog.setContentText("Enter genre:");

                // Get genre input
                genreDialog.showAndWait().ifPresent(genreString -> {
                    // Dialog for entering additional data
                    TextInputDialog dateDialog = new TextInputDialog();
                    dateDialog.setTitle("Add episode");
                    dateDialog.setHeaderText(null);
                    dateDialog.setContentText("Enter date of publish");

                    dateDialog.showAndWait().ifPresent(publishDate -> {
                        try {
                            // Convert genre string to Genre enum
                            Episode.GenrePodcast genre = Episode.GenrePodcast.valueOf(genreString.toUpperCase());

                            // Create and add the podcast
                            Episode e = new Episode(name, creator, genre, chooseFile("Add podcast"),  publishDate);
                            episodePlayer.addEpisode(e);
                            loadEpisodes(currentPlaylistPath);
                            episodePlayer.createPlaylistsByCreators();

                        } catch (Exception ex) {
                            System.out.println("error");
                        }
                    });
                });
            });
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


    private static void createNewSongPlaylist(AudioPlayerUi parentFrame) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Create Playlist");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter playlist name:");

        dialog.showAndWait().ifPresent(name -> {
            Label playlistLabel = createClickableLabel(name);
            playlistLabel.setOnMouseClicked(event -> {
                loadSongs(name + ".txt");
            });
            dropDownSongs.getChildren().add(playlistLabel);

            // Save the playlist name to a file
            savePlaylist(name);
        });
    }

    private static void createNewCustomPlaylist(AudioPlayerUi parentFrame) {
    }

    private static void createNewPodcastPlaylist(AudioPlayerUi parentFrame) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Create Playlist");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter playlist name:");

        dialog.showAndWait().ifPresent(name -> {
            Label playlistLabel = createClickableLabel(name);
            playlistLabel.setOnMouseClicked(event -> {
                loadSongs(name + ".txt");
            });
            dropDownPodcasts.getChildren().add(playlistLabel);

            // Save the playlist name to a file
            savePlaylist(name);
        });
    }

    private static void savePlaylist(String playlistName) {
        File file = new File("playlists.txt");
        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write(playlistName + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

        String createdPlaylist = playlistName + ".txt";
        try (PrintWriter writer = new PrintWriter(new FileWriter(createdPlaylist))) {
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadPlaylists() {
        // Read playlist names from the file
        File file = new File("playlists.txt");
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String playlistName = scanner.nextLine();
                Label playlistLabel = createClickableLabel(playlistName);
                playlistLabel.setOnMouseClicked(event -> {
                    loadSongs(playlistName + ".txt");
                });
                dropDownSongs.getChildren().add(playlistLabel);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


}
