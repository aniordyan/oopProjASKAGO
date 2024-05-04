package am.aua.ui;
import am.aua.core.Song;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import am.aua.core.SongPlayer;

public class MenuBarManager extends AudioPlayerUi{
    SongPlayer songPl;


    public MenuBarManager() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
    }

    public static JMenuBar createMenuBar(AudioPlayerUi parentFrame) {
            JMenuBar menuBar = new JMenuBar();

            JMenu addMenu = new JMenu("Add");
            addMenu.addMenuListener(new MenuListener() {
                @Override
                public void menuSelected(MenuEvent e) {
                    String[] options = {"Song", "Podcast"};
                    int choice = JOptionPane.showOptionDialog(parentFrame, "What do you want to add?", "Add Item",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

                    switch (choice) {
                        case 0:
                            addSong();
                            break;
                        case 1:
                            addPodcast();
                            break;
                        default:
                            // canceled
                            break;
                    }
                }

                @Override
                public void menuDeselected(MenuEvent e) {
                }

                @Override
                public void menuCanceled(MenuEvent e) {
                }
            });
            menuBar.add(addMenu);

            JMenu deleteMenu = new JMenu("Delete");
            menuBar.add(deleteMenu);

        deleteMenu.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                audioPlayer.stopAudioFile();
                if (selectedAudio != null) {
                    if (selectedAudio instanceof Song) {
                        Song selectedSong = (Song) selectedAudio;
                        songPlayer.deleteSong(selectedSong);
                        loadSongs("songDatabase.txt");


                    }
                    selectedAudio = null;
                }
            }

            @Override
            public void menuDeselected(MenuEvent e) {
            }

            @Override
            public void menuCanceled(MenuEvent e) {
            }
        });





            JMenu createPlaylistMenu = new JMenu("Create new playlist");
            menuBar.add(createPlaylistMenu);

        createPlaylistMenu.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                String playlistName = JOptionPane.showInputDialog("Enter playlist name:");
                if (playlistName != null && !playlistName.isEmpty()) {
                    JLabel newPlaylistLabel = createClickableLabel(playlistName);
                    controlPanel.add(newPlaylistLabel);
                    controlPanel.revalidate();
                    controlPanel.repaint();
                }
            }

            @Override
            public void menuDeselected(MenuEvent e) {
            }

            @Override
            public void menuCanceled(MenuEvent e) {
            }
        });

            return menuBar;
        }

    private static void addPodcast() {
    }


    private static void addSong() {
        String name = JOptionPane.showInputDialog("Enter song name:");
        String artist = JOptionPane.showInputDialog("Enter artist:");
        String genreString = JOptionPane.showInputDialog("Enter genre:");
        String filePath = chooseFile("Choose a WAV audio file to add");


        if (name != null && artist != null && genreString != null && filePath != null) {
            // Create Song object and add it to database
            try {
                Song.Genre genre = Song.Genre.valueOf(genreString.toUpperCase());
                Song song = new Song(name, artist, genre, filePath);
                songPlayer.addSong(song);
                loadSongs(currentPlaylistPath);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error adding song: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static String chooseFile(String dialogTitle) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(dialogTitle);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileNameExtensionFilter("WAV Audio Files", "wav"));

        int option = fileChooser.showOpenDialog(null);

        if (option == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getAbsolutePath();
        } else {
            return null;
        }
    }


}
