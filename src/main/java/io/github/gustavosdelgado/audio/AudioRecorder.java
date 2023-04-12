package io.github.gustavosdelgado.audio;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.TargetDataLine;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AudioRecorder extends Thread {

    private final TargetDataLine line;
    private final ByteArrayOutputStream output;

    public AudioRecorder(TargetDataLine line, ByteArrayOutputStream output) {
        this.line = line;
        this.output = output;
    }

    @Override
    public void run() {
        record();
    }

    private ByteArrayOutputStream record() {
        AudioInputStream recordingStream = new AudioInputStream(line);

        try {
            System.out.println("Recording started");
            AudioSystem.write(recordingStream, AudioFileFormat.Type.AU, output);
        } catch (IOException e) {
            System.err.println("Recording failure: " + e);
        }

        System.out.println("Recording stopped.");
        return output;
    }

}
