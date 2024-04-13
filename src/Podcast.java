public class Podcast extends AudioFile{
    public enum Genre1{COMEDY, CONVERSATIONAL, MUSIC, DRAMA, UNKNOWN};

    private String[] partisipants;
    private int date;
    private int[] episodes;
    private String describtion;
    private Podcast.Genre1 genre;

    public Podcast(int id,String name, String creator, String filePath, String[] partisipants,
                   int date, int[]episodes, String describtion, Genre1 genre){
        super(id,name,creator,filePath);
        this.partisipants = partisipants;
        this.date = date;
        this.episodes = episodes;
        this.describtion = describtion;
        this.genre = genre;
    }
    public String[] getPartisipants(){
        return this.partisipants;
    }
    public void setPartisipants(String[] partisipants){
        this.partisipants = partisipants;
    }
    public int getDate(){
        return this.date;
    }
    public void setDate(int date){
        this.date = date;
    }
    public int[] getEpisodes(){
        return this.episodes;
    }
    public void setEpisodes(int[] episods){
        this.episodes = episods;
    }
    public String getDescribtion(){
        return this.describtion;
    }
    public void setDescribtion(String describtion){
        this.describtion = describtion;
    }
    public Genre1 getGenre() {
        return this.genre;
    }
    public void setGenre(Genre1 genre) {
        this.genre = genre;
    }


}
