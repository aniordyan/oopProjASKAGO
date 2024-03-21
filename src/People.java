public class People {
    private String name;


    public People(String name) { //constructor
        this.name = name;
    }


    public People(People original) { //copy costructor
        this.name = original.name;
    }

    public String getName(){
       return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

}
