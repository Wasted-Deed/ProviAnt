package wastedgames.proviant.objects.ui;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import wastedgames.proviant.engine.Vector2;
import wastedgames.proviant.objects.InterfaceUnit;

public class HpBar extends InterfaceUnit {
    private int currentHP;

    public HpBar(Vector2 pos) {
        super(pos);
    }

    @Override
    public void draw(Canvas canvas, Paint paint, Vector2 camera) {
        int color = paint.getColor();
        paint.setColor(Color.RED);
        for (int i = 0; i < currentHP; i++) {
            float x1 = 5 + camera.getX() + i * 10;
            float y1 = camera.getY() + 5;
            canvas.drawOval(x1, y1, x1 + 5, y1 + 5, paint);
        }
        paint.setColor(color);

    }

    public void setCurrentHP(int currentHP) {
        this.currentHP = currentHP;
    }
}
