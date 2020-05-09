package wastedgames.proviant.objects.fauna;

import wastedgames.proviant.enumerations.UnitState;
import wastedgames.proviant.objects.AbstractUnit;
import wastedgames.proviant.objects.MovableUnit;

public class ActiveUnit extends MovableUnit {
    //TODO: Change x,y to Vector2 everywhere
    public ActiveUnit(float x, float y) {
        super(x, y);
    }

    @Override
    public void update() {
        super.update();
        isAttached = currentState == UnitState.CRAWL;
    }

    @Override
    public boolean checkIfLanded() {
        return super.checkIfLanded() || checkIfCanAttach();
    }
}
