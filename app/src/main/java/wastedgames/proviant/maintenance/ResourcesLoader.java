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
    public static Bitmap BASIC_FONT;

    public static void loadResources(Resources resources) {
        bitmaps = new HashMap<>();
        Options options = new Options();
        options.inScaled = false;
        bitmaps.put(Image.ANT_CRAWL_0, BitmapFactory.decodeResource(resources,
                R.drawable.ant_crawl_0, options));
        bitmaps.put(Image.ANT_IDLE_0, BitmapFactory.decodeResource(resources,
                R.drawable.ant_idle_0, options));
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
        bitmaps.put(Image.BUG_CRAWL_0, BitmapFactory.decodeResource(resources,
                R.drawable.bug_crawl_0, options));
        bitmaps.put(Image.BUG_0, BitmapFactory.decodeResource(resources,
                R.drawable.bug_idle_0, options));
        bitmaps.put(Image.BUG_1, BitmapFactory.decodeResource(resources,
                R.drawable.bug_move_0, options));
        bitmaps.put(Image.BUG_2, BitmapFactory.decodeResource(resources,
                R.drawable.bug_move_1, options));
        bitmaps.put(Image.BUG_3, BitmapFactory.decodeResource(resources,
                R.drawable.bug_move_2, options));
        bitmaps.put(Image.LADYBUG_0, BitmapFactory.decodeResource(resources,
                R.drawable.ladybug_idle_0, options));
        bitmaps.put(Image.LADYBUG_1, BitmapFactory.decodeResource(resources,
                R.drawable.ladybug_move_0, options));
        bitmaps.put(Image.LADYBUG_2, BitmapFactory.decodeResource(resources,
                R.drawable.ladybug_move_1, options));
        bitmaps.put(Image.LADYBUG_3, BitmapFactory.decodeResource(resources,
                R.drawable.ladybug_move_2, options));
        bitmaps.put(Image.MEAT_0, BitmapFactory.decodeResource(resources,
                R.drawable.meat_0, options));
        bitmaps.put(Image.MEAT_1, BitmapFactory.decodeResource(resources,
                R.drawable.meat_1, options));
        bitmaps.put(Image.TILE_DIRT_0_0, BitmapFactory.decodeResource(resources,
                R.drawable.tile_dirt_0_0, options));
        bitmaps.put(Image.TILE_DIRT_0_1, BitmapFactory.decodeResource(resources,
                R.drawable.tile_dirt_0_1, options));
        bitmaps.put(Image.TILE_DIRT_0_2, BitmapFactory.decodeResource(resources,
                R.drawable.tile_dirt_0_2, options));
        bitmaps.put(Image.TILE_DIRT_0_3, BitmapFactory.decodeResource(resources,
                R.drawable.tile_dirt_0_3, options));
        bitmaps.put(Image.TILE_DIRT_0_4, BitmapFactory.decodeResource(resources,
                R.drawable.tile_dirt_0_4, options));
        bitmaps.put(Image.TILE_DIRT_0_5, BitmapFactory.decodeResource(resources,
                R.drawable.tile_dirt_0_6, options));
        bitmaps.put(Image.TILE_DIRT_0_6, BitmapFactory.decodeResource(resources,
                R.drawable.tile_dirt_0_6, options));
        bitmaps.put(Image.TILE_DIRT_0_7, BitmapFactory.decodeResource(resources,
                R.drawable.tile_dirt_0_7, options));
        bitmaps.put(Image.TILE_DIRT_0, BitmapFactory.decodeResource(resources,
                R.drawable.tile_dirt_0, options));
        bitmaps.put(Image.TILE_DIRT_1, BitmapFactory.decodeResource(resources,
                R.drawable.tile_dirt_1, options));
        bitmaps.put(Image.TILE_DIRT_2, BitmapFactory.decodeResource(resources,
                R.drawable.tile_dirt_2, options));
        bitmaps.put(Image.TILE_DIRT_BACK, BitmapFactory.decodeResource(resources,
                R.drawable.tile_dirt_back, options));
        bitmaps.put(Image.TILE_DIRT_BACK_0, BitmapFactory.decodeResource(resources,
                R.drawable.tile_dirt_back_0, options));
        bitmaps.put(Image.TILE_DIRT_BACK_1, BitmapFactory.decodeResource(resources,
                R.drawable.tile_dirt_back_1, options));
        bitmaps.put(Image.TILE_DIRT_BACK_2, BitmapFactory.decodeResource(resources,
                R.drawable.tile_dirt_back_2, options));
        bitmaps.put(Image.TILE_DIRT_BACK_3, BitmapFactory.decodeResource(resources,
                R.drawable.tile_dirt_back_3, options));
        bitmaps.put(Image.TILE_GRASS_0, BitmapFactory.decodeResource(resources,
                R.drawable.tile_grass_0, options));
        bitmaps.put(Image.TILE_GRASS_1, BitmapFactory.decodeResource(resources,
                R.drawable.tile_grass_1, options));
        bitmaps.put(Image.TILE_GRASS_2, BitmapFactory.decodeResource(resources,
                R.drawable.tile_grass_2, options));
        bitmaps.put(Image.TILE_TOP_GRASS, BitmapFactory.decodeResource(resources,
                R.drawable.tile_top_grass, options));
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
        bitmaps.put(Image.STONE_0, BitmapFactory.decodeResource(resources,
                R.drawable.stone_0, options));
        bitmaps.put(Image.TILE_STONE_0, BitmapFactory.decodeResource(resources,
                R.drawable.tile_stone_0, options));
        bitmaps.put(Image.SUN_0, BitmapFactory.decodeResource(resources,
                R.drawable.sun_0, options));
        bitmaps.put(Image.DIRT_PILE_0, BitmapFactory.decodeResource(resources,
                R.drawable.dirt_pile_0, options));
        bitmaps.put(Image.BACKGROUND_GRASS, BitmapFactory.decodeResource(resources,
                R.drawable.background_grass, options));
        bitmaps.put(Image.CHAMOMILE_0, BitmapFactory.decodeResource(resources,
                R.drawable.chamomile_0, options));
        bitmaps.put(Image.CHAMOMILE_1, BitmapFactory.decodeResource(resources,
                R.drawable.chamomile_1, options));
        bitmaps.put(Image.CHAMOMILE_2, BitmapFactory.decodeResource(resources,
                R.drawable.chamomile_2, options));
        bitmaps.put(Image.CHAMOMILE_3, BitmapFactory.decodeResource(resources,
                R.drawable.chamomile_3, options));
        bitmaps.put(Image.CHAMOMILE_4, BitmapFactory.decodeResource(resources,
                R.drawable.chamomile_4, options));
        bitmaps.put(Image.CHAMOMILE_5, BitmapFactory.decodeResource(resources,
                R.drawable.chamomile_3, options));
        bitmaps.put(Image.CHAMOMILE_6, BitmapFactory.decodeResource(resources,
                R.drawable.chamomile_2, options));
        bitmaps.put(Image.CHAMOMILE_7, BitmapFactory.decodeResource(resources,
                R.drawable.chamomile_1, options));
        bitmaps.put(Image.CLOUD_0, BitmapFactory.decodeResource(resources,
                R.drawable.cloud_0, options));
        bitmaps.put(Image.CLOUD_1, BitmapFactory.decodeResource(resources,
                R.drawable.cloud_1, options));
        bitmaps.put(Image.CLOUD_2, BitmapFactory.decodeResource(resources,
                R.drawable.cloud_2, options));
        bitmaps.put(Image.CLOUD_3, BitmapFactory.decodeResource(resources,
                R.drawable.cloud_3, options));
        bitmaps.put(Image.ROSE_0, BitmapFactory.decodeResource(resources,
                R.drawable.rose_0, options));
        bitmaps.put(Image.ROSE_1, BitmapFactory.decodeResource(resources,
                R.drawable.rose_1, options));
        bitmaps.put(Image.ROSE_2, BitmapFactory.decodeResource(resources,
                R.drawable.rose_2, options));
        bitmaps.put(Image.ROSE_3, BitmapFactory.decodeResource(resources,
                R.drawable.rose_3, options));
        bitmaps.put(Image.ROSE_4, BitmapFactory.decodeResource(resources,
                R.drawable.rose_4, options));
        bitmaps.put(Image.ROSE_5, BitmapFactory.decodeResource(resources,
                R.drawable.rose_3, options));
        bitmaps.put(Image.ROSE_6, BitmapFactory.decodeResource(resources,
                R.drawable.rose_2, options));
        bitmaps.put(Image.ROSE_7, BitmapFactory.decodeResource(resources,
                R.drawable.rose_1, options));
        bitmaps.put(Image.PROCESS_BAR_0, BitmapFactory.decodeResource(resources,
                R.drawable.processing_0, options));
        bitmaps.put(Image.PROCESS_BAR_1, BitmapFactory.decodeResource(resources,
                R.drawable.processing_1, options));
        bitmaps.put(Image.PROCESS_BAR_2, BitmapFactory.decodeResource(resources,
                R.drawable.processing_2, options));
        bitmaps.put(Image.PROCESS_BAR_3, BitmapFactory.decodeResource(resources,
                R.drawable.processing_3, options));
        bitmaps.put(Image.LARVA_0, BitmapFactory.decodeResource(resources,
                R.drawable.larva_0, options));
        bitmaps.put(Image.LARVA_1, BitmapFactory.decodeResource(resources,
                R.drawable.larva_1, options));
        bitmaps.put(Image.LARVA_2, BitmapFactory.decodeResource(resources,
                R.drawable.larva_2, options));
        bitmaps.put(Image.LARVA_3, BitmapFactory.decodeResource(resources,
                R.drawable.larva_3, options));
        bitmaps.put(Image.LARVA_4, BitmapFactory.decodeResource(resources,
                R.drawable.larva_2, options));
        bitmaps.put(Image.LARVA_5, BitmapFactory.decodeResource(resources,
                R.drawable.larva_1, options));
        bitmaps.put(Image.DROP_FLY_0, BitmapFactory.decodeResource(resources,
                R.drawable.drop_fly_0, options));
        bitmaps.put(Image.DROP_FLY_1, BitmapFactory.decodeResource(resources,
                R.drawable.drop_fly_1, options));
        bitmaps.put(Image.DROP_FLY_2, BitmapFactory.decodeResource(resources,
                R.drawable.drop_fly_2, options));
        bitmaps.put(Image.DROP_LANDED_0, BitmapFactory.decodeResource(resources,
                R.drawable.drop_landed_0, options));
        bitmaps.put(Image.DROP_LANDED_1, BitmapFactory.decodeResource(resources,
                R.drawable.drop_landed_1, options));
        bitmaps.put(Image.DROP_LANDED_2, BitmapFactory.decodeResource(resources,
                R.drawable.drop_landed_2, options));
        bitmaps.put(Image.DROP_LANDED_3, BitmapFactory.decodeResource(resources,
                R.drawable.drop_landed_3, options));
        bitmaps.put(Image.DROP_LANDED_4, BitmapFactory.decodeResource(resources,
                R.drawable.drop_landed_3, options));
        bitmaps.put(Image.WORM_IDLE_0, BitmapFactory.decodeResource(resources,
                R.drawable.worm_idle_0, options));
        bitmaps.put(Image.WORM_MOVE_0, BitmapFactory.decodeResource(resources,
                R.drawable.worm_move_0, options));
        bitmaps.put(Image.WORM_MOVE_1, BitmapFactory.decodeResource(resources,
                R.drawable.worm_move_1, options));
        bitmaps.put(Image.WORM_MOVE_2, BitmapFactory.decodeResource(resources,
                R.drawable.worm_move_2, options));
        bitmaps.put(Image.WORM_MOVE_3, BitmapFactory.decodeResource(resources,
                R.drawable.worm_move_3, options));
        bitmaps.put(Image.WORM_MOVE_4, BitmapFactory.decodeResource(resources,
                R.drawable.worm_move_4, options));
        bitmaps.put(Image.WORM_MOVE_5, BitmapFactory.decodeResource(resources,
                R.drawable.worm_move_5, options));
        bitmaps.put(Image.NEST_0, BitmapFactory.decodeResource(resources,
                R.drawable.nest_0, options));
        bitmaps.put(Image.BAG, BitmapFactory.decodeResource(resources,
                R.drawable.bag, options));
        bitmaps.put(Image.TILE_STONE_BACK, BitmapFactory.decodeResource(resources,
                R.drawable.tile_stone_back, options));
        bitmaps.put(Image.STONE_PILE_0, BitmapFactory.decodeResource(resources,
                R.drawable.stone_pile_0, options));
        BASIC_FONT = BitmapFactory.decodeResource(resources, R.drawable.font_0, options);

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
