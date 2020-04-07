package wastedgames.proviant.layouts;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;

import wastedgames.proviant.engine.Vector2;
import wastedgames.proviant.enumerations.TouchType;
import wastedgames.proviant.enumerations.UnitState;
import wastedgames.proviant.interfaces.Drawable;
import wastedgames.proviant.interfaces.Portable;
import wastedgames.proviant.maintenance.Physics;
import wastedgames.proviant.objects.MovableUnit;
import wastedgames.proviant.objects.PortableUnit;
import wastedgames.proviant.objects.environment.BackgroundGrass;
import wastedgames.proviant.objects.environment.DirtPile;
import wastedgames.proviant.objects.environment.Grass;
import wastedgames.proviant.objects.environment.Meat;
import wastedgames.proviant.objects.environment.Stick;
import wastedgames.proviant.objects.fauna.ActiveUnit;
import wastedgames.proviant.objects.fauna.Ant;
import wastedgames.proviant.objects.fauna.Bug;
import wastedgames.proviant.objects.fauna.Snail;
import wastedgames.proviant.objects.landscape.Tile;
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
    private final int realSizeX;
    private Ant hero;

    private TouchType touchType;

    public GameField(int mapSizeX, int mapSizeY) {
        realSizeX = TileMap.TILE_SIZE * mapSizeX;
        map = new TileMap(mapSizeX, mapSizeY);
        drawableUnits = new ArrayList<>();
        movableUnits = new ArrayList<>();
        SCALE = 8;
        SCALED_SCREEN_WIDTH = SCREEN_WIDTH / SCALE;
        SCALED_SCREEN_HEIGHT = SCREEN_HEIGHT / SCALE;
        CAMERA = new Vector2(0, 0);
        hero = new Ant(2000, 30);
        addEnvironment();
        addUnits();
        touchType = TouchType.NONE;
    }

    private void addEnvironment() {
        for (int i = 0; i <= 10; i++) {
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
    }

    private void addUnits() {
        for (int i = 0; i < 10; i++) {
            Snail s = new Snail((int) (Math.random() * realSizeX), 30);
            drawableUnits.add(s);
            movableUnits.add(s);
            Bug b = new Bug((int) (Math.random() * realSizeX), 30);
            drawableUnits.add(b);
            movableUnits.add(b);

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
        terraform();
    }

    private void terraform() {
        if (hero.isPointReachable(getScaledTouchX(), getScaledTouchY())) {
            Tile touched = map.getTouchedTile();
            if (touched == null || !touched.isSolid()) {
                if (hero.getPickedObject() != null && hero.getPickedObject() instanceof DirtPile) {
                    //    map.fillTouchedTile();
                }
                return;
            }
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
            touchType = TouchType.TOUCH;
            doWhileTouching();
        } else {
            hero.setCurrentState(UnitState.IDLE);
            touchType = TouchType.NONE;
        }
        attachCamera();
        for (int i = 0; i < movableUnits.size(); i++) {
            MovableUnit unit = movableUnits.get(i);
            if (!map.checkUnitCollide(unit.getLeftTop(),
                    unit.getRightBottom(), 0, 1)) {
                unit.move(new Vector2(0, Physics.GRAVITY_SPEED));
                continue;
            }
            unit.move(hero);
            unit.update();
            checkTouchedUnit(unit);
        }
    }

    private void checkTouchedUnit(MovableUnit unit) {
        if (touchType == TouchType.NONE &&
                hero.isPointReachable(unit.getX(), unit.getY()) &&
                unit.isTouched(getScaledTouchX(), getScaledTouchY())) {
            if (unit instanceof Portable) {
                hero.setPickedObject((Portable) unit);
            } else if (unit instanceof ActiveUnit) {
                ((ActiveUnit) unit).damage(hero.getDamage());
                if (((ActiveUnit) unit).isDestroyed()) {
                    Meat meat = new Meat(unit.getX(), unit.getY());
                    movableUnits.add(meat);
                    drawableUnits.add(meat);
                    movableUnits.remove(unit);
                    drawableUnits.remove(unit);
                }
            }
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
