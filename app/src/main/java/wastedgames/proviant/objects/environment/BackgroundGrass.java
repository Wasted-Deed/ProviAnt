package wastedgames.proviant.objects.environment;

import wastedgames.proviant.enumerations.Image;
import wastedgames.proviant.enumerations.UnitState;
import wastedgames.proviant.maintenance.ResourcesLoader;
import wastedgames.proviant.objects.AbstractUnit;
import wastedgames.proviant.objects.Appearance;

public class BackgroundGrass extends AbstractUnit {
    public BackgroundGrass(float x, float y) {
        super(x, y);
        appearance.put(UnitState.EXIST,
                new Appearance(ResourcesLoader.getImage(Image.BACKGROUND_GRASS)));
        currentState = UnitState.EXIST;

    }

    @Override
    public void update() {

    }
}
