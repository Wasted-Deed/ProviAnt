package wastedgames.proviant.objects;

import wastedgames.proviant.engine.Vector2;
import wastedgames.proviant.interfaces.Portable;

public class PortableUnit extends MovableUnit implements Portable {
    private AbstractUnit holder;
    private boolean isPicked;

    public PortableUnit(float x, float y) {
        super(x, y);
    }

    public PortableUnit(Vector2 pos) {
        super(pos);
    }

    @Override
    public void update() {
        super.update();
        if (isPicked) {
            pos.setCoordinates(holder.getPos());
        }
    }

    public boolean isPicked() {
        return isPicked;
    }

    public void setPicked(boolean picked) {
        isPicked = picked;
    }

    @Override
    public void setPickUp(boolean isPicked) {
        this.isPicked = isPicked;
    }

    @Override
    public void setHolder(AbstractUnit holder) {
        this.holder = holder;
    }
}
