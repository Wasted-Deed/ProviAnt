package wastedgames.proviant.layouts;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;

import wastedgames.proviant.engine.Vector2;
import wastedgames.proviant.enumerations.UnitState;
import wastedgames.proviant.interfaces.Drawable;
import wastedgames.proviant.interfaces.Portable;
import wastedgames.proviant.maintenance.Physics;
import wastedgames.proviant.objects.Ant;
import wastedgames.proviant.objects.MovableUnit;
import wastedgames.proviant.objects.PortableUnit;
import wastedgames.proviant.objects.Snail;
import wastedgames.proviant.objects.environment.BackgroundGrass;
import wastedgames.proviant.objects.environment.Grass;
import wastedgames.proviant.objects.environment.Stick;
import wastedgames.proviant.objects.landscape.TileMap;

import static wastedgames.proviant.maintenance.ThreadSolver.IS_TOUCHING;
import static wastedgames.proviant.maintenance.ThreadSolver.SCREEN_HEIGHT;
import static wastedgames.proviant.maintenance.ThreadSolver.SCREEN_WIDTH;
import static wastedgames.proviant.maintenance.ThreadSolver.TOUCH;

public class GameField {
    public static int SCALE;
    public static int SCALED_SCREEN_WIDTH;
    public static int SCALED_SCREEN_HEIGHT;
    private static Vector2 CAMERA;

    private final TileMap map;
    private final ArrayList<Drawable> drawableUnits;
    private final ArrayList<MovableUnit> movableUnits;
    private final int mapSizeX;
    private final int mapSizeY;
    private final int realSizeX;
    private final int realSizeY;
    private Ant hero;

    public GameField(int mapSizeX, int mapSizeY) {
        this.mapSizeX = mapSizeX;
        this.mapSizeY = mapSizeY;
        realSizeX = TileMap.TILE_SIZE * mapSizeX;
        realSizeY = TileMap.TILE_SIZE * mapSizeY;
        map = new TileMap(mapSizeX, mapSizeY);
        drawableUnits = new ArrayList<>();
        movableUnits = new ArrayList<>();
        SCALE = 8;
        SCALED_SCREEN_WIDTH = SCREEN_WIDTH / SCALE;
        SCALED_SCREEN_HEIGHT = SCREEN_HEIGHT / SCALE;
        CAMERA = new Vector2(0, 0);
        hero = new Ant(2000, 30);
        for (int i = 0; i < 10; i++) {
            BackgroundGrass backgroundGrass = new BackgroundGrass(256 * i, 81);
            drawableUnits.add(backgroundGrass);
        }
        for (int i = 0; i < 50; i++) {
            Grass grass = new Grass((int) (Math.random() * realSizeX), 81);
            Stick stick = new Stick((int) (Math.random() * realSizeX), 81);
            drawableUnits.add(grass);
            drawableUnits.add(stick);
            movableUnits.add(stick);
        }
        for (int i = 0; i < 10; i++) {
            Snail s = new Snail((int) (Math.random() * realSizeX), 30);
            drawableUnits.add(s);
            movableUnits.add(s);
        }
        drawableUnits.add(hero);
        movableUnits.add(hero);
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
        drawUnits(canvas, paint);
        map.draw(canvas, paint, CAMERA, false);
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
        if (hero.isPointReachable(getScaledTouchX(), getScaledTouchY())) {
            PortableUnit drop = map.damageTouchedTile(hero.getEfficiency());
            hero.setCurrentState(UnitState.WORK);
            if (drop != null) {
                drawableUnits.add(drop);
                movableUnits.add(drop);
            }
        }
    }

    private void attachCamera() {
        if (hero.getX() > SCALED_SCREEN_WIDTH / 2 &&
                hero.getX() < realSizeX - SCALED_SCREEN_WIDTH) {
            CAMERA.setX(hero.getX() - SCALED_SCREEN_WIDTH / 2);
        }
    }

    public void update() {
        if (IS_TOUCHING) {
            doWhileTouching();
        } else {
            hero.setCurrentState(UnitState.IDLE);
        }
        attachCamera();
        for (MovableUnit unit : movableUnits) {
            unit.update();
            if (IS_TOUCHING &&
                    hero.isPointReachable(unit.getX(), unit.getY()) &&
                    unit.isTouched(getScaledTouchX(), getScaledTouchY()) &&
                    unit instanceof Portable) {
                hero.setPickedObject((Portable) unit);
            }
            if (!map.checkUnitCollide(unit.getLeftTop(),
                    unit.getRightBottom(), 0, 1)) {
                unit.move(new Vector2(0, Physics.GRAVITY_SPEED));
                continue;
            }
            unit.move(hero);
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

    private void drawUnits(Canvas canvas, Paint paint) {
        for (Drawable unit : drawableUnits) {
            if (checkIfOnScreen(unit.getX(), unit.getY(),
                    Math.max(unit.getWidth(), unit.getHeight()))) {
                unit.draw(canvas, paint, CAMERA);
            }
        }
    }

    public static int getScaledTouchX() {
        return (int) (TOUCH.getX() / SCALE + CAMERA.getX());
    }

    public static int getScaledTouchY() {
        return (int) (TOUCH.getY() / SCALE + CAMERA.getY());
    }

    private boolean checkIfOnScreen(int x, int y, int step) {
        return Math.abs(x - CAMERA.getX() - SCALED_SCREEN_WIDTH / 2f)
                < SCALED_SCREEN_WIDTH / 2f + step &&
                Math.abs(y - CAMERA.getY() - SCALED_SCREEN_HEIGHT / 2f)
                        < SCALED_SCREEN_HEIGHT / 2f + step;
    }
}
