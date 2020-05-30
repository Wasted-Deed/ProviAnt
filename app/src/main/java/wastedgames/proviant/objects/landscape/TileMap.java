package wastedgames.proviant.objects.landscape;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.Stack;
import java.util.function.Consumer;

import wastedgames.proviant.engine.Vector2;
import wastedgames.proviant.enumerations.TileType;
import wastedgames.proviant.interfaces.Updatable;
import wastedgames.proviant.objects.PortableUnit;
import wastedgames.proviant.objects.environment.DirtPile;

import static wastedgames.proviant.layouts.GameField.SCALED_SCREEN;
import static wastedgames.proviant.layouts.GameField.getScaledTouch;


public class TileMap implements Updatable {
    public Consumer<PortableUnit> addTile;
    public final static int FLOOR_START = 11;
    public final static int TILE_SIZE = 8;

    private TileSolver solver;
    private final int MAX_CAVE_SIZE = 40;
    private final int MAX_STONE_SIZE = 40;
    private final int MAX_DURATION = 3;
    private Tile[][] map;
    private int sizeX;
    private int sizeY;

    public TileMap(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        map = new Tile[sizeX][sizeY];
        solver = new TileSolver(map, sizeX, sizeY);
        fillMap(FLOOR_START);
        generateCaves(100);
        generateStones(50);
    }

    public void setAddTile(Consumer<PortableUnit> addTile) {
        this.addTile = addTile;
    }

    private void generateCaves(int count) {
        for (int i = 0; i < count; i++) {
            generateCave();
        }
    }

    private void generateStones(int count) {
        for (int i = 0; i < count; i++) {
            generateStone();
        }
    }

