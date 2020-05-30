package wastedgames.proviant.layouts;

import wastedgames.proviant.objects.landscape.AirTile;
import wastedgames.proviant.objects.landscape.Dirt;
import wastedgames.proviant.objects.landscape.DirtGrass;
import wastedgames.proviant.objects.landscape.GrassTop;
import wastedgames.proviant.objects.landscape.StoneTile;
import wastedgames.proviant.objects.landscape.Tile;
import wastedgames.proviant.objects.landscape.TileSolver;

import static wastedgames.proviant.objects.landscape.TileMap.FLOOR_START;
import static wastedgames.proviant.objects.landscape.TileMap.TILE_SIZE;

public class MapSolver {
    TileSolver solver;

    Tile[][] map;
    private int sizeX;
    private int sizeY;

    private final int MAX_CAVE_SIZE = 40;
    private final int MAX_STONE_SIZE = 40;
    private final int MAX_DURATION = 3;


    public MapSolver(Tile[][] map, int sizeX, int sizeY, TileSolver solver) {
        this.map = map;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.solver = solver;
        fillMap(FLOOR_START);
        generateCaves(100);
        generateStones(50);
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
        if (!checkBounds(x, y) || size >= MAX_CAVE_SIZE || y <= FLOOR_START) {
            return;
        } else {
            solver.destroyTile(x, y);
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

    boolean checkBounds(int x, int y) {
        return x >= 0 && x < sizeX && y >= 0 && y < sizeY && map[x][y] != null;
    }

    private void generateStone(int x, int y, int size) {
        if (!checkBounds(x, y) || size >= MAX_STONE_SIZE || y <= FLOOR_START) {
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
}
