package io.github.gustavosdelgado.audio;

import javax.sound.sampled.*;
import javax.swing.*;

public class AudioOrchestrator {

    public static void handleAudio(String audioPath) {

        if (audioPath == null) {
            recordAudio();
        } else {
            // registerSong
        }

    }

    private static void recordAudio() {
        AudioFormat format = AudioFormatBuilder.build();
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

        try {
            TargetDataLine line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);

            JOptionPane.showMessageDialog(null, "Hit ok to start recording");
            line.start();
            AudioRecorder recorder = new AudioRecorder(line);
            recorder.start();

            JOptionPane.showMessageDialog(null, "Hit ok to stop recording");
            line.stop();
            line.close();

        } catch (LineUnavailableException e) {
            System.err.println("Recording failure: " + e);
        }
    }
}
