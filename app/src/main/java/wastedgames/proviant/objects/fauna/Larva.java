package wastedgames.proviant.objects.fauna;

import wastedgames.proviant.enumerations.Image;
import wastedgames.proviant.enumerations.UnitState;
import wastedgames.proviant.layouts.GameField;
import wastedgames.proviant.maintenance.MainThread;
import wastedgames.proviant.maintenance.ResourcesLoader;
import wastedgames.proviant.maintenance.ThreadSolver;
import wastedgames.proviant.objects.Appearance;
import wastedgames.proviant.objects.MovableUnit;

public class Larva extends MovableUnit {

    private final long FRAMES_TO_DAMAGE = MainThread.MAX_FPS;
    private long TIMER;

    public Larva(int x, int y) {
        super(x, y);
        appearance.put(UnitState.EXIST,
                new Appearance(ResourcesLoader.getBitmapSet(Image.LARVA_0, Image.LARVA_5),
                        0, 4));
        currentState = UnitState.EXIST;
        setStandardMask(Image.LARVA_0);
        randomMirror();
        hp = MAX_HP = 5;
        TIMER = 0;
    }

    @Override
    public void update() {
        TIMER++;
        if (TIMER >= FRAMES_TO_DAMAGE) {
            TIMER = 0;
            damage(1);
        }
    }
}
