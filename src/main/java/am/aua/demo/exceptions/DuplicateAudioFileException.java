package am.aua.demo.exceptions;

public class DuplicateAudioFileException extends Exception {
    public DuplicateAudioFileException() {
        super("Duplicate song found in the playlist.");
    }

    public DuplicateAudioFileException(String message) {
        super(message);
    }

}