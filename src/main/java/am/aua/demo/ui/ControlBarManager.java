package am.aua.demo.ui;

import javafx.scene.layout.BorderPane;
import am.aua.demo.core.AudioFile;
import am.aua.demo.core.AudioFilePlayer;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
public class ControlBarManager extends BorderPane {
    private Button playButton;
    private Button pauseButton;
    private Button stopButton;
    private Button shuffleButton;
    private Slider progressSlider;
    private Label currentlyPlayingLabel;
    private boolean isPaused;
    private AudioFilePlayer audioPlayer;
    private AudioPlayerUi parentUi;

    public ControlBarManager(AudioFilePlayer audioPlayer, AudioPlayerUi parentUi) {
        this.audioPlayer = audioPlayer;
        this.parentUi = parentUi;
        initComponents();
        addListeners();
    }

    private void initComponents() {
        setStyle("-fx-background-color: gainsboro ;");
        setPrefHeight(50);

        currentlyPlayingLabel = new Label();
        currentlyPlayingLabel.setStyle("-fx-alignment: center;");
        setTop(currentlyPlayingLabel);

        progressSlider = new Slider();
        progressSlider.setMin(0);
        progressSlider.setMax(100);
        progressSlider.setValue(0);
        setTop(progressSlider);;

        HBox controlBarPanel = new HBox(10);
        controlBarPanel.setStyle("-fx-alignment: center;");
        playButton = new Button("▶");
        pauseButton = new Button("⏸");
        stopButton = new Button("◼");
        shuffleButton = new Button("\uD83D\uDD00");

        controlBarPanel.getChildren().addAll(playButton, pauseButton, stopButton, shuffleButton);
        setCenter(controlBarPanel);
    }

    private void addListeners() {
        playButton.setOnAction((ActionEvent e) -> {
            if (isPaused) {
                audioPlayer.resumeAudioFile();
                isPaused = false;
            } else {
                try {
                    audioPlayer.playAudioFile(parentUi.selectedAudio);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }


        });

        pauseButton.setOnAction((ActionEvent e) -> {
            if (!isPaused) {
                audioPlayer.pauseAudioFile();
                isPaused = true;
            }
        });

        stopButton.setOnAction((ActionEvent e) -> {
            audioPlayer.stopAudioFile();
        });

        shuffleButton.setOnAction((ActionEvent e) -> {
            shufflePlaylist();
        });
    }

    public void setIsPaused(boolean isPaused) {
        this.isPaused = isPaused;
    }

    public void shufflePlaylist() {
    /*    if (parentUi.getCurrentPlaylistPath() != null) {
            try {
                parentUi.getSongPlayer().playlistToPlayTest(parentUi.getSongPlayer().getSongsFromDatabase(parentUi.getCurrentPlaylistPath()), true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No playlist loaded to shuffle.");
        }

     */
    }

    public void updateCurrentlyPlayingLabel(String audioFileName) {
        currentlyPlayingLabel.setText("Currently playing: " + audioFileName);
    }
}
