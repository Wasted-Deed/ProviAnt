package wastedgames.proviant.layouts;

import wastedgames.proviant.engine.Vector2;
import wastedgames.proviant.enumerations.UnitState;
import wastedgames.proviant.maintenance.ThreadSolver;
import wastedgames.proviant.objects.MovableUnit;
import wastedgames.proviant.objects.PortableUnit;
import wastedgames.proviant.objects.environment.BuildingUnit;
import wastedgames.proviant.objects.environment.unique.Bag;
import wastedgames.proviant.objects.fauna.Ant;
import wastedgames.proviant.objects.landscape.Tile;
import wastedgames.proviant.objects.landscape.TileMap;
import wastedgames.proviant.objects.ui.Controller;
import wastedgames.proviant.objects.ui.Interface;

import static wastedgames.proviant.layouts.GameField.getDisplayTouch;
import static wastedgames.proviant.layouts.GameField.getScaledTouch;

public class TouchSolver {

    private void heroUndergroundMovement(Ant hero, Controller controller, TileMap map) {
        Vector2 dir = controller.touchBias(getDisplayTouch()).getNormalizedCopy();
        dir.multiplyVector(hero.getSpeed());
        hero.setDir(dir);
        if (!hero.checkDirCollision(map)) {
            hero.move(dir);
        }
    }

    private void heroPlatformMovement(Ant hero, Controller controller, TileMap map) {
        hero.setCurrentState(UnitState.WALK);
        if (controller.getAngle() > 315 || controller.getAngle() < 45) {
            hero.move(new Vector2(hero.getSpeed(), 0));
        } else if (controller.getAngle() > 135 && controller.getAngle() < 225) {
            hero.move(new Vector2(-hero.getSpeed(), 0));
        } else if (controller.getAngle() > 225 &&
                controller.getAngle() < 315 && !hero.checkIfLandedOnBlock(map)) {
            hero.move(new Vector2(0, hero.getSpeed()));
        } else {
            hero.setCurrentState(UnitState.IDLE);
            hero.setJumping(false);
        }
    }

    private void checkController(Ant hero, TileMap map, Controller controller) {
        if (UnitState.isFloor(hero.getCurrentState())) {
            hero.setAngle(0);
            heroPlatformMovement(hero, controller, map);
        } else {
            hero.setAngle(controller.getAngle());
            heroUndergroundMovement(hero, controller, map);

        }
    }

    void doWhileTouching(Ant hero, Interface ui, UnitSolver solver, TileMap map) {
        ui.getController().updateCenterPosition(getDisplayTouch());
        if (ui.getController().isControlled()) {
            ui.setState(Interface.State.MOVE);
        } else if (ui.getAttack().isTouched(getDisplayTouch())) {
            ui.setState(Interface.State.ATTACK);
        } else if (ui.getPick().isTouched(getDisplayTouch()) &&
                !ThreadSolver.HAD_TOUCHED) {
            ThreadSolver.HAD_TOUCHED = true;
            ui.setState(Interface.State.PICK);
        } else {
            ui.setState(Interface.State.NONE);
        }

        switch (ui.getState()) {
            case MOVE:
                checkController(hero, map, ui.getController());
                break;
            case ATTACK:
                checkAttack(hero, solver);
                break;
            case PICK:
                checkPick(hero, solver);
                break;
            default:
                terraform(hero, map);
        }
    }

    private void checkAttack(Ant hero, UnitSolver solver) {
        MovableUnit nearest = solver.getNearestActive(hero);
        if (nearest == null || !hero.isAttackTime()) {
            return;
        }
        nearest.damage(hero, hero.getWeapon());
    }

    private void checkPick(Ant hero, UnitSolver solver) {
        PortableUnit nearest = solver.getNearestPortable(hero);
        if (nearest == null) {
            if (hero.getPickedObject() != null && !(hero.getPickedObject() instanceof Bag)) {
                hero.throwPickedObject();
            }
            return;
        }
        if (hero.getPickedObject() == null || !(hero.getPickedObject() instanceof Bag)) {
            hero.setPickedObject(nearest);
        } else {
            ((Bag) hero.getPickedObject()).addUnit(nearest);
        }

    }

    private void terraform(Ant hero, TileMap map) {
        if (hero.isPointReachable(map.getRealTilePos(getScaledTouch()))) {
            Tile touched = map.getTouchedTile();
            if (touched == null || !touched.isSolid()) {
                if (hero.getCurrentState() == UnitState.WORK) {
                    hero.setCurrentState(UnitState.IDLE);
                }
                if (hero.getPickedObject() != null
                        && hero.getPickedObject() instanceof BuildingUnit) {
                    map.fillTouchedTile();
                }
                return;
            }
            map.damageTouchedTile(hero.getEfficiency());
            hero.setCurrentState(UnitState.WORK);
        } else {
            hero.setCurrentState(UnitState.IDLE);
        }
    }
}
