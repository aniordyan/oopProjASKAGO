public class Song {
    private String songName;
    private int songDuration;
    private People[] singers;

    public Song(Song original){
        this.songName = original.songName;
        this.songDuration = original.songDuration;
        this.singers = original.getSingers();
    }

    public People[] getSingers(){
        People[] result = new People[singers.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = new People(this.singers[i]);
        }

        return result;
    }
}
