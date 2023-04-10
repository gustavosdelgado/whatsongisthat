import javax.sound.sampled.*;
import javax.swing.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class Solution {

    public static void main(String[] args) {
        AudioFormat format = new AudioFormat(44100f, 16, 1, true, true);
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
        boolean stopped = false;

        try {
            TargetDataLine line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);

            JOptionPane.showMessageDialog(null, "Hit ok to start recording");
            line.start();

            Thread audioRecordingThread = new Thread(() -> {
                AudioInputStream recordingStream = new AudioInputStream(line);
//                File output = new File("record.csv");

                ByteArrayOutputStream output = new ByteArrayOutputStream();
                try {
                    AudioSystem.write(recordingStream, AudioFileFormat.Type.AU, output);

                } catch (IOException e) {
                    System.err.println("Recording failure: " + e);
                }

                byte[] byteArray = output.toByteArray();
                for (int i = 0; i < byteArray.length; i++) {
                    System.out.print(byteArray[i] + ",");
                }

                System.out.println("Recording stopped.");

            });

            audioRecordingThread.start();

            JOptionPane.showMessageDialog(null, "Hit ok to stop recording");
            line.stop();
            line.close();


        } catch (Exception e) {
            System.err.println("I/O problems: " + e);
            System.exit(-1);
        }
    }
}