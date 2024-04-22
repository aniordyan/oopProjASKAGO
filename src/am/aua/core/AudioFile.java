package am.aua.core;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public abstract class AudioFile {
    private int id;
    private String name;
    private String creator;
    private String filePath;
    private String duration;

    public AudioFile(int id,String name, String creator, String filePath){
        this.id = id;
        this.name = name;
        this.creator = creator;
        this.filePath = filePath;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getCreator() {
        return this.creator;
    }


    public String getFilePath() {
        return this.filePath;
    }


    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }



}
