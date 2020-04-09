package wastedgames.proviant.objects.ui;

import wastedgames.proviant.enumerations.Image;
import wastedgames.proviant.enumerations.UnitState;
import wastedgames.proviant.maintenance.ResourcesLoader;
import wastedgames.proviant.objects.Appearance;
import wastedgames.proviant.objects.InterfaceUnit;

public class ProcessingBar extends InterfaceUnit {
    public ProcessingBar(float x, float y) {
        super(x, y);
        appearance.put(UnitState.EXIST,
                new Appearance(ResourcesLoader.getBitmapSet(Image.PROCESS_BAR_0,
                        Image.PROCESS_BAR_3), 0, 4));
        currentState = UnitState.EXIST;
        setStandardMask(Image.PROCESS_BAR_0);
    }
}
