package wastedgames.proviant.objects.ui;

import android.graphics.Canvas;
import android.graphics.Paint;

import wastedgames.proviant.engine.Vector2;
import wastedgames.proviant.enumerations.UnitState;
import wastedgames.proviant.interfaces.Drawable;
import wastedgames.proviant.interfaces.Updatable;
import wastedgames.proviant.objects.AbstractUnit;

public class Interface implements Drawable, Updatable {
    private final int STEP = 20;


    private AbstractUnit holder;
    private HpBar hp;
    private ProcessingBar process;
    private Controller controller;

    public Interface(AbstractUnit holder) {
        this.holder = holder;
        hp = new HpBar(new Vector2(0, 0));
        controller = new Controller(new Vector2(0, 0));
        process = new ProcessingBar(new Vector2(holder.getX(), holder.getY() - STEP));
    }

    @Override
    public void draw(Canvas canvas, Paint paint, Vector2 camera) {
        if (holder.getCurrentState() == UnitState.WORK) {
            process.draw(canvas, paint, camera);
        }
        hp.draw(canvas, paint, camera);
        controller.draw(canvas, paint, camera);
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
        process.setX(holder.getX());
        process.setY(holder.getY() - STEP);
        hp.setCurrentHP(holder.getHp());
    }

    public HpBar getHp() {
        return hp;
    }

    public ProcessingBar getProcess() {
        return process;
    }

    public Controller getController() {
        return controller;
    }
}
