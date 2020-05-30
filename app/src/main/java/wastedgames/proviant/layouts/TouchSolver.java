package wastedgames.proviant.layouts;

import wastedgames.proviant.engine.Vector2;
import wastedgames.proviant.enumerations.UnitState;
import wastedgames.proviant.objects.MovableUnit;
import wastedgames.proviant.objects.environment.BuildingUnit;
import wastedgames.proviant.objects.fauna.Ant;
import wastedgames.proviant.objects.landscape.Tile;
import wastedgames.proviant.objects.landscape.TileMap;
import wastedgames.proviant.objects.ui.AttackButton;
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

    private boolean checkController(Ant hero, TileMap map, Controller controller) {
        controller.updateCenterPosition(getDisplayTouch());
        if (controller.isControlled()) {
            if (UnitState.isFloor(hero.getCurrentState())) {
                hero.setAngle(0);
                heroPlatformMovement(hero, controller, map);
            } else {
                hero.setAngle(controller.getAngle());
                heroUndergroundMovement(hero, controller, map);

            }
            return true;
        }
        return false;
    }

    public void doWhileTouching(Ant hero, Interface ui, UnitSolver solver, TileMap map) {
        if (checkController(hero, map, ui.getController())
                || checkAttack(hero, solver, ui.getAttack())) {
            return;
        }
        terraform(hero, map);
    }

    private boolean checkAttack(Ant hero, UnitSolver solver, AttackButton attack) {
        MovableUnit nearest = solver.getNearestActive(hero);
        if (nearest == null || !attack.isTouched(getDisplayTouch()) || !hero.isAttackTime()) {
            return false;
        }
        nearest.damage(hero, hero.getWeapon());
        return true;
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
