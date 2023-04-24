package d19124715;


import processing.core.*;

public class WaveVisual extends Render{

    float cy = 0;
    D19124715Visual cv;
    float[] vert;
    float[] pointX1;
    float[] pointX2;
    float[] pointY1;
    float[] pointY2;
    int nodeNum = 189;

    public WaveVisual(D19124715Visual cv)
    {
        this.cv = cv;
        cy = this.cv.height / 2;
        vert = new float[this.cv.width];
        System.out.println(vert.length);
        pointX1 = new float[nodeNum];
        pointX2 = new float[nodeNum];
        pointY1 = new float[nodeNum];
        pointY2 = new float[nodeNum];

    }

    PVector polarToCartesian(float r, float theta) {
        float x = r * PApplet.cos(theta);
        float y = r * PApplet.sin(theta);
        return new PVector(x, y);
    }

    public void render()
    {

        cv.translate(cv.width/2, cv.height/2); // 将坐标原点移动到画布中心
        for (int i = vert.length - 1; i > 0; i--)
        {
            vert[i] = vert[i-1];
        }
        vert[0] = cv.getSmoothedAmplitude();
        float a = 8; // 螺线参数a
        float b = 18f; // 螺线参数b
        float tMax = 6 * PApplet.PI; // 参数t的最大值
        float dt = 0.1f; // 参数t的步长
        float r; // 极径
        float theta; // 极角
        float x1, y1, x2, y2, x3, y3; // 坐标点
        // 绘制螺线
        int k = 0;
        cv.noStroke();  // 取消描边
        for (float t = 0; t < tMax; t += dt) {
            cv.fill(PApplet.map((k+cv.frameCount) % nodeNum,  0, nodeNum , 0, 255), 255, 255);
            r = a + b * t; // 计算极径
            theta = t; // 极角与参数t相等
            x1 = polarToCartesian(r, theta).x; // 将极坐标转换为笛卡尔坐标
            y1 = polarToCartesian(r, theta).y;
            r = a + b * (t + dt);
            theta = t + dt;
            x2 = polarToCartesian(r, theta).x;
            y2 = polarToCartesian(r, theta).y;
            x3 = polarToCartesian(r + cy * vert[k], theta - dt / 2).x;
            y3 = polarToCartesian(r + cy * vert[k], theta - dt / 2).y;
            pointX1[k] = x1;
            pointX2[k] = x2;
            pointY1[k] = y1;
            pointY2[k] = y2;
            cv.triangle(x1, y1, x2, y2, x3, y3);
            k++;
        }


        // System.out.println(vert[0]);
        // cv.beginShape();
        // cv.vertex(0, cy);
        // for(int i = 0 ; i < vert.length - 1 ; i ++)
        // {
        //     cv.stroke(PApplet.map(i, 0, vert.length , 0, 255), 255, 255);
        //     // cv.vertex(i, cy - cy * vert[i]);
        //     cv.line(i, cy - vert[i] * cy, i, cy);
        // }
        // cv.vertex(cv.height, cy);
        // cv.endShape(PApplet.CLOSE);
    }

}
