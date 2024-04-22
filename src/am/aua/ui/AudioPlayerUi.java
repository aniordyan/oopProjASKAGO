package am.aua.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AudioPlayerUi extends JFrame {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 500;

    public AudioPlayerUi() {
        setTitle("Audio Player");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel controlPanel = new JPanel();
        controlPanel.setPreferredSize(new Dimension(200, HEIGHT));
        controlPanel.setBackground(Color.LIGHT_GRAY);
        JButton songsButton = new JButton("Songs");
        JButton podcastsButton = new JButton("Podcasts");
        JButton playlistsButton = new JButton("Playlists");


        songsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //logic

            }
        });

        podcastsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //logic

            }
        });

        playlistsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //logic
            }
        });

        controlPanel.add(songsButton);
        controlPanel.add(podcastsButton);
        controlPanel.add(playlistsButton);

        mainPanel.add(controlPanel, BorderLayout.WEST);

        JPanel playerPanel = new JPanel();
        playerPanel.setBackground(Color.GRAY);
        mainPanel.add(playerPanel, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);

    }

}