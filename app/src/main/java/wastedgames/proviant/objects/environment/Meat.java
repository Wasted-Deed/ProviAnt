package wastedgames.proviant.objects.environment;

import wastedgames.proviant.enumerations.Image;
import wastedgames.proviant.enumerations.UnitState;
import wastedgames.proviant.maintenance.ResourcesLoader;
import wastedgames.proviant.objects.Appearance;
import wastedgames.proviant.objects.PortableUnit;

public class Meat extends PortableUnit {
    public Meat(int x, int y) {
        super(x, y);
        appearance.put(UnitState.EXIST,
                new Appearance(ResourcesLoader.getImage(Image.MEAT_0)));
        currentState = UnitState.EXIST;
        setStandardMask(Image.MEAT_0);
        randomMirror();
    }
}
