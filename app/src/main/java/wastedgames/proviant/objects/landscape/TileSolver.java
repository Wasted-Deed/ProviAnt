package wastedgames.proviant.objects.landscape;

import wastedgames.proviant.enumerations.TileState;

public class TileSolver {
    private Tile[][] map;
    private int sizeX;
    private int sizeY;

    public TileSolver(Tile[][] map, int sizeX, int sizeY) {
        this.map = map;
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

    void checkRoundedTiles(int x, int y) {
        if (!checkTilesAround(x, y)) {
            return;
        }
        if (map[x][y] instanceof Dirt) {
            if (!map[x + 1][y].isSolid()) {
                if (!map[x + 1][y - 1].isSolid() &&
                        !map[x][y - 1].isSolid()) {
                    map[x][y].setCurrentState(TileState.ROUNDED);
                    map[x][y].setCurrentFrame(0);
                    if (!map[x - 1][y - 1].isSolid() && !map[x - 1][y].isSolid()) {
                        map[x][y].setCurrentState(TileState.ROUNDED);
                        map[x][y].setCurrentFrame(4);
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
        if (map[x][y] instanceof DirtBack) {
            if (map[x][y - 1].isSolid() && map[x + 1][y].isSolid()) {
                map[x][y].setCurrentState(TileState.ROUNDED);
                map[x][y].setCurrentFrame(0);
            } else if (map[x][y + 1].isSolid() && map[x + 1][y].isSolid()) {
                map[x][y].setCurrentState(TileState.ROUNDED);
                map[x][y].setCurrentFrame(1);
            } else if (map[x][y + 1].isSolid() && map[x - 1][y].isSolid()) {
                map[x][y].setCurrentState(TileState.ROUNDED);
                map[x][y].setCurrentFrame(2);
            } else if (map[x][y - 1].isSolid() && map[x - 1][y].isSolid()) {
                map[x][y].setCurrentState(TileState.ROUNDED);
                map[x][y].setCurrentFrame(3);
            } else {
                map[x][y].setCurrentState(TileState.EXIST);
                map[x][y].setCurrentFrame(0);
            }
        }
    }
}
