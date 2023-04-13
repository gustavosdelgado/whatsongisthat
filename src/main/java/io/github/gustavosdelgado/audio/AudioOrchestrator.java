package io.github.gustavosdelgado.audio;

import io.github.gustavosdelgado.database.SongDatabaseSingleton;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.ByteArrayOutputStream;

public class AudioOrchestrator {

    private static ByteArrayOutputStream output = new ByteArrayOutputStream();

    public static void handleAudio(int input) {

        if (input == 1) {
            record();
            analyze();

        } else if (input == 2) {
            // registerSong
        }

    }

    private static void analyze() {
        AudioAnalyzer analyzer = new AudioAnalyzer(SongDatabaseSingleton.getInstance());
        System.out.println("Song name: " + analyzer.findMatchingSong(output));
    }

    private static void record() {

        try {
            AudioFormat format = AudioFormatBuilder.build();
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            TargetDataLine line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);

            JOptionPane.showMessageDialog(null, "Hit ok to start recording");
            line.start();

            AudioRecorder recorder = new AudioRecorder(line, output);
            recorder.start();

            JOptionPane.showMessageDialog(null, "Hit ok to stop recording");
            line.stop();
            line.close();

        } catch (LineUnavailableException e) {
            System.err.println("Recording failure: " + e);
        }
    }
}
