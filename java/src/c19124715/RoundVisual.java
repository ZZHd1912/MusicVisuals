package c19124715;

import ddf.minim.analysis.BeatDetect;
import ddf.minim.analysis.FFT;
import processing.core.*;

public class RoundVisual {
    C19124715Visual cv;
    float x, y; // 球的位置
    float size = 180; // 球的大小
    float speed = (float) 1.5; // 球的速度
    int colorValue = 0; // 球的颜色值
    BeatDetect beat;

    public RoundVisual(C19124715Visual cv)
    {
        this.cv = cv;
        x = cv.width/2;
        y = cv.height/2;
        beat = new BeatDetect(cv.getFrameSize(), cv.getSampleRate());
        // beat = new BeatDetect();
        beat.detectMode(BeatDetect.SOUND_ENERGY);
        beat.setSensitivity(200);
    }

    public void render()
    {
        cv.translate(x, y);  // 将坐标系移动到屏幕中心
        cv.noStroke();  // 取消描边
        cv.fill(255);  
        float radius = 250;             // 扇形的半径
        cv.beginShape();
        int n = cv.getFFT().specSize() - 50;

        FFT fft = cv.getFFT();
        for (int k = 0; k < n; k++){
            radius = (PApplet.map(PApplet.log(1 + fft.getBand(k)), 0, 2, 180, 280));
            // System.out.println(getFFT().getBand(k));
            float theta =  k * PApplet.TWO_PI / n;
            float x = radius * PApplet.cos(theta);
            float y = radius * PApplet.sin(theta);
            // System.out.print(radius + "  ");
            cv.vertex(x, y);
        }
        radius = (PApplet.map(PApplet.log(1 + fft.getBand(0)), 0, 2, 180, 280));
        cv.vertex(radius, 0); 
        // System.out.print(radius+"  ");
        cv.endShape(PApplet.CLOSE);
        cv.resetMatrix();
        
        if (beat != null){
            beat.detect(cv.getAudioBuffer());
            // 如果检测到了节拍，打印出当前时间戳
            if (beat.isOnset()) {
                System.out.println(cv.millis());
            }
        }

        // 根据速度改变球的大小
        size += speed;
        if (size > 250 || size < 180) {
            speed = -speed; // 碰到边界反弹
            if (speed >= 0)
            {
                colorValue = speed >= 0 ? (int) (cv.random(0, 255)) : colorValue; // 随机改变颜色
                // speed = amp;
                speed = PApplet.map(cv.getSmoothedAmplitude(), 0, 1, (float) 1.5, 30);  //根据振幅改变速度
                // System.out.println(speed);
            }
        }

        // 根据颜色值绘制球
        cv.fill(colorValue, 255, 255); // 填充颜色
        cv.noStroke(); // 不描边
        cv.ellipse(x, y, size, size); // 绘制球
    }
}
