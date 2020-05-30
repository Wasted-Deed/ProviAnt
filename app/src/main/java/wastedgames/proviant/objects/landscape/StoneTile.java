package wastedgames.proviant.objects.landscape;

import wastedgames.proviant.enumerations.Image;
import wastedgames.proviant.enumerations.TileState;
import wastedgames.proviant.enumerations.TileType;
import wastedgames.proviant.maintenance.ResourcesLoader;
import wastedgames.proviant.objects.Appearance;

public class StoneTile extends Tile{
    public StoneTile(int x, int y, int size) {
        super(x, y, size);
        type = TileType.BACKGROUND;
        appearance.put(TileState.EXIST,
                new Appearance(ResourcesLoader.getImage(Image.TILE_STONE_0)));
        isSolid = true;
    }
}
