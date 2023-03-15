package c19124715;

import ie.tudublin.*;
import processing.core.PImage;

public class C19124715Visual extends Visual{

    RoundVisual rv;

    int mode = 1;

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
        loadAudio("heroplanet.mp3");
        smooth(); // 平滑处理
        colorMode(HSB);
        rv = new RoundVisual(this);


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
                rv.render();
                break;
            default:
                rv.render();
        }


    }
    
}