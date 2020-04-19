package wastedgames.proviant.objects.environment;

import wastedgames.proviant.enumerations.Image;
import wastedgames.proviant.enumerations.UnitState;
import wastedgames.proviant.maintenance.ResourcesLoader;
import wastedgames.proviant.objects.AbstractUnit;
import wastedgames.proviant.objects.Appearance;

public class Grass extends AbstractUnit {
    public Grass(int x, int y) {
        super(x, y);
        appearance.put(UnitState.EXIST,
                new Appearance(ResourcesLoader.getBitmapSet(Image.GRASS_0, Image.GRASS_11),
                        0, 4));
        currentState = UnitState.EXIST;
        setStandardMask(Image.GRASS_0);
    }
}
