package am.aua.ui;

import am.aua.core.AudioFile;
import am.aua.core.AudioFilePlayer;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ControlBarPanel extends JPanel {

    private JButton playButton;
    private JButton pauseButton;
    private JButton stopButton;
    private JButton shuffleButton;
    private JSlider progressSlider;
    private JLabel currentlyPlayingLabel;
    private boolean isPaused;
    private AudioFilePlayer audioPlayer;
    private AudioPlayerUi parentUi;
    AudioFile selectedAudio;


    public ControlBarPanel(AudioFilePlayer audioPlayer, AudioPlayerUi parentUi) {
        this.audioPlayer = audioPlayer;
        this.parentUi = parentUi;
        initComponents();
        addListeners();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.GRAY);

        currentlyPlayingLabel = new JLabel();
        currentlyPlayingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(currentlyPlayingLabel, BorderLayout.NORTH);

        progressSlider = new JSlider();
        progressSlider.setMinimum(0);
        progressSlider.setMaximum(100);
        progressSlider.setValue(0);
        add(progressSlider, BorderLayout.CENTER);

        JPanel controlBarPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        playButton = new JButton("Play");
        pauseButton = new JButton("Pause");
        stopButton = new JButton("Stop");
        shuffleButton = new JButton("Shuffle");

        controlBarPanel.add(playButton);
        controlBarPanel.add(pauseButton);
        controlBarPanel.add(stopButton);
        controlBarPanel.add(shuffleButton);

        add(controlBarPanel, BorderLayout.SOUTH);
    }

    private void addListeners() {
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isPaused) {
                    audioPlayer.resumeAudioFile();
                    isPaused = false;
                } else {
                    try {
                        audioPlayer.playAudioFile(parentUi.selectedAudio);
                    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isPaused) {
                    audioPlayer.pauseAudioFile();
                    isPaused = true;
                }
            }
        });

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                audioPlayer.stopAudioFile();
            }
        });

        shuffleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shufflePlaylist();
            }
        });
    }


    public void setIsPaused(boolean isPaused) {
        this.isPaused = isPaused;
    }

    public void shufflePlaylist() {
        if (parentUi.currentPlaylistPath != null) {
            try {
                parentUi.songPlayer.playlistToPlayTest(parentUi.songPlayer.getSongsFromDatabase(parentUi.currentPlaylistPath), true);
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No playlist loaded to shuffle.");
        }
    }

    public void updateCurrentlyPlayingLabel(String audioFileName) {
        currentlyPlayingLabel.setText("Currently playing: " + audioFileName);
    }



}


