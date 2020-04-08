package wastedgames.proviant.objects.environment;

import android.graphics.Bitmap;

import wastedgames.proviant.enumerations.Image;
import wastedgames.proviant.enumerations.UnitState;
import wastedgames.proviant.maintenance.ResourcesLoader;
import wastedgames.proviant.objects.Appearance;
import wastedgames.proviant.objects.CollisionMask;
import wastedgames.proviant.objects.PortableUnit;

public class Stick extends PortableUnit {
    public Stick(int x, int y) {
        super(x, y);
        appearance.put(UnitState.EXIST,
                new Appearance(ResourcesLoader.getImage(Image.STICK_0)));
        currentState = UnitState.EXIST;
        Bitmap maskBit = ResourcesLoader.getImage(Image.STICK_0);
        mask = new CollisionMask(-maskBit.getWidth() / 2,
                -maskBit.getHeight(),
                maskBit.getWidth() / 2, 0);
        randomMirror();
    }
}
