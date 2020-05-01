package wastedgames.proviant.objects.environment;

import wastedgames.proviant.enumerations.Image;
import wastedgames.proviant.enumerations.UnitState;
import wastedgames.proviant.maintenance.ResourcesLoader;
import wastedgames.proviant.objects.Appearance;
import wastedgames.proviant.objects.MovableUnit;

public class Drop extends MovableUnit {
    public Drop(float x, float y) {
        super(x, y);
        appearance.put(UnitState.IDLE, new Appearance(ResourcesLoader.getImage(Image.DROP_FLY_0)));
        appearance.put(UnitState.DESTROYED,
                new Appearance(ResourcesLoader.getBitmapSet(Image.DROP_LANDED_0,
                        Image.DROP_LANDED_4),
                        0, 4));
        setStandardMask(Image.DROP_FLY_0);
        hp = 2;
    }

    @Override
    public void update() {
        super.update();
        if (hp == 1) {
            setCurrentState(UnitState.DESTROYED);
        }
        if (currentState == UnitState.DESTROYED && appearance.get(currentState).isFinalNumber()) {
            destroy();
        }

    }
}
