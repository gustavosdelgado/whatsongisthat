package io.github.gustavosdelgado.audio;

import io.github.gustavosdelgado.database.SongDatabaseSingleton;
import io.github.gustavosdelgado.model.Complex;
import io.github.gustavosdelgado.model.SoundDataPoint;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AudioAnalyzer {

    private static final int CHUNK_SIZE = 4096;

    private static final int[] RANGE = new int[]{40, 80, 120, 180, 300};

    private static final int FUZ_FACTOR = 2;

    private SongDatabaseSingleton databaseSingleton;

    public AudioAnalyzer(SongDatabaseSingleton databaseSingleton) {
        this.databaseSingleton = databaseSingleton;
    }

    public String findMatchingSong(ByteArrayOutputStream output) {
        Complex[][] results = performFFT(output);
        Map<Long, Long> audioFingerprint = generateAudioFingerprint(results);
        return findMatchingSong(audioFingerprint);
    }

    protected String findMatchingSong(Map<Long, Long> audioFingerprint) {
        Map<Long, List<SoundDataPoint>> songDatabase = databaseSingleton.getSongDatabase();
        HashMap<String, HashMap<Long, Integer>> matchesRank = new HashMap<>();

        audioFingerprint.forEach((fingerprint, time) -> {
            List<SoundDataPoint> matches = songDatabase.get(fingerprint);

            for (SoundDataPoint match : matches) {
                long offset = Math.abs(match.getTime() - time);

                HashMap<Long, Integer> matchRankOccurence = matchesRank.get(match.getSongName());
                if (matchRankOccurence == null) {
                    matchRankOccurence = new HashMap<>();
                    matchRankOccurence.put(offset, 1);
                    matchesRank.put(match.getSongName(), matchRankOccurence);
                } else if (matchRankOccurence.get(offset) != null) {
                    matchRankOccurence.put(offset, matchRankOccurence.get(offset) + 1);
                } else {
                    matchRankOccurence.put(offset, 1);
                }
            }

        });

        long maxOffsetHit = 0;
        String mostLikelySongName = "";

        for (HashMap.Entry<String, HashMap<Long, Integer>> match : matchesRank.entrySet()) {
            for (Map.Entry<Long, Integer> offsetHit : match.getValue().entrySet()) {
                if (offsetHit.getValue() > maxOffsetHit) {
                    maxOffsetHit = offsetHit.getValue();
                    mostLikelySongName = match.getKey();
                }
            }
        }

        return mostLikelySongName;
    }

    private Complex[][] performFFT(ByteArrayOutputStream output) {
        byte[] audio = output.toByteArray();
        final int totalSize = audio.length;
        int totalNumberOfChunks = totalSize / CHUNK_SIZE; // 4kb size chunk

        Complex[][] results = new Complex[totalNumberOfChunks][];

        for (int i = 0; i < totalNumberOfChunks; i++) { // for each 4kb size chunk,
            Complex[] complex = new Complex[CHUNK_SIZE]; // create a complex number of 4kb size
            for (int j = 0; j < CHUNK_SIZE; j++) {
                complex[j] = new Complex(audio[(i * CHUNK_SIZE) + j], 0); // inside each complex containing the frequency, insert the time domain
            }

            results[i] = AudioTransform.fft(complex); // Perform FFT analysis on each chunk:
        }

        return results;
    }

    private Map<Long, Long> generateAudioFingerprint(Complex[][] results) {
        // SET 5 RANGES: 0~40, 40~80, 80~120, 120~180, 180~300
        // FOR EACH chunk, TAKE THE FREQUENCY PEAK FOR EACH RANGE
        long[][] peakFreq = new long[results.length][5];
        double[][] highestMag = new double[results.length][5];

        Map<Long, Long> audioFingerprint = new HashMap<>();

        for (int i = 0; i < results.length; i++) { // for each chunk

            for (int freq = 40; freq < 300; freq++) { // verify for each frequency
                // Get the magnitude:
                double mag = Math.log(results[i][freq].abs() + 1);
                // Find out which range we are in:
                int rangeIndex = getRangeIndex(freq);

                // Save the highest magnitude and corresponding frequency:
                if (mag > highestMag[i][rangeIndex]) {
                    highestMag[i][rangeIndex] = mag;
                    peakFreq[i][rangeIndex] = freq;
                }
            }

            audioFingerprint.put(generateHash(peakFreq[i]), (long) i);
        }

        return audioFingerprint;
    }

    private int getRangeIndex(int freq) {
        int i = 0;
        while (RANGE[i] < freq)
            i++;
        return i;
    }

    protected long generateHash(long[] p) {
        return Long.parseLong(new StringBuilder().append(p[4] - (p[4] % FUZ_FACTOR))
                .append(p[3] - (p[3] % FUZ_FACTOR))
                .append(p[2] - (p[2] % FUZ_FACTOR))
                .append(p[1] - (p[1] % FUZ_FACTOR)).toString());
    }
}
