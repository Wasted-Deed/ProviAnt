package wastedgames.proviant.layouts;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;

import wastedgames.proviant.engine.Vector2;
import wastedgames.proviant.enumerations.Font;
import wastedgames.proviant.enumerations.TouchType;
import wastedgames.proviant.enumerations.UnitState;
import wastedgames.proviant.interfaces.Drawable;
import wastedgames.proviant.interfaces.Portable;
import wastedgames.proviant.maintenance.Physics;
import wastedgames.proviant.maintenance.Text;
import wastedgames.proviant.objects.MovableUnit;
import wastedgames.proviant.objects.PortableUnit;
import wastedgames.proviant.objects.environment.BackgroundGrass;
import wastedgames.proviant.objects.environment.BuildingUnit;
import wastedgames.proviant.objects.environment.Chamomile;
import wastedgames.proviant.objects.environment.Grass;
import wastedgames.proviant.objects.environment.Meat;
import wastedgames.proviant.objects.environment.Rose;
import wastedgames.proviant.objects.environment.Stick;
import wastedgames.proviant.objects.environment.Stone;
import wastedgames.proviant.objects.environment.Sun;
import wastedgames.proviant.objects.fauna.ActiveUnit;
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
    private final ArrayList<Drawable> DRAWABLE_UNITS;
    private final ArrayList<MovableUnit> MOVABLE_UNITS;
    private static final int FLOOR_Y = 97;
    private final int realSizeX;

    private static Vector2 CAMERA;
    public static int SCALE;
    public static int SCALED_SCREEN_WIDTH;
    public static int SCALED_SCREEN_HEIGHT;

    private final Text text;
    private final TileMap map;
    private Ant hero;
    private Sun sun;
    private Interface heroInterface;
    private TouchType touchType;

    public GameField(int mapSizeX, int mapSizeY) {
        realSizeX = TileMap.TILE_SIZE * mapSizeX;
        text = new Text(Font.BASIC);
        map = new TileMap(mapSizeX, mapSizeY);
        DRAWABLE_UNITS = new ArrayList<>();
        MOVABLE_UNITS = new ArrayList<>();
        SCALE = 8;
        SCALED_SCREEN_WIDTH = SCREEN_WIDTH / SCALE;
        SCALED_SCREEN_HEIGHT = SCREEN_HEIGHT / SCALE;
        CAMERA = new Vector2(0, 0);
        sun = new Sun(10, 30);
        addEnvironment();
        addUnits();
        setupHero();
        touchType = TouchType.NONE;
    }

    private void setupHero() {
        int x = (int) (Math.random() * realSizeX);
        Larva larva = new Larva(x, FLOOR_Y);
        attachCamera(x, FLOOR_Y);
        DRAWABLE_UNITS.add(larva);
        MOVABLE_UNITS.add(larva);
        heroInterface = new Interface(larva);
    }

    private void addEnvironment() {
        for (int i = 0; i <= 10; i++) {
            BackgroundGrass backgroundGrass = new BackgroundGrass(256 * i, FLOOR_Y);
            DRAWABLE_UNITS.add(backgroundGrass);
        }
        for (int i = 0; i < 50; i++) {
            Grass grass = new Grass((int) (Math.random() * realSizeX), FLOOR_Y);
            Stick stick = new Stick((int) (Math.random() * realSizeX), FLOOR_Y);
            Stone stone = new Stone((int) (Math.random() * realSizeX), FLOOR_Y);
            DRAWABLE_UNITS.add(grass);
            DRAWABLE_UNITS.add(stick);
            MOVABLE_UNITS.add(stick);
            DRAWABLE_UNITS.add(stone);
            MOVABLE_UNITS.add(stone);
            if (i % 4 == 0) {
                Chamomile chamomile = new Chamomile((int) (Math.random() * realSizeX), FLOOR_Y);
                Rose rose = new Rose((int) (Math.random() * realSizeX), FLOOR_Y);
                DRAWABLE_UNITS.add(chamomile);
                DRAWABLE_UNITS.add(rose);
            }
        }
    }

    private void addUnits() {
        for (int i = 0; i < 10; i++) {
            Snail s = new Snail((int) (Math.random() * realSizeX), 30);
            DRAWABLE_UNITS.add(s);
            MOVABLE_UNITS.add(s);
            Bug b = new Bug((int) (Math.random() * realSizeX), 30);
            DRAWABLE_UNITS.add(b);
            MOVABLE_UNITS.add(b);
            LadyBug lb = new LadyBug((int) (Math.random() * realSizeX), 30);
            DRAWABLE_UNITS.add(lb);
            MOVABLE_UNITS.add(lb);
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
        drawUnits(canvas, paint);
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
                DRAWABLE_UNITS.add(drop);
                MOVABLE_UNITS.add(drop);
            }
        }
    }

    private void attachCamera() {
        attachCamera(hero.getX(), hero.getY());
    }

    private void attachCamera(int x, int y) {
        if (x > SCALED_SCREEN_WIDTH / 2 &&
                x < realSizeX - SCALED_SCREEN_WIDTH) {
            CAMERA.setX(x - SCALED_SCREEN_WIDTH / 2);
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
        for (int i = 0; i < MOVABLE_UNITS.size(); i++) {
            MovableUnit unit = MOVABLE_UNITS.get(i);
            if (!map.checkUnitCollide(unit.getLeftTop(),
                    unit.getRightBottom(), 0, 1)) {
                unit.move(new Vector2(0, Physics.GRAVITY_SPEED));
                continue;
            }
            unit.move(hero);
            unit.update();
            checkUnit(unit);
        }
    }

    private void larvaAction(Larva larva) {
        if (larva.isDestroyed()) {
            hero = new Ant(larva.getX(), larva.getY());
            MOVABLE_UNITS.remove(larva);
            DRAWABLE_UNITS.remove(larva);
            heroInterface = new Interface(hero);
            MOVABLE_UNITS.add(hero);
            DRAWABLE_UNITS.add(hero);
        }
    }

    private void checkUnit(MovableUnit unit) {
        if (hero != null && touchType == TouchType.NONE &&
                hero.isPointReachable(unit.getX(), unit.getY()) &&
                unit.isTouched(getScaledTouchX(), getScaledTouchY())) {
            if (unit instanceof Portable) {
                hero.setPickedObject((Portable) unit);
            } else if (unit instanceof ActiveUnit) {
                unit.damage(hero.getDamage());
            }
        }
        if (unit instanceof Larva) {
            larvaAction((Larva) unit);
        }
        if (unit instanceof ActiveUnit && unit.isDestroyed()) {
            Meat meat = new Meat(unit.getX(), unit.getY());
            MOVABLE_UNITS.add(meat);
            DRAWABLE_UNITS.add(meat);
            MOVABLE_UNITS.remove(unit);
            DRAWABLE_UNITS.remove(unit);
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
        for (Drawable unit : DRAWABLE_UNITS) {
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
