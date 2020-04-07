package wastedgames.proviant.maintenance;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;

import java.util.HashMap;

import wastedgames.proviant.R;
import wastedgames.proviant.enumerations.Image;

public class ResourcesLoader {
    private static HashMap<Image, Bitmap> bitmaps;

    public static void loadResources(Resources resources) {
        bitmaps = new HashMap<>();
        Options options = new Options();
        options.inScaled = false;
        bitmaps.put(Image.ANT_WORK_0, BitmapFactory.decodeResource(resources,
                R.drawable.ant_work_0, options));
        bitmaps.put(Image.ANT_WORK_1, BitmapFactory.decodeResource(resources,
                R.drawable.ant_work_1, options));
        bitmaps.put(Image.ANT_WORK_2, BitmapFactory.decodeResource(resources,
                R.drawable.ant_work_2, options));
        bitmaps.put(Image.ANT_WORK_3, BitmapFactory.decodeResource(resources,
                R.drawable.ant_work_3, options));
        bitmaps.put(Image.ANT_WORK_4, BitmapFactory.decodeResource(resources,
                R.drawable.ant_work_4, options));
        bitmaps.put(Image.ANT_WALK_0, BitmapFactory.decodeResource(resources,
                R.drawable.ant_walk_0, options));
        bitmaps.put(Image.ANT_WALK_1, BitmapFactory.decodeResource(resources,
                R.drawable.ant_walk_1, options));
        bitmaps.put(Image.ANT_WALK_2, BitmapFactory.decodeResource(resources,
                R.drawable.ant_walk_2, options));
        bitmaps.put(Image.ANT_WALK_3, BitmapFactory.decodeResource(resources,
                R.drawable.ant_walk_3, options));
        bitmaps.put(Image.ANT_WALK_4, BitmapFactory.decodeResource(resources,
                R.drawable.ant_walk_4, options));
        bitmaps.put(Image.SNAIL_0, BitmapFactory.decodeResource(resources,
                R.drawable.snail_idle_0, options));
        bitmaps.put(Image.SNAIL_1, BitmapFactory.decodeResource(resources,
                R.drawable.snail_move_0, options));
        bitmaps.put(Image.SNAIL_2, BitmapFactory.decodeResource(resources,
                R.drawable.snail_move_1, options));
        bitmaps.put(Image.SNAIL_3, BitmapFactory.decodeResource(resources,
                R.drawable.snail_move_2, options));
        bitmaps.put(Image.BUG_0, BitmapFactory.decodeResource(resources,
                R.drawable.bug_idle_0, options));
        bitmaps.put(Image.BUG_1, BitmapFactory.decodeResource(resources,
                R.drawable.bug_move_0, options));
        bitmaps.put(Image.BUG_2, BitmapFactory.decodeResource(resources,
                R.drawable.bug_move_1, options));
        bitmaps.put(Image.BUG_3, BitmapFactory.decodeResource(resources,
                R.drawable.bug_move_2, options));
        bitmaps.put(Image.MEAT_0, BitmapFactory.decodeResource(resources,
                R.drawable.meat_0, options));
        bitmaps.put(Image.MEAT_1, BitmapFactory.decodeResource(resources,
                R.drawable.meat_1, options));
        bitmaps.put(Image.TILE_DIRT_0, BitmapFactory.decodeResource(resources,
                R.drawable.tile_dirt_0, options));
        bitmaps.put(Image.TILE_DIRT_1, BitmapFactory.decodeResource(resources,
                R.drawable.tile_dirt_1, options));
        bitmaps.put(Image.TILE_DIRT_2, BitmapFactory.decodeResource(resources,
                R.drawable.tile_dirt_2, options));
        bitmaps.put(Image.TILE_DIRT_3, BitmapFactory.decodeResource(resources,
                R.drawable.tile_dirt_3, options));
        bitmaps.put(Image.TILE_GRASS_0, BitmapFactory.decodeResource(resources,
                R.drawable.tile_grass_0, options));
        bitmaps.put(Image.TILE_GRASS_1, BitmapFactory.decodeResource(resources,
                R.drawable.tile_grass_1, options));
        bitmaps.put(Image.TILE_GRASS_2, BitmapFactory.decodeResource(resources,
                R.drawable.tile_grass_2, options));
        bitmaps.put(Image.TILE_GRASS_3, BitmapFactory.decodeResource(resources,
                R.drawable.tile_grass_3, options));
        bitmaps.put(Image.GRASS_0, BitmapFactory.decodeResource(resources,
                R.drawable.grass_0, options));
        bitmaps.put(Image.GRASS_1, BitmapFactory.decodeResource(resources,
                R.drawable.grass_1, options));
        bitmaps.put(Image.GRASS_2, BitmapFactory.decodeResource(resources,
                R.drawable.grass_2, options));
        bitmaps.put(Image.GRASS_3, BitmapFactory.decodeResource(resources,
                R.drawable.grass_3, options));
        bitmaps.put(Image.GRASS_4, BitmapFactory.decodeResource(resources,
                R.drawable.grass_4, options));
        bitmaps.put(Image.GRASS_5, BitmapFactory.decodeResource(resources,
                R.drawable.grass_5, options));
        bitmaps.put(Image.GRASS_6, BitmapFactory.decodeResource(resources,
                R.drawable.grass_6, options));
        bitmaps.put(Image.GRASS_7, BitmapFactory.decodeResource(resources,
                R.drawable.grass_7, options));
        bitmaps.put(Image.GRASS_8, BitmapFactory.decodeResource(resources,
                R.drawable.grass_8, options));
        bitmaps.put(Image.GRASS_9, BitmapFactory.decodeResource(resources,
                R.drawable.grass_9, options));
        bitmaps.put(Image.GRASS_10, BitmapFactory.decodeResource(resources,
                R.drawable.grass_10, options));
        bitmaps.put(Image.GRASS_11, BitmapFactory.decodeResource(resources,
                R.drawable.grass_11, options));
        bitmaps.put(Image.STICK_0, BitmapFactory.decodeResource(resources,
                R.drawable.stick_0, options));
        bitmaps.put(Image.DIRT_PILE_0, BitmapFactory.decodeResource(resources,
                R.drawable.dirt_pile_0, options));
        bitmaps.put(Image.BACKGROUND_GRASS, BitmapFactory.decodeResource(resources,
                R.drawable.background_grass, options));
    }

    public static Bitmap[] getBitmapSet(Image first, Image last) {
        Bitmap[] toReturn = new Bitmap[last.ordinal() - first.ordinal() + 1];
        for (int i = 0; i < toReturn.length; i++) {
            toReturn[i] = bitmaps.get(Image.values()[first.ordinal() + i]);
        }
        return toReturn;
    }

    public static Bitmap getImage(Image image) {
        return bitmaps.get(image);
    }
}
