package am.aua.ui;
import am.aua.core.Song;
import am.aua.exceptions.SongNotFoundException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MenuBarManager extends AudioPlayerUi{
    public MenuBarManager() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
    }

    public static JMenuBar createMenuBar(AudioPlayerUi parentFrame) {
            JMenuBar menuBar = new JMenuBar();

            JMenu addMenu = new JMenu("Add");
            addMenu.addMenuListener(new MenuListener() {
                @Override
                public void menuSelected(MenuEvent e) {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("Choose a WAV audio file to add");
                    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    fileChooser.setFileFilter(new FileNameExtensionFilter("WAV Audio Files", "wav"));

                    int option = fileChooser.showOpenDialog(parentFrame);

                    if (option == JFileChooser.APPROVE_OPTION) {
                        // User selected a file
                        String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                        // Add logic to handle the selected file
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
                    try {
                        if (selectedAudio instanceof Song) {
                            songPlayer.deleteSong(selectedAudio.getId());
                            loadSongs("songDatabase.txt");

                        }
                        selectedAudio = null;
                    } catch (SongNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
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

            return menuBar;
        }


}
