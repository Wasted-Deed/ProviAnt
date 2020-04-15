package wastedgames.proviant.objects.environment;

import wastedgames.proviant.enumerations.Image;
import wastedgames.proviant.enumerations.UnitState;
import wastedgames.proviant.maintenance.ResourcesLoader;
import wastedgames.proviant.objects.AbstractUnit;
import wastedgames.proviant.objects.Appearance;

public class Sun extends AbstractUnit {
    public Sun(float x, float y) {
        super(x, y);
        appearance.put(UnitState.EXIST,
                new Appearance(ResourcesLoader.getImage(Image.SUN_0)));
        currentState = UnitState.EXIST;
        setStandardMask(Image.SUN_0);
    }

    @Override
    public void update() {

    }
}
