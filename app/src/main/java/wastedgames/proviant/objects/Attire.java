package wastedgames.proviant.objects;

public class Attire {
    private int damage;
    private int pushForce;

    public Attire(int damage, int pushForce) {
        this.damage = damage;
        this.pushForce = pushForce;
    }

    public int getDamage() {
        return damage;
    }

    public int getPushForce() {
        return pushForce;
    }
}
