package io.github.gustavosdelgado.audio;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.TargetDataLine;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class AudioRecorder extends Thread {

    private static final int CHUNK_SIZE = 4096;

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

        performFFT(output);

        System.out.println("Recording stopped.");
    }

    private static void performFFT(ByteArrayOutputStream output) {
        byte[] audio = output.toByteArray();
        final int totalSize = audio.length;
        int totalNumberOfChunks = totalSize / CHUNK_SIZE; // 4kb size chunk

        Complex[][] results = new Complex[totalNumberOfChunks][];

        for (int i = 0; i < totalNumberOfChunks; i++) { // for each 4kb size chunk,
            Complex[] complex = new Complex[CHUNK_SIZE]; // create a complex number of 4kb size
            for (int j = 0; j < CHUNK_SIZE; j++) {
                complex[j] = new Complex(audio[(i * CHUNK_SIZE) + j], 0); // inside each complex containing the frequency, insert the time domain
            }

            results[i] = FFT.fft(complex); // Perform FFT analysis on each chunk:
        }
    }
}
