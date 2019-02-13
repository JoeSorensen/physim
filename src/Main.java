import processing.core.PApplet;

public class Main extends PApplet {
    private double pos = 90;
    private double posx = 90;
    private static int platformamnt = 25;
    private boolean isbounce = false;
    private double v = 0;
    private double vx = 0;
    private double bounce = 30;
    private static final double gravity = 9.8;
    private static int width = 1000;
    private static int height = 700;
    private static int[] posdata = new int[platformamnt];
    private static int[] posdatay = new int[platformamnt];
    private Physics p = new Physics(60, gravity);

    public void settings() {
        size(width, height);
    }

    public void setup() {
        background(50);
        ellipse(90, 90, 10, 10);
        frameRate(60);
        for (int i = 0; i < platformamnt; i++) {
            posdata[i] = width + (int) (Math.random() * (1000));
            posdatay[i] = (int) (Math.random() * (350 - 200) + 200);
        }
    }

    public void draw() {
        background(50);
        text((float) v, 10, 10);
        text((float)vx, 10, 20);
        text((float) bounce, 10, 30);
        text((float) posx, 10, 40);
        text((float) pos, 10, 50);
        text((float)smartTolerance(v), 10, 60);
        double delta = p.deltaY(v, 1);
        if(mousePressed) {
            pos = mouseY;
            posx = mouseX;
        }
        v = p.velocity(v, gravity, 1);
        vx = p.velocity(vx, 2, 1);
        pos = pos + delta;
        if (pos > height || posx < -10 || posx > width || p.inVoid(width, height, posx, pos)) {
            pos = -5;
            posx = 5;
        }
        if(v < -50)
            v += 5;
        for (int k = 0; k < posdata.length; k++) {
            rect(posdata[k], posdatay[k], 100, 50);
            rect(posdata[k] - 100, posdatay[k] + 200, 100, 50);
            posdata[k] -= 3;
            if (posdata[k] <= -150) {
                posdata[k] = width + (int) (Math.random() * (1000));
                if (k == posdata.length - 1) {
                    if (posdata[k] <= posdata[k - 1])
                        posdata[k] += posdata[k] - posdata[k - 1];
                } else
                    posdata[k] += posdata[k] - posdata[k + 1];
                while (posdata[k] < 1000)
                    posdata[k] += 1000;
            }
        }
        for (int i = 0; i < posdata.length; i++) {
            if (isbounce) {
                posx = posx + p.deltaX(vx, 1, bounce+100 / 10);
                pos = pos + p.deltaY(v, 1);
            }
            if (p.collision(posdata[i], posdatay[i], posx, pos, 10, 100, 50, smartTolerance(v)) || p.collision(posdata[i] - 100, posdatay[i] + 200, posx, pos, 10, 100, 50, smartTolerance(v))) {
                pos -= 4;
                if (bounce <= 0) {
                    bounce = 30;
                    isbounce = false;
                }
                isbounce = true;
                bounce -= v / 2;
                v = -(bounce/2);
                vx = bounce/10;
            }
        }
        ellipse((float) posx, (float) pos, 10, 10);
        if (bounce > 0 && isbounce) {
            bounce -= 0.1;
        }
    }

    private double smartTolerance(double v){
        //This method calculates the collision detection tolerance. Basically, the higher the ball's velocity, the higher the tolerance.
        double result;
        result = v/2.5;
        if(result < 0)
            result *= -1;
        if(vx >= 7)
            return 15;
        return result;
    }

    public static void main(String[] args) {
        String[] processingArgs = {"Main"};
        Main game = new Main();
        PApplet.runSketch(processingArgs, game);
    }
}
