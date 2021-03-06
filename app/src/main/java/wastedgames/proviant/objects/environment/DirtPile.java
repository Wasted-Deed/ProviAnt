package wastedgames.proviant.objects.environment;

import wastedgames.proviant.enumerations.Image;
import wastedgames.proviant.enumerations.UnitState;
import wastedgames.proviant.maintenance.ResourcesLoader;
import wastedgames.proviant.objects.Appearance;
import wastedgames.proviant.objects.PortableUnit;

public class DirtPile extends BuildingUnit {
    public DirtPile(int x, int y) {
        super(x, y);
        appearance.put(UnitState.EXIST,
                new Appearance(ResourcesLoader.getImage(Image.DIRT_PILE_0)));
        currentState = UnitState.EXIST;
        setStandardMask(Image.DIRT_PILE_0);
    }
}
