package wastedgames.proviant.layouts;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import wastedgames.proviant.engine.Vector2;
import wastedgames.proviant.enumerations.Font;
import wastedgames.proviant.enumerations.TouchType;
import wastedgames.proviant.enumerations.UnitState;
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
import wastedgames.proviant.objects.landscape.Tile;
import wastedgames.proviant.objects.landscape.TileMap;
import wastedgames.proviant.objects.ui.Interface;

import static wastedgames.proviant.maintenance.ThreadSolver.IS_TOUCHING;
import static wastedgames.proviant.maintenance.ThreadSolver.SCREEN_HEIGHT;
import static wastedgames.proviant.maintenance.ThreadSolver.SCREEN_WIDTH;
import static wastedgames.proviant.maintenance.ThreadSolver.TOUCH;

public class GameField {


    final TileMap map;
    private final Vector2 REAL_SIZE;
    private final UnitSolver UNIT_SOLVER;
    private final Text text;
    private final int CAMERA_Y_SETUP;


    static final int FLOOR_Y = 97;
    public static Vector2 SCALED_SCREEN;
    private static Vector2 CAMERA;
    private static int SCALE;

    Ant hero;
    TouchType touchType;
    private Sun sun;
    private Interface heroInterface;

    public GameField(int mapSizeX, int mapSizeY) {
        REAL_SIZE = new Vector2(TileMap.TILE_SIZE * mapSizeX, TileMap.TILE_SIZE * mapSizeY);
        text = new Text(Font.BASIC);
        map = new TileMap(mapSizeX, mapSizeY);
        UNIT_SOLVER = new UnitSolver(this);
        SCALE = 8;
        SCALED_SCREEN = new Vector2(SCREEN_WIDTH / SCALE, SCREEN_HEIGHT / SCALE);
        CAMERA = new Vector2(0, 0);
        CAMERA_Y_SETUP = (int) (FLOOR_Y - SCALED_SCREEN.getY() / 2);
        sun = new Sun(10, 30);
        addEnvironment();
        addUnits();
        setupHero();
        touchType = TouchType.NONE;
    }

    private void setupHero() {
        int x = (int) (Math.random() * REAL_SIZE.getX());
        Larva larva = new Larva(x, FLOOR_Y);
        attachCamera(x, FLOOR_Y);
        UNIT_SOLVER.addBoth(larva);
        heroInterface = new Interface(larva);
    }

    private void addEnvironment() {
        for (int i = 0; i <= 10; i++) {
            BackgroundGrass backgroundGrass = new BackgroundGrass(256 * i, FLOOR_Y);
            UNIT_SOLVER.addDrawableUnit(backgroundGrass);
        }
        for (int i = 0; i < 50; i++) {
            Grass grass = new Grass((int) (Math.random() * REAL_SIZE.getX()), FLOOR_Y);
            Stick stick = new Stick((int) (Math.random() * REAL_SIZE.getX()), FLOOR_Y);
            Stone stone = new Stone((int) (Math.random() * REAL_SIZE.getX()), FLOOR_Y);
            UNIT_SOLVER.addBoth(stick);
            UNIT_SOLVER.addBoth(stone);
            UNIT_SOLVER.addDrawableUnit(grass);
            if (i % 4 == 0) {
                Chamomile chamomile = new Chamomile((int) (Math.random() * REAL_SIZE.getX()), FLOOR_Y);
                Rose rose = new Rose((int) (Math.random() * REAL_SIZE.getX()), FLOOR_Y);
                UNIT_SOLVER.addDrawableUnit(chamomile);
                UNIT_SOLVER.addDrawableUnit(rose);
            }
        }
    }

    private void addUnits() {
        for (int i = 0; i < 10; i++) {
            Snail s = new Snail((int) (Math.random() * REAL_SIZE.getX()), 30);
            Bug b = new Bug((int) (Math.random() * REAL_SIZE.getX()), 30);
            LadyBug lb = new LadyBug((int) (Math.random() * REAL_SIZE.getX()), 30);
            UNIT_SOLVER.addBoth(s);
            UNIT_SOLVER.addBoth(b);
            UNIT_SOLVER.addBoth(lb);
        }
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
        map.draw(canvas, paint, CAMERA, true);
        UNIT_SOLVER.draw(canvas, paint, CAMERA);
        map.draw(canvas, paint, CAMERA, false);
        heroInterface.draw(canvas, paint, CAMERA);
    }

    private boolean heroMovement() {
        double step = 0.1;
        hero.setCurrentState(UnitState.WALK);
        if (TOUCH.getX() < SCREEN_WIDTH * step) {
            if (!map.checkUnitCollide(hero.getLeftTop(), hero.getRightBottom(), -1, 0)) {
                hero.move(new Vector2(-hero.getSpeed(), 0));
            }
        } else if (TOUCH.getX() > SCREEN_WIDTH * (1 - step)) {
            if (!map.checkUnitCollide(hero.getLeftTop(), hero.getRightBottom(), 1, 0)) {
                hero.move(new Vector2(hero.getSpeed(), 0));
            }
        } else {
            hero.setCurrentState(UnitState.IDLE);
            hero.setJumping(false);
            return false;
        }
        return true;
    }

    private void doWhileTouching() {
        if (heroMovement()) {
            return;
        }
        terraform();
    }

    private void terraform() {
        if (hero.isPointReachable(getScaledTouchX(), getScaledTouchY())) {
            Tile touched = map.getTouchedTile();
            if (touched == null || !touched.isSolid()) {
                if (hero.getPickedObject() != null && hero.getPickedObject() instanceof BuildingUnit) {
                    map.fillTouchedTile();
                }
                return;
            }
            PortableUnit drop = map.damageTouchedTile(hero.getEfficiency());
            hero.setCurrentState(UnitState.WORK);
            if (drop != null) {
                UNIT_SOLVER.addBoth(drop);
            }
        }
    }

    private void attachCamera() {
        attachCamera(hero.getX(), hero.getY());
    }

    private void attachCamera(int x, int y) {
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
        }
        attachCamera();
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

    public static int getScaledTouchX() {
        return (int) (TOUCH.getX() / SCALE + CAMERA.getX());
    }

    public static int getScaledTouchY() {
        return (int) (TOUCH.getY() / SCALE + CAMERA.getY());
    }

    boolean checkIfOnScreen(int x, int y, int step) {
        return Math.abs(x - CAMERA.getX() - SCALED_SCREEN.getX() / 2f)
                < SCALED_SCREEN.getX() / 2f + step &&
                Math.abs(y - CAMERA.getY() - SCALED_SCREEN.getY() / 2f)
                        < SCALED_SCREEN.getY() / 2f + step;
    }
}
