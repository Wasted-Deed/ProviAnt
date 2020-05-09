package wastedgames.proviant.layouts;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import wastedgames.proviant.engine.Vector2;
import wastedgames.proviant.enumerations.Font;
import wastedgames.proviant.enumerations.TouchType;
import wastedgames.proviant.enumerations.UnitState;
import wastedgames.proviant.enumerations.Weather;
import wastedgames.proviant.maintenance.Text;
import wastedgames.proviant.objects.PortableUnit;
import wastedgames.proviant.objects.environment.BackgroundGrass;
import wastedgames.proviant.objects.environment.BuildingUnit;
import wastedgames.proviant.objects.environment.Chamomile;
import wastedgames.proviant.objects.environment.Grass;
import wastedgames.proviant.objects.environment.Rose;
import wastedgames.proviant.objects.environment.Stick;
import wastedgames.proviant.objects.environment.Stone;
import wastedgames.proviant.objects.environment.Sun;
import wastedgames.proviant.objects.fauna.Ant;
import wastedgames.proviant.objects.fauna.Bug;
import wastedgames.proviant.objects.fauna.LadyBug;
import wastedgames.proviant.objects.fauna.Larva;
import wastedgames.proviant.objects.fauna.Snail;
import wastedgames.proviant.objects.fauna.Worm;
import wastedgames.proviant.objects.landscape.Tile;
import wastedgames.proviant.objects.landscape.TileMap;
import wastedgames.proviant.objects.ui.Controller;
import wastedgames.proviant.objects.ui.Interface;

import static wastedgames.proviant.maintenance.ThreadSolver.IS_TOUCHING;
import static wastedgames.proviant.maintenance.ThreadSolver.SCREEN_HEIGHT;
import static wastedgames.proviant.maintenance.ThreadSolver.SCREEN_WIDTH;
import static wastedgames.proviant.maintenance.ThreadSolver.TOUCH;

public class GameField {


    public static TileMap MAP;
    final Vector2 REAL_SIZE;
    private final UnitSolver UNIT_SOLVER;
    private final EnvironmentSolver ENV_SOLVER;
    private final int CAMERA_Y_SETUP;


    public static final int FLOOR_Y = 97;
    public static Vector2 SCALED_SCREEN;
    private static Vector2 CAMERA;
    private static int SCALE;

    Ant hero;
    TouchType touchType;
    Weather weather;
    private Sun sun;
    private Interface heroInterface;

    //TODO: Make it invisible the land / underground for a unit
    public GameField(int MAPSizeX, int MAPSizeY) {
        REAL_SIZE = new Vector2(TileMap.TILE_SIZE * MAPSizeX, TileMap.TILE_SIZE * MAPSizeY);
        MAP = new TileMap(MAPSizeX, MAPSizeY);
        UNIT_SOLVER = new UnitSolver(this);
        ENV_SOLVER = new EnvironmentSolver();
        SCALE = 8;
        SCALED_SCREEN = new Vector2(SCREEN_WIDTH / SCALE, SCREEN_HEIGHT / SCALE);
        CAMERA = new Vector2(0, 0);
        CAMERA_Y_SETUP = (int) (FLOOR_Y - SCALED_SCREEN.getY() / 2);
        sun = new Sun(10, 30);
        ENV_SOLVER.addEnvironment(UNIT_SOLVER, REAL_SIZE);
        ENV_SOLVER.addUnits(UNIT_SOLVER, REAL_SIZE);
        setupHero();
        touchType = TouchType.NONE;
        weather = Weather.NORMAL;
    }

    private void setupHero() {
        int x = (int) (Math.random() * REAL_SIZE.getX());
        Larva larva = new Larva(x, FLOOR_Y);
        attachCamera(x, FLOOR_Y);
        UNIT_SOLVER.addBoth(larva);
        heroInterface = new Interface(larva);
    }


