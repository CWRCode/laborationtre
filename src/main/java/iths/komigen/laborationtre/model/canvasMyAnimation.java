package iths.komigen.laborationtre.model;

import javafx.animation.AnimationTimer;

public abstract class canvasMyAnimation extends AnimationTimer {

    long lastFrameTimeNanos;

    @Override
    public void handle(long now) {

        float secondsSinceLastFrame = (float)((now - lastFrameTimeNanos) / 1e9);
        lastFrameTimeNanos = now;
        tick(secondsSinceLastFrame);
    }

    public abstract void tick(float secondsSinceLastFrame);


}
