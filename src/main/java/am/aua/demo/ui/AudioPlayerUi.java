package am.aua.demo.ui;

import am.aua.demo.core.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.ArrayList;
import javafx.scene.Cursor;



public class AudioPlayerUi extends Application {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 500;
    public static final int SIDE_WIDTH = 100;

    private VBox controlPanel;
    private static VBox audiofileListPanel;
    static BorderPane mainPanel;
    private static Label welcomeLabel;
    private static TableView<Song> songTable;
    static String currentPlaylistPath;
    static VBox welcomePanel;
    static AudioFile selectedAudio;
    static SongPlayer songPlayer = new SongPlayer("src/main/java/am/aua/demo/audioFiles");
    static AudioFilePlayer audioPlayer = new AudioFilePlayer("src/main/java/am/aua/demo/audioFiles");
    EpisodePlayer episodePlayer = new EpisodePlayer("src/main/java/am/aua/demo/audioFiles");


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Audio Player");
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(event -> {System.exit(0);});
        mainPanel = new BorderPane();

        mainPanel.setTop(MenuBarManager.createMenuBar(this));
        ControlBarManager controlBarManager = new ControlBarManager(audioPlayer, this);
        mainPanel.setBottom(controlBarManager);

        // Control panel with buttons
        VBox controlPanel = new VBox(10);
        controlPanel.setPadding(new Insets(10));
        controlPanel.setStyle("-fx-background-color: lightgrey;");


        Label songsLabel = createClickableLabel("All Songs");
        songsLabel.setOnMouseClicked(event -> {
            loadSongs("songDatabase.txt");
        });
        Label podcastsLabel = createClickableLabel("Podcasts");
        podcastsLabel.setOnMouseClicked(event -> {

        });
        Label classicalPlaylistLabel = createClickableLabel("Classical");
        Label rockPlaylistLabel = createClickableLabel("Rock");

        VBox dropDownSongs = new VBox(5); //dropdown
        dropDownSongs.setVisible(false);
        dropDownSongs.getChildren().addAll(songsLabel, classicalPlaylistLabel, rockPlaylistLabel);

        Button songItems = new Button("▼ Songs");
        songItems.setOnAction((ActionEvent event) -> {
            dropDownSongs.setVisible(!dropDownSongs.isVisible());

        });

        VBox dropDownPodcasts = new VBox(5); // dropdown
        dropDownPodcasts.setVisible(false);
        dropDownPodcasts.getChildren().add(podcastsLabel);

        Button podcastItems = new Button("▼ Podcast");
        podcastItems.setOnAction((ActionEvent event) -> {
            dropDownPodcasts.setVisible(!dropDownPodcasts.isVisible());

        });

        controlPanel.getChildren().addAll(songItems, dropDownSongs, podcastItems, dropDownPodcasts);




        // Audio file list panel
        audiofileListPanel = new VBox();

        initTableView();


        // Welcome panel
        welcomeLabel = new Label("Welcome to Askago audio player");
        welcomeLabel.setStyle("-fx-font-size: 20pt;");
        welcomePanel = new VBox(welcomeLabel);
        welcomePanel.setPadding(new Insets(20));
        welcomePanel.setAlignment(Pos.TOP_CENTER);

        mainPanel.setLeft(controlPanel);
        mainPanel.setCenter(welcomePanel);

        Scene scene = new Scene(mainPanel, WIDTH, HEIGHT);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static Label createClickableLabel(String labelText) {
        Label label = new Label(labelText);
        label.setStyle("-fx-cursor: hand;");
        label.setOnMouseClicked(event -> {
            System.out.println("Clicked: " + labelText);
        });
        return label;
    }

    private static Label createClickableLabel(AudioFile audioFile) {
        Label label = new Label(audioFile.toString());
        label.setCursor(Cursor.HAND);
        label.setOnMouseClicked(event -> {

            try {
                audioPlayer.playAudioFile(audioFile);
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
                throw new RuntimeException(e);
            }

        });
        return label;
    }

    private void initTableView() {
        songTable = new TableView<>();

        TableColumn<Song, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Song, String> artistColumn = new TableColumn<>("Artist");
        artistColumn.setCellValueFactory(new PropertyValueFactory<>("creator"));

        TableColumn<Song, String> genreColumn = new TableColumn<>("Genre");
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));

        TableColumn<Song, String> durationColumn = new TableColumn<>("Duration");
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));

        songTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        songTable.getColumns().addAll(titleColumn, artistColumn, genreColumn, durationColumn);

        songTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                if(selectedAudio != null)
                    audioPlayer.stopAudioFile();
                selectedAudio = newSelection;
                try {
                    audioPlayer.playAudioFile(selectedAudio);
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Selected Song: " + selectedAudio.getName());
            }
        });

    }


    static void loadSongs(String path) {
        welcomeLabel.setVisible(false);
        audiofileListPanel.getChildren().clear();

        currentPlaylistPath = path;

        ArrayList<Song> songs = songPlayer.loadSongsFromDatabase(path);

        ObservableList<Song> songList = FXCollections.observableArrayList(songs);

        // Set items to TableView
        songTable.setItems(songList);

        // Add TableView to the panel
        audiofileListPanel.getChildren().add(songTable);

        mainPanel.setCenter(audiofileListPanel);
        audiofileListPanel.setVisible(true);
    }



}
