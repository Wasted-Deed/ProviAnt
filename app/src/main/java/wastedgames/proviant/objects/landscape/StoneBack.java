package wastedgames.proviant.objects.landscape;

import wastedgames.proviant.enumerations.Image;
import wastedgames.proviant.enumerations.TileState;
import wastedgames.proviant.enumerations.TileType;
import wastedgames.proviant.maintenance.Physics;
import wastedgames.proviant.maintenance.ResourcesLoader;
import wastedgames.proviant.objects.Appearance;

public class StoneBack extends Tile {

    public StoneBack(int x, int y, int size) {
        super(x, y, size);
        type = TileType.BACKGROUND;
        appearance.put(TileState.EXIST,
                new Appearance(ResourcesLoader.getImage(Image.TILE_STONE_BACK)));
        appearance.put(TileState.ROUNDED, new Appearance
                (ResourcesLoader.getBitmapSet(Image.TILE_DIRT_BACK_0, Image.TILE_DIRT_BACK_3),
                        0, 0));
        isSolid = false;
    }
}
