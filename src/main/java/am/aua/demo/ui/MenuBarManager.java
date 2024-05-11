package am.aua.demo.ui;


import am.aua.demo.core.Episode;
import am.aua.demo.core.Song;
import am.aua.demo.exceptions.DuplicateAudioFileException;
import am.aua.demo.exceptions.InvalidGenreException;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.*;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
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
            try {
                addSong();
            } catch (InvalidGenreException e) {
                throw new RuntimeException(e);
            }
        });

        MenuItem addPodcastItem = new MenuItem("Add Podcast");
        addPodcastItem.setOnAction((ActionEvent event) -> {
            addEpisode();
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
            createNewSongPlaylist();
        });
        createPlaylistMenu.getItems().add(createSongPlaylistMenuItem);

        MenuItem createPodcastPlaylistMenuItem = new MenuItem("Create Podcast Playlist");
        createPodcastPlaylistMenuItem.setOnAction((ActionEvent event) -> {
            createNewPodcastPlaylist(parentFrame);
        });
        createPlaylistMenu.getItems().add(createPodcastPlaylistMenuItem);

   /*     MenuItem createCustomPlaylistMenuItem = new MenuItem("Create Custom Playlist");
        createCustomPlaylistMenuItem.setOnAction((ActionEvent event) -> {
            createNewCustomPlaylist(parentFrame);
        });
        createPlaylistMenu.getItems().add(createCustomPlaylistMenuItem);

    */


        menuBar.getMenus().addAll(addMenu, deleteMenu, createPlaylistMenu);

        return menuBar;
    }


    private static void addSong() throws InvalidGenreException{

        List<String> playlistNames = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File("song_playlist.txt"))) {
            while (scanner.hasNextLine()) {
                playlistNames.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        playlistNames.add("All Songs");


        ChoiceDialog<String> dialog = new ChoiceDialog<>(null, playlistNames);
        dialog.setTitle("Select Playlist");
        dialog.setHeaderText("Select the playlist to add the song:");
        dialog.setContentText("Playlist:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(playlistName -> {
            String playlistFileName = playlistName.equals("All Songs") ? "songDatabase.txt" : playlistName + ".txt";
            TextInputDialog songNameDialog = new TextInputDialog();
            songNameDialog.setTitle("Add Song");
            songNameDialog.setHeaderText(null);
            songNameDialog.setContentText("Enter song name:");
            songNameDialog.showAndWait().ifPresent(name -> {
                TextInputDialog artistDialog = new TextInputDialog();
                artistDialog.setTitle("Add Song");
                artistDialog.setHeaderText(null);
                artistDialog.setContentText("Enter artist:");
                artistDialog.showAndWait().ifPresent(artist -> {
                    TextInputDialog genreDialog = new TextInputDialog();
                    genreDialog.setTitle("Add Song");
                    genreDialog.setHeaderText(null);
                    genreDialog.setContentText("Enter genre:");
                    genreDialog.showAndWait().ifPresent(genreString -> {
                        try {
                            Song.Genre genre = Song.Genre.valueOf(genreString.toUpperCase());
                            if (songPlayer.isDuplicateSong(name, artist, genre)) {
                                throw new DuplicateAudioFileException("Duplicate song found: " + name);
                            }
                            Song song = new Song(name, artist, genre, chooseFile("Add song"));
                            songPlayer.addSong(song, playlistFileName);
                            appendToSongDatabase(song);
                            loadSongs(playlistFileName);
                        } catch (IllegalArgumentException ex) {
                            InvalidGenreException invalidGenreException = new InvalidGenreException(genreString);
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText("Invalid Genre");
                            alert.setContentText(invalidGenreException.getMessage());
                            alert.showAndWait();
                        } catch (DuplicateAudioFileException e) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText("Duplicate Song");
                            alert.setContentText(e.getMessage());
                            alert.showAndWait();
                        }
                    });
                });
            });
        });
    }


    private static void appendToSongDatabase(Song song) {
        try {
            PrintWriter generalWriter = new PrintWriter(new FileWriter("songDatabase.txt", true));
            generalWriter.println(song.getName() + "," + song.getCreator() + "," + song.getGenre() + "," + song.getFilePath());
            generalWriter.close();

            String genreFileName = song.getGenre() + ".txt";
            PrintWriter genreWriter = new PrintWriter(new FileWriter(genreFileName, true));
            genreWriter.println(song.getName() + "," + song.getCreator() + "," + song.getGenre() + "," + song.getFilePath());
            genreWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private static void addEpisode() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Episode");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter episode name:");

        dialog.showAndWait().ifPresent(name -> {
            TextInputDialog creatorDialog = new TextInputDialog();
            creatorDialog.setTitle("Add episode");
            creatorDialog.setHeaderText(null);
            creatorDialog.setContentText("Enter creator:");

            creatorDialog.showAndWait().ifPresent(creator -> {
                TextInputDialog genreDialog = new TextInputDialog();
                genreDialog.setTitle("Add episode");
                genreDialog.setHeaderText(null);
                genreDialog.setContentText("Enter genre:");

                genreDialog.showAndWait().ifPresent(genreString -> {
                    TextInputDialog dateDialog = new TextInputDialog();
                    dateDialog.setTitle("Add episode");
                    dateDialog.setHeaderText(null);
                    dateDialog.setContentText("Enter date of publish");

                    dateDialog.showAndWait().ifPresent(publishDate -> {
                        try {

                            Episode.GenrePodcast genre = Episode.GenrePodcast.valueOf(genreString.toUpperCase());

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


    private static void createNewSongPlaylist() {
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

            customPlaylistTableView.getColumns().clear();
            TableColumn<String, String> column = new TableColumn<>(name);
            column.setCellValueFactory(new PropertyValueFactory<>("name"));
            customPlaylistTableView.getColumns().add(column);
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
        File file;
        if (type == PlaylistType.SONG) {
            file = new File("song_playlist.txt");
        } else if (type == PlaylistType.PODCAST) {
            file = new File("podcast_playlist.txt");
        } else {
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
        File file;
        if (type == PlaylistType.SONG) {
            file = new File("song_playlist.txt");
        } else if (type == PlaylistType.PODCAST) {
            file = new File("podcast_playlist.txt");
        } else {
            file = new File("custom_playlist.txt");
        }

        try {
            List<String> lines = Files.readAllLines(file.toPath());
            lines.remove(playlistName);
            Files.write(file.toPath(), lines);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}
