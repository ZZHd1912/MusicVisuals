package c19124715;

import processing.core.*;

public class WaveVisual extends Render{

    float cy = 0;
    C19124715Visual cv;
    float[] vert;

    public WaveVisual(C19124715Visual cv)
    {
        this.cv = cv;
        cy = this.cv.height / 2;
        vert = new float[this.cv.width];
        System.out.println(vert.length);
    }
    @Override
    public void render()
    {
        for (int i = vert.length - 1; i > 0; i--)
        {   
            vert[i] = vert[i-1];
        }
        vert[0] = cv.getSmoothedAmplitude();


        // System.out.println(vert[0]);
        // cv.beginShape();
        // cv.vertex(0, cy);
        for(int i = 0 ; i < vert.length - 1 ; i ++)
        {
            cv.stroke(PApplet.map(i, 0, vert.length , 0, 255), 255, 255);
            // cv.vertex(i, cy - cy * vert[i]);
            cv.line(i, cy - vert[i] * cy, i, cy);
        }
        // cv.vertex(cv.height, cy);
        // cv.endShape(PApplet.CLOSE);
    }

}
