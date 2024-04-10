public abstract class AudioFile {
    private int id;
    private String name;
    private String creator;
    private int duration;
    private String filePath;

    public AudioFile(int id,String name, String creator, int duration, String filePath){
        this.id = id;
        this.name = name;
        this.creator = creator;
        this.duration = duration;
        this.filePath = filePath;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreator() {
        return this.creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
