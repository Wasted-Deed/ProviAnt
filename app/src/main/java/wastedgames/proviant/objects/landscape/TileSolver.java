package wastedgames.proviant.objects.landscape;

import java.util.Stack;
import java.util.function.Consumer;

import wastedgames.proviant.engine.Vector2;
import wastedgames.proviant.enumerations.TileState;
import wastedgames.proviant.enumerations.TileType;
import wastedgames.proviant.objects.PortableUnit;
import wastedgames.proviant.objects.environment.DirtPile;
import wastedgames.proviant.objects.environment.StonePile;

import static wastedgames.proviant.objects.landscape.TileMap.TILE_SIZE;

public class TileSolver {
    private Consumer<PortableUnit> addTile;

    private Tile[][] map;
    private Tile[][] back_map;
    private int sizeX;
    private int sizeY;

    public TileSolver(Tile[][] map, Tile[][] back_map, int sizeX, int sizeY) {
        this.map = map;
        this.back_map = back_map;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    boolean checkBounds(int x, int y) {
        return x >= 0 && x < sizeX && y >= 0 && y < sizeY && map[x][y] != null;
    }

    boolean checkTilesAround(int x, int y) {
        return checkBounds(x, y) && checkBounds(x, y - 1) && checkBounds(x, y + 1) &&
                checkBounds(x - 1, y) && checkBounds(x + 1, y);
    }

    boolean checkRound(int x, int y, Tile tile) {
        if (tile instanceof Dirt) {
            return !map[x][y].isSolid();
        }
        if (tile instanceof StoneTile) {
            return !(map[x][y] instanceof StoneTile);
        }
        return false;
    }

    void checkRoundedTiles(int x, int y) {
        if (!checkTilesAround(x, y)) {
            return;
        }
        Tile cur = map[x][y];
        if (cur instanceof Dirt) {
            if (checkRound(x + 1, y, cur)) {
                if (checkRound(x + 1, y - 1, cur) &&
                        checkRound(x, y - 1, cur)) {
                    cur.setCurrentState(TileState.ROUNDED);
                    cur.setCurrentFrame(0);
                    if (checkRound(x - 1, y - 1, cur) && checkRound(x - 1, y, cur)) {
                        cur.setCurrentState(TileState.ROUNDED);
                        cur.setCurrentFrame(4);
                        return;
                    }
                }
                if (!map[x + 1][y + 1].isSolid() &&
                        !map[x][y + 1].isSolid()) {
                    map[x][y].setCurrentState(TileState.ROUNDED);
                    map[x][y].setCurrentFrame(1);
                    if (!map[x + 1][y - 1].isSolid() && !map[x][y - 1].isSolid()) {
                        map[x][y].setCurrentState(TileState.ROUNDED);
                        map[x][y].setCurrentFrame(5);
                        return;
                    }
                }
            }
            if (!map[x - 1][y].isSolid()) {
                if (!map[x - 1][y + 1].isSolid() && !map[x][y + 1].isSolid()) {
                    map[x][y].setCurrentState(TileState.ROUNDED);
                    map[x][y].setCurrentFrame(2);
                    if (!map[x + 1][y + 1].isSolid() && !map[x + 1][y].isSolid()) {
                        map[x][y].setCurrentState(TileState.ROUNDED);
                        map[x][y].setCurrentFrame(6);
                        return;
                    }
                }
                if (!map[x - 1][y - 1].isSolid() && !map[x][y - 1].isSolid()) {
                    map[x][y].setCurrentState(TileState.ROUNDED);
                    map[x][y].setCurrentFrame(3);
                    if (!map[x][y + 1].isSolid() && !map[x - 1][y + 1].isSolid()) {
                        map[x][y].setCurrentState(TileState.ROUNDED);
                        map[x][y].setCurrentFrame(7);
                        return;
                    }
                }
            }
        }
        cur = back_map[x][y];
        if (!map[x][y].isSolid() && cur instanceof DirtBack) {
            if (map[x][y - 1].isSolid() && map[x + 1][y].isSolid()) {
                cur.setCurrentState(TileState.ROUNDED);
                cur.setCurrentFrame(0);
            } else if (map[x][y + 1].isSolid() && map[x + 1][y].isSolid()) {
                cur.setCurrentState(TileState.ROUNDED);
                cur.setCurrentFrame(1);
            } else if (map[x][y + 1].isSolid() && map[x - 1][y].isSolid()) {
                cur.setCurrentState(TileState.ROUNDED);
                cur.setCurrentFrame(2);
            } else if (map[x][y - 1].isSolid() && map[x - 1][y].isSolid()) {
                cur.setCurrentState(TileState.ROUNDED);
                cur.setCurrentFrame(3);
            } else {
                cur.setCurrentState(TileState.EXIST);
                cur.setCurrentFrame(0);
            }
        }
    }


    //TODO: Make it work properly
    private int destroySideHeft(int x, int y, int dir, int count, Stack<Vector2> tiles) {
        if (!checkTilesAround(x, y)) {
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

    public void handleTilesAround(int x, int y) {
        setTileEnvironment(x, y);
        setTileEnvironment(x, y - 1);
        setTileEnvironment(x, y + 1);
        setTileEnvironment(x - 1, y);
        setTileEnvironment(x + 1, y);
    }

    public void destroyTile(int x, int y) {
        if (addTile != null) {
            switch (map[x][y].getType()) {
                case DIRT:
                    addTile.accept(new DirtPile(x * TILE_SIZE + TILE_SIZE / 2, y * TILE_SIZE));
                    break;
                case STONE:
                    addTile.accept(new StonePile(x * TILE_SIZE + TILE_SIZE / 2, y * TILE_SIZE));
                    break;
            }
        }
        map[x][y] = new AirTile(x, y, TILE_SIZE);
        handleTilesAround(x, y);
    }

    public void setAddTile(Consumer<PortableUnit> addTile) {
        this.addTile = addTile;
    }

    private void setTileEnvironment(int x, int y) {
        if (checkBounds(x, y - 1) && checkBounds(x, y)) {
            if (map[x][y - 1].getType() == TileType.GRASS
                    && !(map[x][y] instanceof DirtGrass)) {
                map[x][y - 1] = new AirTile(x, y - 1, TILE_SIZE);
            }
        }
        destroyUnstableTiles(x, y);
        checkRoundedTiles(x, y);
    }

}
