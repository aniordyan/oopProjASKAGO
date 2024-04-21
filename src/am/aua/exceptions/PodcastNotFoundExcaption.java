package am.aua.exceptions;

public class PodcastNotFoundExcaption extends Exception{
    public PodcastNotFoundExcaption(){super("No such podcast");}
    public PodcastNotFoundExcaption(String message){super();}
}
