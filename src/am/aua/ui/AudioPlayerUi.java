package am.aua.ui;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

import am.aua.cli.User;
import am.aua.core.*;
import am.aua.exceptions.InvalidGenreException;
import am.aua.exceptions.SongNotFoundException;

public class AudioPlayerUi extends JFrame {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 500;
    public static final int SIDE_WIDTH = 200;

    private JScrollPane scrollPane;
    private JSlider progressSlider;
    private JButton playButton;
    private JButton pauseButton;
    private JButton stopButton;
    private JPanel songListPanel;
    private Song selectedSong;

    SongPlayer songPlayer = new SongPlayer("src/am/aua/songs");

    public AudioPlayerUi() {
        setTitle("Audio Player");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Control panel with buttons
        JPanel controlPanel = new JPanel();
        controlPanel.setPreferredSize(new Dimension(SIDE_WIDTH, HEIGHT));
        controlPanel.setBackground(Color.LIGHT_GRAY);
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));

        JButton songsButton = new JButton("Songs");
        JButton podcastsButton = new JButton("Podcasts");
        JButton playlistsButton = new JButton("Playlists");
        controlPanel.add(songsButton);
        controlPanel.add(podcastsButton);
        controlPanel.add(playlistsButton);
        mainPanel.add(controlPanel, BorderLayout.WEST);

        // Song list panel
        songListPanel = new JPanel();
        songListPanel.setLayout(new BoxLayout(songListPanel, BoxLayout.Y_AXIS));
        songListPanel.setVisible(true); // Initially invisible




        //welcome message
        JPanel welcomePanel = new JPanel();
        welcomePanel.setBackground(Color.WHITE);
        JLabel welcomeLabel = new JLabel("Welcome to Askago audio player");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomePanel.add(welcomeLabel);

        mainPanel.add(welcomePanel, BorderLayout.CENTER);




        songsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                welcomePanel.setVisible(false);

                ArrayList<Song> songs = songPlayer.loadSongsFromDatabase("songDatabase.txt");

                for (Song song : songs) {
                    JLabel songLabel = createClickableLabel(song);
                    songListPanel.add(songLabel);
                }

                mainPanel.add(songListPanel, BorderLayout.CENTER);
                mainPanel.revalidate(); // Revalidate the main panel to reflect changes
                mainPanel.repaint();
                songListPanel.setVisible(true);


            }
        });

        // Player panel with control bar
        JPanel playerPanel = new JPanel(new BorderLayout());
        playerPanel.setBackground(Color.GRAY);

        progressSlider = new JSlider();
        progressSlider.setMinimum(0);
        progressSlider.setMaximum(100);
        progressSlider.setValue(0);
        playerPanel.add(progressSlider, BorderLayout.CENTER);

        JPanel controlBarPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        playButton = new JButton("Play");
        pauseButton = new JButton("Pause");
        stopButton = new JButton("Stop");

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    songPlayer.playSong(selectedSong);
                } catch (SongNotFoundException ex) {
                    throw new RuntimeException(ex);
                } catch (UnsupportedAudioFileException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    songPlayer.pauseSong(selectedSong);
                } catch (UnsupportedAudioFileException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (SongNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        controlBarPanel.add(playButton);
        controlBarPanel.add(pauseButton);
        controlBarPanel.add(stopButton);

        playerPanel.add(controlBarPanel, BorderLayout.SOUTH);

        mainPanel.add(playerPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

        private JLabel createClickableLabel(Song song) {
        JLabel label = new JLabel(song.toString());
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    songPlayer.playSong(song);
                    selectedSong = song;
                } catch (SongNotFoundException ex) {
                    throw new RuntimeException(ex);
                } catch (UnsupportedAudioFileException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        return label;
    }



}