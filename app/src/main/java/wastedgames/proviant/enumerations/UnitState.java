package wastedgames.proviant.enumerations;

public enum UnitState {
    IDLE, WORK, WALK, EXIST, CRAWL;

    public enum Type {
        FLOOR, UNDERGROUND
    }

    public static Type getType(UnitState state) {
        return state.ordinal() <= EXIST.ordinal() ? Type.FLOOR : Type.UNDERGROUND;
    }
}
