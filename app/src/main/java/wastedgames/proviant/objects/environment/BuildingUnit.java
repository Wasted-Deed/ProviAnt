package wastedgames.proviant.objects.environment;

import wastedgames.proviant.engine.Vector2;
import wastedgames.proviant.objects.PortableUnit;

public class BuildingUnit extends PortableUnit {
    public BuildingUnit(float x, float y) {
        super(x, y);
    }

    public BuildingUnit(Vector2 pos) {
        super(pos);
    }
}
