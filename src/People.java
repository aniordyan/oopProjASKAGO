public class People {
    private String name;
    public People(String name){
        this.name = name;
    }

    public People(People original){
        this.name = original.name;
    }

    public String getName(){
        return this.name;
    }
}
