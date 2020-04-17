package wastedgames.proviant.objects.landscape;

import android.graphics.Canvas;
import android.graphics.Paint;

import wastedgames.proviant.engine.Vector2;
import wastedgames.proviant.enumerations.TileType;
import wastedgames.proviant.interfaces.Updatable;
import wastedgames.proviant.objects.PortableUnit;
import wastedgames.proviant.objects.environment.DirtPile;

import static wastedgames.proviant.layouts.GameField.SCALED_SCREEN_HEIGHT;
import static wastedgames.proviant.layouts.GameField.SCALED_SCREEN_WIDTH;
import static wastedgames.proviant.layouts.GameField.getScaledTouchX;
import static wastedgames.proviant.layouts.GameField.getScaledTouchY;

public class TileMap implements Updatable {
    public final static int TILE_SIZE = 8;
    private final int MAX_CAVE_SIZE = 40;
    private final int FLOOR_START = 11;
    private Tile[][] map;
    private int sizeX;
    private int sizeY;

    public TileMap(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        map = new Tile[sizeX][sizeY];
        fillMap(FLOOR_START);
        generateCaves(100);
    }

    private void generateCaves(int count) {
        for (int i = 0; i < count; i++) {
            generateCave();
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
        int startY = (int) (Math.random() * sizeY);
        generateCave(startX, startY, 0);
        generateCave(startX + 1, startY, 0);
        generateCave(startX - 1, startY, 0);
        generateCave(startX, startY + 1, 0);
        generateCave(startX, startY - 1, 0);
    }

    private void generateCave(int x, int y, int size) {
        if (!checkBounds(x, y) || size >= MAX_CAVE_SIZE || y <= FLOOR_START) {
            return;
        } else {
            destroyDirtTile(x, y);
        }
        int dir = Math.random() > 0.5f ? 1 : -1;
        boolean isDirX = Math.random() > 0.5;
        if (isDirX) {
            generateCave(x + dir, y, size + (int) (Math.random() * 2) + 1);
        } else {
            generateCave(x, y + dir, size + (int) (Math.random() * 2) + 1);
        }
    }

    public void draw(Canvas canvas, Paint paint, Vector2 camera, boolean isBackground) {
        int startX = (int) (camera.getX() / TILE_SIZE);
        int endX = startX + SCALED_SCREEN_WIDTH / TILE_SIZE + 1;
        int startY = (int) (camera.getY() / TILE_SIZE);
        int endY = startY + SCALED_SCREEN_HEIGHT / TILE_SIZE;
        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                if (!checkBounds(x, y)) {
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

    private boolean checkBounds(int x, int y) {
        return x >= 0 && x < sizeX && y >= 0 && y < sizeY && map[x][y] != null;
    }

    public Tile getTouchedTile() {
        int x = getTouchedTileX();
        int y = getTouchedTileY();
        if (checkBounds(x, y)) {
            return map[x][y];
        }
        return null;
    }

    public void fillTouchedTile() {
        int x = getTouchedTileX();
        int y = getTouchedTileY();
        if (checkBounds(x, y)) {
            map[x][y] = new Dirt(x, y, TILE_SIZE, 0);
        }
    }

    public PortableUnit damageTouchedTile(int frequency) {
        Tile touchedTile = getTouchedTile();
        if (touchedTile == null) {
            return null;
        }
        if (touchedTile.isSolid()) {
            touchedTile.damage(frequency);
        } else {
            return null;
        }
        if (touchedTile.isDestroyed()) {
            int x = getTouchedTileX();
            int y = getTouchedTileY();
            destroyDirtTile(x, y);
            return new DirtPile(x * TILE_SIZE + TILE_SIZE / 2, y * TILE_SIZE);
        }
        return null;
    }

    private void destroyDirtTile(int x, int y) {
        map[x][y] = new DirtBack(x, y, TILE_SIZE);
        setTileEnvironment(x, y);
    }

    private void setTileEnvironment(int x, int y) {
        if (checkBounds(x, y - 1) && checkBounds(x, y)) {
            if (map[x][y - 1].getType() == TileType.GRASS) {
                map[x][y - 1] = null;
            }
        }
    }

    public int getTouchedTileX() {
        return getScaledTouchX() / TILE_SIZE;
    }

    public int getTouchedTileY() {
        return getScaledTouchY() / TILE_SIZE;
    }

    public boolean checkUnitCollide(Vector2 leftTop, Vector2 rightBottom, int dirX, int dirY) {
        int startX = (int) (leftTop.getX() / TILE_SIZE);
        int startY = (int) (leftTop.getY() / TILE_SIZE);
        int endX = (int) (rightBottom.getX() / TILE_SIZE);
        int endY = (int) (rightBottom.getY() / TILE_SIZE);
        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y < endY; y++) {
                if (!checkBounds(x + dirX, y + dirY)) {
                    continue;
                }
                if (map[x + dirX][y + dirY].isSolid()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void update() {

    }
}
