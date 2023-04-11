package io.github.gustavosdelgado.audio;

import javax.sound.sampled.AudioFormat;

public class AudioFormatBuilder {

    private static float SAMPLE_RATE = 44100;
    private static int SAMPLE_SIZE = 16;
    private static int CHANNELS = 1;
    private static boolean SIGNED = true;
    private static boolean BIG_ENDIAN = true;

    public static AudioFormat build() {
        return new AudioFormat(SAMPLE_RATE, SAMPLE_SIZE, CHANNELS, SIGNED, BIG_ENDIAN);
    }
}
