package am.aua.demo.ui;

import javafx.application.Platform;
import javafx.scene.layout.BorderPane;
import am.aua.demo.core.AudioFile;
import am.aua.demo.core.AudioFilePlayer;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

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
    private Thread shuffleThread;

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
        Button stopLoopButton = new Button("Stop loop");
        stopLoopButton.setOnAction(event -> {
            shuffleThread.interrupt();
        });

        HBox controlBarPanel = (HBox) getCenter();
        controlBarPanel.getChildren().add(stopLoopButton);

        //new thread for shuffling
        shuffleThread = new Thread(() -> {
            if (parentUi.currentPlaylistPath != null) {
                try {
                    parentUi.songPlayer.playlistToPlayTest(parentUi.songPlayer.getSongsFromDatabase(parentUi.currentPlaylistPath), true);
                } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("No playlist loaded to shuffle.");
            }


            Platform.runLater(() -> {
                controlBarPanel.getChildren().remove(stopLoopButton);
            });
        });

        shuffleThread.start();
    }



}
