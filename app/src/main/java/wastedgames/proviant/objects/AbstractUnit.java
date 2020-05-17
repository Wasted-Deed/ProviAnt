package wastedgames.proviant.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

import java.util.HashMap;

import wastedgames.proviant.engine.Vector2;
import wastedgames.proviant.enumerations.Image;
import wastedgames.proviant.enumerations.UnitState;
import wastedgames.proviant.interfaces.Drawable;
import wastedgames.proviant.interfaces.Updatable;
import wastedgames.proviant.layouts.GameField;
import wastedgames.proviant.maintenance.ResourcesLoader;
import wastedgames.proviant.objects.landscape.TileMap;

public abstract class AbstractUnit implements Updatable, Drawable {
    protected HashMap<UnitState, Appearance> appearance;
    protected CollisionMask mask;
    protected UnitState currentState;
    protected Vector2 pos;
    protected Vector2 dir;

    protected static int MAX_HP;
    protected float parallax;
    protected int hp;
    protected int actionDistance;
    protected int angle;

    protected boolean isMirrored;

    public AbstractUnit(float x, float y) {
        this.pos = new Vector2(x, y);
        dir = new Vector2(0, 0);
        isMirrored = false;
        appearance = new HashMap<>();
        currentState = UnitState.IDLE;
        angle = 0;
        parallax = 0;
    }

    public AbstractUnit(Vector2 pos) {
        this(pos.getX(), pos.getY());
    }

    private void drawMask(Canvas canvas, Paint paint) {
        if (mask != null) {
            canvas.drawOval(getX() + mask.getLeftTop().getX() - 1, getY() + mask.getLeftTop().getY() - 1,
                    getX() + mask.getLeftTop().getX() + 1, getY() + mask.getLeftTop().getY() + 1, paint);
            canvas.drawOval(getX() + mask.getLeftBottom().getX() - 1, getY() + mask.getLeftBottom().getY() - 1,
                    getX() + mask.getLeftBottom().getX() + 1, getY() + mask.getLeftBottom().getY() + 1, paint);
            paint.setColor(Color.RED);
            canvas.drawOval(getX() + mask.getRightBottom().getX() - 1, getY() + mask.getRightBottom().getY() - 1,
                    getX() + mask.getRightBottom().getX() + 1, getY() + mask.getRightBottom().getY() + 1, paint);
            canvas.drawOval(getX() + mask.getRightTop().getX() - 1, getY() + mask.getRightTop().getY() - 1,
                    getX() + mask.getRightTop().getX() + 1, getY() + mask.getRightTop().getY() + 1, paint);
            paint.setColor(Color.WHITE);
        }
        canvas.drawOval(getX() - 1, getY() - 1, getX() + 1, getY() + 1, paint);
    }

    @Override
    public void draw(Canvas canvas, Paint paint, Vector2 camera) {
        Bitmap currentFrame = getCurrentFrame();
        canvas.drawBitmap(currentFrame, getFrameMatrix(camera), paint);
        appearance.get(currentState).updateAppearance();
        //drawMask(canvas, paint);
    }

    public void setCurrentState(UnitState currentState) {
        this.currentState = currentState;
    }

    @Override
    public void update() {
        setCurrentMask();
    }

    public Vector2 getPos() {
        return pos;
    }

    @Override
    public float getX() {
        return pos.getX();
    }

    public void setX(float x) {
        pos.setX(x);
    }

    @Override
    public float getY() {
        return pos.getY();
    }

    public void setY(float y) {
        pos.setY(y);
    }

    public int getActionDistance() {
        return actionDistance;
    }

    public boolean isPointReachable(float x, float y) {
        return Math.abs(pos.getX() - x) <= actionDistance &&
                Math.abs(pos.getY() - y) <= actionDistance;
    }

    public boolean isPointReachable(Vector2 point) {
        return isPointReachable((int) point.getX(), (int) point.getY());
    }

    protected Bitmap getCurrentFrame() {
        return appearance.get(currentState).getCurrentFrame();
    }

    private Matrix getFrameMatrix(Vector2 camera) {
        Bitmap currentFrame = getCurrentFrame();
        Matrix matrix = new Matrix();
        Vector2 center = new Vector2(currentFrame.getWidth() / 2,
                currentFrame.getHeight() / 2);
        int arg = 1;
        float shift = 1;
        if (isMirrored && getAngle() == 0) {
            arg = -1;
            matrix.setScale(arg, 1);
        }

        if (UnitState.getType(currentState) == UnitState.Type.UNDERGROUND) {
            matrix.setRotate(-getAngle(), center.getX(), center.getY());
            shift = 2;
        }
        matrix.postTranslate(getX() + camera.getX() * parallax
                        - arg * currentFrame.getWidth() / 2f,
                getY() - currentFrame.getHeight() / shift);
        return matrix;
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

    protected void setCurrentMask() {
        Bitmap maskBit = getCurrentFrame();
        if (angle != 0) {
            mask = new CollisionMask(-maskBit.getWidth() / 2,
                    -maskBit.getHeight() / 2,
                    maskBit.getWidth() / 2, maskBit.getHeight() / 2);
            mask.setRotated(angle);
        } else {
            mask = new CollisionMask(-maskBit.getWidth() / 2,
                    -maskBit.getHeight(),
                    maskBit.getWidth() / 2, 0);
        }
    }

    public boolean checkIfLanded(TileMap map) {
        return mask.checkBottom(pos, map::checkPointCollision);
    }

    public boolean isTouched(Vector2 touch) {
        return mask.isTouched(touch, pos);
    }

    public boolean checkDirCollision(TileMap map) {
        return mask.checkFront(pos.addedCopy(dir), map::checkPointCollision);
    }

    public UnitState getCurrentState() {
        return currentState;
    }

    public int getHp() {
        return hp;
    }

    public void damage(int damage) {
        hp -= damage;
    }

    public void destroy() {
        hp = 0;
    }

    public boolean isDestroyed() {
        return hp <= 0;
    }

    public int getAngle() {
        return angle;
    }

    public void setDir(Vector2 dir) {
        this.dir = dir;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    @Override
    public float getX(Vector2 camera) {
        return getX() + camera.getX() * parallax;
    }

}
