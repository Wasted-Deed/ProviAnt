package wastedgames.proviant.objects.fauna;

import wastedgames.proviant.enumerations.Image;
import wastedgames.proviant.enumerations.UnitState;
import wastedgames.proviant.maintenance.Physics;
import wastedgames.proviant.maintenance.ResourcesLoader;
import wastedgames.proviant.objects.Appearance;
import wastedgames.proviant.objects.Attire;
import wastedgames.proviant.objects.MovableUnit;
import wastedgames.proviant.objects.landscape.TileMap;

public class Ant extends MovableUnit {
    private int efficiency;

    //TODO: Handle unexpected hops of bitmap in CRAWL state
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
        speed = 1;
        MAX_JUMP_HEIGHT = Physics.ANT_JUMP_HEIGHT;
        hp = MAX_HP = 5;
        attackDistance = actionDistance / 2;
        timer.setAttackFrequency(16);
        weapon = new Attire(1, 5);
    }

    public int getEfficiency() {
        return efficiency;
    }

    @Override
    public boolean checkIfLanded(TileMap map) {
        return super.checkIfLanded(map) || checkIfCanAttach();
    }
}
