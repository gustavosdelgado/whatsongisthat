package io.github.gustavosdelgado.audio;

import io.github.gustavosdelgado.audio.AudioFormatBuilder;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.DataLine;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AudioAnalyzer {

    public static void analyze() {
        AudioFormat format = AudioFormatBuilder.build();
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

        try {
            TargetDataLine line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);

            JOptionPane.showMessageDialog(null, "Hit ok to start recording");
            line.start();

            Thread audioRecordingThread = new Thread(() -> {
                AudioInputStream recordingStream = new AudioInputStream(line);

                ByteArrayOutputStream output = new ByteArrayOutputStream();
                try {
                    AudioSystem.write(recordingStream, AudioFileFormat.Type.AU, output);

                } catch (IOException e) {
                    System.err.println("Recording failure: " + e);
                }

                byte[] byteArray = output.toByteArray();
                for (byte b : byteArray) {
                    System.out.print(b + ",");
                }

                System.out.println("Recording stopped.");

            });

            audioRecordingThread.start();

            JOptionPane.showMessageDialog(null, "Hit ok to stop recording");
            line.stop();
            line.close();
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }

    }
}
