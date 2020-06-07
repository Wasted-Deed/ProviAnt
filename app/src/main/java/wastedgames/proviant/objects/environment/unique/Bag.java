package wastedgames.proviant.objects.environment.unique;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import wastedgames.proviant.enumerations.Image;
import wastedgames.proviant.enumerations.UnitState;
import wastedgames.proviant.maintenance.ResourcesLoader;
import wastedgames.proviant.objects.Appearance;
import wastedgames.proviant.objects.PortableUnit;

public class Bag extends PortableUnit {
    private ArrayDeque<PortableUnit> container;
    private final int CAPACITY = 3;

    public Bag(int x, int y) {
        super(x, y);
        appearance.put(UnitState.EXIST,
                new Appearance(ResourcesLoader.getImage(Image.BAG)));
        currentState = UnitState.EXIST;
        setCurrentMask();
        container = new ArrayDeque<>();
    }

    public void addUnit(PortableUnit toAdd) {
        if(container.size() >= CAPACITY) {
            container.pop();
        }
        container.add(toAdd);
    }
}
