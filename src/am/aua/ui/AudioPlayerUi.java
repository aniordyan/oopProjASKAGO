package am.aua.ui;

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

import am.aua.core.*;
import am.aua.exceptions.SongNotFoundException;

public class AudioPlayerUi extends JFrame {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 500;
    public static final int SIDE_WIDTH = 100;

    private JScrollPane scrollPane;
    private JSlider progressSlider;
    private JButton playButton;
    private JButton pauseButton;
    private JButton stopButton;
    private JPanel audiofileListPanel;
    private Song selectedSong;
    private boolean isPaused;

    SongPlayer songPlayer = new SongPlayer("src/am/aua/songs");

    public AudioPlayerUi() {
        setTitle("Audio Player");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setResizable(false);

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
        audiofileListPanel = new JPanel();
        audiofileListPanel.setLayout(new BoxLayout(audiofileListPanel, BoxLayout.Y_AXIS));
        audiofileListPanel.setVisible(true);




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
                audiofileListPanel.removeAll();

                ArrayList<Song> songs = songPlayer.loadSongsFromDatabase("songDatabase.txt");

                for (Song song : songs) {
                    JLabel songLabel = createClickableLabel(song);
                    audiofileListPanel.add(songLabel);
                }

                mainPanel.add(audiofileListPanel, BorderLayout.CENTER);
                mainPanel.revalidate();
                mainPanel.repaint();
                audiofileListPanel.setVisible(true);


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

        controlBarPanel.add(playButton);
        controlBarPanel.add(pauseButton);
        controlBarPanel.add(stopButton);


        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isPaused) {
                    songPlayer.resumeSong();
                    isPaused = false;
                } else {
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
            }
        });

        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isPaused) {
                    songPlayer.pauseSong();
                    isPaused = true;
                }
            }
        });

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                songPlayer.stopSong();
            }
        });



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

                    label.setBackground(Color.YELLOW);
                    label.setOpaque(true);
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