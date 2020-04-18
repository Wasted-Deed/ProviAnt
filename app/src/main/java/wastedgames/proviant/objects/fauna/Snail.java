package wastedgames.proviant.objects.fauna;

import android.graphics.Bitmap;

import wastedgames.proviant.enumerations.Image;
import wastedgames.proviant.enumerations.UnitState;
import wastedgames.proviant.layouts.GameField;
import wastedgames.proviant.maintenance.Physics;
import wastedgames.proviant.maintenance.ResourcesLoader;
import wastedgames.proviant.objects.Appearance;
import wastedgames.proviant.objects.CollisionMask;

public class Snail extends ActiveUnit {
    public Snail(int x, int y) {
        super(x, y);
        appearance.put(UnitState.IDLE, new Appearance(ResourcesLoader.getImage(Image.SNAIL_0)));
        appearance.put(UnitState.WALK,
                new Appearance(ResourcesLoader.getBitmapSet(Image.SNAIL_1, Image.SNAIL_3),
                        0, 8));
        Bitmap maskBit = ResourcesLoader.getImage(Image.SNAIL_0);
        mask = new CollisionMask(-maskBit.getWidth() / 2,
                -maskBit.getHeight(),
                maskBit.getWidth() / 2, 0);
        jumpSpeed = 2;
        speed = 0.5f;
        vision = (int) (GameField.SCALED_SCREEN.getX() / 3);
        MAX_JUMP_HEIGHT = Physics.SNAIL_JUMP_HEIGHT;
        hp = 3;
    }
}
