public class Song extends AudioFile{
    public enum Genre{CLASSICAL, POP, ROCK, JAZZ};

    private Genre genre;

    public Song(int id,String name, String creator, int duration, Genre genre, String filePath){
        super(id,name,creator,duration,filePath);
        this.genre = genre;
    }

    public Genre getGenre() {
        return this.genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String toString() {
        return
                "title: " + this.getName()  +
                ", artist: " + this.getCreator() +
                ", genre: " + this.getGenre() +
                ", duration: " + this.getDuration();
    }
}
