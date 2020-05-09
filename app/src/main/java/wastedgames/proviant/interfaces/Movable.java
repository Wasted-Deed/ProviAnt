package wastedgames.proviant.interfaces;

import wastedgames.proviant.engine.Vector2;
import wastedgames.proviant.objects.AbstractUnit;

public interface Movable {

    void move(Vector2 direction);

    float getCurrentSpeed();
}
