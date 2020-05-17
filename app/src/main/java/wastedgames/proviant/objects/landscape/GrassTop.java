package wastedgames.proviant.objects.landscape;

import wastedgames.proviant.enumerations.Image;
import wastedgames.proviant.enumerations.TileState;
import wastedgames.proviant.enumerations.TileType;
import wastedgames.proviant.maintenance.ResourcesLoader;
import wastedgames.proviant.objects.Appearance;

public class GrassTop extends Tile {

    public GrassTop(int x, int y, int size) {
        super(x, y, size);
        type = TileType.GRASS;
        appearance.put(TileState.EXIST,
                new Appearance(ResourcesLoader.getImage(Image.TILE_TOP_GRASS)));
        isSolid = false;
    }
}