    private void fillMap(int start) {
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                if (y == start) {
                    map[x][y] = new GrassTop(x, y, TILE_SIZE);
                    continue;
                }
                if (y == start + 1) {
                    map[x][y] = new DirtGrass(x, y, TILE_SIZE, (int) (Math.random() * 3));
                    continue;
                }
                if (y > start + 1) {
                    map[x][y] = new Dirt(x, y, TILE_SIZE, (int) (Math.random() * 3));
                } else {
                    map[x][y] = new AirTile(x, y, TILE_SIZE);
                }
            }
        }
    }

    private void generateCave() {
        int startX = (int) (Math.random() * sizeX);
        int startY = FLOOR_START + (int) (Math.random() * (sizeY - FLOOR_START));
        generateCave(startX, startY, 0);
        generateCave(startX + 1, startY, 0);
        generateCave(startX - 1, startY, 0);
        generateCave(startX, startY + 1, 0);
        generateCave(startX, startY - 1, 0);
    }

    private void generateCave(int x, int y, int size) {
        if (!solver.checkBounds(x, y) || size >= MAX_CAVE_SIZE || y <= FLOOR_START) {
            return;
        } else {
            destroyTile(x, y);
        }
        int dir = Math.random() > 0.5f ? 1 : -1;
        boolean isDirX = Math.random() > 0.5;
        if (isDirX) {
            generateCave(x + dir, y, size + (int) (Math.random() * 2) + 1);
        } else {
            generateCave(x, y + dir, size + (int) (Math.random() * 2) + 1);
        }
    }

    private void generateStone() {
        int startX = (int) (Math.random() * sizeX);
        int startY = FLOOR_START * 3 / 2 + (int) (Math.random() * (sizeY - FLOOR_START * 3 / 2));
        generateStone(startX, startY, 0);
        generateStone(startX + 1, startY, 0);
        generateStone(startX - 1, startY, 0);
        generateStone(startX, startY + 1, 0);
        generateStone(startX, startY - 1, 0);

    }

    private void generateStone(int x, int y, int size) {
        if (!solver.checkBounds(x, y) || size >= MAX_STONE_SIZE || y <= FLOOR_START) {
            return;
        } else if (map[x][y].isSolid()) {
            map[x][y] = new StoneTile(x, y, TILE_SIZE);
        }
        int dir = Math.random() > 0.5f ? 1 : -1;
        boolean isDirX = Math.random() > 0.5;
        if (isDirX) {
            generateStone(x + dir, y, size + (int) (Math.random() * 2) + 1);
        } else {
            generateStone(x, y + dir, size + (int) (Math.random() * 2) + 1);
        }
    }

    public void draw(Canvas canvas, Paint paint, Vector2 camera, boolean isBackground) {
        int startX = (int) (camera.getX() / TILE_SIZE);
        int endX = (int) (startX + SCALED_SCREEN.getX() / TILE_SIZE + 1);
        int startY = (int) (camera.getY() / TILE_SIZE);
        int endY = (int) (startY + SCALED_SCREEN.getY() / TILE_SIZE + 1);
        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                if (!solver.checkBounds(x, y)) {
                    continue;
                }
                if (map[x][y].getType() == TileType.AIR) {
                    continue;
                }
                if (map[x][y].getType() == TileType.BACKGROUND && isBackground ||
                        map[x][y].getType() != TileType.BACKGROUND && !isBackground) {
                    map[x][y].draw(canvas, paint, camera);
                }
            }
        }
    }

    public Tile getTouchedTile() {
        int x = getTouchedTileX();
        int y = getTouchedTileY();
        if (solver.checkBounds(x, y)) {
            return map[x][y];
        }
        return null;
    }

    public void fillTouchedTile() {
        int x = getTouchedTileX();
        int y = getTouchedTileY();
        if (solver.checkBounds(x, y)) {
            map[x][y] = new Dirt(x, y, TILE_SIZE, 0);
        }
    }

    public void damageTouchedTile(int frequency) {
        Tile touchedTile = getTouchedTile();
        if (touchedTile == null) {
            return;
        }
        if (touchedTile.isSolid()) {
            touchedTile.damage(frequency);
        } else {
            return;
        }
        if (touchedTile.isDestroyed()) {
            int x = getTouchedTileX();
            int y = getTouchedTileY();
            destroyTile(x, y);
        }
    }

    private void destroyTile(int x, int y) {
        map[x][y] = new DirtBack(x, y, TILE_SIZE);
        if (addTile != null) {
            addTile.accept(new DirtPile(x * TILE_SIZE + TILE_SIZE / 2, y * TILE_SIZE));
        }
        handleTilesAround(x, y);
    }

    private void setTileEnvironment(int x, int y) {
        if (solver.checkBounds(x, y - 1) && solver.checkBounds(x, y)) {
            if (map[x][y - 1].getType() == TileType.GRASS
                    && !map[x][y].isSolid()) {
                map[x][y - 1] = new AirTile(x, y - 1, TILE_SIZE);
            }
        }
        destroyUnstableTiles(x, y);
        solver.checkRoundedTiles(x, y);
    }

    private int countSolidAround(int x, int y) {
        if (!solver.checkTilesAround(x, y)) {
            return 8;
        }
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                if (map[x + i][y + j].isSolid()) {
                    count++;
                }
            }
        }
        return count;
    }


    private void handleTilesAround(int x, int y) {
        setTileEnvironment(x, y);
        setTileEnvironment(x, y - 1);
        setTileEnvironment(x, y + 1);
        setTileEnvironment(x - 1, y);
        setTileEnvironment(x + 1, y);
    }


    //TODO: Make it work properly
    private int destroySideHeft(int x, int y, int dir, int count, Stack<Vector2> tiles) {
        if (!solver.checkTilesAround(x, y)) {
            return count;
        }
        boolean isClear = !map[x][y + 1].isSolid() && !map[x][y - 1].isSolid();
        boolean isCurrentSolid = map[x][y].isSolid();
        if (isClear && isCurrentSolid && (!map[x + 1][y].isSolid() || !map[x - 1][y].isSolid())) {
            destroyTile(x, y);
        }
        return count;
    }

    private void destroyUnstableTiles(int x, int y) {
        Stack<Vector2> tiles = new Stack<>();
        destroySideHeft(x, y, 1, 0, tiles);
    }

    public int getTouchedTileX() {
        return (int) (getScaledTouch().getX() / TILE_SIZE);
    }

    public int getTouchedTileY() {
        return (int) (getScaledTouch().getY() / TILE_SIZE);
    }

    public boolean checkPointCollision(Vector2 point) {
        int x = (int) (point.getX() / TILE_SIZE);
        int y = (int) (point.getY() / TILE_SIZE);
        return !solver.checkBounds(x, y) || map[x][y].isSolid;
    }

    public Vector2 getRealTilePos(Vector2 touch) {
        touch.setX((int) (touch.getX() / TILE_SIZE) * TILE_SIZE + TILE_SIZE / 2);
        touch.setY((int) (touch.getY() / TILE_SIZE) * TILE_SIZE + TILE_SIZE / 2);
        return touch;
    }

    @Override
    public void update() {

    }
}
