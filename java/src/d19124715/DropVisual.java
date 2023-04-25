package d19124715;


import processing.core.PApplet;
import ddf.minim.analysis.BeatDetect;

public class DropVisual extends Render{
    D19124715Visual cv;
    int cut = 75;
    boolean[] isFall;
    float[] pos_y;
    float[] vy;
    float[] fallHeight;
    float g = 0.2f;
    float gap;
    BeatDetect beat;
    int[] trapezoid;
    int score = 0;

    public DropVisual(D19124715Visual cv)
    {
        this.cv = cv;
        isFall = new boolean[cv.getBands().length];
        pos_y = new float[cv.getBands().length];
        vy =  new float[cv.getBands().length];
        fallHeight = new float[cv.getBands().length];
        gap = cv.width / (float) cv.getBands().length;
        beat = new BeatDetect(cv.getFrameSize(), cv.getSampleRate());
        beat.detectMode(BeatDetect.SOUND_ENERGY);
        beat.setSensitivity(500);
        trapezoid = new int[]{1, cv.height - 20, (int) gap, 20};
    }


    public void render()
    {
        cv.noStroke();
        int idx = 0;
        int threshold = 25;
        if (beat != null){
            beat.detect(cv.getAudioBuffer());
            // 如果检测到了节拍，随机选一个掉落
            if (beat.isOnset()) {
                do {
                    idx = (int)cv.random(0, vy.length);
                } while (isFall[idx] == true || cv.getSmoothedBands()[idx] * 0.2f < threshold--);
                isFall[idx] = true;
                pos_y[idx] = 0;
                fallHeight[idx] = cv.getSmoothedBands()[idx] * 0.2f;
                vy[idx] = 0;
                // System.out.println(idx);
            }
        }
        for(int i = 0 ; i < cv.getBands().length ; i ++)
        {

            if (isFall[i])
            {
                vy[i] += g;
                pos_y[i] += vy[i];
                if (i == trapezoid[0] && pos_y[i] + fallHeight[i] > cv.height - trapezoid[3])
                {

                    int trans = (int) (pos_y[i] + fallHeight[i] - cv.height + trapezoid[3]);
                    if (pos_y[i] > cv.height - trapezoid[3])  //可以完全接住
                        trans = (int) fallHeight[i];
                    score += trans;
                    fallHeight[i] -= trans;
                }
                cv.fill(PApplet.map(fallHeight[i], 0, cv.height , 0, 255), 255, 255);
                cv.rect(i * gap, pos_y[i], gap, fallHeight[i]);
            }
            cv.fill(PApplet.map(i, 0, cv.getBands().length, 255, 0), 255, 255);
            float height  = cv.getSmoothedBands()[i] * 0.2f;
            cv.rect(i * gap, 0, gap, height);
            if (cv.move == 1){
                trapezoid[0] =  trapezoid[0] == 0 ? 0 : trapezoid[0] - 1;
                cv.move = 0;
            } else if (cv.move == 2){
                trapezoid[0] =  trapezoid[0] == 9 ? 9 : trapezoid[0] + 1;
                cv.move = 0;
            }
            int x = (int) (trapezoid[0] % 10 * gap);
            int y = (int) trapezoid[1];
            int w = (int) trapezoid[2];
            int h = (int) trapezoid[3];
            cv.quad(x, y, x+w, y, x+w*3/4, y+h, x+w/4, y+h);
            if (pos_y[i] > cv.height)
            {
                isFall[i] = false;
            }
            // System.out.println(score);
            cv.fill(255);
            cv.text("Score: " + score, 0, 200);
        }
    }

}
