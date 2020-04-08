package wastedgames.proviant.objects.environment;

import wastedgames.proviant.enumerations.Image;
import wastedgames.proviant.enumerations.UnitState;
import wastedgames.proviant.maintenance.ResourcesLoader;
import wastedgames.proviant.objects.AbstractUnit;
import wastedgames.proviant.objects.Appearance;

public class Chamomile extends AbstractUnit {
    public Chamomile(float x, float y) {
        super(x, y);
        appearance.put(UnitState.EXIST,
                new Appearance(ResourcesLoader.getBitmapSet(Image.CHAMOMILE_0, Image.CHAMOMILE_7),
                        0, 6));
        currentState = UnitState.EXIST;
        setStandardMask(Image.CHAMOMILE_0);
        randomMirror();
    }

    @Override
    public void update() {

    }
}
