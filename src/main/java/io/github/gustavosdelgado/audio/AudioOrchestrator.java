package io.github.gustavosdelgado.audio;

import io.github.gustavosdelgado.database.SongDatabaseSingleton;
import io.github.gustavosdelgado.song.SongRegistrar;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.ByteArrayOutputStream;
import java.util.Scanner;

public class AudioOrchestrator {

    private static ByteArrayOutputStream output = new ByteArrayOutputStream();

    public static void handleAudio(int input) {

        if (input == 1) {
            record();
            analyze();

        } else if (input == 2) {
            register();
        }

    }

    private static void register() {
        Scanner in = new Scanner(System.in);

        System.out.println("Type the song name: ");
        String songName = in.next();

        System.out.println("Type the song ID: ");
        int songId = in.nextInt();

        SongRegistrar registrar = new SongRegistrar();
        registrar.registerSong(songName, songId);
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
