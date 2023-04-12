package io.github.gustavosdelgado.audio;

import java.io.ByteArrayOutputStream;

public class AudioAnalyzer {

    private static final int CHUNK_SIZE = 4096;

    private static final int[] RANGE = new int[]{40, 80, 120, 180, 300};

    public void analyze(ByteArrayOutputStream output) {
        Complex[][] results = performFFT(output);
        long[][] peakFrequencies = calculatePeakFrequencies(results);
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

            results[i] = FFT.fft(complex); // Perform FFT analysis on each chunk:
        }

        return results;
    }

    private long[][] calculatePeakFrequencies(Complex[][] results) {
        // SET 5 RANGES: 0~40, 40~80, 80~120, 120~180, 180~300
        // FOR EACH chunk, TAKE THE FREQUENCY PEAK FOR EACH RANGE
        long[][] peakFreq = new long[results.length][5];
        double[][] highestMag = new double[results.length][5];

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
        }

        return peakFreq;
    }

    public int getRangeIndex(int freq) {
        int i = 0;
        while (RANGE[i] < freq)
            i++;
        return i;
    }
}
