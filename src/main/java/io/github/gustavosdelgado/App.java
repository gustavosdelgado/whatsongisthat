package io.github.gustavosdelgado;

import edu.emory.mathcs.jtransforms.fft.DoubleFFT_1D;
import io.github.gustavosdelgado.audio.AudioAnalyzer;
import io.github.gustavosdelgado.audio.AudioFormatBuilder;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.DataLine;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class App {
    public static void main(String[] args) {

        try {
            AudioAnalyzer.analyze();

        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
            System.exit(-1);
        }
    }
}
