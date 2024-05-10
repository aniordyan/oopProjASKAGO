package am.aua.demo.ui;


import am.aua.demo.core.AudioFile;
import am.aua.demo.core.Episode;
import am.aua.demo.core.Song;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.*;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;


import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class MenuBarManager extends AudioPlayerUi{
    public enum PlaylistType {
        SONG,
        PODCAST,
        CUSTOM
    }


    private static TableView<String> customPlaylistTableView = new TableView<>();


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
            VBox dropdown = dropDownSongs.isVisible() ? dropDownSongs : dropDownPodcasts;

            ChoiceDialog<String> dialog = new ChoiceDialog<>(null, dropdown.getChildren().stream()
                    .filter(node -> node instanceof Label)
                    .map(node -> ((Label) node).getText())
                    .toArray(String[]::new));
            dialog.setTitle("Delete Playlist");
            dialog.setHeaderText("Select the playlist to delete:");
            dialog.setContentText("Playlist:");

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(playlistName -> {
                dropdown.getChildren().stream()
                        .filter(node -> node instanceof Label && ((Label) node).getText().equals(playlistName))
                        .findFirst()
                        .ifPresent(node -> {
                            ((Label) node).setVisible(false);

                            deletePlaylist(playlistName, dropDownSongs.isVisible() ? PlaylistType.SONG : PlaylistType.PODCAST);
                        });
            });
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
            savePlaylist(name, PlaylistType.SONG);
        });
    }

    public static void createNewCustomPlaylist(AudioPlayerUi parentFrame) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Create Playlist");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter playlist name:");
        dialog.showAndWait().ifPresent(name -> {
            Label playlistLabel = new Label(name);
            ((VBox) parentFrame.mainPanel.getLeft()).getChildren().add(playlistLabel);

            // Create a TableView for the custom playlist
            customPlaylistTableView.getColumns().clear();
            TableColumn<String, String> column = new TableColumn<>(name);
            column.setCellValueFactory(new PropertyValueFactory<>("name"));
            customPlaylistTableView.getColumns().add(column);

            // Add the TableView to the control panel of the AudioPlayerUi
            //mainPanel().getChildren().add(customPlaylistTableView);

            // Save the playlist name to a file
            savePlaylist(name, PlaylistType.CUSTOM);
        });
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
            savePlaylist(name, PlaylistType.PODCAST);
        });
    }

    private static void savePlaylist(String playlistName, PlaylistType type) {
        File file;
        if (type == PlaylistType.SONG) {
            file = new File("song_playlist.txt");
        } else if (type == PlaylistType.PODCAST) {
            file = new File("podcast_playlist.txt");
        } else {
            file = new File("custom_playlist.txt");
        }

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
        loadPlaylists(PlaylistType.SONG, dropDownSongs);
        loadPlaylists(PlaylistType.PODCAST, dropDownPodcasts);
    }

    private static void loadPlaylists(PlaylistType type, VBox dropdown) {
        // Read playlist names from the appropriate file
        File file;
        if (type == PlaylistType.SONG) {
            file = new File("song_playlist.txt");
        } else if (type == PlaylistType.PODCAST) {
            file = new File("podcast_playlist.txt");
        } else {
            // Handle invalid type
            return;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String playlistName = scanner.nextLine();
                Label playlistLabel = createClickableLabel(playlistName);
                playlistLabel.setOnMouseClicked(event -> {
                    if (type == PlaylistType.SONG) {
                        loadSongs(playlistName + ".txt");
                    } else if (type == PlaylistType.PODCAST) {
                        loadEpisodes(playlistName + ".txt");
                    }
                });
                dropdown.getChildren().add(playlistLabel);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void deletePlaylist(String playlistName, PlaylistType type) {
        // Determine the file corresponding to the playlist type
        File file;
        if (type == PlaylistType.SONG) {
            file = new File("song_playlist.txt");
        } else if (type == PlaylistType.PODCAST) {
            file = new File("podcast_playlist.txt");
        } else {
            file = new File("custom_playlist.txt");
        }

        // Remove the playlist name from the file
        try {
            List<String> lines = Files.readAllLines(file.toPath());
            lines.remove(playlistName); // Remove the playlist name from the list
            Files.write(file.toPath(), lines); // Write the updated list back to the file
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
