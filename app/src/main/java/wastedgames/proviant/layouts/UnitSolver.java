package wastedgames.proviant.layouts;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;

import wastedgames.proviant.engine.Vector2;
import wastedgames.proviant.enumerations.UnitState;
import wastedgames.proviant.interfaces.Drawable;
import wastedgames.proviant.maintenance.Physics;
import wastedgames.proviant.objects.AbstractUnit;
import wastedgames.proviant.objects.MovableUnit;
import wastedgames.proviant.objects.environment.Meat;
import wastedgames.proviant.objects.fauna.ActiveUnit;
import wastedgames.proviant.objects.fauna.Ant;
import wastedgames.proviant.objects.fauna.Bug;
import wastedgames.proviant.objects.fauna.Larva;

import static wastedgames.proviant.layouts.GameField.FLOOR_Y;

public class UnitSolver {
    private final GameField GAMEFIELD;
    private final ArrayList<Drawable> DRAWABLE_UNITS;
    private final ArrayList<MovableUnit> MOVABLE_UNITS;

    public UnitSolver(GameField GAMEFIELD) {
        DRAWABLE_UNITS = new ArrayList<>();
        MOVABLE_UNITS = new ArrayList<>();
        this.GAMEFIELD = GAMEFIELD;
    }

    private void setUnitToUnderground(MovableUnit unit) {
        if ((unit instanceof Ant || unit instanceof Bug) && unit.getY() > FLOOR_Y) {
            unit.setCurrentState(UnitState.CRAWL);
        }
    }

    private void setUnitToPlatform(MovableUnit unit) {
        if (!unit.checkIfCanAttach() &&
                UnitState.getType(unit.getCurrentState()) == UnitState.Type.UNDERGROUND) {
            unit.setY(FLOOR_Y);
            unit.setAngle(0);
            unit.setCurrentState(UnitState.IDLE);
        }
    }

    private void setUnitToGravity(MovableUnit unit) {
        if (!unit.checkIfLanded() && !unit.isAttached()) {
            unit.move(new Vector2(0, Physics.GRAVITY_SPEED));
        }
    }

    public void update() {
        for (int i = 0; i < MOVABLE_UNITS.size(); i++) {
            MovableUnit unit = MOVABLE_UNITS.get(i);
            setUnitToUnderground(unit);
            setUnitToPlatform(unit);
            setUnitToGravity(unit);
            unit.move(GAMEFIELD.hero);
            unit.update();
            checkUnit(unit);
        }
    }

    public void draw(Canvas canvas, Paint paint, Vector2 CAMERA) {
        for (Drawable unit : DRAWABLE_UNITS) {
            if (GAMEFIELD.checkIfOnScreen(unit.getX(), unit.getY(),
                    Math.max(unit.getWidth(), unit.getHeight()))) {
                unit.draw(canvas, paint, CAMERA);
            }
        }
    }

    private void checkUnit(MovableUnit unit) {
        Ant hero = GAMEFIELD.hero;
        /*if (hero != null && GAMEFIELD.touchType == TouchType.NONE &&
                hero.isPointReachable(unit.getX(), unit.getY()) &&
                unit.isTouched(getScaledTouch())) {
            if (unit instanceof Portable) {
                hero.setPickedObject((Portable) unit);
            } else if (unit instanceof ActiveUnit) {
                unit.damage(hero.getDamage());
            }
        }*/
        if (unit instanceof Larva) {
            GAMEFIELD.larvaAction((Larva) unit);
        }
        if (unit instanceof ActiveUnit && unit.isDestroyed()) {
            Meat meat = new Meat(unit.getX(), unit.getY());
            addBoth(meat);
            removeBoth(unit);
        }
    }

    public void addBoth(AbstractUnit unit) {
        DRAWABLE_UNITS.add(unit);
        if (unit instanceof MovableUnit) {
            MOVABLE_UNITS.add((MovableUnit) unit);
        }
    }

    public void removeBoth(AbstractUnit unit) {
        DRAWABLE_UNITS.remove(unit);
        if (unit instanceof MovableUnit) {
            MOVABLE_UNITS.remove(unit);
        }
    }

    public void addDrawableUnit(Drawable unit) {
        DRAWABLE_UNITS.add(unit);
    }

    public void addMovableUnit(MovableUnit unit) {
        MOVABLE_UNITS.add(unit);
    }
}
