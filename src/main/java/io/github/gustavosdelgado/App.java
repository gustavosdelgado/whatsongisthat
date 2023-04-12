package io.github.gustavosdelgado;

import io.github.gustavosdelgado.audio.AudioOrchestrator;

public class App {
    public static void main(String[] args) {

        try {
            AudioOrchestrator.handleAudio(args);

        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
            System.exit(-1);
        }
    }
}
