package io.github.gustavosdelgado.database;

import io.github.gustavosdelgado.model.SoundDataPoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class SongDatabaseSingleton {

    private static volatile SongDatabaseSingleton instance;

    private Map<Long, ArrayList<SoundDataPoint>> songDatabase;

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

    public Map<Long, ArrayList<SoundDataPoint>> getSongDatabase() {
        if (songDatabase == null) songDatabase = new HashMap<>();

        return songDatabase;
    }

    public void addSong(Map<Long, Long> songData, int songId, String songName) {
        this.songDatabase = getSongDatabase();

        songData.forEach((songFingerprint, time) -> {
            SoundDataPoint soundDataPoint = new SoundDataPoint(songId, time, songName);
            if (songDatabase.get(songFingerprint) == null) {
                ArrayList<SoundDataPoint> soundDataPointList = new ArrayList<>();
                soundDataPointList.add(soundDataPoint);
                songDatabase.put(songFingerprint, soundDataPointList);
            } else {
                songDatabase.get(songFingerprint).add(soundDataPoint);
            }
        });
    }
}
