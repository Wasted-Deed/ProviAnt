package wastedgames.proviant.objects.environment;

import android.graphics.Bitmap;

import wastedgames.proviant.engine.Vector2;
import wastedgames.proviant.enumerations.Image;
import wastedgames.proviant.enumerations.UnitState;
import wastedgames.proviant.maintenance.ResourcesLoader;
import wastedgames.proviant.objects.AbstractUnit;
import wastedgames.proviant.objects.Appearance;
import wastedgames.proviant.objects.CollisionMask;

public class Grass extends AbstractUnit {
    public Grass(int x, int y) {
        super(x, y);
        appearance.put(UnitState.EXIST,
                new Appearance(ResourcesLoader.getBitmapSet(Image.GRASS_0, Image.GRASS_11),
                        0, 4));
        currentState = UnitState.EXIST;
        Bitmap maskBit = ResourcesLoader.getImage(Image.GRASS_0);
        mask = new CollisionMask(-maskBit.getWidth() / 2,
                -maskBit.getHeight(),
                maskBit.getWidth() / 2, 0);
    }

    @Override
    public void update() {

    }
}
