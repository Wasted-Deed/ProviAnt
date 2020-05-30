package wastedgames.proviant.layouts;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;

import wastedgames.proviant.engine.Vector2;
import wastedgames.proviant.enumerations.UnitState;
import wastedgames.proviant.enumerations.Weather;
import wastedgames.proviant.interfaces.Drawable;
import wastedgames.proviant.maintenance.Physics;
import wastedgames.proviant.maintenance.ThreadSolver;
import wastedgames.proviant.objects.AbstractUnit;
import wastedgames.proviant.objects.MovableUnit;
import wastedgames.proviant.objects.environment.Drop;
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
        if (!unit.checkIfLanded(GAMEFIELD.map) && !unit.isAttached()) {
            unit.move(new Vector2(0, Physics.GRAVITY_SPEED));
        } else if (unit instanceof Drop && unit.getCurrentState() != UnitState.DESTROYED) {
            unit.damage(1);
        }
    }

    private void checkState(MovableUnit unit) {
        if (!(unit instanceof ActiveUnit)) {
            return;
        }
        if (unit.hasCome()) {
            unit.setCurrentState(UnitState.IDLE);
        } else {
            unit.setCurrentState(UnitState.WALK);
        }
    }

    public void update() {
        for (int i = 0; i < MOVABLE_UNITS.size(); i++) {
            MovableUnit unit = MOVABLE_UNITS.get(i);
            setUnitToUnderground(unit);
            setUnitToPlatform(unit);
            setUnitToGravity(unit);
            unit.update();
            checkUnit(unit);
            checkState(unit);
            if (unit.isDestroyed()) {
                removeBoth(unit);
            }
        }
        checkWeather();
    }

    public void draw(Canvas canvas, Paint paint, Vector2 CAMERA) {
        for (Drawable unit : DRAWABLE_UNITS) {
            if (GAMEFIELD.checkIfOnScreen(unit.getX(CAMERA), unit.getY(),
                    Math.max(unit.getWidth(), unit.getHeight()))) {
                unit.draw(canvas, paint, CAMERA);
            }
        }
    }

    private void checkWeather() {
        if (GAMEFIELD.weather == Weather.RAIN) {
            if (ThreadSolver.CURRENT_FRAME % 4 == 0) {
                addBoth(new Drop((float) (Math.random() * GAMEFIELD.REAL_SIZE.getX()), 0));
            }
        }
    }

    private void checkUnit(MovableUnit unit) {
        Ant hero = GAMEFIELD.hero;
        /*if (hero != null && ThreadSolver.IS_TOUCHING &&
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
            unit.destroy();
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

    //O(n)
    public ActiveUnit getNearestActive(MovableUnit unit) {
        int dist = unit.getAttackDistance();
        ActiveUnit res = null;
        for (MovableUnit movable : MOVABLE_UNITS) {
            if (movable instanceof ActiveUnit) {
                float cur = Vector2.getDistance(movable.getPos(), unit.getPos());
                if (cur <= dist) {
                    dist = (int) cur;
                    res = (ActiveUnit) movable;
                }
            }
        }
        return res;
    }

    public void addDrawableUnit(Drawable unit) {
        DRAWABLE_UNITS.add(unit);
    }

    public void addMovableUnit(MovableUnit unit) {
        MOVABLE_UNITS.add(unit);
    }
}
