package am.aua.demo.core;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public interface Playable {

    void setDuration(Duration duration);

    class Duration {
        private String duration;

        public Duration(String filePath) {
            try {
                File file = getFile(filePath);
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                long frameLength = clip.getFrameLength();
                float frameRate = clip.getFormat().getFrameRate();
                clip.close();
                audioInputStream.close();

                long milliseconds = (long) (1000 * frameLength / frameRate);
                long seconds = milliseconds / 1000;
                long hours = seconds / 3600;
                seconds %= 3600;
                long minutes = seconds / 60;
                seconds %= 60;

                this.duration = String.format("%02d:%02d:%02d", hours, minutes, seconds);
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
                this.duration = "00:00:00";
            }
        }

        public String getDuration() {
            return duration;
        }

        public String toString(){
            return duration.toString();
        }

        public File getFile(String filePath) {
            if (new File(filePath).isAbsolute()) {
                return new File(filePath);
            } else {
                return new File("src/main/java/am/aua/demo/audioFiles" + File.separator + filePath);
            }
        }

    }
}
