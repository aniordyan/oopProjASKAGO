import javazoom.jl.player.advanced.AdvancedPlayer;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

public class MusicPlayer {
    private Song currentSong;

    //uses JLayer lib to create AdvancedPlayer obj to handle playing music
    private AdvancedPlayer advancedPlayer;

    //constructor
    public MusicPlayer(){

    }

    public void loadSong(Song song){
    this.currentSong = song;
    if(this.currentSong != null){
        playCurrentSong();
    }

    }

    public void playCurrentSong(){
        try{
            // read mp3 data
            FileInputStream fileInputStream = new FileInputStream(currentSong.getFilePath());
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);

            advancedPlayer = new AdvancedPlayer(bufferedInputStream);

            startMusicThread();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    private void startMusicThread(){
        new Thread(new Runnable(){
            public void run(){
                try{
                    advancedPlayer.play();
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
