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
        setConfiguration(configuration);
        currentState = TileState.EXIST;
        durability = Physics.DIRT_DURABILITY;
        isSolid = true;
    }

    private void setConfiguration(int configuration) {
        switch (configuration) {
            case 1:
                appearance.put(TileState.EXIST,
                        new Appearance(ResourcesLoader.getImage(Image.TILE_DIRT_2)));
                break;
            case 2:
                appearance.put(TileState.EXIST,
                        new Appearance(ResourcesLoader.getImage(Image.TILE_DIRT_3)));
                break;
            default:
                appearance.put(TileState.EXIST,
                        new Appearance(ResourcesLoader.getImage(Image.TILE_DIRT_0)));
        }
    }
}
