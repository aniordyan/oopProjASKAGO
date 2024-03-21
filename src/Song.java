public class Song {
    private String songName;
    private int songDuration;
    private People[] singers;

    //constructor

    public Song(String songName, int songDuration, People[] singers ){
        this.songName = songName;
        this.songDuration = songDuration;
        this.setSingers(singers);
    }
    public Song(Song original){ //copy constructor
        this.songName = original.songName;
        this.songDuration = original.songDuration;
        this.singers = original.getSingers();
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


}
