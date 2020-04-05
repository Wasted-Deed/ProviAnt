package wastedgames.proviant.objects.environment;

import android.graphics.Bitmap;

import wastedgames.proviant.enumerations.Image;
import wastedgames.proviant.enumerations.UnitState;
import wastedgames.proviant.maintenance.ResourcesLoader;
import wastedgames.proviant.objects.Appearance;
import wastedgames.proviant.objects.CollisionMask;
import wastedgames.proviant.objects.PortableUnit;

public class DirtPile extends PortableUnit {
    public DirtPile(int x, int y) {
        super(x, y);
        appearance.put(UnitState.EXIST,
                new Appearance(ResourcesLoader.getImage(Image.DIRT_PILE_0)));
        currentState = UnitState.EXIST;
        Bitmap maskBit = ResourcesLoader.getImage(Image.DIRT_PILE_0);
        mask = new CollisionMask(-maskBit.getWidth() / 2,
                -maskBit.getHeight(),
                maskBit.getWidth() / 2, 0);
    }
}
