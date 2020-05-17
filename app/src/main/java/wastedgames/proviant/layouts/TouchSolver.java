package wastedgames.proviant.layouts;

import wastedgames.proviant.engine.Vector2;
import wastedgames.proviant.enumerations.UnitState;
import wastedgames.proviant.objects.PortableUnit;
import wastedgames.proviant.objects.environment.BuildingUnit;
import wastedgames.proviant.objects.fauna.Ant;
import wastedgames.proviant.objects.landscape.Tile;
import wastedgames.proviant.objects.landscape.TileMap;
import wastedgames.proviant.objects.ui.Controller;

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

    public void doWhileTouching(Ant hero, Controller controller, UnitSolver solver, TileMap map) {
        controller.updateCenterPosition(getDisplayTouch());
        if (controller.isControlled()) {
            if (UnitState.isFloor(hero.getCurrentState())) {
                hero.setAngle(0);
                heroPlatformMovement(hero, controller, map);
            } else {
                hero.setAngle(controller.getAngle());
                heroUndergroundMovement(hero, controller, map);

            }
            return;
        }
        terraform(hero, solver, map);
    }


    private void terraform(Ant hero, UnitSolver solver, TileMap map) {
        if (hero.isPointReachable(getScaledTouch())) {
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
            PortableUnit drop = map.damageTouchedTile(hero.getEfficiency());
            hero.setCurrentState(UnitState.WORK);
            if (drop != null) {
                solver.addBoth(drop);
            }
        }
    }
}