    private void drawSky(Canvas canvas, Paint paint) {
        paint.setColor(Color.CYAN);
        canvas.drawRect(new Rect(0, 0,
                SCREEN_WIDTH,
                SCREEN_HEIGHT), paint);
    }

    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        drawSky(canvas, paint);
        setConfiguration(canvas, paint);
        sun.setX((int) CAMERA.getX() + 100);
        sun.setY((int) CAMERA.getY() + 70);
        sun.draw(canvas, paint, CAMERA);
        MAP.draw(canvas, paint, CAMERA, true);
        UNIT_SOLVER.draw(canvas, paint, CAMERA);
        MAP.draw(canvas, paint, CAMERA, false);
        heroInterface.draw(canvas, paint, CAMERA);
    }

    private void heroPlatformMovement(Controller controller) {
        hero.setCurrentState(UnitState.WALK);
        if (controller.getAngle() > 315 || controller.getAngle() < 45) {
            hero.move(new Vector2(hero.getSpeed(), 0));
        } else if (controller.getAngle() > 135 && controller.getAngle() < 225) {
            hero.move(new Vector2(-hero.getSpeed(), 0));
        } else if (controller.getAngle() > 225 &&
                controller.getAngle() < 315 && !hero.checkIfLandedOnBlock()) {
            hero.move(new Vector2(0, hero.getSpeed()));
        } else {
            hero.setCurrentState(UnitState.IDLE);
            hero.setJumping(false);
        }
    }

    private void heroUndergroundMovement(Controller controller) {
        Vector2 dir = controller.touchBias(getDisplayTouch()).getNormalizedCopy();
        dir.multiplyVector(hero.getSpeed());
        hero.setDir(dir);
        if (!hero.checkDirCollision()) {
            hero.move(dir);
        }
    }

    //TODO: Here might be the problem (jump from 0 to 359)
    private void doWhileTouching() {
        Controller controller = heroInterface.getController();
        controller.updateCenterPosition(getDisplayTouch());
        if (controller.isControlled()) {
            if (UnitState.isFloor(hero.getCurrentState())) {
                hero.setAngle(0);
                heroPlatformMovement(controller);
            } else {
                hero.setAngle(controller.getAngle());
                heroUndergroundMovement(controller);
            }
            return;
        }
        terraform();
    }

    private void terraform() {
        if (hero.isPointReachable(getScaledTouch())) {
            Tile touched = MAP.getTouchedTile();
            if (touched == null || !touched.isSolid()) {
                if (hero.getCurrentState() == UnitState.WORK) {
                    hero.setCurrentState(UnitState.IDLE);
                }
                if (hero.getPickedObject() != null && hero.getPickedObject() instanceof BuildingUnit) {
                    MAP.fillTouchedTile();
                }
                return;
            }
            PortableUnit drop = MAP.damageTouchedTile(hero.getEfficiency());
            hero.setCurrentState(UnitState.WORK);
            if (drop != null) {
                UNIT_SOLVER.addBoth(drop);
            }
        }
    }

    private void attachCamera() {
        attachCamera(hero.getX(), hero.getY());
    }

    private void attachCamera(float x, float y) {
        if (x > SCALED_SCREEN.getX() / 2 &&
                x < REAL_SIZE.getX() - SCALED_SCREEN.getX()) {
            CAMERA.setX(x - SCALED_SCREEN.getX() / 2);
        }
        if (y > SCALED_SCREEN.getY() / 2 &&
                y < REAL_SIZE.getY() - SCALED_SCREEN.getY()) {
            CAMERA.setY(y - SCALED_SCREEN.getY() / 2f - CAMERA_Y_SETUP);
        }
    }

    private void heroAction() {
        if (hero == null) {
            return;
        }
        if (IS_TOUCHING) {
            touchType = TouchType.TOUCH;
            doWhileTouching();
        } else {
            hero.setCurrentState(UnitState.IDLE);
            touchType = TouchType.NONE;
            heroInterface.getController().setToInitialPosition();
        }
        attachCamera();
        hero.setAim(hero.getX(), hero.getY());
    }

    public void update() {
        heroAction();
        heroInterface.update();
        UNIT_SOLVER.update();
    }

    void larvaAction(Larva larva) {
        if (larva.isDestroyed()) {
            hero = new Ant(larva.getX(), larva.getY());
            UNIT_SOLVER.removeBoth(larva);
            heroInterface = new Interface(hero);
            UNIT_SOLVER.addBoth(hero);
        }
    }

    private void setScale(Canvas canvas, int value) {
        canvas.scale(value, value);
    }

    private void setConfiguration(Canvas canvas, Paint paint) {
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(false);
        paint.setFilterBitmap(false);
        setScale(canvas, SCALE);
        canvas.translate(-CAMERA.getX(), -CAMERA.getY());
    }

    public static Vector2 getScaledTouch() {
        Vector2 division = TOUCH.dividedCopy(SCALE);
        division.addVector2(CAMERA);
        return division;
    }

    private Vector2 getDisplayTouch() {
        return TOUCH.dividedCopy(SCALE);
    }

    boolean checkIfOnScreen(float x, float y, int step) {
        return Math.abs(x - CAMERA.getX() - SCALED_SCREEN.getX() / 2f)
                < SCALED_SCREEN.getX() / 2f + step &&
                Math.abs(y - CAMERA.getY() - SCALED_SCREEN.getY() / 2f)
                        < SCALED_SCREEN.getY() / 2f + step;
    }
}
