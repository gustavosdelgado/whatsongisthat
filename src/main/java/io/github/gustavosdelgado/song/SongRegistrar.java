package io.github.gustavosdelgado.song;

import io.github.gustavosdelgado.audio.AudioAnalyzer;
import io.github.gustavosdelgado.audio.AudioFormatBuilder;
import io.github.gustavosdelgado.database.SongDatabaseSingleton;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Scanner;

public class SongRegistrar {

    public void registerSong(String songName, int songId) {

        try {
            Scanner in = new Scanner(System.in);

            System.out.println("Type the song path: ");
            String songPath = in.next();

            byte[] audioData = Files.readAllBytes(Paths.get(songPath));

            ByteArrayInputStream byteArray = new ByteArrayInputStream(audioData);
            AudioInputStream inputStream = new AudioInputStream(byteArray, AudioFormatBuilder.build(), audioData.length);

            ByteArrayOutputStream output = new ByteArrayOutputStream();
            AudioSystem.write(inputStream, AudioFileFormat.Type.AU, output);

            SongDatabaseSingleton databaseSingleton = SongDatabaseSingleton.getInstance();
            AudioAnalyzer analyzer = new AudioAnalyzer(databaseSingleton);

            Map<Long, Long> audioFingerprint = analyzer.convertSongToAudioFingerprint(output);
            databaseSingleton.addSong(audioFingerprint, songId, songName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
