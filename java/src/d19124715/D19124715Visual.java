package d19124715;

import ie.tudublin.*;
import processing.core.PImage;

public class D19124715Visual extends Visual{
    Render[] render;
    RoundVisual rv;
    WaveVisual wv;
    DropVisual tv;
    int mode = 1;
    int move = 0;

    public void keyPressed()
    {
        if (key == ' ')
        {
            getAudioPlayer().cue(0);
            getAudioPlayer().play();
        } else if (key == '1'){
            mode = 1;
        }else if (key == '2'){
            mode = 2;
        }else if (key == '3'){
            mode = 3;
        }else if (key == 'a'){
            move = 1;
        }else if (key == 'd'){
            move = 2;
        }
    }

    public void settings() {
        size(800, 800);
    }

    public void setup() {
        startMinim();

        // Call loadAudio to load an audio file to process 
        // loadAudio("heroplanet.mp3");   
        // Call this instead to read audio from the microphone
//         startListening();
//        loadAudio("Havana.mp3");
//        loadAudio("What Makes You Beautiful.mp3");
        loadAudio("Coincidance.mp3");
//        loadAudio("heroplanet.mp3");
        smooth(); // 平滑处理
        colorMode(HSB);
        // rv = new RoundVisual(this);
        // wv = new WaveVisual(this);
        // tv = new TestVisual(this);
        render = new Render[3];
        render[0] = new RoundVisual(this);
        render[1] = new WaveVisual(this);
        render[2] = new DropVisual(this);
    }

    public void draw() {
        background(0); // 黑色色背景
        PImage img;
        try {
            calculateFFT();     // Call this if you want to use FFT data
        } catch(VisualException e) {
            e.printStackTrace();
        }
        calculateFrequencyBands();
        calculateAverageAmplitude();
        switch (mode) {
            case 1:
                // rv.render();
                render[0].render();
                break;
            case 2:
                // wv.render();
                render[1].render();
                break;
            case 3:
                // tv.render();
                render[2].render();
                break;
            default:
                // rv.render();
                render[0].render();
        }

    }
    
}