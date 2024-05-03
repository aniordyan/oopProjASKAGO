package am.aua.ui;

import am.aua.core.AudioFilePlayer;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SliderManager extends Thread{
    private JSlider slider;
    private AudioFilePlayer audioPlayer;
    private int duration;

    public SliderManager(JSlider slider, AudioFilePlayer audioPlayer, int duration) {
        this.slider = slider;
        this.audioPlayer = audioPlayer;
        this.duration = duration;
    }

    @Override
    public void run() {
        try {
            while (true) {
                long position = audioPlayer.getCurrentPosition();
                int value = (int) ((position * 100) / duration);
                slider.setValue(value);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
