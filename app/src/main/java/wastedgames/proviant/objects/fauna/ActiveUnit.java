package wastedgames.proviant.objects.fauna;

import wastedgames.proviant.engine.Vector2;
import wastedgames.proviant.enumerations.UnitState;
import wastedgames.proviant.objects.AbstractUnit;
import wastedgames.proviant.objects.MovableUnit;

public class ActiveUnit extends MovableUnit {
    protected int hp;

    public ActiveUnit(int x, int y) {
        super(x, y);
    }

    @Override
    public void update() {

    }

    @Override
    public void move(AbstractUnit danger) {
        if (Math.abs(x - danger.getX()) < vision) {
            currentState = UnitState.WALK;
            currentSpeed = danger.getX() > x ? -speed : speed;
            move(new Vector2(currentSpeed, 0));
            return;
        }
        currentSpeed = 0;
        currentState = UnitState.IDLE;
    }

    public void damage(int damage) {
        hp -= damage;
    }

    public boolean isDestroyed() {
        return hp <= 0;
    }
}
