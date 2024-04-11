public class Podcast extends AudioFile{
    private String[] partisipants;
    private int date;
    private int[] episods;
    private String describtion;

    public Podcast(int id,String name, String creator, int duration, String filePath, String[] partisipants,
                   int date, int[]episods, String describtion){
        super(id,name,creator,duration,filePath);
        this.partisipants = partisipants;
        this.date = date;
        this.episods = episods;
        this.describtion = describtion;
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
    public int[] getEpisods(){
        return this.episods;
    }
    public void setEpisods(int[] episods){
        this.episods = episods;
    }
    public String getDescribtion(){
        return this.describtion;
    }
    public void setDescribtion(String describtion){
        this.describtion = describtion;
    }
}
