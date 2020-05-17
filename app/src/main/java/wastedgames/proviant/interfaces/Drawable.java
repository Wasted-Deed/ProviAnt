package wastedgames.proviant.interfaces;

import android.graphics.Canvas;
import android.graphics.Paint;

import wastedgames.proviant.engine.Vector2;

public interface Drawable {
    void draw(Canvas canvas, Paint paint, Vector2 camera);

    float getX();

    float getY();

    float getX(Vector2 camera);

    int getWidth();

    int getHeight();
}
