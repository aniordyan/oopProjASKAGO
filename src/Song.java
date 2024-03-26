import com.mpatric.mp3agic.Mp3File;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;

import java.io.File;

public class Song {
    private String songName;
    private int songDuration;
    private People[] singers;
    private String filePath;

    //constructor

    public Song(String songName, int songDuration, People[] singers, String filePath ){
        this.songName = songName;
        this.songDuration = songDuration;
        this.setSingers(singers);
        this.filePath = filePath;
        try{
            // uses jaudiotagger lib to create an audiofile obj to read mp3
            AudioFile audioFile = AudioFileIO.read(new File(filePath));

            Tag tag = audioFile.getTag();
            if(tag != null){
                songName = tag.getFirst(FieldKey.TITLE);
                //fix song Artist of People array
            }

        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    public Song(Song original){ //copy constructor
        this.songName = original.songName;
        this.songDuration = original.songDuration;
        this.singers = original.getSingers();
        this.filePath = original.filePath;
    }

    public String getSongName(){
        return this.songName;
    }

    public void setSongName(String songName){
        this.songName = songName;
    }

    public int getSongDuration(){
        return this.songDuration;
    }

    public void setSongDuration(int songDuration){
        this.songDuration = songDuration;
    }

    public People[] getSingers(){
        People[] result = new People[singers.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = new People(this.singers[i]);
        }

        return result;
    }

    public void setSingers(People[] singers){
        People[] temp = new People[singers.length];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = new People(singers[i]);
        }

        this.singers = temp;
    }


    public String getFilePath(){
        return this.filePath;
    }

}
