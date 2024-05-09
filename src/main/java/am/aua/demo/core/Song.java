package am.aua.demo.core;


import java.util.Objects;

public class Song extends AudioFile implements Playable{
    public enum Genre {CLASSICAL, POP, ROCK, FUNK, RNB }
    private Playable.Duration duration;

    private Genre genre;



    public Song(String name, String creator, Genre genre, String filePath) {
        super( name, creator, filePath);
        this.genre = genre;
        this.duration = new Playable.Duration(filePath);
    }

    public Genre getGenre() {
        return genre;
    }

    public Duration getDuration() {
        return duration;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Song song = (Song) o;
        return Objects.equals(getName(), song.getName()) &&
                Objects.equals(getCreator(), song.getCreator()) &&
                genre == song.genre &&
                Objects.equals(getFilePath(), song.getFilePath());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getCreator(), getGenre(), getFilePath());
    }


}
