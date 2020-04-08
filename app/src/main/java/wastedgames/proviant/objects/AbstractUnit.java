package wastedgames.proviant.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import java.util.HashMap;

import wastedgames.proviant.engine.Vector2;
import wastedgames.proviant.enumerations.Image;
import wastedgames.proviant.enumerations.UnitState;
import wastedgames.proviant.interfaces.Drawable;
import wastedgames.proviant.interfaces.Updatable;
import wastedgames.proviant.maintenance.ResourcesLoader;

public abstract class AbstractUnit implements Updatable, Drawable {
    protected HashMap<UnitState, Appearance> appearance;
    protected CollisionMask mask;
    protected UnitState currentState;
    protected float x;
    protected float y;

    protected int hp;
    protected int actionDistance;

    protected boolean isMirrored;

    public AbstractUnit(float x, float y) {
        this.x = x;
        this.y = y;
        isMirrored = false;
        appearance = new HashMap<>();
        currentState = UnitState.IDLE;

    }

    @Override
    public void draw(Canvas canvas, Paint paint, Vector2 camera) {
        Bitmap currentFrame = getCurrentFrame();
        Matrix matrix = new Matrix();
        int arg = isMirrored ? -1 : 1;
        matrix.setScale(arg, 1);
        matrix.postTranslate(x - arg * currentFrame.getWidth() / 2f,
                y - currentFrame.getHeight());
        canvas.drawBitmap(currentFrame, matrix, paint);
        appearance.get(currentState).updateAppearance();
    }

    public void setCurrentState(UnitState currentState) {
        this.currentState = currentState;
    }

    @Override
    public int getX() {
        return (int) x;
    }

    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return (int) y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getActionDistance() {
        return actionDistance;
    }

    public boolean isPointReachable(int x, int y) {
        return Math.abs(this.x - x) <= actionDistance &&
                Math.abs(this.y - y) <= actionDistance;
    }

    public Vector2 getLeftTop() {
        Vector2 lt = mask.getLeftTop();
        Vector2 rb = mask.getRightBottom();
        if (!isMirrored) {
            return new Vector2(lt.getX() + x, lt.getY() + y);
        }
        return new Vector2(-rb.getX() + x, lt.getY() + y);
    }

    public Vector2 getRightBottom() {
        Vector2 lt = mask.getLeftTop();
        Vector2 rb = mask.getRightBottom();
        if (!isMirrored) {
            return new Vector2(rb.getX() + x, rb.getY() + y);
        }
        return new Vector2(-lt.getX() + x, rb.getY() + y);
    }

    protected Bitmap getCurrentFrame() {
        return appearance.get(currentState).getCurrentFrame();
    }

    @Override
    public int getWidth() {
        return getCurrentFrame().getWidth();
    }

    @Override
    public int getHeight() {
        return getCurrentFrame().getHeight();
    }

    protected void randomMirror() {
        isMirrored = Math.random() > 0.5;
    }

    protected void setStandardMask(Image image) {
        Bitmap maskBit = ResourcesLoader.getImage(image);
        mask = new CollisionMask(-maskBit.getWidth() / 2,
                -maskBit.getHeight(),
                maskBit.getWidth() / 2, 0);

    }

    public boolean isTouched(int x, int y) {
        return getLeftTop().getX() < x && getLeftTop().getY() < y &&
                getRightBottom().getX() > x && getRightBottom().getY() > y;
    }
}
