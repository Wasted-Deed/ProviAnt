package wastedgames.proviant.objects.environment;

import wastedgames.proviant.enumerations.Image;
import wastedgames.proviant.enumerations.UnitState;
import wastedgames.proviant.maintenance.ResourcesLoader;
import wastedgames.proviant.objects.Appearance;
import wastedgames.proviant.objects.MovableUnit;

public class Stone extends MovableUnit {
    public Stone(int x, int y) {
        super(x, y);
        appearance.put(UnitState.EXIST,
                new Appearance(ResourcesLoader.getImage(Image.STONE_0)));
        currentState = UnitState.EXIST;
        setStandardMask(Image.STONE_0);
        randomMirror();
    }

    @Override
    public void update() {

    }
}
