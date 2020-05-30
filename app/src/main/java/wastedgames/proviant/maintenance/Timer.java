package wastedgames.proviant.maintenance;

public class Timer {
    private int currentFrame;
    private int attackFrequency;

    private boolean hasAttacked;

    public Timer(int attackFrequency) {
        this.attackFrequency = attackFrequency;
        currentFrame = 0;
        hasAttacked = false;
    }

    public boolean isAttackTime() {
        hasAttacked = ThreadSolver.CURRENT_FRAME % attackFrequency == 0 && !hasAttacked;
        return hasAttacked;
    }

    public void setAttackFrequency(int attackFrequency) {
        this.attackFrequency = attackFrequency;
    }
}
