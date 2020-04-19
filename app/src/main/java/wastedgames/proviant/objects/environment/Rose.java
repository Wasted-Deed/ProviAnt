package wastedgames.proviant.objects.environment;

import wastedgames.proviant.enumerations.Image;
import wastedgames.proviant.enumerations.UnitState;
import wastedgames.proviant.maintenance.ResourcesLoader;
import wastedgames.proviant.objects.AbstractUnit;
import wastedgames.proviant.objects.Appearance;

public class Rose extends AbstractUnit {
    public Rose(float x, float y) {
        super(x, y);
        appearance.put(UnitState.EXIST,
                new Appearance(ResourcesLoader.getBitmapSet(Image.ROSE_0, Image.ROSE_7),
                        0, 6));
        currentState = UnitState.EXIST;
        setStandardMask(Image.ROSE_0);
        randomMirror();
    }
}
