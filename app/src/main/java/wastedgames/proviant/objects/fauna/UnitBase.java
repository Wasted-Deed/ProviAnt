package wastedgames.proviant.objects.fauna;

import java.util.ArrayList;

import wastedgames.proviant.enumerations.Image;
import wastedgames.proviant.enumerations.UnitState;
import wastedgames.proviant.layouts.GameField;
import wastedgames.proviant.maintenance.ResourcesLoader;
import wastedgames.proviant.objects.Appearance;
import wastedgames.proviant.objects.MovableUnit;

public class UnitBase extends MovableUnit {
    private ArrayList<MovableUnit> units;
    private Ant hero;
    public UnitBase(float x, float y) {
        super(x, y);
        appearance.put(UnitState.IDLE, new Appearance(ResourcesLoader.getImage(Image.NEST_0)));
        setStandardMask(Image.LARVA_0);
        hp = MAX_HP = 5;
        vision = (int) (GameField.SCALED_SCREEN.getX() / 3);
        units = new ArrayList<>();
    }

    public void setHero(Ant hero) {
        this.hero = hero;
    }

    @Override
    public void update() {
        super.update();
        for (MovableUnit unit : units) {
            if (unit.hasCome()) {
                unit.setAim(getX() + (float) Math.random() * vision * 2 - vision, getY());
            }
        }
    }

    public void addUnit(MovableUnit unit) {
        units.add(unit);
    }
}
