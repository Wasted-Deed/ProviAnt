package wastedgames.proviant.objects.landscape;

import wastedgames.proviant.enumerations.Image;
import wastedgames.proviant.enumerations.TileState;
import wastedgames.proviant.enumerations.TileType;
import wastedgames.proviant.maintenance.Physics;
import wastedgames.proviant.maintenance.ResourcesLoader;
import wastedgames.proviant.objects.Appearance;

public class Dirt extends Tile {

    public Dirt(int x, int y, int size, int configuration) {
        super(x, y, size);
        type = TileType.DIRT;
        appearance.put(TileState.EXIST, new Appearance
                (ResourcesLoader.getImage(Image.valueOf("TILE_DIRT_" + configuration))));
        appearance.put(TileState.ROUNDED, new Appearance
                (ResourcesLoader.getBitmapSet(Image.TILE_DIRT_0_0, Image.TILE_DIRT_0_7),
                        0, 0));
        durability = Physics.DIRT_DURABILITY;
        isSolid = true;
    }

}
