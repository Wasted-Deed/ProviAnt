package wastedgames.proviant.objects.fauna;

import wastedgames.proviant.enumerations.Image;
import wastedgames.proviant.enumerations.UnitState;
import wastedgames.proviant.layouts.GameField;
import wastedgames.proviant.maintenance.Physics;
import wastedgames.proviant.maintenance.ResourcesLoader;
import wastedgames.proviant.objects.Appearance;
import wastedgames.proviant.objects.MovableUnit;

public class Ant extends MovableUnit {
    private int efficiency;

    public Ant(float x, float y) {
        super(x, y);
        appearance.put(UnitState.IDLE, new Appearance(ResourcesLoader.getImage(Image.ANT_WALK_0)));
        appearance.put(UnitState.CRAWL, new Appearance(ResourcesLoader.getImage(Image.ANT_CRAWL_0)));
        appearance.put(UnitState.WORK,
                new Appearance(ResourcesLoader.getBitmapSet(Image.ANT_WORK_0, Image.ANT_WORK_4),
                        0, 4));
        appearance.put(UnitState.WALK,
                new Appearance(ResourcesLoader.getBitmapSet(Image.ANT_WALK_0, Image.ANT_WALK_4),
                        0, 4));
        actionDistance = 32;
        efficiency = 8;
        setStandardMask(Image.ANT_WORK_0);
        jumpSpeed = 2;
        speed = damage = 1;
        MAX_JUMP_HEIGHT = Physics.ANT_JUMP_HEIGHT;
        hp = MAX_HP = 5;
    }

    public int getEfficiency() {
        return efficiency;
    }

    @Override
    public boolean checkIfLanded() {
        return super.checkIfLanded() || checkIfCanAttach();
    }
}
