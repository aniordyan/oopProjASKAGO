package am.aua.core;


public class Song extends AudioFile implements Playable{
    public enum Genre {CLASSICAL, POP, ROCK, FUNK, RNB }
    private Playable.Duration duration;

    private Genre genre;



    public Song(int id, String name, String creator, Genre genre, String filePath) {
        super(id, name, creator, filePath);
        this.genre = genre;
        this.duration = new Playable.Duration(filePath);
    }

    public Genre getGenre() {
        return genre;
    }

    public void setDuration(Playable.Duration duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        final int COLUMN_WIDTH = 40;

        String Title = String.format("%-" + COLUMN_WIDTH + "s",  getName());
        String Artist = String.format("%-" + COLUMN_WIDTH + "s",  getCreator());
        String Genre = String.format("%-" + COLUMN_WIDTH + "s",  getGenre());
        String Duration = String.format("%-" + COLUMN_WIDTH + "s", duration.getDuration());

        return Title + Artist + Genre + Duration;
    }

}
