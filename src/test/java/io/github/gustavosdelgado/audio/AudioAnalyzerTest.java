package io.github.gustavosdelgado.audio;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AudioAnalyzerTest {

    @Mock
    private SongDatabaseSingleton mockSongDatabase;

    @InjectMocks
    private AudioAnalyzer analyzer;

    @Before
    public void setup() {
        Map<Long, List<SoundDataPoint>> songDatabase = new HashMap<>();

        List<SoundDataPoint> dataPointList = new ArrayList<>();
        SoundDataPoint dp1 = new SoundDataPoint(1, 4, "songName1");
        dataPointList.add(dp1);
        SoundDataPoint dp2 = new SoundDataPoint(2, 18, "songName2");
        dataPointList.add(dp2);
        songDatabase.put(123456789L, dataPointList);

        List<SoundDataPoint> dataPointList2 = new ArrayList<>();
        SoundDataPoint dp3 = new SoundDataPoint(1, 5, "songName1");
        dataPointList2.add(dp3);
        SoundDataPoint dp4 = new SoundDataPoint(3, 26, "songName3");
        dataPointList2.add(dp4);
        songDatabase.put(666666666L, dataPointList2);

        List<SoundDataPoint> dataPointList3 = new ArrayList<>();
        SoundDataPoint dp5 = new SoundDataPoint(2, 21, "songName2");
        dataPointList3.add(dp5);
        songDatabase.put(777777777L, dataPointList3);

        when(mockSongDatabase.getSongDatabase()).thenReturn(songDatabase);
    }

    @Test
    public void calculateHash() {
        assertEquals(1801379447, analyzer.genereateHash(new long[]{33, 47, 94, 137, 180}));
    }

    @Test
    public void lookoutInSongDatabase() {
        Map<Long, Long> audioFingerprint = new HashMap<>();
        audioFingerprint.put(123456789L, 1L);
        audioFingerprint.put(666666666L, 2L);
        audioFingerprint.put(777777777L, 3L);
        Assert.assertEquals("songName1", analyzer.lookOutInSongDatabase(audioFingerprint));
    }

}