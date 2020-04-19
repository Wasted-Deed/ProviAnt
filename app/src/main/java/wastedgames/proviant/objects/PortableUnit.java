package wastedgames.proviant.objects;

import wastedgames.proviant.interfaces.Portable;

public class PortableUnit extends MovableUnit implements Portable {
    private AbstractUnit holder;
    private boolean isPicked;

    public PortableUnit(float x, float y) {
        super(x, y);
    }

    @Override
    public void update() {
        super.update();
        if (isPicked) {
            pos.setCoordinates(holder.getPos());
        }
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
