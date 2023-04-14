package io.github.gustavosdelgado;

import io.github.gustavosdelgado.audio.AudioOrchestrator;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        int input = -1;

        while (input != 0) {
            System.out.println("Type the chosen option: 0 - exit 1 - song matching, 2 - Song recording");
            input = in.nextInt();
            AudioOrchestrator audio = new AudioOrchestrator();
            audio.handleAudio(input);
        }

    }
}
