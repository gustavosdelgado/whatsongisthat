package io.github.gustavosdelgado.audio;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public final class SongDatabaseSingleton {

    private static volatile SongDatabaseSingleton instance;

    private Map<Long, List<SoundDataPoint>> songDatabase;

    public static SongDatabaseSingleton getInstance() {
        SongDatabaseSingleton result = instance;

        if (result != null) {
            return result;
        }

        synchronized (SongDatabaseSingleton.class) {
            if (instance == null) {
                instance = new SongDatabaseSingleton();
            }

            return instance;
        }
    }

    public Map<Long, List<SoundDataPoint>> getSongDatabase() {
        return songDatabase;
    }

    public void addSong(Map<Long, Long> songData, int songId, String songName) {
        songData.forEach((songFingerprint, time) -> {
            SoundDataPoint soundDataPoint = new SoundDataPoint(songId, time, songName);
            if (songDatabase.get(songFingerprint) == null) {
                songDatabase.put(songFingerprint, Arrays.asList(soundDataPoint));
            } else {
                songDatabase.get(songFingerprint).add(soundDataPoint);
            }
        });
    }
}
