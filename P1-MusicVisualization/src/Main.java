import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import ddf.minim.analysis.*;
import processing.core.PApplet;

import java.io.File;

/**
 * Created by BodeNg on 2016/12/12.
 */
public class Main extends PApplet {
    private static final long serialVersionUID = 1L;
    private static final String resourcePath = "resource";

    private static String fileName;
    private static boolean isStart = false;
    private static String myFilePath;

    private Minim minim;
    private AudioPlayer player;
    private FFT fft;

    public void fileSelected(File selection) {
        if (selection == null) {
            println("no selection so far...");
        } else {
            myFilePath = selection.getAbsolutePath();
            fileName = selection.getName();
            println("User selected " + myFilePath);
            isStart = true;
        }
    }

    @Override
    public void setup() {
        size(512,256);
        background(0x000000);
        selectInput("Select a file : ", "fileSelected");
    }

    @Override
    public void draw() {
        if (!isStart) return;
        if (player == null) {
            minim = new Minim(this);
//        player = minim.loadFile(resourcePath + File.separator + "test.mp3");
            player = minim.loadFile(myFilePath);
            fft = new FFT(player.bufferSize(), player.sampleRate());
            player.play();
        }
        background(0x000000);
        draw_Waveform();
        draw_FrequencySpectrum();
    }

    private void draw_Waveform() {
        stroke(135, 206, 250);
        textAlign(CENTER, CENTER);
        text(fileName, 256, 128);


        for(int i = 0; i < player.bufferSize() - 1; i++)
        {
            float left1 = 50 + player.left.get(i) * 50;
            float left2 = 50 + player.left.get(i+1) * 50;
            float right1 = 60 + player.right.get(i) * 50;
            float right2 = 60 + player.right.get(i+1) * 50;
            line(i, left1, i+1, left2);
            line(i, right1, i+1, right2);
        }
    }

    private void draw_FrequencySpectrum() {
        fft.forward(player.mix);
        noStroke();
//        fill(255, 0, 0, 128);
        fill(0xFF8247);
        fill(255, 106, 106, 128);
        for(int i = 0; i < 250; i++) {
            float b = fft.getBand(i);
            rect(i*2, height - b, 5, b);
        }
    }

    @Override
    public void stop() {
        player.close();
        minim.stop();
        super.stop();
    }

    public static void main(String args[]) {
        PApplet.main(new String[] { "Main" });
    }
}