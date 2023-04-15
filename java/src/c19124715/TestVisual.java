package c19124715;

import processing.core.PApplet;

public class TestVisual {
    C19124715Visual cv;
    int cut = 30;
    boolean[] isFall;
    float[] pos_y;
    float[] vy;
    float g = 1;
    float gap;

    public TestVisual(C19124715Visual cv)
    {
        this.cv = cv; 
        isFall = new boolean[cv.getBands().length];
        pos_y = new float[cv.getBands().length];
        vy =  new float[cv.getBands().length];
        gap = cv.width / (float) cv.getBands().length;
    }

    public void render()
    {
        cv.noStroke();
        for(int i = 0 ; i < cv.getBands().length ; i ++)
        {
            cv.fill(PApplet.map(i, 0, cv.getBands().length, 255, 0), 255, 255);
            float height  = cv.getSmoothedBands()[i] * 0.2f;
            System.out.println(height);
            if (height > cut){
                if (!isFall[i]){
                    isFall[i] = true;
                    pos_y[i] = cut;
                    vy[i] = 0;
                }
                vy[i] += g;
                pos_y[i] += vy[i];
                cv.rect(i * gap, 0, gap,cut); 
                cv.rect(i * gap, pos_y[i], gap, height - cut);
            } else {
                isFall[i] = false;
                cv.rect(i * gap, 0, gap, height); 
            }

            if (pos_y[i] > cv.height)
            {
                isFall[i] = false;
            }
        }
    }
}
