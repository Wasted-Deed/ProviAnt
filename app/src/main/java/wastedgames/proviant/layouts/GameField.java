package wastedgames.proviant.layouts;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import wastedgames.proviant.engine.Vector2;
import wastedgames.proviant.enumerations.TouchType;
import wastedgames.proviant.enumerations.UnitState;
import wastedgames.proviant.enumerations.Weather;
import wastedgames.proviant.maintenance.ThreadSolver;
import wastedgames.proviant.objects.environment.unique.Bag;
import wastedgames.proviant.objects.fauna.Ant;
import wastedgames.proviant.objects.fauna.Larva;
import wastedgames.proviant.objects.landscape.TileMap;
import wastedgames.proviant.objects.ui.Interface;

import static wastedgames.proviant.maintenance.ThreadSolver.FIRST_TOUCH;
import static wastedgames.proviant.maintenance.ThreadSolver.IS_TOUCHING;
import static wastedgames.proviant.maintenance.ThreadSolver.LAST_TOUCH;
import static wastedgames.proviant.maintenance.ThreadSolver.SCREEN_HEIGHT;
import static wastedgames.proviant.maintenance.ThreadSolver.SCREEN_WIDTH;

public class GameField {


    public TileMap map;
    final Vector2 REAL_SIZE;
    private final UnitSolver UNIT_SOLVER;
    private final EnvironmentSolver ENV_SOLVER;
    private final TouchSolver TOUCH_SOLVER;
    private final int CAMERA_Y_SETUP;


    public static final int FLOOR_Y = (TileMap.FLOOR_START + 1) * TileMap.TILE_SIZE;
    public static Vector2 SCALED_SCREEN;
    private static Vector2 CAMERA;
    private static int SCALE;

    Ant hero;
    TouchType touchType;
    Weather weather;
    private Interface heroInterface;

    //TODO: Make it invisible the land / underground for a unit
    public GameField(int mapSizeX, int mapSizeY) {
        REAL_SIZE = new Vector2(TileMap.TILE_SIZE * mapSizeX, TileMap.TILE_SIZE * mapSizeY);
        UNIT_SOLVER = new UnitSolver(this);
        map = new TileMap(mapSizeX, mapSizeY);
        map.setAddTile(UNIT_SOLVER::addBoth);
        ENV_SOLVER = new EnvironmentSolver();
        TOUCH_SOLVER = new TouchSolver();
        SCALE = 8;
        SCALED_SCREEN = new Vector2(SCREEN_WIDTH / SCALE, SCREEN_HEIGHT / SCALE);
        CAMERA = new Vector2(0, 0);
        CAMERA_Y_SETUP = (int) (FLOOR_Y - SCALED_SCREEN.getY() / 2);
        ENV_SOLVER.addEnvironment(UNIT_SOLVER, REAL_SIZE, SCALED_SCREEN);
        ENV_SOLVER.addUnits(UNIT_SOLVER, REAL_SIZE);
        setupHero();
        touchType = TouchType.NONE;
        weather = Weather.NORMAL;
    }

    private void setupHero() {
        int x = (int) (Math.random() * REAL_SIZE.getX());
        Larva larva = new Larva(x, FLOOR_Y);
        Bag bag = new Bag(x, FLOOR_Y);
        attachCamera(x, FLOOR_Y);
        UNIT_SOLVER.addBoth(larva);
        UNIT_SOLVER.addBoth(bag);
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
        map.draw(canvas, paint, CAMERA, true);
        UNIT_SOLVER.draw(canvas, paint, CAMERA);
        map.draw(canvas, paint, CAMERA, false);
        heroInterface.draw(canvas, paint, CAMERA);
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
            TOUCH_SOLVER.doWhileTouching(hero, heroInterface, UNIT_SOLVER, map);
        } else {
            hero.setCurrentState(UnitState.IDLE);
            touchType = TouchType.NONE;
            heroInterface.getController().setToInitialPosition();
        }
        attachCamera();
        hero.setAim(hero.getX(), hero.getY());
        heroInterface.update();
    }

    public void update() {
        heroAction();
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
        Vector2 division = FIRST_TOUCH.dividedCopy(SCALE);
        division.addVector2(CAMERA);
        return division;
    }

    public static Vector2 getDisplayTouch() {
        return FIRST_TOUCH.dividedCopy(SCALE);
    }

    public static Vector2 getDisplayLastTouch() {
        return ThreadSolver.getTouchCount() > 1 ? LAST_TOUCH.dividedCopy(SCALE) : null;
    }

    boolean checkIfOnScreen(float x, float y, int step) {
        return Math.abs(x - CAMERA.getX() - SCALED_SCREEN.getX() / 2f)
                < SCALED_SCREEN.getX() / 2f + step &&
                Math.abs(y - CAMERA.getY() - SCALED_SCREEN.getY() / 2f)
                        < SCALED_SCREEN.getY() / 2f + step;
    }
}
