package io.github.gustavosdelgado.audio;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.TargetDataLine;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AudioRecorder extends Thread {

    private TargetDataLine line;

    public AudioRecorder(TargetDataLine line) {
        this.line = line;
    }

    @Override
    public void run() {
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
    }
}
