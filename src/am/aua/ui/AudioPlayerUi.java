package am.aua.ui;

import javax.sound.sampled.LineUnavailableException;
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
    private JButton shuffleButton;
    private JPanel audiofileListPanel;
    private AudioFile selectedAudio;
    private Song selectedSong;
    private boolean isPaused;

    SongPlayer songPlayer = new SongPlayer("src/am/aua/songs");
    AudioFilePlayer audioPlayer = new AudioFilePlayer("src/am/aua/audioFiles");
    //AudioFilePlayer audioPlayer = new AudioFilePlayer("src/am/aua/songs");
    EpisodePlayer episodePlayer = new EpisodePlayer("src/am/aua");

    public AudioPlayerUi() {
        setTitle("Audio Player");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu addMenu = new JMenu("Add");
        JMenu deleteMenu = new JMenu("Delete");
        JMenu createPlylistMenu = new JMenu("Create new playlist");

        menuBar.add(addMenu);
        menuBar.add(deleteMenu);
        menuBar.add(createPlylistMenu);
        setJMenuBar(menuBar);




        // Control panel with buttons
        JPanel controlPanel = new JPanel();
        controlPanel.setPreferredSize(new Dimension(SIDE_WIDTH, HEIGHT));
        controlPanel.setBackground(Color.LIGHT_GRAY);
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));

        JLabel songsLabel = createClickableLabel("Songs");
        JLabel podcastsLabel = createClickableLabel("Podcasts");
        JLabel classicalPlylistLabel = createClickableLabel("Classical");
        JLabel rockPlylistLabel = createClickableLabel("Rock");

        controlPanel.add(songsLabel);
        controlPanel.add(podcastsLabel);
        controlPanel.add(classicalPlylistLabel);
        controlPanel.add(rockPlylistLabel);

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


        songsLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
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


        podcastsLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                welcomePanel.setVisible(false);
                audiofileListPanel.removeAll();

                ArrayList<Episode> episodes = episodePlayer.loadPodcastsFromDatabase("podcastDatabase.txt");

                for (Episode episode : episodes) {
                    JLabel episodeLabel = createClickableLabel(episode);
                    audiofileListPanel.add(episodeLabel);
                }

                mainPanel.add(audiofileListPanel, BorderLayout.CENTER);
                mainPanel.revalidate();
                mainPanel.repaint();
                audiofileListPanel.setVisible(true);
            }
        });





        classicalPlylistLabel.addMouseListener(new MouseAdapter() {
                    @Override
            public void mouseClicked(MouseEvent e) {
                welcomePanel.setVisible(false);
                audiofileListPanel.removeAll();

                ArrayList<Song> songs = songPlayer.loadSongsFromDatabase("CLASSICAL.txt");

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

       //slider



        JPanel controlBarPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        playButton = new JButton("Play");
        pauseButton = new JButton("Pause");
        stopButton = new JButton("Stop");
        shuffleButton = new JButton("Shuffle");

        controlBarPanel.add(playButton);
        controlBarPanel.add(pauseButton);
        controlBarPanel.add(stopButton);
        controlBarPanel.add(shuffleButton);


        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isPaused) {
                    audioPlayer.resumeAudioFile();
                    isPaused = false;
                } else {
                    try {
                        audioPlayer.playAudioFile(selectedAudio);
                    } catch (UnsupportedAudioFileException ex) {
                        throw new RuntimeException(ex);
                    } catch (IOException | LineUnavailableException ex) {
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

            }
        });



        playerPanel.add(controlBarPanel, BorderLayout.SOUTH);

        mainPanel.add(playerPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }


    private JLabel createClickableLabel(String labelText) {
        JLabel label = new JLabel(labelText);
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return label;
    }


        private JLabel createClickableLabel(AudioFile audioFile) {
        JLabel label = new JLabel(audioFile.toString());
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    audioPlayer.playAudioFile(audioFile);
                    selectedAudio = audioFile;

                    label.setBackground(Color.YELLOW);
                    label.setOpaque(true);

                    Timer timer = new Timer(3000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            label.setBackground(null);
                            label.setOpaque(false);
                        }
                    });


                } catch (UnsupportedAudioFileException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (LineUnavailableException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        return label;
    }



}