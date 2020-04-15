package wastedgames.proviant.objects.landscape;

import wastedgames.proviant.enumerations.Image;
import wastedgames.proviant.enumerations.TileState;
import wastedgames.proviant.enumerations.TileType;
import wastedgames.proviant.maintenance.ResourcesLoader;
import wastedgames.proviant.objects.Appearance;

public class AirTile extends Tile {
    public AirTile(int x, int y, int size) {
        super(x, y, size);
        type = TileType.AIR;
        appearance.put(TileState.EXIST,
                new Appearance(ResourcesLoader.getImage(Image.TILE_GRASS_1)));
        currentState = TileState.EXIST;
        isSolid = false;
    }
}
