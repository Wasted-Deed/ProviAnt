package wastedgames.proviant.interfaces;

import wastedgames.proviant.objects.AbstractUnit;

public interface Portable {

    void setPickUp(boolean isPicked);

    void setHolder(AbstractUnit holder);
}
