package wastedgames.proviant.objects.ui;

import android.graphics.Canvas;
import android.graphics.Paint;

import wastedgames.proviant.engine.Vector2;
import wastedgames.proviant.layouts.GameField;
import wastedgames.proviant.objects.InterfaceUnit;

public class AttackButton extends InterfaceUnit {
    private final Vector2 INIT_CENTER;
    final int SIZE_FRAME = 10;
    final int STEP = 5;

    public AttackButton(Vector2 pos) {
        super(pos);
        INIT_CENTER = new Vector2(GameField.SCALED_SCREEN.getX() - STEP - SIZE_FRAME,
                GameField.SCALED_SCREEN.getY() - SIZE_FRAME - STEP);
    }

    @Override
    public void draw(Canvas canvas, Paint paint, Vector2 camera) {
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawOval(camera.getX() + INIT_CENTER.getX() - SIZE_FRAME,
                camera.getY() + INIT_CENTER.getY() - SIZE_FRAME,
                camera.getX() + INIT_CENTER.getX() + SIZE_FRAME,
                camera.getY() + INIT_CENTER.getY() + SIZE_FRAME,
                paint);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    public boolean isTouched(Vector2 touch) {
        return Vector2.getDistance(INIT_CENTER, touch) <= SIZE_FRAME;
    }

}
