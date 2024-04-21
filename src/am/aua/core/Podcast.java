package am.aua.core;

public class Podcast extends AudioFile{
    public enum Genre{COMEDY, EDUCATION, SOPRT, UNKNOWN};

    private String[] partisipants;
    private int date;
    private String episodes;
    private String describtion;
    private Podcast.Genre genre;


    public Podcast(int id,String name, String creator, String[] partisipants, Genre genre,
                    String episodes, int date, String describtion, String filePath){
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
    public String getEpisodes(){
        return this.episodes;
    }
    public void setEpisodes(String episods){
        this.episodes = episods;
    }
    public String getDescribtion(){
        return this.describtion;
    }
    public void setDescribtion(String describtion){
        this.describtion = describtion;
    }
    public Genre getGenre() {
        return this.genre;
    }
    public void setGenre(Genre genre) {
        this.genre = genre;
    }


}
