package wastedgames.proviant.objects.ui;

import android.graphics.Canvas;
import android.graphics.Paint;

import wastedgames.proviant.engine.Vector2;
import wastedgames.proviant.enumerations.Font;
import wastedgames.proviant.enumerations.UnitState;
import wastedgames.proviant.interfaces.Drawable;
import wastedgames.proviant.interfaces.Updatable;
import wastedgames.proviant.maintenance.Text;
import wastedgames.proviant.objects.AbstractUnit;

public class Interface implements Drawable, Updatable {
    private final int STEP = 20;


    private AbstractUnit holder;
    private HpBar hp;
    private RoundButton attack;
    private RoundButton pick;
    private ProcessingBar process;
    private Controller controller;
    private Text text;
    State state;

    public enum State {
        MOVE, PICK, ATTACK, NONE
    }

    public Interface(AbstractUnit holder) {
        this.holder = holder;
        attack = new RoundButton(new Vector2(10, 10));
        pick = new RoundButton(new Vector2(10, 35));
        hp = new HpBar(new Vector2(0, 0));
        controller = new Controller(new Vector2(0, 0));
        process = new ProcessingBar(new Vector2(holder.getX(), holder.getY() - STEP));
        text = new Text(Font.BASIC);
        state = State.MOVE;
    }

    @Override
    public void draw(Canvas canvas, Paint paint, Vector2 camera) {
        if (holder.getCurrentState() == UnitState.WORK) {
            process.draw(canvas, paint, camera);
        }
        hp.draw(canvas, paint, camera);
        attack.draw(canvas, paint, camera);
        pick.draw(canvas, paint, camera);
        controller.draw(canvas, paint, camera);
    }

    @Override
    public float getX() {
        return 0;
    }

    @Override
    public float getY() {
        return 0;
    }

    @Override
    public float getX(Vector2 camera) {
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
        process.update();
        process.setX(holder.getX());
        process.setY(holder.getY() - STEP);
        hp.setCurrentHP(holder.getHp());
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public RoundButton getAttack() {
        return attack;
    }

    public RoundButton getPick() {
        return pick;
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
