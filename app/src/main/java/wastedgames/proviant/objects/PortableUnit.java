package wastedgames.proviant.objects;

import wastedgames.proviant.interfaces.Portable;

public class PortableUnit extends MovableUnit implements Portable {
    private AbstractUnit holder;
    private boolean isPicked;

    public PortableUnit(int x, int y) {
        super(x, y);
    }

    @Override
    public void update() {
        if (isPicked) {
            this.x = holder.getX();
            this.y = holder.getY();
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
