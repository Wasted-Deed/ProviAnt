package wastedgames.proviant.objects.environment;

import wastedgames.proviant.enumerations.Image;
import wastedgames.proviant.enumerations.UnitState;
import wastedgames.proviant.maintenance.ResourcesLoader;
import wastedgames.proviant.objects.Appearance;
import wastedgames.proviant.objects.PortableUnit;

public class Stick extends PortableUnit {
    public Stick(int x, int y) {
        super(x, y);
        appearance.put(UnitState.EXIST,
                new Appearance(ResourcesLoader.getImage(Image.STICK_0)));
        currentState = UnitState.EXIST;
        setStandardMask(Image.STICK_0);
        randomMirror();
    }
}
