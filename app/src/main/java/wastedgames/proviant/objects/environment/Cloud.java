package wastedgames.proviant.objects.environment;

import wastedgames.proviant.enumerations.Image;
import wastedgames.proviant.enumerations.UnitState;
import wastedgames.proviant.maintenance.ResourcesLoader;
import wastedgames.proviant.objects.AbstractUnit;
import wastedgames.proviant.objects.Appearance;

public class Cloud extends AbstractUnit {
    public Cloud(float x, float y) {
        super(x, y);
        String name = "CLOUD_" + (int) (Math.random() * 4);
        appearance.put(UnitState.EXIST,
                new Appearance(ResourcesLoader.getImage(Image.valueOf(name))));
        currentState = UnitState.EXIST;
        parallax = 1;
        setStandardMask(Image.valueOf(name));
    }

    @Override
    public void update() {

    }
}
