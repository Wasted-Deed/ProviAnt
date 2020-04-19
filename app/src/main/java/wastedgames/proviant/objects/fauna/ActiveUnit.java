package wastedgames.proviant.objects.fauna;

import wastedgames.proviant.engine.Vector2;
import wastedgames.proviant.enumerations.UnitState;
import wastedgames.proviant.objects.AbstractUnit;
import wastedgames.proviant.objects.MovableUnit;

public class ActiveUnit extends MovableUnit {

    public ActiveUnit(float x, float y) {
        super(x, y);
    }

    @Override
    public void update() {
        super.update();
        isAttached = currentState == UnitState.CRAWL;
    }

    @Override
    public void move(AbstractUnit danger) {
        if (danger == null || currentState == UnitState.CRAWL) {
            return;
        }
        /*if (Math.abs(x - danger.getX()) < vision) {
            currentState = UnitState.WALK;
            currentSpeed = danger.getX() > x ? -speed : speed;
            move(new Vector2(currentSpeed, 0));
            return;
        }*/
        currentSpeed = 0;
        currentState = UnitState.IDLE;
    }
}
