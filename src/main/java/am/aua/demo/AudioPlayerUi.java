package am.aua.demo;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AudioPlayerUi extends Application {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 500;
    public static final int SIDE_WIDTH = 100;

    private VBox controlPanel;
    private VBox audiofileListPanel;
    private BorderPane mainPanel;
    private Label welcomeLabel;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Audio Player");
        mainPanel = new BorderPane();

        // Control panel with buttons
        VBox controlPanel = new VBox(10); // Vertical box layout with spacing of 10
        controlPanel.setPadding(new Insets(10)); // Add padding
        controlPanel.setStyle("-fx-background-color: lightgrey;");

        Label songsLabel = createClickableLabel("All Songs");
        Label podcastsLabel = createClickableLabel("Podcasts");
        Label classicalPlaylistLabel = createClickableLabel("Classical");
        Label rockPlaylistLabel = createClickableLabel("Rock");

        VBox dropDownSongs = new VBox(5); // Vertical box layout for dropdown
        dropDownSongs.setVisible(false);
        dropDownSongs.getChildren().addAll(songsLabel, classicalPlaylistLabel, rockPlaylistLabel);

        Button playlists = new Button("▼ Songs");
        playlists.setOnAction((ActionEvent event) -> {
            dropDownSongs.setVisible(!dropDownSongs.isVisible());

        });

        VBox dropDownPodcasts = new VBox(5); // Vertical box layout for dropdown
        dropDownPodcasts.setVisible(false);
        dropDownPodcasts.getChildren().add(podcastsLabel);

        Button podcast = new Button("▼ Podcast");
        podcast.setOnAction((ActionEvent event) -> {
            dropDownPodcasts.setVisible(!dropDownPodcasts.isVisible());

        });

        controlPanel.getChildren().addAll(playlists, dropDownSongs, podcast, dropDownPodcasts);



        // Audio file list panel
        audiofileListPanel = new VBox();
        audiofileListPanel.setPadding(new Insets(10));

        // Welcome panel
        welcomeLabel = new Label("Welcome to Askago audio player");
        welcomeLabel.setStyle("-fx-font-size: 20pt;");
        VBox welcomePanel = new VBox(welcomeLabel);
        welcomePanel.setPadding(new Insets(20));
        welcomePanel.setAlignment(Pos.TOP_CENTER);

        mainPanel.setLeft(controlPanel);
        mainPanel.setCenter(welcomePanel);

        Scene scene = new Scene(mainPanel, WIDTH, HEIGHT);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Label createClickableLabel(String labelText) {
        Label label = new Label(labelText);
        label.setStyle("-fx-cursor: hand;");
        label.setOnMouseClicked(event -> {
            // Handle click event
            System.out.println("Clicked: " + labelText);
        });
        return label;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
