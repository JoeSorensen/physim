class Physics {
    private static double gravity;
    private static int fps;

    Physics(int fps, double gravity) {
        Physics.fps = fps;
        Physics.gravity = gravity;
    }

    double deltaY(double initv, double time) {
        double delta = ((initv * time) + (0.5 * gravity * Math.pow(time, 2))) / fps;
        return delta;
    }

    double velocity(double initv, double acceleration, double time) {
        double v = initv + (acceleration * time) / fps;
        return v;
    }

    double deltaX(double initv, double time, double acceleration) {
        double delta = ((initv * time) + (0.5 * acceleration * Math.pow(time, 2))) / fps;
        return delta;
    }

    boolean collision(double boxposx, double boxposy, double ballposx, double ballposy, double sizeball, double sizeboxx, double sizeboxy, double  tolerance) {
        //We start by checking the collision on the y axis, since this is the simplest we need
        if (ballposy + (sizeball/2) <= (boxposy+tolerance) || ballposy + (sizeball/2) <= (boxposy-tolerance)){
            //Next, we're going to check the x coordinate
            if (ballposx <= (boxposx+tolerance) || ballposx <= (boxposx-tolerance) || ballposx <= ((int)((boxposx+sizeboxx)+tolerance)) || ballposx <= ((int)((boxposx+sizeboxx)-tolerance)))
                if (!(ballposy + (sizeball/2) <= (boxposy+tolerance)) || !(ballposy + (sizeball/2) <= (boxposy-tolerance)))
                    return ballposx > boxposx;
        }
        return false;
    }

    boolean inVoid(int windowWidth, int windowHeight, double posx, double posy){
        return (int) posy >= windowHeight || (int) posy >= windowWidth;
    }
}
