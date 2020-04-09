package wastedgames.proviant.objects.ui;

import android.graphics.Canvas;
import android.graphics.Paint;

import wastedgames.proviant.engine.Vector2;
import wastedgames.proviant.enumerations.UnitState;
import wastedgames.proviant.interfaces.Drawable;
import wastedgames.proviant.interfaces.Updatable;
import wastedgames.proviant.objects.AbstractUnit;

public class Interface implements Drawable, Updatable {
    HpBar hp;
    ProcessingBar process;
    AbstractUnit holder;
    private final int step = 20;

    public Interface(AbstractUnit holder) {
        this.holder = holder;
        this.hp = new HpBar(holder.getX(), holder.getY());
        process = new ProcessingBar(holder.getX(), holder.getY() - step);
    }

    @Override
    public void draw(Canvas canvas, Paint paint, Vector2 camera) {
        if (holder.getCurrentState() == UnitState.WORK) {
            process.setX(holder.getX());
            process.setY(holder.getY() - step);
            process.draw(canvas, paint, camera);
        }
    }

    @Override
    public int getX() {
        return 0;
    }

    @Override
    public int getY() {
        return 0;
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public void update() {

    }
}
