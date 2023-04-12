package io.github.gustavosdelgado.audio;

public class SoundDataPoint {

    private long time;
    private int songId;

    private String songName;

    public SoundDataPoint(int songId, long time, String songName) {
        this.songId = songId;
        this.time = time;
        this.songName = songName;
    }

    public long getTime() {
        return time;
    }

    public int getSongId() {
        return songId;
    }

    public String getSongName() {
        return songName;
    }

}
