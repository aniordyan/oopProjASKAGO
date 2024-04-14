package am.aua.core;

public class Podcast extends AudioFile{
    public enum PodGenre{COMEDY, CONVERSATIONAL, MUSIC, DRAMA, UNKNOWN};

    private String[] partisipants;
    private int date;
    private int[] episodes;
    private String describtion;
    private Podcast.PodGenre genre;

    public Podcast(int id,String name, String creator, String filePath, String[] partisipants,
                   int date, int[]episodes, String describtion, PodGenre genre){
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
    public int getDate(){
        return this.date;
    }
    public int[] getEpisodes(){
        return this.episodes;
    }
    public String getDescribtion(){
        return this.describtion;
    }
    public PodGenre getGenre() {
        return this.genre;
    }



}
