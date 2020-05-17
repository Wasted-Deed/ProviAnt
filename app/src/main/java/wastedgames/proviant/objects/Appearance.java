package wastedgames.proviant.objects;

import android.graphics.Bitmap;

import wastedgames.proviant.maintenance.ThreadSolver;

public class Appearance {
    private Bitmap[] appearance;
    private int currentFrame;
    private int updateFrequency;
    private boolean isStatic;

    public Appearance(Bitmap[] appearance, int currentFrame, int updateFrequency) {
        this.appearance = appearance;
        this.currentFrame = currentFrame;
        this.updateFrequency = updateFrequency;
        isStatic = appearance.length == 1;
    }

    public Appearance(Bitmap appearance) {
        this(new Bitmap[]{appearance}, 0, 0);
    }

    public Appearance() {
        this(new Bitmap[0], 0, 0);
    }

    public void updateAppearance() {
        if (isStatic) {
            currentFrame = 0;
            return;
        }
        if (ThreadSolver.CURRENT_FRAME % updateFrequency == 0) {
            currentFrame++;
            currentFrame %= appearance.length;
        }

    }

    public void setUpdateFrequency(int updateFrequency) {
        this.updateFrequency = updateFrequency;
    }

    public Bitmap getCurrentFrame() {
        return appearance[currentFrame];
    }

    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }

    public int getCurrentNumber() {
        return currentFrame;
    }

    public int getMaxNumber() {
        return appearance.length - 1;
    }
    public boolean isFinalNumber() {
        return getCurrentNumber() >= getMaxNumber();
    }
}
