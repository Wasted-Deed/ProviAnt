package wastedgames.proviant.objects.landscape;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.Stack;
import java.util.function.Consumer;

import wastedgames.proviant.engine.Vector2;
import wastedgames.proviant.enumerations.TileType;
import wastedgames.proviant.interfaces.Updatable;
import wastedgames.proviant.layouts.MapSolver;
import wastedgames.proviant.objects.PortableUnit;
import wastedgames.proviant.objects.environment.DirtPile;

import static wastedgames.proviant.layouts.GameField.SCALED_SCREEN;
import static wastedgames.proviant.layouts.GameField.getScaledTouch;


public class TileMap implements Updatable {
    public final static int FLOOR_START = 11;
    public final static int TILE_SIZE = 8;

    private TileSolver solver;
    private Tile[][] map;
    MapSolver mapSolver;

    public TileMap(int sizeX, int sizeY) {
        map = new Tile[sizeX][sizeY];
        solver = new TileSolver(map, sizeX, sizeY);
        mapSolver = new MapSolver(map, sizeX, sizeY, solver);
    }
    public void setAddTile(Consumer<PortableUnit> addTile) {
        solver.setAddTile(addTile);
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
            solver.destroyTile(x, y);
        }
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
