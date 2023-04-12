package io.github.gustavosdelgado.audio;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AudioAnalyzerTest {

    @Test
    public void calculateHash() {
        AudioAnalyzer analyzer = new AudioAnalyzer();

        assertEquals(137944733, analyzer.genereateHash(33, 47, 94, 137));
    }

}