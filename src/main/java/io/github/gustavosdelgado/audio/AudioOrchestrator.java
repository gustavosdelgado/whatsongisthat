package io.github.gustavosdelgado.audio;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.ByteArrayOutputStream;

public class AudioOrchestrator {

    private static ByteArrayOutputStream output = new ByteArrayOutputStream();

    public static void handleAudio(String[] args) {

        if (args.length == 0) {
            record();
            analyze();

        } else {
            // registerSong
        }

    }

    private static void analyze() {
        AudioAnalyzer analyzer = new AudioAnalyzer(SongDatabaseSingleton.getInstance());
        analyzer.analyze(output);
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
