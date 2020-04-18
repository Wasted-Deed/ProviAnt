package wastedgames.proviant.objects.ui;

import android.graphics.Canvas;
import android.graphics.Paint;

import wastedgames.proviant.engine.Vector2;
import wastedgames.proviant.layouts.GameField;
import wastedgames.proviant.objects.InterfaceUnit;

public class Controller extends InterfaceUnit {
    final int SIZE_CENTER = 7;
    final int SIZE_FRAME = 20;
    final int STEP = 5;
    private Vector2 center;

    public Controller(Vector2 pos) {
        super(pos);
        center = pos;
    }

    @Override
    public void draw(Canvas canvas, Paint paint, Vector2 camera) {
        Vector2 bottomLeft = new Vector2(camera.getX(),
                camera.getY() + GameField.SCALED_SCREEN.getY());
        int centerStep = SIZE_FRAME - SIZE_CENTER + STEP;
        canvas.drawOval(bottomLeft.getX() + centerStep,
                bottomLeft.getY() - SIZE_FRAME - SIZE_CENTER - STEP,
                bottomLeft.getX() + centerStep + SIZE_CENTER * 2,
                bottomLeft.getY() - SIZE_FRAME + SIZE_CENTER - STEP,
                paint);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawOval(bottomLeft.getX() + STEP,
                bottomLeft.getY() - STEP - SIZE_FRAME * 2,
                bottomLeft.getX() + STEP + SIZE_FRAME * 2,
                bottomLeft.getY() - STEP,
                paint);
        paint.setStyle(Paint.Style.FILL);
    }

    public void setCenter(Vector2 center) {
        this.center = center;
    }
}
