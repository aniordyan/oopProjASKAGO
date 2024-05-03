package am.aua.ui;

import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.filechooser.FileNameExtensionFilter;
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

     JScrollPane scrollPane;
     JSlider progressSlider;
     static JPanel audiofileListPanel;
    private static JPanel mainPanel;
    private static JPanel welcomePanel;
    static AudioFile selectedAudio;
    Song selectedSong;
    boolean isPaused;


    static SongPlayer songPlayer = new SongPlayer("src/am/aua/audioFiles");
    static AudioFilePlayer audioPlayer = new AudioFilePlayer("src/am/aua/audioFiles");
    EpisodePlayer episodePlayer = new EpisodePlayer("src/am/aua");
    static String currentPlaylistPath;



    public AudioPlayerUi() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        setTitle("Audio Player");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setResizable(false);

        mainPanel = new JPanel(new BorderLayout());

        // Menu bar
        JMenuBar menuBar = MenuBarManager.createMenuBar(this);
        setJMenuBar(menuBar);


        // Control panel with buttons
        JPanel controlPanel = new JPanel();
        controlPanel.setPreferredSize(new Dimension(SIDE_WIDTH, HEIGHT));
        controlPanel.setBackground(Color.LIGHT_GRAY);
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));

        controlPanel.setSize(300, 150);
        controlPanel.setLayout(new java.awt.FlowLayout());
        controlPanel.setVisible(true);


        JLabel songsLabel = createClickableLabel(" All Songs");
        JLabel podcastsLabel = createClickableLabel("Podcasts");
        JLabel classicalPlylistLabel = createClickableLabel("Classical");
        JLabel rockPlylistLabel = createClickableLabel("Rock");

        JPanel dropDownSongs = new JPanel();
        dropDownSongs.setPreferredSize(new Dimension(80,70));
        dropDownSongs.setBackground(Color.LIGHT_GRAY);
        dropDownSongs.setVisible(false);

        JLabel playlists = createClickableLabel("▼ Songs");

        dropDownSongs.add(songsLabel);
        dropDownSongs.add(classicalPlylistLabel);
        dropDownSongs.add(rockPlylistLabel);
        playlists.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                dropDownSongs.setVisible(!dropDownSongs.isVisible());
            }
        });
        JPanel dropDownPodcasts = new JPanel();
        dropDownPodcasts.setPreferredSize(new Dimension(80,60));
        dropDownPodcasts.setBackground(Color.LIGHT_GRAY);
        dropDownPodcasts.setVisible(false);

        JLabel podcast = createClickableLabel("▼ Podcast");

        dropDownPodcasts.add(podcastsLabel);
        podcast.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                dropDownPodcasts.setVisible(!dropDownPodcasts.isVisible());
            }
        });

        controlPanel.add(playlists);
        controlPanel.add(dropDownSongs);
        controlPanel.add(podcast);
        controlPanel.add(dropDownPodcasts);

        mainPanel.add(controlPanel, BorderLayout.WEST);

        // Audifile list panel
        audiofileListPanel = new JPanel();
        audiofileListPanel.setLayout(new BoxLayout(audiofileListPanel, BoxLayout.Y_AXIS));
        audiofileListPanel.setVisible(true);




        //welcome message
        welcomePanel = new JPanel();
        welcomePanel.setBackground(Color.WHITE);
        JLabel welcomeLabel = new JLabel("Welcome to Askago audio player");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomePanel.add(welcomeLabel);

        mainPanel.add(welcomePanel, BorderLayout.CENTER);


        songsLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                loadSongs("songDatabase.txt");
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
                loadSongs("CLASSICAL.txt");
            }
        });

        rockPlylistLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                loadSongs("ROCK.txt");
            }
        });

        //CONTROL BAR PANEL
        ControlBarPanel controlBarPanel = new ControlBarPanel(audioPlayer, this);
        add(controlBarPanel, BorderLayout.SOUTH);



        add(mainPanel);
        setVisible(true);
    }


    private JLabel createClickableLabel(String labelText) {
        JLabel label = new JLabel(labelText);
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return label;
    }


    private static JLabel createClickableLabel(AudioFile audioFile) {
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

    static void loadSongs(String path) {
        welcomePanel.setVisible(false);
        audiofileListPanel.removeAll();

        currentPlaylistPath = path;

        ArrayList<Song> songs = songPlayer.loadSongsFromDatabase(path);

        for (Song song : songs) {
            JLabel songLabel = createClickableLabel(song);
            audiofileListPanel.add(songLabel);
        }

        mainPanel.add(audiofileListPanel, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
        audiofileListPanel.setVisible(true);
    }


}