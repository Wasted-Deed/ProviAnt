package wastedgames.proviant.objects.fauna;

import wastedgames.proviant.enumerations.Image;
import wastedgames.proviant.enumerations.UnitState;
import wastedgames.proviant.layouts.GameField;
import wastedgames.proviant.maintenance.Physics;
import wastedgames.proviant.maintenance.ResourcesLoader;
import wastedgames.proviant.objects.Appearance;

public class Bug extends ActiveUnit {
    public Bug(int x, int y) {
        super(x, y);
        appearance.put(UnitState.IDLE, new Appearance(ResourcesLoader.getImage(Image.BUG_0)));
        appearance.put(UnitState.WALK,
                new Appearance(ResourcesLoader.getBitmapSet(Image.BUG_1, Image.BUG_3),
                        0, 8));
        setStandardMask(Image.BUG_0);
        jumpSpeed = 2;
        speed = 0.5f;
        vision = (int) (GameField.SCALED_SCREEN.getX() / 3);
        MAX_JUMP_HEIGHT = Physics.SNAIL_JUMP_HEIGHT;
        hp = 4;
    }

}
