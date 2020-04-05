package wastedgames.proviant.objects;

import android.graphics.Bitmap;

import wastedgames.proviant.enumerations.Image;
import wastedgames.proviant.enumerations.UnitState;
import wastedgames.proviant.maintenance.Physics;
import wastedgames.proviant.maintenance.ResourcesLoader;

public class Ant extends MovableUnit {
    private int efficiency;

    public Ant(int x, int y) {
        super(x, y);
        appearance.put(UnitState.IDLE, new Appearance(ResourcesLoader.getImage(Image.ANT_WORK_0)));
        appearance.put(UnitState.WORK,
                new Appearance(ResourcesLoader.getBitmapSet(Image.ANT_WORK_0, Image.ANT_WORK_4),
                        0, 4));
        appearance.put(UnitState.WALK,
                new Appearance(ResourcesLoader.getBitmapSet(Image.ANT_WALK_0, Image.ANT_WALK_4),
                        0, 4));
        actionDistance = 32;
        efficiency = 8;
        Bitmap maskBit = ResourcesLoader.getImage(Image.ANT_WORK_0);

        mask = new CollisionMask(-maskBit.getWidth() / 2,
                -15,
                maskBit.getWidth() / 2 - 9, 0);
        jumpSpeed = 2;
        speed = 1;
        MAX_JUMP_HEIGHT = Physics.ANT_JUMP_HEIGHT;
    }

    @Override
    public void update() {

    }

    public int getEfficiency() {
        return efficiency;
    }

}