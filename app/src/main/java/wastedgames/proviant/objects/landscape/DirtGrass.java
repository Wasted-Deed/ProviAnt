package wastedgames.proviant.objects.landscape;

import wastedgames.proviant.enumerations.Image;
import wastedgames.proviant.enumerations.TileState;
import wastedgames.proviant.enumerations.TileType;
import wastedgames.proviant.maintenance.Physics;
import wastedgames.proviant.maintenance.ResourcesLoader;
import wastedgames.proviant.objects.Appearance;

public class DirtGrass extends Tile {

    public DirtGrass(int x, int y, int size, int configuration) {
        super(x, y, size);
        type = TileType.DIRT;
        appearance.put(TileState.EXIST, new Appearance
                (ResourcesLoader.getImage(Image.valueOf("TILE_GRASS_" + configuration))));
        durability = Physics.DIRT_DURABILITY;
        isSolid = true;
    }
}
