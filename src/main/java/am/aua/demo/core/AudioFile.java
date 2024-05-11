package am.aua.demo.core;

public abstract class AudioFile {
    private int id;
    private String name;
    private String creator;
    private String filePath;

    public AudioFile(String name, String creator, String filePath){
        this.name = name;
        this.creator = creator;
        this.filePath = filePath;
    }

    public String getName() {
        return this.name;
    }

    public String getCreator() {
        return this.creator;
    }


    public String getFilePath() {
        return this.filePath;
    }


}
