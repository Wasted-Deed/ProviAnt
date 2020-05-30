package wastedgames.proviant.objects.fauna;

import wastedgames.proviant.enumerations.Image;
import wastedgames.proviant.enumerations.UnitState;
import wastedgames.proviant.layouts.GameField;
import wastedgames.proviant.maintenance.ResourcesLoader;
import wastedgames.proviant.objects.Appearance;

public class Worm extends ActiveUnit {

    public Worm(float x, float y) {
        super(x, y);
        appearance.put(UnitState.IDLE, new Appearance(ResourcesLoader.getImage(Image.WORM_IDLE_0)));
        appearance.put(UnitState.WALK,
                new Appearance(ResourcesLoader.getBitmapSet(Image.WORM_MOVE_0, Image.WORM_MOVE_5),
                        0, 8));
        setStandardMask(Image.WORM_IDLE_0);
        jumpSpeed = 2;
        speed = 0.5f;
        vision = (int) (GameField.SCALED_SCREEN.getX() / 3);
        hp = MAX_HP = 4;
    }
}
