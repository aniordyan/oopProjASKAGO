package am.aua.exceptions;

public class DuplicateSongException extends Exception {
    public DuplicateSongException() {
        super("Duplicate song found in the playlist.");
    }

    public DuplicateSongException(String message) {
        super(message);
    }

}