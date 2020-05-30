package wastedgames.proviant.objects.ui;

import android.graphics.Canvas;
import android.graphics.Paint;

import wastedgames.proviant.engine.Vector2;
import wastedgames.proviant.layouts.GameField;
import wastedgames.proviant.objects.InterfaceUnit;

public class Controller extends InterfaceUnit {
    private final Vector2 INIT_CENTER;
    final int SIZE_CENTER = 7;
    final int SIZE_FRAME = 20;
    final int STEP = 5;
    final int CENTER_LOCATION = SIZE_FRAME + STEP;

    private Vector2 center;
    private boolean isPicked;

    public Controller(Vector2 center) {
        super(center);
        this.center = center;
        INIT_CENTER = new Vector2(CENTER_LOCATION,
                GameField.SCALED_SCREEN.getY() - CENTER_LOCATION);
        setToInitialPosition();
    }

    public void setToInitialPosition() {
        center.setCoordinates(INIT_CENTER);
        isPicked = false;
    }

    @Override
    public void draw(Canvas canvas, Paint paint, Vector2 camera) {
        canvas.drawOval(camera.getX() + center.getX() - SIZE_CENTER,
                camera.getY() + center.getY() - SIZE_CENTER,
                camera.getX() + center.getX() + SIZE_CENTER,
                camera.getY() + center.getY() + SIZE_CENTER,
                paint);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawOval(camera.getX() + STEP,
                camera.getY() + INIT_CENTER.getY() - SIZE_FRAME,
                camera.getX() + STEP + SIZE_FRAME * 2,
                camera.getY() + INIT_CENTER.getY() + SIZE_FRAME,
                paint);
        paint.setStyle(Paint.Style.FILL);
    }

    public void setCenter(Vector2 center) {
        this.center = center;
    }

    public Vector2 getBiasVector() {
        return center.subtractedCopy(INIT_CENTER);
    }

    public void updateCenterPosition(Vector2 touch) {
        Vector2 newBias = touchBias(touch);
        if (isTouched(touch)) {
            isPicked = true;
        } else {
            newBias.setLength(SIZE_FRAME);
        }
        if (isPicked) {
            center.setCoordinates(INIT_CENTER);
            center.addVector2(newBias);
        }
    }

    public int getAngle() {
        return getBiasVector().getAngleWithX();
    }

    public Vector2 touchBias(Vector2 touch) {
        return touch.subtractedCopy(INIT_CENTER);
    }

    public boolean isControlled() {
        return isPicked;
    }

    @Override
    public boolean isTouched(Vector2 touch) {
        return touchBias(touch).getLength() <= SIZE_FRAME;
    }
}
